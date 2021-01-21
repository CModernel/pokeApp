package com.pedrogomez.mylistaplication.booklist.models.pokelist

import com.pedrogomez.mylistaplication.booklist.models.pokedetail.PokeDetailResponse
import kotlinx.serialization.Serializable

@Serializable
data class PokeItem(
    val name: String,
    val url: String,
    var pokeDetail:PokeDetailResponse? = null
)