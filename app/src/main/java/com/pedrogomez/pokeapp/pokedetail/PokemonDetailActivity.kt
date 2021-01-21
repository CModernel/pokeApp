package com.pedrogomez.pokeapp.pokedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.pedrogomez.pokeapp.pokelist.models.PokemonData
import com.pedrogomez.pokeapp.databinding.ActivityPokemonDetailBinding
import com.pedrogomez.pokeapp.utils.extensions.print


class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding

    companion object {
        const val BOOK_DATA = "bookData"
    }

    private lateinit var bookData : PokemonData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
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