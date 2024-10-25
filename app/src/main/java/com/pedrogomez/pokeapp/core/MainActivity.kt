package com.pedrogomez.pokeapp.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.pedrogomez.pokeapp.core.di.networkModule
import com.pedrogomez.pokeapp.core.navigation.AppNavHost
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(applicationContext)
            modules(networkModule)
        }
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()) {
                    AppNavHost()
                }
            }
        }
    }
}

