package com.pedrogomez.mylistaplication.booklist.models.bookresponse

import kotlinx.serialization.Serializable

@Serializable
data class IndustryIdentifier(
    val identifier: String?,
    val type: String?
):java.io.Serializable