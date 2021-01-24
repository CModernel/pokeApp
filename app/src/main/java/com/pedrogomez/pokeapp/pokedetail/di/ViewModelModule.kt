package com.pedrogomez.pokeapp.pokedetail.di

import com.pedrogomez.pokeapp.pokedetail.viewmodel.PokeDetailsViewModel
import com.pedrogomez.pokeapp.pokelist.viewmodel.PokeListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelDetailModule = module {
    viewModel { PokeDetailsViewModel(get()) }
}