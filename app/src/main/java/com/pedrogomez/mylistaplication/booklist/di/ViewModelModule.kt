package com.pedrogomez.mylistaplication.booklist.di

import com.pedrogomez.mylistaplication.booklist.viewmodel.BookListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { BookListViewModel(get()) }
}