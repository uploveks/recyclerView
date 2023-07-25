package com.example.recyclerproject



import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.res.TypedArrayUtils.getString
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

class BookAdapter(private val context: Context, private val books: MutableList<Book>, var isStaggeredLayout: Boolean, private val listener: OnBookClickListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {


    companion object {
        private const val TYPE_FINANCIAL_LINEAR = 0
        private const val TYPE_S_F_LINEAR = 1
        private const val TYPE_KIDS_LINEAR = 2
        private const val TYPE_FINANCIAL_STAGGERED = 3
        private const val TYPE_S_F_STAGGERED = 4
        private const val TYPE_KIDS_STAGGERED = 5

    }

    private var booksList: MutableList<Book> = arrayListOf()
    private var filteredBooksList: MutableList<Book> = arrayListOf()


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


                binding.bookFavorite.setOnCheckedChangeListener { button, isChecked ->
                    if (button.isPressed){
                        val book = books[adapterPosition]
                        book.favorite = isChecked
                        notifyItemChanged(adapterPosition)
                    }
                }
            }



        fun bind(book: Book) {
            val bookTypeImage = R.drawable.finance

            binding.bookTypeIcon.setImageResource(bookTypeImage)
            Glide.with(binding.root).load(book.bookImage).error(R.mipmap.ic_launcher).into(binding.bookImage)
            Glide.with(binding.root).load(book.authorImage).error(R.mipmap.ic_launcher).into(binding.authorImage)
            binding.bookName.text = book.bookName
            binding.bookAuthor.text = book.authorName
            binding.bookType.text = book.bookType
            binding.isbn.text = book.isbn

            binding.bookFavorite.isChecked = book.favorite
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

            binding.bookFavorite.setOnCheckedChangeListener { button, isChecked ->
                if (button.isPressed){
                    val book = books[adapterPosition]
                    book.favorite = isChecked
                    notifyItemChanged(adapterPosition)
                }
            }
        }

        fun bind(book: Book) {
            val bookTypeImage = R.drawable.children_literature

            binding.bookTypeIcon.setImageResource(bookTypeImage)
            Glide.with(binding.root).load(book.bookImage).error(R.mipmap.ic_launcher).into(binding.bookImage)
            Glide.with(binding.root).load(book.authorImage).error(R.mipmap.ic_launcher).into(binding.authorImage)
            binding.bookName.text = book.bookName
            binding.bookAuthor.text = book.authorName
            binding.bookType.text = book.bookType
            binding.isbn.text = book.isbn

            binding.bookFavorite.isChecked = book.favorite
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

            binding.bookFavorite.setOnCheckedChangeListener { button, isChecked ->
                if (button.isPressed){
                    val book = books[adapterPosition]
                    book.favorite = isChecked
                    notifyItemChanged(adapterPosition)
                }
            }
        }

        fun bind(book: Book) {
            val bookTypeImage = R.drawable.fantasy

            binding.bookTypeIcon.setImageResource(bookTypeImage)
            Glide.with(binding.root).load(book.bookImage).error(R.mipmap.ic_launcher).into(binding.bookImage)
            Glide.with(binding.root).load(book.authorImage).error(R.mipmap.ic_launcher).into(binding.authorImage)
            binding.bookName.text = book.bookName
            binding.bookAuthor.text = book.authorName
            binding.bookType.text = book.bookType
            binding.isbn.text = book.isbn

            binding.bookFavorite.isChecked = book.favorite
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


            Glide.with(binding.root).load(book.bookImage).error(R.mipmap.ic_launcher).into(binding.bookImage)
            binding.bookAuthor.text = book.authorName
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
            Glide.with(binding.root).load(book.bookImage).error(R.mipmap.ic_launcher).into(binding.bookImage)
            Glide.with(binding.root).load(book.authorImage).error(R.mipmap.ic_launcher).into(binding.authorImage)
            binding.bookAuthor.text = book.authorName
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
            Glide.with(binding.root).load(book.bookImage).error(R.mipmap.ic_launcher).into(binding.bookImage)
            Glide.with(binding.root).load(book.authorImage).error(R.mipmap.ic_launcher).into(binding.authorImage)
            binding.bookAuthor.text = book.authorName
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

            else -> {throw IllegalArgumentException(context.getString(R.string.invalid_view))}
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

            else -> throw IllegalArgumentException(context.getString(R.string.invalid_viewholder)) }
    }

    override fun getItemViewType(position: Int): Int {
        val book = books[position]

        return when {
            !isStaggeredLayout -> {
                when (book.bookType) {
                    context.getString(R.string.finance_type) -> TYPE_FINANCIAL_LINEAR
                    context.getString(R.string.sf_type) -> TYPE_S_F_LINEAR
                    context.getString(R.string.kids_type) -> TYPE_KIDS_LINEAR
                    else ->throw IllegalArgumentException(context.getString(R.string.invalid_view))
                }
            }
            else -> {
                when (book.bookType) {
                    context.getString(R.string.finance_type) -> TYPE_FINANCIAL_STAGGERED
                    context.getString(R.string.sf_type) -> TYPE_S_F_STAGGERED
                    context.getString(R.string.kids_type) -> TYPE_KIDS_STAGGERED
                    else -> throw IllegalArgumentException(context.getString(R.string.invalid_view))
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return books.size
    }

    fun updateBooks(newBooks: List<Book>) {
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString()?.trim() ?: ""
                if (charString.isEmpty()) {
                    filteredBooksList = books.toMutableList()
                } else {
                    val filteredList = ArrayList<Book>()
                    val filterPattern = constraint.toString().lowercase().trim()
                    for (book in books) {
                        if ((book.bookName?.lowercase()?.contains(charString, true) == true) || (book.authorName?.lowercase()?.contains(charString, true) == true)) {
                            filteredList.add(book)
                        }
                    }
                    filteredBooksList = filteredList
                }
                return FilterResults().apply { values = filteredBooksList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                filteredBooksList = results?.values as MutableList<Book>? ?: mutableListOf()
                notifyDataSetChanged()
            }
        }
    }

}

