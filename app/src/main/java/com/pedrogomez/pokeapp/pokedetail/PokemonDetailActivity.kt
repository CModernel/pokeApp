package com.pedrogomez.pokeapp.pokedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.pedrogomez.pokeapp.R
import com.pedrogomez.pokeapp.models.PokemonData
import com.pedrogomez.pokeapp.databinding.ActivityPokemonDetailBinding
import com.pedrogomez.pokeapp.models.PokeType
import com.pedrogomez.pokeapp.models.result.Result
import com.pedrogomez.pokeapp.pokedetail.typeshelper.TypesHelper
import com.pedrogomez.pokeapp.pokedetail.viewmodel.PokeDetailsViewModel
import com.pedrogomez.pokeapp.utils.extensions.print
import com.pedrogomez.pokeapp.utils.extensions.remove
import com.pedrogomez.pokeapp.utils.extensions.shortToast
import com.pedrogomez.pokeapp.utils.extensions.show
import com.pedrogomez.pokeapp.utils.getDrawableResByType
import org.koin.android.viewmodel.ext.android.viewModel


class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding

    private val pokeDetailsViewModel : PokeDetailsViewModel by viewModel()

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
            pokeDetailsViewModel.getSpeciesDetails(
                    pokemonData.id
            )
            binding.tvName.text = pokemonData.name
            binding.tvHeight.text = "${pokemonData.height/10} m"
            binding.tvWeight.text = "${pokemonData.weight/10} kg"
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
        initObservers()
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

    private fun initObservers() {
        pokeDetailsViewModel.observeSpeciesDetaiData().observe(
                this,
                Observer {
                    it?.let {
                        binding.tvDescription.text = it.flavor_text_entries[0].flavor_text
                    }
                }
        )
    }

    private fun openOnBrowser(url:String){
        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        startActivity(browserIntent)
    }
}