package com.pedrogomez.mylistaplication.booklist.models.pokedetail

import kotlinx.serialization.Serializable

@Serializable
data class PokeDetailResponse(
    val abilities: List<Ability>? = null,
    val base_experience: Int? = null,
    val height: Int? = null,
    val id: Int? = null,
    val is_default: Boolean? = null,
    val name: String? = null,
    val order: Int? = null,
    val sprites: Sprites? = null,
    val stats: List<Stat>? = null,
    val types: List<Type>? = null,
    val weight: Int? = null
)