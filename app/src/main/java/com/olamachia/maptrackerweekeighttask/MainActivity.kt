package com.olamachia.maptrackerweekeighttask

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.olamachia.maptrackerweekeighttask.ui.PokemonActivity

class MainActivity : AppCompatActivity() {

    lateinit var trackButton: Button
    lateinit var pokemonButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trackButton = findViewById(R.id.btn_track_partner)
        trackButton.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        pokemonButton = findViewById(R.id.btn_pokemon)
        pokemonButton.setOnClickListener {
            startActivity(Intent(this, PokemonActivity::class.java))
        }
    }
}
