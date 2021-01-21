package com.pedrogomez.pokeapp.pokelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pedrogomez.pokeapp.pokelist.models.PokemonData
import com.pedrogomez.pokeapp.utils.extensions.print

class PokemonsAdapter(
    private val onClickItemListener: PokemonViewHolder.OnClickItemListener
) : RecyclerView.Adapter<PokemonViewHolder>() {

    private var items: ArrayList<PokemonData> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        val inflater = LayoutInflater.from(
            parent.context
        )
        return PokemonViewHolder(
            inflater,
            parent
        )
    }

    override fun onBindViewHolder(
        holder: PokemonViewHolder,
        position: Int
    ) {
        holder.setData(
            items[position],
            onClickItemListener
        )
    }

    override fun getItemCount() = items.size

    fun setData(newItems: List<PokemonData>?) {
        newItems?.let {
            items.addAll(it)
            "size in adapter ${items.size}".print()
            notifyItemInserted(newItems.size)
        }
    }

    fun clearData(){
        items.clear()
        notifyDataSetChanged()
    }
}