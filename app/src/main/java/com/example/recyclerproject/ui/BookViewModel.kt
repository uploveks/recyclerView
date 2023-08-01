import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerproject.data.BookDataSourceImplementation
import com.example.recyclerproject.data.BookLocalDataSource
import com.example.recyclerproject.data.BookRepository
import com.example.recyclerproject.model.Book
import com.example.recyclerproject.network.ApiInterface
import com.example.recyclerproject.ui.MainActivity.Companion.ERROR_MESSAGE_TEXT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookViewModel(private val localDataSource: BookLocalDataSource, private val apiInterface: ApiInterface) : ViewModel() {

    private val bookRepository = BookRepository(
        BookDataSourceImplementation(localDataSource, apiInterface),
        localDataSource
    )

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
                val response = bookRepository.getBooks()
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
