package com.pedrogomez.mylistaplication.booklist.models.bookresponse

import kotlinx.serialization.Serializable

@Serializable
data class ListPriceX(
    val amountInMicros: Int?,
    val currencyCode: String?
):java.io.Serializable