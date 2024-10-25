package com.pedrogomez.pokeapp.pokedex.pokelist.models.pokelist

import kotlinx.serialization.Serializable

@Serializable
data class PokeItem(
    val name: String,
    val url: String = "",
    val abilities: List<com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail.Ability>? = null,
    val base_experience: Int? = null,
    val height: Int? = null,
    val id: Int? = null,
    val is_default: Boolean? = null,
    val order: Int? = null,
    val sprites: com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail.Sprites? = null,
    val stats: List<com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail.Stat>? = null,
    val types: List<com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail.Type>? = null,
    val weight: Int? = null
)