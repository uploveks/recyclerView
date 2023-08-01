package com.example.recyclerproject.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.recyclerproject.BookApplication
import com.example.recyclerproject.data.BookDataBase
import com.example.recyclerproject.data.NetworkDataSourceImplementation
import com.example.recyclerproject.data.LocalDataSourceImplementation
import com.example.recyclerproject.data.BookRepository
import com.example.recyclerproject.model.Book
import com.example.recyclerproject.network.ApiInterface
import com.example.recyclerproject.network.RetrofitInstance
import com.example.recyclerproject.ui.MainActivity.Companion.ERROR_MESSAGE_TEXT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BookViewModel(private val repository: BookRepository) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                Log.d("text_context", "factory initializer")
                val repository = BookRepository(
                    NetworkDataSourceImplementation(
                        RetrofitInstance.retrofit.create(
                            ApiInterface::class.java
                        )
                    ),
                    LocalDataSourceImplementation(
                        BookDataBase.getDataBase(BookApplication.getApplicationContext()!!)
                            ?.bookDao()!!
                    )
                )
                Log.d("text_context", "after val repository")
                BookViewModel(
                    repository
                )
            }
            Log.d("text_context", "after initializer")
        }
    }


    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> get() = _progressBarVisibility

    fun fetchBooksFromServer() {
        _progressBarVisibility.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getBooks()
                Log.d("text_context", "repository get books")
                withContext(Dispatchers.Main) {
                    _progressBarVisibility.value = false
                    if (response.isNotEmpty()) {
                        _books.value = response
                    } else {
                        showError(ERROR_MESSAGE_TEXT)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _progressBarVisibility.value = false
                    showError("$ERROR_MESSAGE_TEXT: ${e.message}")
                }
            }
        }
    }

    private fun showError(message: String) {
        _errorMessage.value = message
    }
}
