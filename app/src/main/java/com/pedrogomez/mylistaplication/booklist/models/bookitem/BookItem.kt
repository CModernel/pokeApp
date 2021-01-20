package com.pedrogomez.mylistaplication.booklist.models.bookitem

import com.pedrogomez.mylistaplication.booklist.models.bookresponse.*
import java.io.Serializable

@kotlinx.serialization.Serializable
class BookItem(
    val id: String?,
    val prevLink: String?,
    val title : String,
    val authors: String,
    val datePublication: String?,
    val cover:String?,
    val categories:String?,
    val description: String?,
    val infoLink: String? = null,
    val language: String? = null,
    val pageCount: Int? = null,
    val publisher: String? = null,
    val subtitle: String? = null,
    val buyLink: String? = null,
    val country: String?,
    val isEbook: Boolean?,
    val industryIdentifiers: List<IndustryIdentifier>? = null,
):Serializable