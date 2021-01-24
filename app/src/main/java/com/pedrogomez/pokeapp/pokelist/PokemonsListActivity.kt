package com.pedrogomez.pokeapp.pokelist

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.pedrogomez.pokeapp.R
import com.pedrogomez.pokeapp.base.BaseActivity
import com.pedrogomez.pokeapp.pokedetail.PokemonDetailActivity
import com.pedrogomez.pokeapp.pokelist.listadapter.PokemonViewHolder
import com.pedrogomez.pokeapp.pokelist.listadapter.PokemonsAdapter
import com.pedrogomez.pokeapp.models.result.Result
import com.pedrogomez.pokeapp.pokelist.viewmodel.PokeListViewModel
import com.pedrogomez.pokeapp.databinding.ActivityPokemonListBinding
import com.pedrogomez.pokeapp.models.PokemonData
import com.pedrogomez.pokeapp.utils.PageScrollListener
import com.pedrogomez.pokeapp.utils.extensions.print
import com.pedrogomez.pokeapp.utils.extensions.remove
import com.pedrogomez.pokeapp.utils.extensions.shortToast
import com.pedrogomez.pokeapp.utils.extensions.show
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception

class PokemonsListActivity : BaseActivity(),
    PokemonViewHolder.OnClickItemListener{

    private val pokeListViewModel : PokeListViewModel by viewModel()

    private lateinit var binding: ActivityPokemonListBinding

    private lateinit var pokemonsAdapter : PokemonsAdapter

    private lateinit var pageScrollListener : PageScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initObservers()
        initListeners()
        binding.btnToTop.hide()
        hideKeyboard(binding.etSearchField)
        pokeListViewModel.getListOfPokemons()
    }

    private fun initListeners() {
        binding.searchBtn.setOnClickListener {
            pokeListViewModel.findPokemon(
                    binding.etSearchField.text.toString()
            )
        }
    }

    private fun initRecyclerView() {
        pokemonsAdapter = PokemonsAdapter(this@PokemonsListActivity)
        binding.rvPokeItems.apply{
            adapter = pokemonsAdapter
            layoutManager = GridLayoutManager(this@PokemonsListActivity,3)
        }
        binding.srlContainer.setOnRefreshListener {
            pokeListViewModel.getListOfPokemons()
        }
        pageScrollListener = object : PageScrollListener(
            binding.rvPokeItems.layoutManager as GridLayoutManager
        ){
            override fun onLoadMore(
                currentPage: Int
            ) {
                "current page: $currentPage".print()
                pokeListViewModel.loadMorePokemonsToList(
                    currentPage
                )
            }

            override fun scrollIsOnTop(isOnTop: Boolean) {
                if(isOnTop){
                    binding.btnToTop.hide()
                }else{
                    binding.btnToTop.show()
                }
            }
        }
        binding.rvPokeItems.addOnScrollListener(
            pageScrollListener
        )
        binding.btnToTop.setOnClickListener {
            binding.rvPokeItems.smoothScrollToPosition(0)
        }
    }

    private fun initObservers(){
        pokeListViewModel.observeApiState().observe(
            this,
            Observer {
                binding.srlContainer.isRefreshing = false
                when (it) {
                    is Result.Success -> {
                        binding.pbPokesLoading.remove()
                        binding.srlContainer.isEnabled = true
                        pageScrollListener.enablePaging(true)
                    }
                    is Result.LoadingNewContent -> {
                        binding.srlContainer.isEnabled = false
                        pageScrollListener.initFields()
                        pokemonsAdapter.clearData()
                        hideKeyboard(binding.etSearchField)
                        binding.pbPokesLoading.show()
                    }
                    is Result.LoadingMoreContent -> {
                        binding.srlContainer.isEnabled = false
                        pageScrollListener.enablePaging(false)
                        binding.pbPokesLoading.show()
                    }
                    is Result.Error -> {
                        shortToast(
                                this,
                                this.getString(R.string.search_error)
                        )
                        binding.pbPokesLoading.remove()
                        binding.srlContainer.isEnabled = true
                        pageScrollListener.enablePaging(true)
                    }
                }
            }
        )
        pokeListViewModel.observeFindedApiState().observe(
            this,
            Observer {
                binding.srlContainer.isRefreshing = false
                when (it) {
                    is Result.Success -> {
                        binding.searchBtn.isClickable = true
                        binding.searchBtn.isEnabled = true
                        binding.pbPokesLoading.remove()
                    }
                    is Result.Error -> {
                        binding.searchBtn.isClickable = true
                        binding.searchBtn.isEnabled = true
                        shortToast(
                                this,
                                this.getString(R.string.search_error)
                        )
                        binding.pbPokesLoading.remove()
                    }
                    else ->{
                        binding.pbPokesLoading.show()
                        binding.searchBtn.isClickable = false
                        binding.searchBtn.isEnabled = false
                        hideKeyboard(binding.etSearchField)
                        binding.etSearchField.setText("")
                    }
                }
            }
        )
        pokeListViewModel.observePokemonData().observe(
                this,
                Observer {
                    pokemonsAdapter.setData(it.toList())
                }
        )
        pokeListViewModel.observeFindedPokemon().observe(
            this,
            Observer {
                if(it!=null){
                    goToBookDetail(it)
                }else{
                    shortToast(
                        this@PokemonsListActivity,
                        this.getString(R.string.search_no_results)
                    )
                }
            }
        )
    }

    override fun goToBookDetail(data: PokemonData) {
        val intent = Intent(
            this,
            PokemonDetailActivity::class.java
        )
        intent.putExtra(
            PokemonDetailActivity.POKE_DATA,
            data
        )
        startActivity(
            intent
        )
    }
}