package com.pedrogomez.pokeapp.pokedex.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.pedrogomez.pokeapp.core.ui.common.LoadingIndicator
import com.pedrogomez.pokeapp.pokedex.models.PokemonData
import com.pedrogomez.pokeapp.pokedex.models.result.PokeApiResult
import org.koin.androidx.compose.koinViewModel
import coil3.compose.AsyncImage
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.cmodernel004.pokemontrainer.R
import java.util.Locale
import java.util.UUID

@Composable
fun PokedexEntryScreen(
    modifier: Modifier = Modifier,
    onPokemonClick: (PokemonData) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: PokeListViewModel = koinViewModel()
) {
    val pokemonList by viewModel.observePokemonData().observeAsState(emptyList())
    val apiState by viewModel.observeApiState()
        .observeAsState(PokeApiResult.LoadingNewContent(false))

    Surface(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            PokemonList(
                pokemonList = pokemonList,
                apiState = apiState,
                onPokemonClick = onPokemonClick,
                fetchPokemonData = { viewModel.loadInitialPokemonList() },
                fetchNextPage = { viewModel.loadNextPage() },
                onBackPressed = onBackPressed,
            )
            LoadingOverlay(
                isVisible = (apiState as? PokeApiResult.LoadingMoreContent)?.status ?: false,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonList(
    pokemonList: List<PokemonData>,
    apiState: PokeApiResult,
    onPokemonClick: (PokemonData) -> Unit,
    fetchPokemonData: () -> Unit,
    fetchNextPage: () -> Unit,
    onBackPressed: () -> Unit
) {
    val listState = rememberLazyGridState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.pokedex_text)) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
    ) { padding ->
        if (apiState is PokeApiResult.LoadingNewContent && pokemonList.isEmpty()) {
            LoadingIndicator(enabled = true)
            LaunchedEffect(Unit) { fetchPokemonData() }
        } else {
            LoadingIndicator(enabled = false)
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(padding),
                state = listState,
            ) {
                items(pokemonList) { pokemon ->
                    PokemonItem(pokemon = pokemon) { onPokemonClick(pokemon) }
                }
                if (apiState is PokeApiResult.LoadingNewContent) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }

            if (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == pokemonList.size - 1) {
                LaunchedEffect(Unit) { fetchNextPage() }
            }
        }
    }
}

@Composable
fun LoadingOverlay(isVisible: Boolean) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.Gray)
        }
    }
}

@Composable
fun PokemonItem(pokemon: PokemonData, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(modifier = Modifier.border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)) )
            {
            val imageApi = stringResource(R.string.image_api) + pokemon.name + ".jpg"
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageApi)
                    .crossfade(true)
                    .listener(
                        onStart = { Log.d("Coil", "Image loading started") },
                        onSuccess = { _, _ -> Log.d("Coil", "Image loaded successfully") },
                        onError = { _, throwable ->
                            Log.e(
                                "Coil",
                                "Image loading failed",
                                throwable.throwable
                            )
                        },
                    )
                    .build(),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f)
                    .clip(MaterialTheme.shapes.small)
            )

            Text(
                text = pokemon.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                modifier = Modifier
                    .padding(8.dp)

                    .align(Alignment.BottomCenter)
            )
        }
    }
}