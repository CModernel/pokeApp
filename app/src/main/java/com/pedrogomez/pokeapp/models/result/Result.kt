package com.pedrogomez.pokeapp.models.result

sealed class Result{
    data class Success(val finished: Boolean):Result()
    data class LoadingNewContent(val status: Boolean):Result()
    data class LoadingMoreContent(val status: Boolean):Result()
    sealed class Error(val exception: Exception):Result() {
        class RecoverableError(exception: Exception) : Error(exception)
        class NonRecoverableError(exception: Exception) :
            Error(exception)
    }
}