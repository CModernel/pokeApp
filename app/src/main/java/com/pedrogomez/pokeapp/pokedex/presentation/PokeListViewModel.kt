package com.pedrogomez.pokeapp.pokedex.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedrogomez.pokeapp.core.extensions.print
import com.pedrogomez.pokeapp.pokedex.models.PokemonData
import com.pedrogomez.pokeapp.pokedex.models.dataadapters.PokemonDataAdapter
import com.pedrogomez.pokeapp.pokedex.models.result.PokeApiResult
import com.pedrogomez.pokeapp.pokedex.pokelist.repository.PokemonsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class PokeListViewModel(
    private val pokemonsRepository: PokemonsRepository,
    private val pokeDataAdapter : PokemonDataAdapter
) : ViewModel(){
    val scope : CoroutineScope = CoroutineScope(
        Dispatchers.IO
    )

    private val pokemonListLiveData = MutableLiveData<MutableList<PokemonData>>()

    private val findedPokemonLiveData = MutableLiveData<PokemonData?>()

    private val pokeListStateApi = MutableLiveData<PokeApiResult>()

    private val pokeFindedStateApi = MutableLiveData<PokeApiResult>()

    private val pokemonList = ArrayList<PokemonData>()

    init {
        pokemonListLiveData.postValue(pokemonList)
    }

    fun observeApiState() : MutableLiveData<PokeApiResult> {
        return pokeListStateApi
    }

    fun observeFindedApiState() : MutableLiveData<PokeApiResult> {
        return pokeFindedStateApi
    }

    fun observePokemonData() : MutableLiveData<MutableList<PokemonData>> {
        return pokemonListLiveData
    }

    fun observeFindedPokemon() : MutableLiveData<PokemonData?> {
        return findedPokemonLiveData
    }

    fun findPokemon(name:String){
        if(name.isNotEmpty()){
            pokeFindedStateApi.postValue(
                PokeApiResult.LoadingMoreContent(true)
            )
            scope.launch {
                val findedPokemon = pokemonsRepository.getPokeDetailsByName(
                        name
                )
                pokeFindedStateApi.postValue(
                        if(findedPokemon!=null){
                            PokeApiResult.Success(true)
                        }else{
                            PokeApiResult.Error.RecoverableError("Check your internet connexion")
                        }
                )
                findedPokemonLiveData.postValue(
                        findedPokemon?.let {
                            pokeDataAdapter.getAsPokemonData(it)
                        }
                )
            }
        }
    }

    fun getListOfPokemons(page: Int) {
        pokemonList.clear()
        pokeListStateApi.postValue(PokeApiResult.LoadingNewContent(true))
        getPokeListByPage(page)
    }

    fun loadMorePokemonsToList(
            page:Int
    ){
        pokeListStateApi.postValue(
            PokeApiResult.LoadingMoreContent(true)
        )
        getPokeListByPage(page)
    }

    private val accumulatedPokemonData = mutableListOf<PokemonData>()

    private var currentPage = 0
    private var isLoading = false

    fun loadInitialPokemonList() {
        currentPage = 0
        loadNextPage(reset = true)
    }

    fun loadNextPage(reset: Boolean = false) {
        if (isLoading) return
        isLoading = true

        pokeListStateApi.postValue(PokeApiResult.LoadingMoreContent(true))

        viewModelScope.launch {
            try {
                if (reset) currentPage = 0
                val pokemonsList = pokemonsRepository.getPokeList(currentPage)

                if (pokemonsList.isNotEmpty()) {
                    val currentList = pokemonListLiveData.value.orEmpty().toMutableList()
                    val newItems = pokeDataAdapter.getAsPokemonDataList(pokemonsList)
                    if (reset) {
                        currentList.clear()
                    }
                    currentList.addAll(newItems)
                    pokemonListLiveData.postValue(currentList)

                    pokeListStateApi.postValue(PokeApiResult.Success(finished = true))
                    currentPage++
                } else {
                    pokeListStateApi.postValue(PokeApiResult.Error.RecoverableError("No more data available"))
                }
            } catch (e: Exception) {
                pokeListStateApi.postValue(PokeApiResult.Error.RecoverableError("Check your internet connection"))
            } finally {
                isLoading = false
                pokeListStateApi.postValue(PokeApiResult.LoadingMoreContent(false))
            }
        }
    }

    fun getPokeListByPage(page: Int) {
        scope.launch {
            val pokemonsList = pokemonsRepository.getPokeList(page)

            val newPokemonDataList = pokeDataAdapter.getAsPokemonDataList(pokemonsList)

            accumulatedPokemonData.addAll(newPokemonDataList)

            pokemonListLiveData.postValue(accumulatedPokemonData.toList().toMutableList())

            pokeListStateApi.postValue(
                if (newPokemonDataList.isNotEmpty()) {
                    PokeApiResult.Success(true)
                } else {
                    PokeApiResult.Error.RecoverableError("Check your internet connection")
                }
            )

            val fullPokeDataList = pokemonsList.map {
                pokemonsRepository.getPokeDetailsByUrl(it.url)
            }
            "Lista de pokemons n : ${fullPokeDataList.size} = $fullPokeDataList".print()

            fullPokeDataList.let {
                accumulatedPokemonData.addAll(
                    pokeDataAdapter.getAsPokemonDataList(it)
                )
                pokemonListLiveData.postValue(accumulatedPokemonData.toMutableList())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }

}