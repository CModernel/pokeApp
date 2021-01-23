package com.pedrogomez.pokeapp.models.pokedetail

import kotlinx.serialization.Serializable

@Serializable
data class Type(
    val slot: Int? = null,
    val type: TypeX? = null
)