package com.pedrogomez.mylistaplication.booklist.models.bookresponse

import kotlinx.serialization.Serializable

@Serializable
data class Item(
        val accessInfo: AccessInfo?,
        val etag: String?,
        val id: String?,
        val kind: String?,
        val saleInfo: SaleInfo?,
        val searchInfo: SearchInfo? = null,
        val selfLink: String?,
        val volumeInfo: VolumeInfo?
)