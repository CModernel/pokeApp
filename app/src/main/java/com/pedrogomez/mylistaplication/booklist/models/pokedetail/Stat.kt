package com.pedrogomez.mylistaplication.booklist.models.pokedetail

import kotlinx.serialization.Serializable

@Serializable
data class Stat(
    val base_stat: Int? = null,
    val effort: Int? = null,
    val stat: StatX? = null
)