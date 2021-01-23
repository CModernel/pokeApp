package com.pedrogomez.pokeapp.models

import java.io.Serializable

data class PokemonData(
    val id:Int = 0,
    val name:String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val backDefaultImg: String = "",
    val frontDefaultImg: String = "",
    val hp: Int? = null,
    val attack : Int? = null,
    val defense : Int? = null,
    val specialAttack : Int? = null,
    val specialDefense : Int? = null,
    val speed : Int? = null,
    val type : List<PokeType>? = null,
): Serializable