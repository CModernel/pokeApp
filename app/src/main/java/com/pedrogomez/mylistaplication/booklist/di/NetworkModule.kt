package com.pedrogomez.mylistaplication.booklist.di

import android.app.Application
import com.pedrogomez.mylistaplication.R
import com.pedrogomez.mylistaplication.booklist.repository.BooksRepository
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val networkModule = module {
    single { okHttpKtor }
    single {

        BooksRepository(
            get(),
            androidApplication().getString(R.string.url_api)
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