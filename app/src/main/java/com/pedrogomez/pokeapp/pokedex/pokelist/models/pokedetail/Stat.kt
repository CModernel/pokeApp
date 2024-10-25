package com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail

import kotlinx.serialization.Serializable

@Serializable
data class Stat(
    val base_stat: Int? = null,
    val effort: Int? = null,
    val stat: com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail.StatX? = null
)