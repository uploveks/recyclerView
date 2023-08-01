package com.example.recyclerproject.data

import android.util.Log
import com.example.recyclerproject.model.Book

class LocalDataSourceImplementation(private val bookDao: BookDao) : LocalDataSource {

    override suspend fun getAllBooks(): List<Book> {
        return bookDao.getAllBooks().map { it.toBook() }
    }

    override suspend fun insertAll(books: List<Book>) {
        val bookEntities = books.map { BookEntity.fromBook(it) }
        Log.d("database_size","insertAll called from implementation "  + bookEntities.size)
        bookDao.insertAll(bookEntities)
    }

    override suspend fun saveBooks(books: List<Book>) {
        insertAll(books)
    }
}
