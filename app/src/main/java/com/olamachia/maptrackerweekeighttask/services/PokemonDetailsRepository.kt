package com.olamachia.maptrackerweekeighttask.services

import com.olamachia.maptrackerweekeighttask.models.PokemonDetailsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailsRepository(private var iPokemonService: IPokemonService) {

    //get individual pokemon details
    fun getPokemonDetails(pokemonDataResponse: PokemonDetailsDataResponse, theId: Int) {
        iPokemonService.getPokemonDetails(theId).enqueue(object: Callback<PokemonDetailsModel>{
            override fun onResponse(call: Call<PokemonDetailsModel>, response: Response<PokemonDetailsModel>) {
                pokemonDataResponse.onPokemonDetailsDisplay(response)
            }

            override fun onFailure(call: Call<PokemonDetailsModel>, t: Throwable) {
                pokemonDataResponse.onFailure(t)
            }
        })
    }
}

interface PokemonDetailsDataResponse {

    fun onPokemonDetailsDisplay(response: Response<PokemonDetailsModel>)
    fun onFailure(t: Throwable)
}