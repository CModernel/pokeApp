package com.pedrogomez.pokeapp.pokelist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedrogomez.pokeapp.models.PokemonData
import com.pedrogomez.pokeapp.pokelist.repository.PokemonsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import com.pedrogomez.pokeapp.models.result.Result

class PokeListViewModel(
    private val pokemonsRepository: PokemonsRepository
) : ViewModel(),
    PokemonsRepository.OnFetching {

    val scope : CoroutineScope = CoroutineScope(
        Dispatchers.IO
    )

    private val pokemonListLiveData = MutableLiveData<ArrayList<PokemonData>>()

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
        return pokeListStateApi
    }

    fun observePokemonData() : MutableLiveData<ArrayList<PokemonData>> {
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
                pokeFindedStateApi.postValue(
                    pokemonsRepository.getPokeDetailsByName(
                        name,
                        this@PokeListViewModel
                    )
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
            pokeListStateApi.postValue(
                pokemonsRepository.getPokeList(
                    page,
                    //para hacer andar esto necesito eliminar el actual modo de observar la data.
                    // Debo usar un observador para los estados del app y otro para la data
                    this@PokeListViewModel
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }

    override fun recibeNewState(newPoke: PokemonData) {
        pokemonList.add(newPoke)
        pokemonListLiveData.postValue(
            pokemonList
        )
    }

    override fun recibeFinded(newFindedPoke: PokemonData?) {
        findedPokemonLiveData.postValue(
            newFindedPoke
        )
    }

}