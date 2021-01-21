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
        const val POKE_DATA = "pokeData"
    }

    private lateinit var pokemonData : PokemonData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try{
            pokemonData = intent.getSerializableExtra(POKE_DATA) as PokemonData
            Glide.with(this)
                .load(
                    pokemonData.frontDefaultImg
                ).into(
                    binding?.ivPokemon!!
                )
            binding.tvName.text = pokemonData.name
            /*binding.tvAuthor.text = pokemonData.authors
            binding.tvDatePublication.text = pokemonData.datePublication
            binding.tvDescription.text = pokemonData.description
            binding.btnShowMore.setOnClickListener {
                openOnBrowser(
                    pokemonData.prevLink?:""
                )
            }
            binding.btnBuyThis.setOnClickListener {
                openOnBrowser(
                    pokemonData.buyLink?:""
                )
            }
            if(pokemonData.prevLink==null){
                binding.btnShowMore.remove()
            }
            if(pokemonData.buyLink==null){
                binding.btnBuyThis.remove()
            }*/
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