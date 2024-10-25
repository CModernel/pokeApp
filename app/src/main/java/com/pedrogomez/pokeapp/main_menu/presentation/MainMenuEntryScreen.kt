package com.pedrogomez.pokeapp.main_menu.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmodernel004.pokemontrainer.R
import com.pedrogomez.pokeapp.core.navigation.ScreenRoute
import com.pedrogomez.pokeapp.main_menu.presentation.components.ListRow
import com.pedrogomez.pokeapp.main_menu.presentation.components.ListRowIcon


// Fragment1.kt
@Composable
fun MainMenuEntryScreen(
    modifier : Modifier = Modifier,
    onNavigateTo: (String) -> Unit,
) {
    MainMenuScreen(
        modifier = modifier,
        onNavigateTo = onNavigateTo,
    )
}

@Composable
fun MainMenuScreen(
    modifier : Modifier = Modifier,
    onNavigateTo: (String) -> Unit,
){
    Surface(modifier = modifier
        .fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(Color.LightGray),
        ) {
            FullMenu(onNavigateTo = onNavigateTo)
        }
    }
}

@Composable
fun FullMenu(onNavigateTo: (String) -> Unit){
    MainMenu(
        onNavigateTo = onNavigateTo,
    )
}

@Composable
fun MainMenu(
    modifier: Modifier = Modifier,
    onNavigateTo: (String) -> Unit
){
    Column(
        modifier = modifier
            .padding(20.dp)
    ) {
        ListRow(
            iconContent = {
                ListRowIcon(icon = R.drawable.ic_pokedex_icon)
            },
            title = stringResource(R.string.pokedex_text),
            onClick = { onNavigateTo(ScreenRoute.Pokedex.route) },
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(12.dp)),
        )
        Spacer(modifier = Modifier.padding(4.dp))
        ListRow(
            iconContent = {
                ListRowIcon(icon = R.drawable.baseline_exit_to_app_24)
            },
            title = stringResource(R.string.quit_text),
            onClick = { onNavigateTo(ScreenRoute.CloseApp.route) },
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(12.dp)),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMainMenuFragment(){
    MainMenuScreen(
        modifier = Modifier,
        onNavigateTo = {},
    )
}