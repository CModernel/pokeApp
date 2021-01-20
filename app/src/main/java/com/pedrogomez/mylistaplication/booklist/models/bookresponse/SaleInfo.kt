package com.pedrogomez.mylistaplication.booklist.models.bookresponse

import kotlinx.serialization.Serializable

@Serializable
data class SaleInfo(
        val buyLink: String? = null,
        val country: String?,
        val isEbook: Boolean?,
        val listPrice: ListPrice? = null,
        val offers: List<Offer>? = null,
        val retailPrice: RetailPriceX? = null,
        val saleability: String?
)