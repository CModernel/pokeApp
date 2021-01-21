package com.pedrogomez.pokeapp.pokelist.models.pokelist

import com.pedrogomez.pokeapp.pokelist.models.pokedetail.PokeDetailResponse
import kotlinx.serialization.Serializable

@Serializable
data class PokeItem(
    val name: String,
    val url: String,
    var pokeDetail:PokeDetailResponse? = null
)