package com.pedrogomez.pokeapp.models.pokelist

import kotlinx.serialization.Serializable

@Serializable
data class PokeListResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokeItem>
)