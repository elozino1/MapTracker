package com.olamachia.maptrackerweekeighttask.services

import com.olamachia.maptrackerweekeighttask.models.ResultModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonListRepository(private var iPokemonService: IPokemonService) {

    fun getPokemonList(dataResponse: DataResponse) {
        dataResponse.onLoading()
        iPokemonService.getPokemonList(100,0).enqueue(object: Callback<ResultModel>{
            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                dataResponse.onPokemonListDisplay(response)
            }

            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                dataResponse.onFailure(t)
            }

            fun onLoading() {

            }
        })
    }

}

interface DataResponse {

    fun onPokemonListDisplay(response: Response<ResultModel>)
    fun onFailure(t: Throwable)
    fun onLoading()

}