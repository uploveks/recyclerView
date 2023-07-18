package com.example.recyclerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.recyclerproject.databinding.ActivityDetailsBinding
import java.lang.IllegalArgumentException

class DetailsActivity : AppCompatActivity() {
    private lateinit var book: Book
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        book = intent.getSerializableExtra("book") as? Book
            ?: throw IllegalArgumentException("Book object not found")

        populateBookDetails()

    }

    private fun populateBookDetails() {
        binding.bookName.text = book.name
        binding.bookAuthor.text = book.author
        binding.bookType.text = book.type
        binding.isbn.text = book.isbn
        binding.bookFavorite.isChecked = book.isFavorite
        binding.bookFavorite.isEnabled = false
        var bookType = when (book.type) {
            "Financial" -> R.drawable.finance
            "Science Fiction" -> R.drawable.fiction_type
            "Kids" -> R.drawable.children_literature
            else -> {R.drawable.default_clip_art}
        }
        binding.bookTypeIcon.setImageResource(bookType)

        Glide.with(binding.root).load(book.bookImageUrl).error(R.mipmap.ic_launcher).into(binding.bookImage)
        Glide.with(binding.root).load(book.authorImageUrl).error(R.mipmap.ic_launcher).into(binding.authorImage)
    }
}