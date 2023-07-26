package com.example.recyclerproject.ui

import com.example.recyclerproject.model.Book

interface OnBookClickListener {
    fun onBookClicked(book: Book)
}