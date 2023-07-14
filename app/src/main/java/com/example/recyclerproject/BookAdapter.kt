package com.example.recyclerproject


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerproject.databinding.ItemBookBinding
import com.bumptech.glide.Glide
import kotlin.collections.MutableList

class BookAdapter(private val books: MutableList<Book>): RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    inner class BookViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isChecked = false
        init {
            binding.bookTrash.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val deletedBook = books[position]
                    books.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
/*
            binding.bookFavorite.setOnCheckedChangeListener { _ , isChecked: Boolean ->
                //if compound button is pressed cartea de la pozitia curenta

                //si cand apasa pe buto si cand e setat programabil deci treb sa verific daca schimbarea se face numai cand fac eu pressed
                // daca apas eu - setez la cartea respectiva favoritul true sau false si dau notify item changed
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val book = books[position]
                    if (book.isFavorite != isChecked) {
                        book.isFavorite = isChecked
                        notifyItemChanged(position)
                    }
                }
            }*/

            binding.bookFavorite.setOnCheckedChangeListener(null)
            binding.bookFavorite.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val book = books[position]
                    if (book.isFavorite != isChecked) {
                        book.isFavorite = isChecked
                        notifyItemChanged(position)
                    }
                }
            }
        }

        fun bind(book: Book) {

            val bookTypeImage = when (book.type) {
                "Adventure" -> R.drawable.adventure
                "Children Literature" -> R.drawable.children_literature
                "Comedy" -> R.drawable.comedy_type
                "Fantasy" -> R.drawable.fantasy
                "Fiction" -> R.drawable.fiction_type
                "Horror" -> R.drawable.horror
                "Classic" -> R.drawable.classic
                else -> {R.drawable.default_clip_art}
            }
            binding.bookTypeIcon.setImageResource(bookTypeImage)
            Glide.with(binding.root).load(book.bookImageUrl).error(R.mipmap.ic_launcher).into(binding.bookImage)
            Glide.with(binding.root).load(book.authorImageUrl).error(R.mipmap.ic_launcher).into(binding.authorImage)
            binding.bookName.text = book.name
            binding.bookAuthor.text = book.author
            binding.bookType.text = book.type
            binding.isbn.text = book.isbn

            binding.bookFavorite.isChecked = book.isFavorite

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookBinding.inflate(inflater, parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int {
        return books.size
    }
}