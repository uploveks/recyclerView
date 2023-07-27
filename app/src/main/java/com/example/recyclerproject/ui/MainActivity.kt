package com.example.recyclerproject.ui


import BookViewModel
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.recyclerproject.R
import com.example.recyclerproject.databinding.ActivityMainBinding
import com.example.recyclerproject.model.Book


class MainActivity : AppCompatActivity(), OnBookClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bookAdapter: BookAdapter
    private var isStaggeredLayout = false
    private var isFavoriteBooks = false
    private var progressBar: ProgressBar? = null
    private val bookViewModel: BookViewModel by viewModels()


    companion object {
        const val BOOK_BUNDLE = "book"
        const val ERROR_ICON_URL =
            "https://icon-library.com/images/error-icon-transparent/error-icon-transparent-5.jpg"
        const val ERROR_MESSAGE_TEXT = "Error fetching books from server"
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progressBar
        progressBar?.visibility = View.VISIBLE

        bookAdapter = BookAdapter(this, mutableListOf(), isStaggeredLayout, this)
        setupAdapter()
        setupViewLayout()
        setupSearchBar()
        setupObservers()
    }

    private fun setupAdapter() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = bookAdapter
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setupViewLayout() {
        binding.switchLayout.setOnCheckedChangeListener { _, isChecked ->
            isStaggeredLayout = isChecked
            if (isStaggeredLayout) {
                val staggeredGridLayoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                binding.recyclerView.layoutManager = staggeredGridLayoutManager
            } else {
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
            }
            bookAdapter.isStaggeredLayout = isStaggeredLayout

            bookAdapter.notifyDataSetChanged()
        }
    }

    private fun setupSearchBar() {
        val editTextSearch = binding.searchBox.searchEditText
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                bookAdapter.filter.filter(s.toString())
            }
        })
    }

    /*private fun filterFavoriteBooks() {
        val favoriteListButton = binding.favoriteBooks.setOnClickListener {
            val onlyFavorites = binding.favoriteBooks.isClic
        }
    }*/

    private fun setupObservers() {
        bookViewModel.books.observe(this) { books ->
            bookAdapter.updateBooks(books)
        }

        bookViewModel.errorMessage.observe(this) {
            showError()
        }

        bookViewModel.progressBarVisibility.observe(this) { isVisible ->
            if (isVisible) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        bookViewModel.fetchBooksFromServer()
    }

    private fun showError() {
        binding.errorRetryLayout.root.visibility = View.VISIBLE
        Glide.with(this@MainActivity)
            .load(ERROR_ICON_URL)
            .error(R.mipmap.ic_launcher)
            .into(binding.errorRetryLayout.errorImage)
        binding.errorRetryLayout.retryButton.setOnClickListener {
            binding.errorRetryLayout.root.visibility = View.GONE
            bookViewModel.fetchBooksFromServer()
        }
        binding.switchLayout.visibility = View.GONE
    }

    override fun onBookClicked(book: Book) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(BOOK_BUNDLE, book)
        startActivity(intent)
    }

}

