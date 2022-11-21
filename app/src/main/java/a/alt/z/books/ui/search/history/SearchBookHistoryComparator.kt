package a.alt.z.books.ui.search.history

import a.alt.z.books.model.BookSearchHistory
import androidx.recyclerview.widget.DiffUtil

object SearchBookHistoryComparator : DiffUtil.ItemCallback<BookSearchHistory>() {

    override fun areItemsTheSame(oldItem: BookSearchHistory, newItem: BookSearchHistory): Boolean {
        return oldItem.query == newItem.query
    }

    override fun areContentsTheSame(oldItem: BookSearchHistory, newItem: BookSearchHistory): Boolean {
        return oldItem == newItem
    }
}