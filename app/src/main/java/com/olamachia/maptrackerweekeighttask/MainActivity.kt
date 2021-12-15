package com.olamachia.maptrackerweekeighttask

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var trackButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trackButton = findViewById(R.id.btn_track_partner)
        trackButton.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }
}
