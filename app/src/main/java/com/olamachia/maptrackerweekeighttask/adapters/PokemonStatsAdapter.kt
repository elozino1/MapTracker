package com.olamachia.maptrackerweekeighttask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.olamachia.maptrackerweekeighttask.R
import com.olamachia.maptrackerweekeighttask.models.Stats

class PokemonStatsAdapter(
    private var pokemonStats: List<Stats>?
) : RecyclerView.Adapter<PokemonStatsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var pokemonAttribute: TextView = itemView.findViewById(R.id.pokemon_info)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_detail_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val pokemonStat = "${pokemonStats?.get(position)?.stat?.name}: ${pokemonStats?.get(position)?.base_stat}"
        holder.pokemonAttribute.text = pokemonStat
    }

    override fun getItemCount(): Int = pokemonStats?.size!!

}