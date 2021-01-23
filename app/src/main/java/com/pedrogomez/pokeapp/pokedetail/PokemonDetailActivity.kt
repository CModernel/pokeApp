package com.pedrogomez.pokeapp.pokedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pedrogomez.pokeapp.models.PokemonData
import com.pedrogomez.pokeapp.databinding.ActivityPokemonDetailBinding
import com.pedrogomez.pokeapp.models.PokeType
import com.pedrogomez.pokeapp.pokedetail.typesadapter.TypesHelper
import com.pedrogomez.pokeapp.utils.extensions.print
import com.pedrogomez.pokeapp.utils.getDrawableResByType


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
            initTypes()
            initTitleBgs()
        }catch (e: Exception){
            "bookData: error".print()
        }
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initTypes() {
        pokemonData.type?.let { list ->
            TypesHelper().inflateTypes(list,this@PokemonDetailActivity).map {
                "hay ${it.size} tipos".print()
                binding.lyTypes.addView(
                    it
                )
            }
        }
    }

    private fun initTitleBgs(){
        val bg = getDrawableResByType(
            pokemonData.type?.get(0)?:PokeType.NORMAL
        )
        binding.tvTitleDesc.setBackground(bg)
        binding.tvTitleHeight.setBackground(bg)
        binding.tvTitleName.setBackground(bg)
        binding.tvTitleTypes.setBackground(bg)
        binding.tvTitleStats.setBackground(bg)
        binding.tvTitleWeight.setBackground(bg)
    }

    private fun openOnBrowser(url:String){
        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        startActivity(browserIntent)
    }
}