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
                    binding?.ivPokemonFront!!
                )
            Glide.with(this)
                    .load(
                            pokemonData.backDefaultImg
                    ).into(
                            binding?.ivPokemonBack!!
                    )
            binding.tvName.text = pokemonData.name
            binding.tvHeight.text = "${pokemonData.height} m"
            binding.tvWeight.text = "${pokemonData.weight} kg"
            binding.tvId.text = "#${pokemonData.id}"
            binding.tsfHp.setValue(pokemonData.hp?:0)
            binding.tsfAtk.setValue(pokemonData.attack?:0)
            binding.tsfDef.setValue(pokemonData.defense?:0)
            binding.tsfSpd.setValue(pokemonData.speed?:0)
            binding.tsfSpAtk.setValue(pokemonData.specialAttack?:0)
            binding.tsfSpDef.setValue(pokemonData.specialDefense?:0)
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