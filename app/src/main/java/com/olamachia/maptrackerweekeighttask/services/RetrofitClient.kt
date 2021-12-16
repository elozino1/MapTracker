package com.olamachia.maptrackerweekeighttask.services

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient : Application() {

    companion object {
        private fun getRetrofitClientInstance() : IPokemonService {
            val retrofit = Retrofit.Builder().
            baseUrl("https://pokeapi.co/api/v2/").
            addConverterFactory(GsonConverterFactory.create()).
            build()

            return retrofit.create(IPokemonService::class.java)
        }

        fun getPokemonListRepository() : PokemonListRepository {
            return PokemonListRepository(getRetrofitClientInstance())
        }

        fun getPokemonDetailsRepository(): PokemonDetailsRepository{
            return PokemonDetailsRepository(getRetrofitClientInstance())
        }

    }
}