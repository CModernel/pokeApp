package com.pedrogomez.mylistaplication.booklist.models.adapters

import com.pedrogomez.mylistaplication.booklist.models.bookitem.BookItem
import com.pedrogomez.mylistaplication.booklist.models.bookresponse.Item

class BookDataAdapter {

    fun getItemAsBookItem(item: Item): BookItem {
        return BookItem(
            item.id,
            item.volumeInfo?.previewLink,
            item.volumeInfo?.title?:"",
            getAsASingleString(item.volumeInfo?.authors),
            item.volumeInfo?.publishedDate,
            item.volumeInfo?.imageLinks?.thumbnail,
            getAsASingleString(item.volumeInfo?.categories),
            item.volumeInfo?.description,
            item.volumeInfo?.infoLink,
            item.volumeInfo?.language,
            item.volumeInfo?.pageCount,
            item.volumeInfo?.publisher,
            item.volumeInfo?.subtitle,
            item.saleInfo?.buyLink,
            item.saleInfo?.country,
            item.saleInfo?.isEbook,
            item.volumeInfo?.industryIdentifiers
        )
    }

    private fun getAsASingleString(words: List<String>?):String{
        return if(words!=null){
            var singleAux = ""
            words.forEach {
                singleAux+= " $it,"
            }
            singleAux.dropLast(1)
        } else{
            ""
        }
    }

}