package com.pedrogomez.pokeapp.pokedex.pokedetail.models.pokemonspecies

import kotlinx.serialization.Serializable

@Serializable
data class EggGroup(
    val name: String,
    val url: String
)