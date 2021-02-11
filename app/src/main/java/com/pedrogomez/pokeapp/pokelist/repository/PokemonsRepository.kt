package com.pedrogomez.pokeapp.pokelist.repository


import com.pedrogomez.pokeapp.pokelist.models.pokelist.PokeListResponse
import com.pedrogomez.pokeapp.pokedetail.models.pokemonspecies.SpeciesDetails
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.pedrogomez.pokeapp.pokelist.models.pokelist.PokeItem
import com.pedrogomez.pokeapp.utils.extensions.print

class PokemonsRepository(
    private val client : HttpClient,
    private val urlBase:String
    ) {

    /**
     * @param name {pokemon's name to find}
     */
    suspend fun getPokeDetailsByName(
        name : String
    ):PokeItem?{
        val requestUrl = "$urlBase/pokemon/$name"
        "Ktor_request getPokeDetailsByName: $requestUrl".print()
        return getPokeDetailsByUrl(requestUrl)
    }

    suspend fun getPokeDescriptionById(
        id : String
    ):SpeciesDetails?{
        return try{
            val requestUrl = "$urlBase/pokemon-species/$id"
            "Ktor_request getPokeDetailsByName: $requestUrl".print()
            val response = client.request<SpeciesDetails>(requestUrl) {
                method = HttpMethod.Get
            }
            response
        }catch (e : java.lang.Exception){
            null
        }
    }

    /**
     * @param requestUrl {url used to get pokemon's specific data}
     */
    suspend fun getPokeDetailsByUrl(
            requestUrl:String
    ):PokeItem?{
        return try{
            "Ktor_request url: $requestUrl".print()
            val response = client.request<PokeItem>(requestUrl) {
                method = HttpMethod.Get
            }
            //"Ktor_request getPokeDetailsByUrl: $response".print()
            response
        }catch (e : java.lang.Exception){
            "Ktor_request getPokeDetailsByUrl: ${e.message}".print()
            null
        }
    }

    suspend fun getPokeList(
            page:Int
    ):List<PokeItem>{
        return try{
            val requestUrl ="$urlBase/pokemon?limit=21&offset=${page*21}"
            "Ktor_request url: $requestUrl".print()
            val response = client.request<PokeListResponse>(requestUrl) {
                method = HttpMethod.Get
            }
            //"Ktor_request getPokeList: $response".print()
            response.results
        }catch (e : java.lang.Exception){
            ArrayList<PokeItem>().toList()
        }
    }

}