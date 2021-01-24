package com.pedrogomez.pokeapp.di

import com.pedrogomez.pokeapp.R
import com.pedrogomez.pokeapp.pokelist.repository.PokemonsRepository
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val networkModule = module {
    single { okHttpKtor }
    single {
        PokemonsRepository(
            get(),
            androidApplication().getString(R.string.url_api),
            get()
        )
    }
}

private val okHttpKtor = HttpClient(CIO) {
    install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}