package com.pedrogomez.pokeapp.pokelist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedrogomez.pokeapp.models.PokemonData
import com.pedrogomez.pokeapp.models.dataadapters.PokemonDataAdapter
import com.pedrogomez.pokeapp.pokelist.repository.PokemonsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import com.pedrogomez.pokeapp.models.result.Result
import com.pedrogomez.pokeapp.utils.extensions.print

class PokeListViewModel(
    private val pokemonsRepository: PokemonsRepository,
    private val pokeDataAdapter : PokemonDataAdapter
) : ViewModel(){

    val scope : CoroutineScope = CoroutineScope(
        Dispatchers.IO
    )

    private val pokemonListLiveData = MutableLiveData<MutableList<PokemonData>>()

    private val findedPokemonLiveData = MutableLiveData<PokemonData?>()

    private val pokeListStateApi = MutableLiveData<Result>()

    private val pokeFindedStateApi = MutableLiveData<Result>()

    private val pokemonList = ArrayList<PokemonData>()

    init {
        pokemonListLiveData.postValue(pokemonList)
    }

    fun observeApiState() : MutableLiveData<Result> {
        return pokeListStateApi
    }

    fun observeFindedApiState() : MutableLiveData<Result> {
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
                Result.LoadingMoreContent(true)
            )
            scope.launch {
                val findedPokemon = pokemonsRepository.getPokeDetailsByName(
                        name
                )
                pokeFindedStateApi.postValue(
                        if(findedPokemon!=null){
                            Result.Success(true)
                        }else{
                            Result.Error.RecoverableError("Check your internet connexion")
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

    fun getListOfPokemons(){
        pokemonList.clear()
        pokeListStateApi.postValue(
            Result.LoadingNewContent(true)
        )
        getPokeListByPage(0)
    }

    fun loadMorePokemonsToList(
            page:Int
    ){
        pokeListStateApi.postValue(
            Result.LoadingMoreContent(true)
        )
        getPokeListByPage(page)
    }

    private fun getPokeListByPage(page:Int){
        scope.launch {
            val pokemonsList = pokemonsRepository.getPokeList(
                    page
            )
            pokemonListLiveData.postValue(
                pokeDataAdapter.getAsPokemonDataList(
                        pokemonsList
                ).toMutableList()
            )
            pokeListStateApi.postValue(
                    if(pokemonsList.isNotEmpty()){
                        Result.Success(true)
                    }else{
                        Result.Error.RecoverableError("Check your internet connexion")
                    }
            )
            val fullPokeDataList = pokemonsList.map {
                pokemonsRepository.getPokeDetailsByUrl(
                        it.url
                )
            }
            "Lista de pokemons n : ${fullPokeDataList.size} = $fullPokeDataList".print()
            fullPokeDataList.let {
                pokemonListLiveData.postValue(
                        pokeDataAdapter.getAsPokemonDataList(
                                it
                        ).toMutableList()
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }

}