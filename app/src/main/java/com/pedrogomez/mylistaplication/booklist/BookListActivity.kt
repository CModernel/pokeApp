package com.pedrogomez.mylistaplication.booklist

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedrogomez.mylistaplication.base.BaseActivity
import com.pedrogomez.mylistaplication.bookdetail.BookDetailActivity
import com.pedrogomez.mylistaplication.booklist.adapter.BookViewHolder
import com.pedrogomez.mylistaplication.booklist.adapter.BooksAdapter
import com.pedrogomez.mylistaplication.booklist.models.adapters.BookDataAdapter
import com.pedrogomez.mylistaplication.booklist.models.bookresponse.Item
import com.pedrogomez.mylistaplication.booklist.models.result.Result
import com.pedrogomez.mylistaplication.booklist.viewmodel.BookListViewModel
import com.pedrogomez.mylistaplication.databinding.ActivityBookListBinding
import com.pedrogomez.mylistaplication.utils.PageScrollListener
import com.pedrogomez.mylistaplication.utils.extensions.remove
import com.pedrogomez.mylistaplication.utils.extensions.show
import org.koin.android.viewmodel.ext.android.viewModel

class BookListActivity : BaseActivity(),
    BookViewHolder.OnClickItemListener{

    private val bookListViewModel : BookListViewModel by viewModel()

    private lateinit var binding: ActivityBookListBinding

    private lateinit var booksAdapter : BooksAdapter

    private var counter : CountDownTimer? = null

    private lateinit var pageScrollListener : PageScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookListBinding.inflate(layoutInflater)
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
                    bookListViewModel.getNewBooks(
                        it.toString()
                    )
                }

            }.start()
        }
    }

    private fun initRecyclerView() {
        booksAdapter = BooksAdapter(this@BookListActivity)
        binding.rvBookItems.apply{
            adapter = booksAdapter
            layoutManager = LinearLayoutManager(this@BookListActivity)
        }
        binding.srlContainer.setOnRefreshListener {
            bookListViewModel.getNewBooks(
                binding.etSearchField.text.toString()
            )
        }
        pageScrollListener = object : PageScrollListener(
            binding.rvBookItems.layoutManager as LinearLayoutManager
        ){
            override fun onLoadMore(
                currentPage: Int
            ) {
                bookListViewModel.loadMoreBooks(
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
        bookListViewModel.observeData().observe(
            this,
            Observer {
                binding.srlContainer.isRefreshing = false
                when (it) {
                    is Result.Success -> {
                        binding.pbBooksLoading.remove()
                        booksAdapter.setData(
                            it.data.items
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

    override fun goToBookDetail(data: Item) {
        val intent = Intent(
            this,
            BookDetailActivity::class.java
        )
        intent.putExtra(
            BookDetailActivity.BOOK_DATA,
            BookDataAdapter().getItemAsBookItem(data)
        )
        startActivity(
            intent
        )
    }
}