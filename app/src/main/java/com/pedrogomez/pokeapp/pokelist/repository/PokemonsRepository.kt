package com.pedrogomez.pokeapp.pokelist.repository


import com.pedrogomez.pokeapp.models.PokemonData
import com.pedrogomez.pokeapp.models.dataadapters.PokemonDataAdapter
import com.pedrogomez.pokeapp.utils.extensions.isValid
import com.pedrogomez.pokeapp.models.pokedetail.PokeDetailResponse
import com.pedrogomez.pokeapp.models.pokelist.PokeListResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.pedrogomez.pokeapp.models.result.Result
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
        name : String,
        listener:OnFetching
    ):Result{
        return try{
            val requestUrl = "$urlBase/pokemon/$name"
            "Ktor_request getPokeDetailsByName: $requestUrl".print()
            val response = getPokeDetailsByUrl(requestUrl)
            listener.recibeFinded(
                response?.let{
                    pokeDataAdapter.getAsPokemonData(
                        it
                    )
                }
            )
            Result.Success(true)
        }catch (e : java.lang.Exception){
            if (e.message.isValid()) {
                Result.Error.RecoverableError(Exception(e.message))
            }else{
                Result.Error.NonRecoverableError(Exception("Un-traceable"))
            }
        }
    }

    suspend fun getPokeDescriptionById(
        id : String,
        listener:OnFetching
    ):Result{
        return try{
            val requestUrl = "$urlBase/characteristic/$id"
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
            Result.Success(true)
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
    ):Result{
        return getPokeList(page,null)
    }

    suspend fun getPokeList(
            page:Int,
            listener:OnFetching?
    ):Result{
        return try{
            val requestUrl ="$urlBase/pokemon?limit=21&offset=${page*21}"
            "Ktor_request url: $requestUrl".print()
            val response = client.request<PokeListResponse>(requestUrl) {
                method = HttpMethod.Get
            }
            //"Ktor_request getPokeList: $response".print()
            response.results.map {
                val pokemonData = getPokeDetailsByUrl(it.url)
                if(pokemonData!=null){
                    listener?.recibeNewState(
                        pokeDataAdapter.getAsPokemonData(
                            pokemonData
                        )
                    )
                }
            }
            Result.Success(true)
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

    interface OnFetching{
        fun recibeNewState(newPoke: PokemonData)
        fun recibeFinded(newFindedPoke: PokemonData?)
    }

}