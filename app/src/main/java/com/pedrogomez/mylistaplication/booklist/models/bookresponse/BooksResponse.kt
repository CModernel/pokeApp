package com.pedrogomez.mylistaplication.booklist.models.bookresponse

import kotlinx.serialization.Serializable

@Serializable
data class BooksResponse(
        val items: List<Item>?,
        val kind: String?,
        val totalItems: Int?
)