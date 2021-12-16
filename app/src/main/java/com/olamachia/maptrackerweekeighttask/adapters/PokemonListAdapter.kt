package com.olamachia.maptrackerweekeighttask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.olamachia.maptrackerweekeighttask.R
import com.olamachia.maptrackerweekeighttask.models.Result
import com.olamachia.maptrackerweekeighttask.ui.PokemonDetailsFragment

class PokemonListAdapter(
    var pokemonList: List<Result>?
) : RecyclerView.Adapter<PokemonListAdapter.PokemonListAdapterViewHolder>() {

    inner class PokemonListAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var pokemonListImage = itemView.findViewById<ImageView>(R.id.pokemon_list_image)
        var pokemonListName = itemView.findViewById<TextView>(R.id.pokemon_list_text)
        var pokemonItem = itemView.findViewById<RelativeLayout>(R.id.pokemon_list_container)

        fun bindData() = with(itemView) {
            val id = adapterPosition + 1

            val pokemonName = pokemonList?.get(adapterPosition)
            pokemonListName.text = pokemonName?.name.toString()

            pokemonItem.setOnClickListener {
                val appCompatActivity = it.context as AppCompatActivity
                appCompatActivity.supportFragmentManager.beginTransaction().
                        replace(R.id.frame_layout, PokemonDetailsFragment(id)).
                        addToBackStack(null).
                        commit()
            }

            Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${id}.png").into(pokemonListImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListAdapterViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list_item, parent, false)
        return PokemonListAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonListAdapterViewHolder, position: Int) {

        holder.bindData()

    }

    override fun getItemCount(): Int = pokemonList?.size!!
}