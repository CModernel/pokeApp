package com.pedrogomez.mylistaplication.booklist.repository


import com.pedrogomez.mylistaplication.utils.extensions.isValid
import com.pedrogomez.mylistaplication.booklist.models.pokedetail.PokeDetailResponse
import com.pedrogomez.mylistaplication.booklist.models.pokelist.PokeListResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.pedrogomez.mylistaplication.booklist.models.result.Result
import com.pedrogomez.mylistaplication.utils.extensions.print

class PokemonsRepository(
    private val client : HttpClient,
    private val urlBase:String
    ) {

    /**
     * @param name {pokemon's name to find}
     */
    suspend fun getPokeDetailsByName(
        name : String
    ):Result<PokeDetailResponse>{
        return try{
            val requestUrl = "$urlBase/$name"
            val response = client.request<PokeDetailResponse>(requestUrl) {
                method = HttpMethod.Get
            }
            "Ktor_request getPokeDetailsByName: $response".print()
            Result.Success(response)
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
            val response = client.request<PokeDetailResponse>(requestUrl) {
                method = HttpMethod.Get
            }
            "Ktor_request getPokeDetailsByUrl: $response".print()
            response
        }catch (e : java.lang.Exception){
            null
        }
    }

    /**
     * @param page {page number of scroll}
     */
    suspend fun getPokeList(
        page:Int
    ):Result<PokeListResponse>{
        return try{
            val requestUrl ="$urlBase?limit=10&offset=${page*10}"
            val response = client.request<PokeListResponse>(requestUrl) {
                method = HttpMethod.Get
            }
            "Ktor_request getPokeList: $response".print()
            response.results.map {
                it.pokeDetail = getPokeDetailsByUrl(it.url)
            }
            Result.Success(response)
        }catch (e : java.lang.Exception){
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