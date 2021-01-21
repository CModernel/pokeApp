package com.pedrogomez.pokeapp.pokelist.models.result

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class LoadingNewContent(val status: Boolean) : Result<Nothing>()
    data class LoadingMoreContent(val status: Boolean) : Result<Nothing>()
    sealed class Error(val exception: Exception) : Result<Nothing>() {
        class RecoverableError(exception: Exception) : Error(exception)
        class NonRecoverableError(exception: Exception) :
            Error(exception)
    }
}