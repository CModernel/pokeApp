package com.pedrogomez.pokeapp.pokedex.pokelist.di

import com.pedrogomez.pokeapp.pokedex.presentation.PokeListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelListModule = module {
    viewModel {
        PokeListViewModel(
            get(),
            get()
        )
    }
}