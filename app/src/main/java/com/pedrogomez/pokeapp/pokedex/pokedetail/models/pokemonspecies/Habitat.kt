package com.pedrogomez.pokeapp.pokedex.pokedetail.models.pokemonspecies

import kotlinx.serialization.Serializable

@Serializable
data class Habitat(
    val name: String,
    val url: String
)