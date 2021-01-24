package com.pedrogomez.pokeapp.pokelist.di

import com.pedrogomez.pokeapp.pokelist.viewmodel.PokeListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelListModule = module {
    viewModel { PokeListViewModel(get()) }
}