import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.recyclerproject.databinding.ActivityDetailsBinding
import java.lang.IllegalArgumentException
import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import com.example.recyclerproject.Book
import com.example.recyclerproject.MainActivity
import com.example.recyclerproject.R


class DetailsActivity : AppCompatActivity() {
    private lateinit var book: Book
    private lateinit var binding: ActivityDetailsBinding
    private val CAMERA_PERMISSION_REQUEST_CODE = 1001
    private val CAMERA_REQUEST_CODE = 1002
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        book = intent.getSerializableExtra("book") as? Book
            ?: throw IllegalArgumentException("Book object not found")

        populateBookDetails()

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.bookImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                requestCameraPermission()
            }
        }

        binding.shareButton.setOnClickListener {
            shareBookTitle()
        }

        binding.callDialer.setOnClickListener {
            openDialer()
        }

    }

    private fun populateBookDetails() {
        binding.bookName.text = book.bookName
        binding.bookAuthor.text = book.authorName
        binding.bookType.text = book.bookType
        binding.isbn.text = book.isbn
        if (book.favorite) {
            binding.bookFavorite.setImageResource(R.drawable.ic_favorite_selected)
        } else {
            binding.bookFavorite.setImageResource(R.drawable.ic_favorite_unselected)
        }

        binding.bookFavorite.isEnabled = false
        var bookType = when (book.bookType) {
            "Financial" -> R.drawable.finance
            "Science Fiction" -> R.drawable.fiction_type
            "Kids" -> R.drawable.children_literature
            else -> {R.drawable.default_clip_art}
        }
        binding.bookTypeIcon.setImageResource(bookType)

        Glide.with(binding.root).load(book.bookImage).error(R.mipmap.ic_launcher).into(binding.bookImage)
        Glide.with(binding.root).load(book.authorImage).error(R.mipmap.ic_launcher).into(binding.authorImage)
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            val imageBitmap: Bitmap? = data?.extras?.get("data") as? Bitmap
            if (imageBitmap != null) {
                binding.authorImage.setImageBitmap(imageBitmap)
            }
        }
    }

    private fun shareBookTitle() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, book.bookName)
        startActivity(Intent.createChooser(shareIntent, "Share Book Title"))
    }

    private fun openDialer() {
        val phoneNumber = "1234567890"
        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(dialIntent)
    }

}