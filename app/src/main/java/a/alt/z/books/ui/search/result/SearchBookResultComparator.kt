package a.alt.z.books.ui.search.result

import a.alt.z.books.model.Book
import androidx.recyclerview.widget.DiffUtil

object SearchBookResultComparator : DiffUtil.ItemCallback<Book>() {

    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }
}