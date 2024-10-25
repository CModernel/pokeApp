package com.pedrogomez.pokeapp.core.di

import com.cmodernel004.pokemontrainer.R
import com.pedrogomez.pokeapp.pokedex.models.dataadapters.PokemonDataAdapter
import com.pedrogomez.pokeapp.pokedex.presentation.PokeListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.json
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { provideOkHttpKtorClient() }
    single {
        com.pedrogomez.pokeapp.pokedex.pokelist.repository.PokemonsRepository(
            client = get(),
            urlBase = androidApplication().getString(R.string.url_api)
        )
    }
    single {
        PokemonDataAdapter()
    }

    viewModel {
        PokeListViewModel(
            pokemonsRepository = get(),
            pokeDataAdapter = get()
        )

    }
}


fun provideOkHttpKtorClient(): HttpClient {
    return HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
}



