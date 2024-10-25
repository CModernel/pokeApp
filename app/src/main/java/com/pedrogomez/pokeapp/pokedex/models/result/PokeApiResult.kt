package com.pedrogomez.pokeapp.pokedex.models.result

sealed class PokeApiResult{
    data class Success(val finished: Boolean): PokeApiResult()
    data class LoadingNewContent(val status: Boolean): PokeApiResult()
    data class LoadingMoreContent(val status: Boolean): PokeApiResult()
    sealed class Error(val errorMsg: String): PokeApiResult() {
        class RecoverableError(errorMsg: String) : Error(errorMsg)
        class NonRecoverableError(errorMsg: String) :
            Error(errorMsg)
    }
}