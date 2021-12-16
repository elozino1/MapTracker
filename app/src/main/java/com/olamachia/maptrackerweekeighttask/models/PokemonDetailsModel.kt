package com.olamachia.maptrackerweekeighttask.models

data class PokemonDetailsModel(
    val abilities: List<Ability>,
    val height: Int,
    val id: Int,
    val moves: List<Move>,
    val name: String,
    val sprites: Sprites,
    val weight: Int,
    val stats: List<Stats>
)