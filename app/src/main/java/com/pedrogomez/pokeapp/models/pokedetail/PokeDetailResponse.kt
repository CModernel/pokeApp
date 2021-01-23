package com.pedrogomez.pokeapp.models.pokedetail

import kotlinx.serialization.Serializable

@Serializable
data class PokeDetailResponse(
    val abilities: List<Ability>? = null,
    val base_experience: Int? = null,
    val height: Int? = null,
    val id: Int? = null,
    val is_default: Boolean? = null,
    val name: String,
    val order: Int? = null,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int? = null
)