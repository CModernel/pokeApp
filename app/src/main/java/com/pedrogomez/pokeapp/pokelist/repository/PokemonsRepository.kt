package com.pedrogomez.pokeapp.pokelist.repository


import com.pedrogomez.pokeapp.pokelist.models.PokemonData
import com.pedrogomez.pokeapp.pokelist.models.adapters.PokemonDataAdapter
import com.pedrogomez.pokeapp.utils.extensions.isValid
import com.pedrogomez.pokeapp.pokelist.models.pokedetail.PokeDetailResponse
import com.pedrogomez.pokeapp.pokelist.models.pokelist.PokeListResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.pedrogomez.pokeapp.pokelist.models.result.Result
import com.pedrogomez.pokeapp.utils.extensions.print

class PokemonsRepository(
    private val client : HttpClient,
    private val urlBase:String,
    private val pokeDataAdapter : PokemonDataAdapter
    ) {

    /**
     * @param name {pokemon's name to find}
     */
    suspend fun getPokeDetailsByName(
        name : String
    ):Result<List<PokemonData>>{
        return try{
            val requestUrl = "$urlBase/$name"
            val response = client.request<PokeDetailResponse>(requestUrl) {
                method = HttpMethod.Get
            }
            val pokeListData = ArrayList<PokemonData>()
            pokeListData.add(
                pokeDataAdapter.getAsPokemonData(
                    response
                )
            )
            "Ktor_request getPokeDetailsByName: $response".print()
            Result.Success(
                pokeListData.toList()
            )
        }catch (e : java.lang.Exception){
            if (e.message.isValid()) {
                Result.Error.RecoverableError(Exception(e.message))
            }else{
                Result.Error.NonRecoverableError(Exception("Un-traceable"))
            }
        }
    }

    /**
     * @param requestUrl {url used to get pokemon's specific data}
     */
    private suspend fun getPokeDetailsByUrl(
            requestUrl:String
    ):PokeDetailResponse?{
        return try{
            "Ktor_request url: $requestUrl".print()
            val response = client.request<PokeDetailResponse>(requestUrl) {
                method = HttpMethod.Get
            }
            //"Ktor_request getPokeDetailsByUrl: $response".print()
            response
        }catch (e : java.lang.Exception){
            "Ktor_request getPokeDetailsByUrl: ${e.message}".print()
            null
        }
    }

    /**
     * @param page {page number of scroll}
     */
    suspend fun getPokeList(
        page:Int
    ):Result<List<PokemonData>>{
        return try{
            val requestUrl ="$urlBase?limit=21&offset=${page*21}"
            "Ktor_request url: $requestUrl".print()
            val response = client.request<PokeListResponse>(requestUrl) {
                method = HttpMethod.Get
            }
            //"Ktor_request getPokeList: $response".print()
            val pokeListData = ArrayList<PokemonData>()
            response.results.map {
                val pokemonData = getPokeDetailsByUrl(it.url)
                if(pokemonData!=null){
                    pokeListData.add(
                        pokeDataAdapter.getAsPokemonData(
                            pokemonData
                        )
                    )
                }
            }
            Result.Success(
                pokeListData.toList()
            )
        }catch (e : java.lang.Exception){
            "Ktor_request getPokeList: ${e.message}".print()
            if (e.message.isValid()) {
                Result.Error.RecoverableError(
                        Exception(e.message)
                )
            }else{
                Result.Error.NonRecoverableError(
                        Exception("Un-traceable")
                )
            }
        }
    }

}