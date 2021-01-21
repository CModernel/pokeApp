package com.pedrogomez.pokeapp.pokelist.di

import com.pedrogomez.pokeapp.pokelist.models.adapters.PokemonDataAdapter
import org.koin.dsl.module

val pokeDataAdapterModule = module {
    single {
        PokemonDataAdapter()
    }
}