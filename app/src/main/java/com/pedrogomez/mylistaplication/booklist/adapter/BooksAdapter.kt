package com.pedrogomez.mylistaplication.booklist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pedrogomez.mylistaplication.booklist.models.bookresponse.Item
import com.pedrogomez.mylistaplication.utils.extensions.print

class BooksAdapter(
    private val onClickItemListener: BookViewHolder.OnClickItemListener
) : RecyclerView.Adapter<BookViewHolder>() {

    private var items: ArrayList<Item> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookViewHolder {
        val inflater = LayoutInflater.from(
            parent.context
        )
        return BookViewHolder(
            inflater,
            parent
        )
    }

    override fun onBindViewHolder(
        holder: BookViewHolder,
        position: Int
    ) {
        holder.setData(
            items[position],
            onClickItemListener
        )
    }

    override fun getItemCount() = items.size

    fun setData(newItems: List<Item>?) {
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