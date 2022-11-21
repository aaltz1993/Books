package a.alt.z.books.ui.search.result

import a.alt.z.books.R
import a.alt.z.books.databinding.ItemViewSearchBookResultBinding
import a.alt.z.books.model.Book
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import timber.log.Timber

class SearchBookResultViewHolder constructor(
    private val binding: ItemViewSearchBookResultBinding,
    private val onClickAction: (url: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(book: Book) {
        binding.apply {
            root.setOnClickListener {
                book.url?.let(onClickAction)
            }

            Glide.with(coverImageView)
                .load(book.imageUrl)
                .placeholder(R.drawable.drawable_book_cover_placeholder)
                .centerCrop()
                .transform(GranularRoundedCorners(0F, 8F, 8F, 0F))
                .into(coverImageView)

            Timber.d("title = ${book.title}, imageUrl = ${book.imageUrl}")

            titleTextView.text = book.title

            authorsTextView.isVisible = book.authors.isNotEmpty()
            authorsTextView.text = String.format(
                titleTextView.context.getString(R.string.authors_format),
                book.authors.joinToString()
            )

            publisherTextView.isVisible = !book.publisher.isNullOrBlank()
            publisherTextView.text = book.publisher

            val publishedDateString = book.publishedDate
                ?.let { SearchBookResultDateFormatter.format(it) }
                ?: book.publishedYear?.let { SearchBookResultDateFormatter.format(it) }

            publishedDateString
                ?.let {
                    publishedDateTextView.text = String.format(
                        titleTextView.context.getString(R.string.published_date_format),
                        publishedDateString
                    )
                }
                .let { publishedDateTextView.isVisible = it != null }
        }
    }
}

