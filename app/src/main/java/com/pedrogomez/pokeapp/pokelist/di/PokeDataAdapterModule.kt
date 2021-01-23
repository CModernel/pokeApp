package com.pedrogomez.pokeapp.pokelist.di

import com.pedrogomez.pokeapp.models.dataadapters.PokemonDataAdapter
import org.koin.dsl.module

val pokeDataAdapterModule = module {
    single {
        PokemonDataAdapter()
    }
}