package com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail

import kotlinx.serialization.Serializable

@Serializable
data class Ability(
    val ability: com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail.AbilityX? = null,
    val is_hidden: Boolean? = null,
    val slot: Int? = null
)