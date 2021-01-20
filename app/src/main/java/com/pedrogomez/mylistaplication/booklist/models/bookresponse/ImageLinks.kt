package com.pedrogomez.mylistaplication.booklist.models.bookresponse

import kotlinx.serialization.Serializable

@Serializable
data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
)