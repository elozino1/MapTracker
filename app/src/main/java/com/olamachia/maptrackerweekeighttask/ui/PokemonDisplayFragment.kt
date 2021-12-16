package com.olamachia.maptrackerweekeighttask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.olamachia.maptrackerweekeighttask.R
import com.olamachia.maptrackerweekeighttask.adapters.PokemonListAdapter
import com.olamachia.maptrackerweekeighttask.services.DataResponse
import com.olamachia.maptrackerweekeighttask.services.RetrofitClient
import com.olamachia.maptrackerweekeighttask.models.ResultModel
import com.olamachia.maptrackerweekeighttask.utils.ItemOffsetDecoration
import retrofit2.Response

class PokemonDisplayFragment : Fragment(), DataResponse {

    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pokemon_display, container, false)

        progressBar = view.findViewById(R.id.progress_bar)

        RetrofitClient.getPokemonListRepository().getPokemonList(this)

        return view
    }

    override fun onPokemonListDisplay(response: Response<ResultModel>) {

        progressBar.visibility = View.GONE

        val pokemonRecyclerView = view?.findViewById<RecyclerView>(R.id.pokemon_recyclerview)
        pokemonRecyclerView?.setHasFixedSize(true)
        pokemonRecyclerView?.layoutManager = GridLayoutManager(activity, 2)

        val itemDecoration = ItemOffsetDecoration(requireActivity(), R.dimen.spacing)
        pokemonRecyclerView?.addItemDecoration(itemDecoration)

        val pokemonListAdapter = PokemonListAdapter(response.body()?.results)
        pokemonRecyclerView?.adapter = pokemonListAdapter
    }

    override fun onFailure(t: Throwable) {
        progressBar.visibility = View.VISIBLE
        Toast.makeText(requireContext(), "${ t.message.toString() }. Check Internet connection", Toast.LENGTH_LONG).show()
    }

    override fun onLoading() {
        progressBar.visibility = View.VISIBLE
    }
}