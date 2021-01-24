package com.pedrogomez.pokeapp.pokedetail.models.pokemonspecies

import kotlinx.serialization.Serializable

@Serializable
data class GrowthRate(
    val name: String,
    val url: String
)