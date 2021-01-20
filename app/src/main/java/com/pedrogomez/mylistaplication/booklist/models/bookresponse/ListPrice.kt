package com.pedrogomez.mylistaplication.booklist.models.bookresponse

import kotlinx.serialization.Serializable

@Serializable
data class ListPrice(
    val amount: Double? = null,
    val currencyCode: String? = null
)