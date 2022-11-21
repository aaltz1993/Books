package a.alt.z.books.ui.search.result

import a.alt.z.books.databinding.ItemViewSearchBookResultBinding
import a.alt.z.books.model.Book
import a.alt.z.books.util.extension.layoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class  SearchBookResultsAdapter constructor(
    private val onClickAction: (url: String) -> Unit
) : ListAdapter<Book, SearchBookResultViewHolder>(SearchBookResultComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBookResultViewHolder {
        return ItemViewSearchBookResultBinding
            .inflate(parent.layoutInflater, parent, false)
            .let { SearchBookResultViewHolder(it, onClickAction) }
    }

    override fun onBindViewHolder(holder: SearchBookResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}