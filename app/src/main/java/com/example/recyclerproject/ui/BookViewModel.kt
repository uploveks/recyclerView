import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerproject.model.Book
import com.example.recyclerproject.ui.MainActivity.Companion.ERROR_MESSAGE_TEXT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookViewModel : ViewModel() {

    private val bookRepository = BookRepository()

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
                    //Log.d("view_model_books", "request made")
                    if (response.isSuccessful) {
                        //Log.d("view_model_books", "request successfull")
                        val books = response.body()
                        if (books != null) {
                            //Log.d("view_model_books", "books not null")
                            _books.value = books ?: emptyList()
                        }
                    } else {
                        //Log.d("view_model_books", "showError(ERROR_MESSAGE_TEXT)")
                        showError(ERROR_MESSAGE_TEXT)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _progressBarVisibility.value = false
                    //Log.d("view_model_books", e.message.toString())
                    _errorMessage.value = "$ERROR_MESSAGE_TEXT: ${e.message}"
                }
            }
        }
    }

    private fun showError(message: String) {
        _errorMessage.value = message
    }
}
