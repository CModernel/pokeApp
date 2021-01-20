package com.pedrogomez.mylistaplication.booklist.repository

import androidx.lifecycle.MutableLiveData
import com.pedrogomez.mylistaplication.utils.extensions.isValid
import com.pedrogomez.mylistaplication.booklist.models.bookresponse.BooksResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.pedrogomez.mylistaplication.booklist.models.result.Result
import com.pedrogomez.mylistaplication.utils.extensions.print

class BooksRepository( private val client : HttpClient) {

    suspend fun getListOfBooks(
            liveData : MutableLiveData<Result<BooksResponse>>,
            query : String,
            page:Int
    ){
        try{
            val requestUrl = "https://www.googleapis.com/books/v1/volumes?q=$query&startIndex=${page*10}&maxResults=10"
            val response =
                client.request<BooksResponse>(requestUrl) {
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