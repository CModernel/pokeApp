package com.pedrogomez.pokeapp.pokelist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedrogomez.pokeapp.pokelist.models.PokemonData
import com.pedrogomez.pokeapp.pokelist.repository.PokemonsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import com.pedrogomez.pokeapp.pokelist.models.result.Result

class PokeListViewModel(
    private val pokemonsRepository: PokemonsRepository
    ) : ViewModel() {

    val scope : CoroutineScope = CoroutineScope(
        Dispatchers.IO
    )

    private val pokemonLiveData = MutableLiveData<ArrayList<PokemonData>>()

    private val pokeStateApi = MutableLiveData<Result>()

    private val pokemonList = ArrayList<PokemonData>()

    init {
        pokemonLiveData.postValue(pokemonList)
    }

    fun observeApiState() : MutableLiveData<Result> {
        return pokeStateApi
    }

    fun observePokemonData() : MutableLiveData<ArrayList<PokemonData>> {
        return pokemonLiveData
    }

    fun findPokemon(name:String){

    }

    fun getListOfPokemons(){
        pokemonList.clear()
        pokeStateApi.postValue(
            Result.LoadingNewContent(true)
        )
        getPokeListByPage(0)
    }

    fun loadMorePokemonsToList(
            page:Int
    ){
        pokeStateApi.postValue(
            Result.LoadingMoreContent(true)
        )
        getPokeListByPage(page)
    }

    private fun getPokeListByPage(page:Int){
        scope.launch {
            pokeStateApi.postValue(
                pokemonsRepository.getPokeList(
                    page,
                    //para hacer andar esto necesito eliminar el actual modo de observar la data.
                    // Debo usar un observador para los estados del app y otro para la data
                    object : PokemonsRepository.OnFetching{
                        override fun recibeNewState(newPoke: PokemonData) {
                            pokemonList.add(newPoke)
                            pokemonLiveData.postValue(
                                    pokemonList
                            )
                        }
                    }
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }

}