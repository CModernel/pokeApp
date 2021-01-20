package com.pedrogomez.mylistaplication.booklist.models.pokedetail

import kotlinx.serialization.Serializable

@Serializable
data class Ability(
    val ability: AbilityX? = null,
    val is_hidden: Boolean? = null,
    val slot: Int? = null
)