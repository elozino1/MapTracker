package com.olamachia.maptrackerweekeighttask.services

import com.olamachia.maptrackerweekeighttask.models.PokemonDetailsModel
import com.olamachia.maptrackerweekeighttask.models.ResultModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IPokemonService {

    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: Int, @Query("offset") offset: Int
    ) : Call<ResultModel>

    @GET("pokemon/{id}")
    fun getPokemonDetails(
        @Path("id") pokemonId: Int) : Call<PokemonDetailsModel>
}