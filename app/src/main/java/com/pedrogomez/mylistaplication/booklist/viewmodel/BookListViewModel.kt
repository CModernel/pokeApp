package com.pedrogomez.mylistaplication.booklist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedrogomez.mylistaplication.booklist.models.bookresponse.BooksResponse
import com.pedrogomez.mylistaplication.booklist.repository.BooksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import com.pedrogomez.mylistaplication.booklist.models.result.Result
import com.pedrogomez.mylistaplication.utils.extensions.print

class BookListViewModel(private val booksRepository: BooksRepository) : ViewModel() {

    val scope : CoroutineScope = CoroutineScope(
        Dispatchers.IO
    )

    private val booksLiveData = MutableLiveData<Result<BooksResponse>>()

    fun observeData() : MutableLiveData<Result<BooksResponse>> {
        return booksLiveData
    }

    fun getNewBooks(
        query : String
    ){
        "Query $query".print()
        if(query.isNotEmpty()){
            booksLiveData.postValue(
                Result.LoadingNewContent(true)
            )
            getReposFromGitHub(
                getStringAsQueryString(query),
                0
            )
        }
    }

    fun loadMoreBooks(
        query : String,
        page:Int
    ){
        "Query $query".print()
        if(query.isNotEmpty()){
            booksLiveData.postValue(
                Result.LoadingMoreContent(true)
            )
            getReposFromGitHub(
                getStringAsQueryString(query),
                page
            )
        }
    }

    private fun getReposFromGitHub(
        query : String,
        page:Int
    ){
        scope.launch {
            booksRepository.getListOfBooks(
                booksLiveData,
                query,
                page
            )
        }
    }

    private fun getStringAsQueryString(query:String):String{
        val separatedQuery = query.trim().split(
            Regex("([\\s])+")
        )
        var finalQuery = ""
        if(separatedQuery.size>1){
            separatedQuery.forEach {
                finalQuery += "$it+"
            }
            return finalQuery.dropLast(1)
        }
        if(separatedQuery.size==1){
            return separatedQuery[0]
        }
        return finalQuery
    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }

}