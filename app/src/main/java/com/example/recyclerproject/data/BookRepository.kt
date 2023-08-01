package com.example.recyclerproject.data

import android.util.Log
import com.example.recyclerproject.model.Book

class BookRepository(
    private val remoteDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) {


    suspend fun getBooks(): List<Book> {
        val localBooks = localDataSource.getAllBooks()
        Log.d("database_size", "getallbooks" + localBooks.size.toString())
        return localBooks.ifEmpty {
            Log.d("database_size", "if empty" + localBooks.size.toString())
            val remoteBooks = fetchBooksFromServer()
            Log.d("database_size", "after fetch" + localBooks.size.toString())
            localDataSource.insertAll(remoteBooks)
           val allBooks =  localDataSource.getAllBooks()
            Log.d("database_size", "after insert all" + allBooks.size.toString())
            remoteBooks
        }
    }

    private suspend fun fetchBooksFromServer(): List<Book> {
        return remoteDataSource.getBooks()
    }
}
