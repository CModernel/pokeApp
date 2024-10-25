package com.pedrogomez.pokeapp.pokedex.models

import java.util.Locale

enum class PokeType(val type:String) {
    NORMAL("NORMAL"),
    FIRE("FIRE"),
    WATER("WATER"),
    ELECTRIC("ELECTRIC"),
    GRASS("GRASS"),
    ICE("ICE"),
    FIGHTING("FIGHTING"),
    POISON("POISON"),
    GROUND("GROUND"),
    FLYING("FLYING"),
    PHYCHIC("PHYCHIC"),
    BUG("BUG"),
    ROCK("ROCK"),
    GHOST("GHOST"),
    DRAGON("DRAGON"),
    DARK("DARK"),
    STEEL("STEEL"),
    FAIRY("FAIRY");

    companion object {
        fun getAsPokeType(value: String): PokeType? = PokeType.values().find { it.type.lowercase(
            Locale.getDefault()
        ) == value }
    }
}

