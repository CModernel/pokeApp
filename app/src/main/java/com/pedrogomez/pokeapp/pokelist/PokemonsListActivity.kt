package com.pedrogomez.pokeapp.pokelist

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedrogomez.pokeapp.base.BaseActivity
import com.pedrogomez.pokeapp.pokedetail.PokemonDetailActivity
import com.pedrogomez.pokeapp.pokelist.adapter.BookViewHolder
import com.pedrogomez.pokeapp.pokelist.adapter.BooksAdapter
import com.pedrogomez.pokeapp.pokelist.models.result.Result
import com.pedrogomez.pokeapp.pokelist.viewmodel.PokeListViewModel
import com.pedrogomez.pokeapp.databinding.ActivityPokemonListBinding
import com.pedrogomez.pokeapp.pokelist.models.PokemonData
import com.pedrogomez.pokeapp.utils.PageScrollListener
import org.koin.android.viewmodel.ext.android.viewModel

class PokemonsListActivity : BaseActivity(),
    BookViewHolder.OnClickItemListener{

    private val pokeListViewModel : PokeListViewModel by viewModel()

    private lateinit var binding: ActivityPokemonListBinding

    private lateinit var booksAdapter : BooksAdapter

    private var counter : CountDownTimer? = null

    private lateinit var pageScrollListener : PageScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initObservers()
        initEditTextListeners()
        binding.btnToTop.hide()
        hideKeyboard(binding.etSearchField)
    }

    private fun initEditTextListeners() {
        binding.etSearchField.addTextChangedListener {
            if(counter!=null){
                counter?.cancel()
            }
            counter = object : CountDownTimer(500, 100){
                override fun onTick(millisUntilFinished: Long) {

                }
                /**
                 * Este contador se ejecuta para llamar al endpoint si y solo si el usario
                 * dejo de teclear durante un tiempo mayor a 500ms, y asi evitar multiples
                 * llamadas a backend
                 * */
                override fun onFinish() {
                    pokeListViewModel.getListOfPokemons(
                        it.toString()
                    )
                }

            }.start()
        }
    }

    private fun initRecyclerView() {
        booksAdapter = BooksAdapter(this@PokemonsListActivity)
        binding.rvBookItems.apply{
            adapter = booksAdapter
            layoutManager = LinearLayoutManager(this@PokemonsListActivity)
        }
        binding.srlContainer.setOnRefreshListener {
            pokeListViewModel.getListOfPokemons(
                binding.etSearchField.text.toString()
            )
        }
        pageScrollListener = object : PageScrollListener(
            binding.rvBookItems.layoutManager as LinearLayoutManager
        ){
            override fun onLoadMore(
                currentPage: Int
            ) {
                pokeListViewModel.loadMoreBooks(
                    binding.etSearchField.text.toString(),
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
        binding.rvBookItems.addOnScrollListener(
            pageScrollListener
        )
        binding.btnToTop.setOnClickListener {
            binding.rvBookItems.smoothScrollToPosition(0)
        }
    }

    private fun initObservers(){
        pokeListViewModel.observeData().observe(
            this,
            Observer {
                binding.srlContainer.isRefreshing = false
                when (it) {
                    is Result.Success -> {
                        binding.pbBooksLoading.remove()
                        booksAdapter.setData(
                            it.data
                        )
                    }
                    is Result.LoadingNewContent -> {
                        pageScrollListener.initFields()
                        booksAdapter.clearData()
                        hideKeyboard(binding.etSearchField)
                        binding.pbBooksLoading.show()
                    }
                    is Result.LoadingMoreContent -> {
                        binding.pbBooksLoading.show()
                    }
                    is Result.Error -> {
                        binding.pbBooksLoading.remove()
                    }
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
            PokemonDetailActivity.BOOK_DATA,
            data
        )
        startActivity(
            intent
        )
    }
}