package com.pedrogomez.pokeapp

import android.app.Application
import com.pedrogomez.pokeapp.pokedetail.di.viewModelDetailModule
import com.pedrogomez.pokeapp.pokelist.di.networkModule
import com.pedrogomez.pokeapp.pokelist.di.pokeDataAdapterModule
import com.pedrogomez.pokeapp.pokelist.di.viewModelListModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokeListAplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(
                this@PokeListAplication
            )
            androidLogger()
            modules(
                listOf(
                    viewModelListModule,
                    viewModelDetailModule,
                    pokeDataAdapterModule,
                    networkModule
                )
            )
        }
    }

}