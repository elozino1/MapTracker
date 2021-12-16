package com.olamachia.maptrackerweekeighttask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.olamachia.maptrackerweekeighttask.R
import com.olamachia.maptrackerweekeighttask.models.PokemonDetailsModel
import com.olamachia.maptrackerweekeighttask.services.PokemonDetailsDataResponse
import com.olamachia.maptrackerweekeighttask.services.RetrofitClient
import com.olamachia.maptrackerweekeighttask.utils.ItemOffsetDecoration
import com.olamachia.maptrackerweekeighttask.adapters.PokemonAbilitiesAdapter
import com.olamachia.maptrackerweekeighttask.adapters.PokemonMovesAdapter
import com.olamachia.maptrackerweekeighttask.adapters.PokemonStatsAdapter
import retrofit2.Response


class PokemonDetailsFragment(var pokemonImageId: Int) : Fragment(), PokemonDetailsDataResponse {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pokemon_details, container, false)

        RetrofitClient.getPokemonDetailsRepository().getPokemonDetails(this, pokemonImageId)

        //load image
        val pokemonImage = view.findViewById<ImageView>(R.id.pokemon_display_image)
        Glide.with(requireContext()).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${pokemonImageId}.png").into(pokemonImage)

        return view
    }

    override fun onPokemonDetailsDisplay(response: Response<PokemonDetailsModel>) {

        //set up recycler views
        val pokemonMovesRecyclerView = view?.findViewById<RecyclerView>(R.id.moves_recycler_view)
        pokemonMovesRecyclerView?.setHasFixedSize(true)
        pokemonMovesRecyclerView?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val pokemonAbilitiesRecyclerView = view?.findViewById<RecyclerView>(R.id.abilities_recycler_view)
        pokemonAbilitiesRecyclerView?.setHasFixedSize(true)
        pokemonAbilitiesRecyclerView?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val pokemonStatsRecyclerView = view?.findViewById<RecyclerView>(R.id.stats_recycler_view)
        pokemonStatsRecyclerView?.setHasFixedSize(true)
        pokemonStatsRecyclerView?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        //item decoration to set spacing between recycler views
        val itemDecoration = ItemOffsetDecoration(requireActivity(), R.dimen.spacing)
        pokemonMovesRecyclerView?.addItemDecoration(itemDecoration)
        pokemonAbilitiesRecyclerView?.addItemDecoration(itemDecoration)
        pokemonStatsRecyclerView?.addItemDecoration(itemDecoration)

        val pokemonName = view?.findViewById<TextView>(R.id.pokemon_display_name)
        pokemonName?.text = response.body()?.name


        //set adapter to recycler views
        val pokemonMovesAdapter = PokemonMovesAdapter(response.body()?.moves)
        pokemonMovesRecyclerView?.adapter = pokemonMovesAdapter

        val pokemonAbilitiesAdapter = PokemonAbilitiesAdapter(response.body()?.abilities)
        pokemonAbilitiesRecyclerView?.adapter = pokemonAbilitiesAdapter

        val pokemonStatsAdapter = PokemonStatsAdapter(response.body()?.stats)
        pokemonStatsRecyclerView?.adapter = pokemonStatsAdapter
    }

    override fun onFailure(t: Throwable) {
        Toast.makeText(requireContext(), "An error occurred. Please check internet connection!", Toast.LENGTH_LONG).show()
    }

}