package com.example.recyclerproject


import ApiInterface
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
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
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progressBar
        progressBar?.visibility = View.VISIBLE

        bookAdapter = BookAdapter(mutableListOf(), isStaggeredLayout, this)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = bookAdapter
            itemAnimator = DefaultItemAnimator()
        }


        binding.switchLayout.setOnCheckedChangeListener { _ , isChecked ->
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
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

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
            .baseUrl("https://jsonkeeper.com/")
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
                        showError("Error fetching books from server")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    Log.e("Network", "Error fetching books: ${e.message}")
                    showError("Error fetching books: ${e.message}")
                }
            }
        }
    }


    private fun showError(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        binding.errorLayout.visibility = View.VISIBLE
        Glide.with(this@MainActivity)
            .load("https://icon-library.com/images/error-icon-transparent/error-icon-transparent-5.jpg")
            .error(R.mipmap.ic_launcher)
            .into(binding.errorImage)
        binding.retryButton.setOnClickListener {
            binding.errorLayout.visibility = View.GONE
            fetchBooksFromServer()
        }
        binding.switchLayout.visibility = View.GONE
    }

    override fun onBookClicked(book: Book) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("book", book)
        startActivity(intent)
    }
}