package a.alt.z.books.ui.search.history

import a.alt.z.books.databinding.ItemViewSearchBookHistoryBinding
import a.alt.z.books.model.BookSearchHistory
import a.alt.z.books.util.extension.layoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class SearchBookHistoriesAdapter constructor(
    private val onQueryClickAction: (BookSearchHistory) -> Unit,
    private val onDeleteButtonClickAction: (BookSearchHistory) -> Unit
) : ListAdapter<BookSearchHistory, SearchBookHistoryViewHolder>(SearchBookHistoryComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBookHistoryViewHolder {
        return ItemViewSearchBookHistoryBinding
            .inflate(parent.layoutInflater, parent, false)
            .let { SearchBookHistoryViewHolder(it, onQueryClickAction, onDeleteButtonClickAction) }
    }

    override fun onBindViewHolder(holder: SearchBookHistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}