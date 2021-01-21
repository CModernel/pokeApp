package com.pedrogomez.pokeapp.pokelist.models

import java.io.Serializable

data class PokemonData(
    val name:String,
    val height: Int,
    val backDefaultImg: String,
    val frontDefaultImg: String,
    val attack : Int? = null,
    val defense : Int? = null,
    val specialAttack : Int? = null,
    val specialDefense : Int? = null,
    val speed : Int? = null,
    val type : PokeType? = null,
): Serializable