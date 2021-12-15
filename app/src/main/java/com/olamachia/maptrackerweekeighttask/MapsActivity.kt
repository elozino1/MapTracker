package com.olamachia.maptrackerweekeighttask

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.olamachia.maptrackerweekeighttask.databinding.ActivityMapsBinding
import com.olamachia.maptrackerweekeighttask.models.LocationModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private var requestCode: Int = 100
    private var coarsePermission = Manifest.permission.ACCESS_COARSE_LOCATION
    private var finePermission = Manifest.permission.ACCESS_FINE_LOCATION

    //minimum refresh time and distance
    private val MINTIME: Long = 1000
    private val MINDIST: Float = 2f

    //location coordinates
    private lateinit var latLng: LatLng

    //location manager
    private lateinit var locationManager: LocationManager

    //database references
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference: DatabaseReference = database.getReference("Partners")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        var locationModel: LocationModel
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        retrieveCurrentLocation()
        retrievePartnerLocation()

    }

    private fun retrievePartnerLocation() {

        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val peterLocation = snapshot.child("Peter").getValue(LocationModel::class.java)
                val peterLatitude = peterLocation!!.latitude
                val peterLongitude = peterLocation.longitude
                val peterLatLng = LatLng(peterLatitude!!, peterLongitude!!)

                mMap.addMarker(
                    MarkerOptions().position(peterLatLng)
                        .title("Na Peter be this")
                )

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(peterLatLng, 16.2f))
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MapsActivity, "An error occurred. Please check Internet connection", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun retrieveCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(
                this, finePermission
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, coarsePermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINTIME, MINDIST) { location ->
            latLng = LatLng(location.latitude, location.longitude)
            mMap.clear()
            mMap.addMarker(
                MarkerOptions().position(latLng)
                    .title("${location.latitude}, ${location.longitude}")
            )

            databaseReference.child("Zino").setValue(latLng)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.2f))
        }
    }
}