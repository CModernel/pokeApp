package com.pedrogomez.mylistaplication.booklist.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.pedrogomez.mylistaplication.R
import com.pedrogomez.mylistaplication.utils.extensions.isValid
import com.pedrogomez.mylistaplication.booklist.models.bookresponse.BooksResponse
import com.pedrogomez.mylistaplication.booklist.models.pokedetail.PokeDetailResponse
import com.pedrogomez.mylistaplication.booklist.models.pokelist.PokeListResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.pedrogomez.mylistaplication.booklist.models.result.Result
import com.pedrogomez.mylistaplication.utils.extensions.print
import io.ktor.client.engine.*

class BooksRepository(
    private val client : HttpClient,
    private val urlBase:String
    ) {
    
    suspend fun getPokeDetailsByName(
        requestUrl:String,
        liveData : MutableLiveData<Result<BooksResponse>>,
        name : String,
    ){
        try{
            val requestUrl = "$urlBase/$name"
            val response =
                client.request<PokeDetailResponse>(requestUrl) {
                    method = HttpMethod.Get
                }
            "Ktor_request Response: $response".print()
            liveData.postValue(
                Result.Success(response)
            )
        }catch (e : java.lang.Exception){
            if (e.message.isValid()) {
                liveData.postValue(Result.Error.RecoverableError(Exception(e.message)))
            }else{
                liveData.postValue(Result.Error.NonRecoverableError(Exception("Un-traceable")))
            }
        }
    }

    suspend fun getPokeDetails(
            requestUrl:String,
            liveData : MutableLiveData<Result<BooksResponse>>,
            query : String,
            page:Int
    ){
        try{
            val response =
                client.request<PokeDetailResponse>(requestUrl) {
                    method = HttpMethod.Get
                }
            "Ktor_request Response: $response".print()
            liveData.postValue(
                    Result.Success(response)
            )
        }catch (e : java.lang.Exception){
            if (e.message.isValid()) {
                liveData.postValue(Result.Error.RecoverableError(Exception(e.message)))
            }else{
                liveData.postValue(Result.Error.NonRecoverableError(Exception("Un-traceable")))
            }
        }
    }

    suspend fun getPokeList(
        liveData : MutableLiveData<Result<BooksResponse>>,
        page:Int
    ){
        try{
            val requestUrl ="$urlBase?limit=15&offset=${page*10}"
            val response =
                client.request<PokeListResponse>(requestUrl) {
                    method = HttpMethod.Get
                }
            "Ktor_request Response: $response".print()
            liveData.postValue(
                Result.Success(response)
            )
        }catch (e : java.lang.Exception){
            if (e.message.isValid()) {
                liveData.postValue(Result.Error.RecoverableError(Exception(e.message)))
            }else{
                liveData.postValue(Result.Error.NonRecoverableError(Exception("Un-traceable")))
            }
        }
    }

}