package com.pedrogomez.pokeapp.pokedex.pokedetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedrogomez.pokeapp.pokedex.pokedetail.models.pokemonspecies.SpeciesDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class PokeDetailsViewModel(
        private val pokemonsRepository: com.pedrogomez.pokeapp.pokedex.pokelist.repository.PokemonsRepository
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