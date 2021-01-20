package com.pedrogomez.mylistaplication.booklist.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pedrogomez.mylistaplication.R
import com.pedrogomez.mylistaplication.booklist.models.bookresponse.Item
import com.pedrogomez.mylistaplication.databinding.ViewHolderBookBinding

class BookViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    inflater.inflate(
        R.layout.view_holder_book,
        parent,
        false
    )
) {
    private var context : Context

    private var binding: ViewHolderBookBinding? = null

    init {
        binding = ViewHolderBookBinding.bind(itemView)
        context = parent.context
    }

    fun setData(
        data: Item,
        onClickItemListener: OnClickItemListener
    ) {
        try{
            Glide.with(context)
                .load(
                    data.volumeInfo?.
                    imageLinks?.
                    smallThumbnail
                ).into(
                    binding?.ivBook!!
                )
        }catch (e:Exception){

        }
        binding?.tvTitle?.text = data.volumeInfo?.title?:"No Title"
        binding?.tvAuthor?.text = data.volumeInfo?.authors?.get(0) ?:"No Author"
        binding?.bookRowContainer?.setOnClickListener {
            onClickItemListener.goToBookDetail(
                data
            )
        }
    }

    interface OnClickItemListener{
        fun goToBookDetail(data: Item)
    }

}