package com.pedrogomez.pokeapp.pokelist.models.adapters

import com.pedrogomez.pokeapp.pokelist.models.PokemonData
import com.pedrogomez.pokeapp.pokelist.models.pokedetail.PokeDetailResponse
import com.pedrogomez.pokeapp.pokelist.models.pokelist.PokeItem

class PokemonDataAdapter {

    fun getAsPokemonData(item: PokeDetailResponse?): PokemonData {
        return PokemonData(
            item?.name
        )
    }

    fun getAsPokemonDataList(pokeList:List<PokeItem>):List<PokemonData>{
        val pokemonDataList = ArrayList<PokemonData>()
        pokeList.map {
            pokemonDataList.add(getAsPokemonData(it.pokeDetail))
        }
        return pokemonDataList.toList()
    }

}