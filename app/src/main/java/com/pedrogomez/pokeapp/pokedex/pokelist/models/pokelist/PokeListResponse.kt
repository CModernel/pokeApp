package com.pedrogomez.pokeapp.pokedex.pokelist.models.pokelist

import kotlinx.serialization.Serializable


@Serializable
data class PokeListResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<com.pedrogomez.pokeapp.pokedex.pokelist.models.pokelist.PokeItem>
)

