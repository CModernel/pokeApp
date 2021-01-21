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

    private val pokeListLiveData = MutableLiveData<Result<List<PokemonData>>>()

    fun observeData() : MutableLiveData<Result<List<PokemonData>>> {
        return pokeListLiveData
    }

    fun findPokemon(name:String){

    }

    fun getListOfPokemons(){
        pokeListLiveData.postValue(
            Result.LoadingNewContent(true)
        )
        getPokeListByPage(0)
    }

    fun loadMorePokemonsToList(
            page:Int
    ){
        pokeListLiveData.postValue(
            Result.LoadingMoreContent(true)
        )
        getPokeListByPage(page)
    }

    private fun getPokeListByPage(page:Int){
        scope.launch {
            pokeListLiveData.postValue(
                pokemonsRepository.getPokeList(
                    page
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }

}