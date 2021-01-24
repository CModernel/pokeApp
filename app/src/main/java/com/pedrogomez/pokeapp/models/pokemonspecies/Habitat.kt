package com.pedrogomez.pokeapp.models.pokemonspecies

import kotlinx.serialization.Serializable

@Serializable
data class Habitat(
    val name: String,
    val url: String
)