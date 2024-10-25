package com.pedrogomez.pokeapp.pokedex.pokelist.repository

import android.util.Log
import com.pedrogomez.pokeapp.core.extensions.print
import com.pedrogomez.pokeapp.pokedex.pokedetail.models.pokemonspecies.SpeciesDetails
import com.pedrogomez.pokeapp.pokedex.pokelist.models.pokelist.PokeItem
import com.pedrogomez.pokeapp.pokedex.pokelist.models.pokelist.PokeListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod


class PokemonsRepository(
    private val client : HttpClient,
    private val urlBase:String
    ) {

    /**
     * @param name {pokemon's name to find}
     */
    suspend fun getPokeDetailsByName(
        name : String
    ): PokeItem?{
        val requestUrl = "$urlBase/pokemon/$name"
        "Ktor_request getPokeDetailsByName: $requestUrl".print()
        return getPokeDetailsByUrl(requestUrl)
    }

    suspend fun getPokeDescriptionById(
        id : String
    ): SpeciesDetails?{
        return try{
            val requestUrl = "$urlBase/pokemon-species/$id"
            "Ktor_request getPokeDetailsByName: $requestUrl".print()

            val response = client.request(requestUrl) {
                method = HttpMethod.Get
            }
            response.body()
        }catch (e : java.lang.Exception){
            null
        }
    }

    /**
     * @param requestUrl {url used to get pokemon's specific data}
     */
    suspend fun getPokeDetailsByUrl(
            requestUrl:String
    ): PokeItem?{
        return try{
            client.get(requestUrl) {
                // Optionally set headers or other request settings
                accept(ContentType.Application.Json)
            }.body() // Deserializes the response body into a PokeItem
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
            val response: PokeListResponse = client.get(requestUrl) {
                method = HttpMethod.Get
                accept(ContentType.Application.Json) // Especifica que esperas JSON
            }.body() // Usa .body() para deserializar

            // Retorna la lista de PokeItem desde la respuesta
            response.results
        }catch (e : java.lang.Exception){
            Log.e("PokemonsRepository", "Error fetching data: ${e.message}")
            ArrayList<PokeItem>().toList()
        }
    }

}