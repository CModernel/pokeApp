package com.pedrogomez.pokeapp.models.dataadapters

import com.pedrogomez.pokeapp.models.PokeStat
import com.pedrogomez.pokeapp.models.PokeType
import com.pedrogomez.pokeapp.models.PokemonData
import com.pedrogomez.pokeapp.pokelist.models.pokedetail.PokeDetailResponse
import com.pedrogomez.pokeapp.pokelist.models.pokedetail.Stat
import com.pedrogomez.pokeapp.pokelist.models.pokedetail.Type
import com.pedrogomez.pokeapp.pokelist.models.pokelist.PokeItem
import com.pedrogomez.pokeapp.utils.extensions.print

class PokemonDataAdapter {

    fun getAsPokemonData(item: PokeItem): PokemonData {
        "Convierto : ${item.name}".print()
        return PokemonData(
            item.id?:0,
            item.name,
            item.height?.toFloat()?:0f,
            item.weight?.toFloat()?:0f,
            item.sprites?.back_default?:"",
            item.sprites?.front_default?:"",
            item.stats?.let {
                getStatByTag(PokeStat.HP,it)
            },
            item.stats?.let {
                getStatByTag(PokeStat.ATTACK,it)
            },
            item.stats?.let {
                getStatByTag(PokeStat.DEFENSE,it)
            },
            item.stats?.let {
                getStatByTag(PokeStat.SPECIAL_ATTACK,it)
            },
            item.stats?.let {
                getStatByTag(PokeStat.SPECIAL_DEFENSE,it)
            },
            item.stats?.let {
                getStatByTag(PokeStat.SPEED,it)
            },
            item.types?.let{
                getAsPokeTypeList(it)
            }
        )
    }

    fun getAsPokemonDataList(pokeList:List<PokeItem?>):List<PokemonData>{
        val pokemonDataList = ArrayList<PokemonData>()
        pokeList.map {
            pokemonDataList.add(
                it?.let { it1 -> getAsPokemonData(it1) }?: PokemonData()
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