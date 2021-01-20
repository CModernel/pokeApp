package com.pedrogomez.mylistaplication

import android.app.Application
import com.pedrogomez.mylistaplication.booklist.di.networkModule
import com.pedrogomez.mylistaplication.booklist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BooksListAplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(
                this@BooksListAplication
            )
            androidLogger()
            modules(
                listOf(
                    viewModelModule,
                    networkModule
                )
            )
        }
    }

}