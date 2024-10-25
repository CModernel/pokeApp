package com.pedrogomez.pokeapp.pokedex.models.dataadapters

import com.pedrogomez.pokeapp.core.extensions.print
import com.pedrogomez.pokeapp.pokedex.models.PokeStat
import com.pedrogomez.pokeapp.pokedex.models.PokeType
import com.pedrogomez.pokeapp.pokedex.models.PokemonData

class PokemonDataAdapter {

    fun getAsPokemonData(item: com.pedrogomez.pokeapp.pokedex.pokelist.models.pokelist.PokeItem): PokemonData {
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

    fun getAsPokemonDataList(pokeList:List<com.pedrogomez.pokeapp.pokedex.pokelist.models.pokelist.PokeItem?>):List<PokemonData>{
        val pokemonDataList = ArrayList<PokemonData>()
        pokeList.map {
            pokemonDataList.add(
                it?.let { it1 -> getAsPokemonData(it1) }?: PokemonData()
            )
        }
        return pokemonDataList.toList()
    }

    private fun getStatByTag(
        statTag: PokeStat,
        stats:List<com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail.Stat>
    ):Int?{
        return stats.filter {
            it.stat?.name == statTag.nameStat
        }[0].base_stat
    }

    private fun getAsPokeTypeList(types:List<com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail.Type>):List<PokeType>{
        val pokeTypeList = ArrayList<PokeType>()
        types.map {
            pokeTypeList.add(
                    getAsPokeType(it)
            )
        }
        return pokeTypeList.toList()
    }

    private fun getAsPokeType(
        type: com.pedrogomez.pokeapp.pokedex.pokelist.models.pokedetail.Type
    ): PokeType {
        return PokeType.getAsPokeType(
                type.type?.name?:"normal"
        )?: PokeType.NORMAL
    }

}