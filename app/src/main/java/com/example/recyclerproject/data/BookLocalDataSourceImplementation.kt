package com.example.recyclerproject.data

import com.example.recyclerproject.model.Book

class BookLocalDataSourceImplementation(private val bookDao: BookDao) : BookLocalDataSource {

    override suspend fun getAllBooks(): List<Book> {
        return bookDao.getAllBooks().map { it.toBook() }
    }

    override suspend fun insertAll(books: List<Book>) {
        val bookEntities = books.map { BookEntity.fromBook(it) }
        bookDao.insertAll(bookEntities)
    }

    override suspend fun saveBooks(books: List<Book>) {
        insertAll(books)
    }
}
