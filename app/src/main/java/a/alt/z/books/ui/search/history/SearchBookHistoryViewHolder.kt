package a.alt.z.books.ui.search.history

import a.alt.z.books.databinding.ItemViewSearchBookHistoryBinding
import a.alt.z.books.model.BookSearchHistory
import androidx.recyclerview.widget.RecyclerView

class SearchBookHistoryViewHolder constructor(
    private val binding: ItemViewSearchBookHistoryBinding,
    private val onQueryClickAction: (BookSearchHistory) -> Unit,
    private val onDeleteButtonClickAction: (BookSearchHistory) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(searchHistory: BookSearchHistory) {
        binding.apply {
            root.setOnClickListener { onQueryClickAction(searchHistory) }

            searchedQueryTextView.text = searchHistory.query

            deleteSearchHistoryImageView.setOnClickListener { onDeleteButtonClickAction(searchHistory) }
        }
    }
}