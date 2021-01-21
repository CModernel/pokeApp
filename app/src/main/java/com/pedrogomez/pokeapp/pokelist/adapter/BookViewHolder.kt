package com.pedrogomez.pokeapp.pokelist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pedrogomez.pokeapp.R
import com.pedrogomez.pokeapp.pokelist.models.PokemonData
import com.pedrogomez.pokeapp.databinding.ViewHolderPokemonBinding

class BookViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    inflater.inflate(
        R.layout.view_holder_pokemon,
        parent,
        false
    )
) {
    private var context : Context

    private var binding: ViewHolderPokemonBinding? = null

    init {
        binding = ViewHolderPokemonBinding.bind(itemView)
        context = parent.context
    }

    fun setData(
        data: PokemonData,
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
        fun goToBookDetail(data: PokemonData)
    }

}