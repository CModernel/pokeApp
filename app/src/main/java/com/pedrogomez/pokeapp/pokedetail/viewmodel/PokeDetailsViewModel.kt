package com.pedrogomez.pokeapp.pokedetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedrogomez.pokeapp.pokedetail.models.pokemonspecies.SpeciesDetails
import com.pedrogomez.pokeapp.pokelist.repository.PokemonsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class PokeDetailsViewModel(
        private val pokemonsRepository: PokemonsRepository
) : ViewModel(){

    val scope : CoroutineScope = CoroutineScope(
            Dispatchers.IO
    )

    private val speciesDetailLiveData = MutableLiveData<SpeciesDetails?>()

    fun observeSpeciesDetaiData() : MutableLiveData<SpeciesDetails?> {
        return speciesDetailLiveData
    }

    fun getSpeciesDetails(id:Int){
        scope.launch {
            speciesDetailLiveData.postValue(
                    pokemonsRepository.getPokeDescriptionById(
                            "$id"
                    )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }

}