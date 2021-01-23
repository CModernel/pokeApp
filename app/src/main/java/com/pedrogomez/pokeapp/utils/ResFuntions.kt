package com.pedrogomez.pokeapp.utils

import com.pedrogomez.pokeapp.R
import com.pedrogomez.pokeapp.models.PokeType

fun getDrawableResByType(type: PokeType?):Int {
    return when (type) {
        PokeType.NORMAL -> R.drawable.type_normal
        PokeType.FIRE -> R.drawable.type_fire
        PokeType.WATER -> R.drawable.type_water
        PokeType.GRASS -> R.drawable.type_grass
        PokeType.ELECTRIC -> R.drawable.type_electric
        PokeType.ICE -> R.drawable.type_ice
        PokeType.FIGHTING -> R.drawable.type_fighting
        PokeType.POISON -> R.drawable.type_poison
        PokeType.GROUND -> R.drawable.type_ground
        PokeType.FLYING -> R.drawable.type_flying
        PokeType.PHYCHIC -> R.drawable.type_phychic
        PokeType.BUG -> R.drawable.type_bug
        PokeType.ROCK -> R.drawable.type_rock
        PokeType.GHOST -> R.drawable.type_ghost
        PokeType.DRAGON -> R.drawable.type_dragon
        PokeType.DARK -> R.drawable.type_dark
        PokeType.STEEL -> R.drawable.type_steel
        PokeType.FAIRY -> R.drawable.type_fairy
        else -> R.drawable.type_normal
    }
}