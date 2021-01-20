package com.pedrogomez.mylistaplication.booklist.models.bookresponse

import kotlinx.serialization.Serializable

@Serializable
data class Offer(
        val finskyOfferType: Int? = null,
        val listPrice: ListPriceX? = null,
        val retailPrice: RetailPrice? = null
)