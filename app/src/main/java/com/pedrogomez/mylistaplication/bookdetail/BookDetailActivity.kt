package com.pedrogomez.mylistaplication.bookdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.pedrogomez.mylistaplication.booklist.models.bookitem.BookItem
import com.pedrogomez.mylistaplication.databinding.ActivityBookDetailBinding
import com.pedrogomez.mylistaplication.utils.extensions.print
import com.pedrogomez.mylistaplication.utils.extensions.remove


class BookDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailBinding

    companion object {
        const val BOOK_DATA = "bookData"
    }

    private lateinit var bookData : BookItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try{
            bookData = intent.getSerializableExtra(BOOK_DATA) as BookItem
            Glide.with(this)
                .load(
                    bookData.cover
                ).into(
                    binding?.ivBook!!
                )
            binding.tvTitle.text = bookData.title
            binding.tvAuthor.text = bookData.authors
            binding.tvDatePublication.text = bookData.datePublication
            binding.tvDescription.text = bookData.description
            binding.btnShowMore.setOnClickListener {
                openOnBrowser(
                    bookData.prevLink?:""
                )
            }
            binding.btnBuyThis.setOnClickListener {
                openOnBrowser(
                    bookData.buyLink?:""
                )
            }
            if(bookData.prevLink==null){
                binding.btnShowMore.remove()
            }
            if(bookData.buyLink==null){
                binding.btnBuyThis.remove()
            }
        }catch (e: Exception){
            "bookData: error".print()
        }
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun openOnBrowser(url:String){
        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        startActivity(browserIntent)
    }
}