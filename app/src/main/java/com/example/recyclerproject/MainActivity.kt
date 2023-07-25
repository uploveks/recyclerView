package com.example.recyclerproject


import ApiInterface
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.SearchEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.recyclerproject.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class MainActivity : AppCompatActivity(), OnBookClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bookAdapter: BookAdapter
    private var isStaggeredLayout = false
    private var progressBar: ProgressBar? = null

    companion object {
        const val BOOK_BUNDLE = "book"
        const val ERROR_ICON_URL =
            "https://icon-library.com/images/error-icon-transparent/error-icon-transparent-5.jpg"
        const val LOG_TAG = "Network"
        const val ERROR_MESSAGE_TEXT = "Error fetching books from server"
        const val JSON_KEEPER = "https://jsonkeeper.com/"
        const val ERROR_MESSAGE = "Error fetching books:"
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progressBar
        progressBar?.visibility = View.VISIBLE

        bookAdapter = BookAdapter(this, mutableListOf(), isStaggeredLayout, this)

        binding.recyclerView.apply {

            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = bookAdapter
            itemAnimator = DefaultItemAnimator()
        }


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

        fetchBooksFromServer()

    }

    private fun fetchBooksFromServer() {
        binding.progressBar.visibility = View.VISIBLE

        val trustAllCertificates = arrayOf<TrustManager>(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCertificates, SecureRandom())

        val unsafeOkHttpClient = OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCertificates[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(JSON_KEEPER)
            .addConverterFactory(GsonConverterFactory.create())
            .client(unsafeOkHttpClient)
            .build()

        val apiInterface = retrofit.create(ApiInterface::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiInterface.getBooks()
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val books = response.body()
                        if (books != null) {
                            bookAdapter.updateBooks(books)
                        }
                    } else {
                        showError(ERROR_MESSAGE_TEXT)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    Log.e(LOG_TAG, ERROR_MESSAGE + "${e.message}")
                    showError(ERROR_MESSAGE + "${e.message}")
                }
            }
        }
    }


    private fun showError(message: String) {
        //Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        binding.errorRetryLayout.root.visibility = View.VISIBLE
        Glide.with(this@MainActivity)
            .load(ERROR_ICON_URL)
            .error(R.mipmap.ic_launcher)
            .into(binding.errorRetryLayout.errorImage)
        binding.errorRetryLayout.retryButton.setOnClickListener {
            binding.errorRetryLayout.root.visibility = View.GONE
            fetchBooksFromServer()
        }
        binding.switchLayout.visibility = View.GONE
    }

    override fun onBookClicked(book: Book) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(BOOK_BUNDLE, book)
        startActivity(intent)
    }

    /* override fun onQueryTextSubmit(constraint: String?): Boolean {
        bookAdapter.filter.filter(constraint)
        return false
    }

    override fun onQueryTextChange(constraint: String?): Boolean {
        bookAdapter.filter.filter(constraint)
        return false
    }*/

    /* override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu?.findItem(R.id.app_bar_search)
        val searchView = item?.actionView as SearchView

        searchView.setOnQueryTextListener(this)

        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                bookAdapter.filter.filter("")
                return true
            }

            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                return true
            }
        })
        return true
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu?.findItem(R.id.app_bar_search)
        val searchView = item?.actionView as SearchView
        //searchView.setOnQueryTextListener(this)
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                bookAdapter.filter.filter(newText)
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}

