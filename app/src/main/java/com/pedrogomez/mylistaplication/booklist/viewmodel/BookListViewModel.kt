package com.pedrogomez.mylistaplication.booklist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedrogomez.mylistaplication.booklist.models.pokelist.PokeListResponse
import com.pedrogomez.mylistaplication.booklist.repository.PokemonsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import com.pedrogomez.mylistaplication.booklist.models.result.Result

class BookListViewModel(private val pokemonsRepository: PokemonsRepository) : ViewModel() {

    val scope : CoroutineScope = CoroutineScope(
        Dispatchers.IO
    )

    private val pokeListLiveData = MutableLiveData<Result<PokeListResponse>>()

    fun observeData() : MutableLiveData<Result<PokeListResponse>> {
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