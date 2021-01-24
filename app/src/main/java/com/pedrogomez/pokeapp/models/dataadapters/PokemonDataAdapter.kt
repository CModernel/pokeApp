package com.pedrogomez.pokeapp.models.dataadapters

import com.pedrogomez.pokeapp.models.PokeStat
import com.pedrogomez.pokeapp.models.PokeType
import com.pedrogomez.pokeapp.models.PokemonData
import com.pedrogomez.pokeapp.models.pokedetail.PokeDetailResponse
import com.pedrogomez.pokeapp.models.pokedetail.Stat
import com.pedrogomez.pokeapp.models.pokedetail.Type
import com.pedrogomez.pokeapp.models.pokelist.PokeItem

class PokemonDataAdapter {

    fun getAsPokemonData(item: PokeDetailResponse): PokemonData {
        return PokemonData(
            item.id?:0,
            item.name,
            item.height?.toFloat()?:0f,
            item.weight?.toFloat()?:0f,
            item.sprites.back_default?:"",
            item.sprites.front_default?:"",
            getStatByTag(PokeStat.HP,item.stats),
            getStatByTag(PokeStat.ATTACK,item.stats),
            getStatByTag(PokeStat.DEFENSE,item.stats),
            getStatByTag(PokeStat.SPECIAL_ATTACK,item.stats),
            getStatByTag(PokeStat.SPECIAL_DEFENSE,item.stats),
            getStatByTag(PokeStat.SPEED,item.stats),
            getAsPokeTypeList(item.types)
        )
    }

    fun getAsPokemonDataList(pokeList:List<PokeItem>):List<PokemonData>{
        val pokemonDataList = ArrayList<PokemonData>()
        pokeList.map {
            pokemonDataList.add(
                it.pokeDetail?.let { it1 -> getAsPokemonData(it1) }?: PokemonData()
            )
        }
        return pokemonDataList.toList()
    }

    private fun getStatByTag(
        statTag:PokeStat,
        stats:List<Stat>
    ):Int?{
        return stats.filter {
            it.stat?.name == statTag.nameStat
        }[0].base_stat
    }

    private fun getAsPokeTypeList(types:List<Type>):List<PokeType>{
        val pokeTypeList = ArrayList<PokeType>()
        types.map {
            pokeTypeList.add(
                    getAsPokeType(it)
            )
        }
        return pokeTypeList.toList()
    }

    private fun getAsPokeType(
        type: Type
    ): PokeType {
        return PokeType.getAsPokeType(
                type.type?.name?:"normal"
        )?:PokeType.NORMAL
    }

}