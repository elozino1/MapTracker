package com.olamachia.maptrackerweekeighttask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.olamachia.maptrackerweekeighttask.R
import com.olamachia.maptrackerweekeighttask.models.Ability

class PokemonAbilitiesAdapter(
    var pokemonAbilities: List<Ability>?
) : RecyclerView.Adapter<PokemonAbilitiesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var pokemonAttribute = itemView.findViewById<TextView>(R.id.pokemon_info)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_detail_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.pokemonAttribute.text = pokemonAbilities?.get(position)?.ability?.name
    }

    override fun getItemCount(): Int = pokemonAbilities?.size!!

}