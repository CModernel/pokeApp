package com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail

import kotlinx.serialization.Serializable

@Serializable
data class Type(
    val slot: Int? = null,
    val type: com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail.TypeX? = null
)