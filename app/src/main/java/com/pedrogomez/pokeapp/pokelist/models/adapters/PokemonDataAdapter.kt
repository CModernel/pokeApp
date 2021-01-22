package com.pedrogomez.pokeapp.pokelist.models.adapters

import com.pedrogomez.pokeapp.pokelist.models.PokeStat
import com.pedrogomez.pokeapp.pokelist.models.PokeType
import com.pedrogomez.pokeapp.pokelist.models.PokemonData
import com.pedrogomez.pokeapp.pokelist.models.pokedetail.PokeDetailResponse
import com.pedrogomez.pokeapp.pokelist.models.pokedetail.Stat
import com.pedrogomez.pokeapp.pokelist.models.pokedetail.Type
import com.pedrogomez.pokeapp.pokelist.models.pokelist.PokeItem

class PokemonDataAdapter {

    fun getAsPokemonData(item: PokeDetailResponse): PokemonData {
        return PokemonData(
            item.name?:"",
            item.height?:0,
            item.sprites.back_default?:"",
            item.sprites.front_default?:"",
            getStatByTag(PokeStat.ATTACK,item.stats),
            getStatByTag(PokeStat.DEFENSE,item.stats),
            getStatByTag(PokeStat.SPECIAL_ATTACK,item.stats),
            getStatByTag(PokeStat.SPECIAL_DEFENSE,item.stats),
            getStatByTag(PokeStat.SPEED,item.stats),
            getAsPokeType(item.types[0])
        )
    }

    fun getAsPokemonDataList(pokeList:List<PokeItem>):List<PokemonData>{
        val pokemonDataList = ArrayList<PokemonData>()
        pokeList.map {
            pokemonDataList.add(
                getAsPokemonData(it.pokeDetail)
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

    private fun getAsPokeType(
        type: Type
    ): PokeType {
        return PokeType.valueOf(type.type?.name?:"normal")
        /*return when(type.type?.name?.toUpperCase()){
            PokeType.NORMAL.type -> PokeType.NORMAL
            PokeType.FIRE.type -> PokeType.FIRE
            PokeType.WATER.type -> PokeType.WATER
            PokeType.ELECTRIC.type -> PokeType.ELECTRIC
            PokeType.GRASS.type -> PokeType.GRASS
            PokeType.ICE.type -> PokeType.ICE
            PokeType.FIGHTING.type -> PokeType.FIGHTING
            PokeType.POISON.type -> PokeType.POISON
            PokeType.GROUND.type -> PokeType.GROUND
            PokeType.PHYCHIC.type -> PokeType.PHYCHIC
            PokeType.BUG.type -> PokeType.BUG
            PokeType.ROCK.type -> PokeType.ROCK
            PokeType.GHOST.type -> PokeType.GHOST
            PokeType.DRAGON.type -> PokeType.DRAGON
            PokeType.DARK.type -> PokeType.DARK
            PokeType.STEEL.type -> PokeType.STEEL
            PokeType.FAIRY.type -> PokeType.FAIRY
            else -> PokeType.NORMAL
        }*/
    }

}