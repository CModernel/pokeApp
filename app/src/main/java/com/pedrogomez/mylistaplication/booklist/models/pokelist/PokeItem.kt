package com.pedrogomez.mylistaplication.booklist.models.pokelist

import kotlinx.serialization.Serializable

@Serializable
data class PokeItem(
    val name: String,
    val url: String
)