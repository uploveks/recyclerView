package com.example.recyclerproject



import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.lang.IllegalArgumentException
import kotlin.collections.MutableList
import com.example.recyclerproject.databinding.ItemFinanceBookBinding
import com.example.recyclerproject.databinding.ItemSfBookBinding
import com.example.recyclerproject.databinding.ItemKidsBookBinding
import com.example.recyclerproject.databinding.FinanceStaggeredBinding
import com.example.recyclerproject.databinding.SfStaggeredBinding
import com.example.recyclerproject.databinding.KidsStaggeredBinding
import com.example.recyclerproject.OnBookClickListener

class BookAdapter(private val books: MutableList<Book>, var isStaggeredLayout: Boolean, private val listener: OnBookClickListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        private const val TYPE_FINANCIAL_LINEAR = 0
        private const val TYPE_S_F_LINEAR = 1
        private const val TYPE_KIDS_LINEAR = 2
        private const val TYPE_FINANCIAL_STAGGERED = 3
        private const val TYPE_S_F_STAGGERED = 4
        private const val TYPE_KIDS_STAGGERED = 5

    }

    inner class FinanceLinearViewHolder(private val binding: ItemFinanceBookBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.bookDetails.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val book = books[position]
                    listener.onBookClicked(book)
                }
            }

                binding.bookTrash.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val deletedBook = books[position]
                        books.removeAt(position)
                        notifyItemRemoved(position)
                    }
                }

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
            val bookTypeImage = R.drawable.finance

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

    inner class KidsLinearViewHolder(private val binding: ItemKidsBookBinding) : RecyclerView.ViewHolder(binding.root) {
        init {

            binding.bookDetails.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val book = books[position]
                    listener.onBookClicked(book)
                }
            }

            binding.bookTrash.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val deletedBook = books[position]
                    books.removeAt(position)
                    notifyItemRemoved(position)
                }
            }

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
            val bookTypeImage = R.drawable.children_literature

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

    inner class SFLinearViewHolder(private val binding: ItemSfBookBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.bookDetails.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val book = books[position]
                    listener.onBookClicked(book)
                }
            }

            binding.bookTrash.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val deletedBook = books[position]
                    books.removeAt(position)
                    notifyItemRemoved(position)
                }
            }

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
            val bookTypeImage = R.drawable.fantasy

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

    inner class FinanceStaggeredViewHolder(private val binding: FinanceStaggeredBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.bookDetails.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val book = books[position]
                    listener.onBookClicked(book)
                }
            }
        }
        fun bind(book: Book) {


            Glide.with(binding.root).load(book.bookImageUrl).error(R.mipmap.ic_launcher).into(binding.bookImage)
            binding.bookAuthor.text = book.author
            binding.isbn.text = book.isbn
        }
    }

    inner class SFStaggeredViewHolder(private val binding: SfStaggeredBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.bookDetails.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val book = books[position]
                    listener.onBookClicked(book)
                }
            }
        }
        fun bind(book: Book) {
            Glide.with(binding.root).load(book.bookImageUrl).error(R.mipmap.ic_launcher).into(binding.bookImage)
            Glide.with(binding.root).load(book.authorImageUrl).error(R.mipmap.ic_launcher).into(binding.authorImage)
            binding.bookAuthor.text = book.author
            binding.isbn.text = book.isbn
        }
    }

    inner class KidsStaggeredViewHolder(private val binding: KidsStaggeredBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.bookDetails.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val book = books[position]
                    listener.onBookClicked(book)
                }
            }
        }
        fun bind(book: Book) {
            Glide.with(binding.root).load(book.bookImageUrl).error(R.mipmap.ic_launcher).into(binding.bookImage)
            Glide.with(binding.root).load(book.authorImageUrl).error(R.mipmap.ic_launcher).into(binding.authorImage)
            binding.bookAuthor.text = book.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_FINANCIAL_LINEAR -> {
                val binding = ItemFinanceBookBinding.inflate(inflater, parent, false)
                FinanceLinearViewHolder(binding)
            }
            TYPE_S_F_LINEAR -> {
                val binding = ItemSfBookBinding.inflate(inflater, parent, false)
                SFLinearViewHolder(binding)
            }
            TYPE_KIDS_LINEAR -> {
                val binding = ItemKidsBookBinding.inflate(inflater, parent, false)
                KidsLinearViewHolder(binding)
            }
            TYPE_FINANCIAL_STAGGERED -> {
                val binding = FinanceStaggeredBinding.inflate(inflater, parent, false)
                FinanceStaggeredViewHolder(binding)
            }
            TYPE_S_F_STAGGERED -> {
                val binding = SfStaggeredBinding.inflate(inflater, parent, false)
                SFStaggeredViewHolder(binding)
            }
            TYPE_KIDS_STAGGERED -> {
                val binding = KidsStaggeredBinding.inflate(inflater, parent, false)
                KidsStaggeredViewHolder(binding)
            }

            else -> {throw IllegalArgumentException("Invalid view type")}
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val book = books[position]
        when (holder) {
            is FinanceLinearViewHolder -> holder.bind(book)
            is SFLinearViewHolder -> holder.bind(book)
            is KidsLinearViewHolder -> holder.bind(book)
            is FinanceStaggeredViewHolder -> holder.bind(book)
            is SFStaggeredViewHolder -> holder.bind(book)
            is KidsStaggeredViewHolder -> holder.bind(book)

            else -> throw IllegalArgumentException("Invalid ViewHolder") }
    }

    override fun getItemViewType(position: Int): Int {
        val book = books[position]

        return when {
            !isStaggeredLayout -> {
                when (book.type) {
                    "Financial" -> TYPE_FINANCIAL_LINEAR
                    "Science Fiction" -> TYPE_S_F_LINEAR
                    "Kids" -> TYPE_KIDS_LINEAR
                    else ->throw IllegalArgumentException("Invalid book type")
                }
            }
            else -> {
                when (book.type) {
                    "Financial" -> TYPE_FINANCIAL_STAGGERED
                    "Science Fiction" -> TYPE_S_F_STAGGERED
                    "Kids" -> TYPE_KIDS_STAGGERED
                    else -> throw IllegalArgumentException("Invalid book type")
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return books.size
    }

}