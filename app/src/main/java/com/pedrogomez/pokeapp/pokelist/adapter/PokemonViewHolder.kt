package com.pedrogomez.pokeapp.pokelist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pedrogomez.pokeapp.R
import com.pedrogomez.pokeapp.pokelist.models.PokemonData
import com.pedrogomez.pokeapp.databinding.ViewHolderPokemonBinding
import com.pedrogomez.pokeapp.pokelist.models.PokeType

class PokemonViewHolder(
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
                    data.frontDefaultImg
                ).into(
                    binding?.ivPokemon!!
                )
        }catch (e:Exception){

        }
        binding?.tvName?.text = data.name
        binding?.clBgCard?.background = context.getDrawable(
            getDrawableResByType(
                    data.type?.get(0)?:PokeType.NORMAL
            )
        )
        binding?.pokemonRowContainer?.setOnClickListener {
            onClickItemListener.goToBookDetail(
                data
            )
        }
    }

    private fun getDrawableResByType(type: PokeType?):Int{
        return when(type){
            PokeType.NORMAL -> R.drawable.type_normal
            PokeType.FIRE -> R.drawable.type_fire
            PokeType.WATER -> R.drawable.type_water
            PokeType.GRASS -> R.drawable.type_grass
            PokeType.ELECTRIC -> R.drawable.type_electric
            PokeType.ICE -> R.drawable.type_ice
            PokeType.FIGHTING -> R.drawable.type_fighting
            PokeType.POISON -> R.drawable.type_poison
            PokeType.GROUND -> R.drawable.type_ground
            PokeType.FLYING -> R.drawable.type_flying
            PokeType.PHYCHIC -> R.drawable.type_phychic
            PokeType.BUG -> R.drawable.type_bug
            PokeType.ROCK -> R.drawable.type_rock
            PokeType.GHOST -> R.drawable.type_ghost
            PokeType.DRAGON -> R.drawable.type_dragon
            PokeType.DARK -> R.drawable.type_dark
            PokeType.STEEL -> R.drawable.type_steel
            PokeType.FAIRY -> R.drawable.type_fairy
            else -> R.drawable.type_normal
        }
    }

    interface OnClickItemListener{
        fun goToBookDetail(data: PokemonData)
    }

}