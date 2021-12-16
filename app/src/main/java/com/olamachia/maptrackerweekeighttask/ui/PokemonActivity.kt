package com.olamachia.maptrackerweekeighttask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.olamachia.maptrackerweekeighttask.R

class PokemonActivity : AppCompatActivity() {

    private val pokemonDisplayFragment = PokemonDisplayFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, pokemonDisplayFragment).commit()
    }
}