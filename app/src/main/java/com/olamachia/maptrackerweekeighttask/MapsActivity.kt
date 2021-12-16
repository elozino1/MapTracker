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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.olamachia.maptrackerweekeighttask.databinding.ActivityMapsBinding
import com.olamachia.maptrackerweekeighttask.models.LocationModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var myMap: GoogleMap
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
    private val databaseReference: DatabaseReference = database.getReference("Partners")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap

        setCurrentLocation()
        retrieveCurrentLocation()
    }

    private fun retrieveCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(
                this, finePermission
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, coarsePermission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            databaseReference.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //retrieve partner's location
                    val peterLocation = snapshot.child("Peter").getValue(LocationModel::class.java)
                    val peterLatitude = peterLocation!!.latitude
                    val peterLongitude = peterLocation.longitude
                    val peterLatLng = LatLng(peterLatitude!!, peterLongitude!!)

                    //retrieve user's location
                    val myLocation = snapshot.child("Zino").getValue(LocationModel::class.java)
                    val myLatitude = myLocation!!.latitude
                    val myLongitude = myLocation.longitude
                    val myLatLng = LatLng(myLatitude!!, myLongitude!!)

                    //clear previously marked location and mark updated location
                    myMap.clear()
                    myMap.addMarker(
                        MarkerOptions().position(peterLatLng)
                            .title("Na Peter be this")
                    )

                    myMap.addMarker(
                        MarkerOptions().position(myLatLng)
                            .title("$myLatitude, $myLongitude")
                    )

                    //move camera on to marker set location
                    myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(peterLatLng, 16.2f))
                    myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 16.2f))
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MapsActivity, "An error occurred. Please check Internet connection", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    //get user's current location and save to database
    private fun setCurrentLocation() {

        //request permission
        if (ActivityCompat.checkSelfPermission(
                this, finePermission
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, coarsePermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(finePermission, coarsePermission), requestCode)
            return
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINTIME, MINDIST) { location ->
            latLng = LatLng(location.latitude, location.longitude)

            databaseReference.child("Zino").setValue(latLng)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            requestCode -> retrieveCurrentLocation()

        else -> Snackbar.make(findViewById(R.id.map),
            "Location permission needed for core functionality",
            Snackbar.LENGTH_LONG).setAction("OK"){
            ActivityCompat.requestPermissions(
                this@MapsActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                requestCode
            )
        }.show()
            }
        }

}