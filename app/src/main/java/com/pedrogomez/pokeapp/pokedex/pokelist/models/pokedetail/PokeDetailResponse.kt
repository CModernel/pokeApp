package com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail

import kotlinx.serialization.Serializable

@Serializable
data class PokeDetailResponse(
    val abilities: List<com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail.Ability>? = null,
    val base_experience: Int? = null,
    val height: Int? = null,
    val id: Int? = null,
    val is_default: Boolean? = null,
    val name: String,
    val order: Int? = null,
    val sprites: com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail.Sprites,
    val stats: List<com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail.Stat>,
    val types: List<com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail.Type>,
    val weight: Int? = null
)