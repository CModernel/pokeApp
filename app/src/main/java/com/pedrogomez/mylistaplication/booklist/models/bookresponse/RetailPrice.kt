package com.pedrogomez.mylistaplication.booklist.models.bookresponse

import kotlinx.serialization.Serializable

@Serializable
data class RetailPrice(
    val amountInMicros: Int?,
    val currencyCode: String?
):java.io.Serializable