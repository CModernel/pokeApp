package com.pedrogomez.mylistaplication.booklist.models.bookresponse

import kotlinx.serialization.Serializable

@Serializable
data class ReadingModes(
    val image: Boolean?,
    val text: Boolean?
)