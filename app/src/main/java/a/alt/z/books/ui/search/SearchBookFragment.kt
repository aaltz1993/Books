package a.alt.z.books.ui.search

import a.alt.z.books.R
import a.alt.z.books.databinding.FragmentSearchBookBinding
import a.alt.z.books.ui.search.history.SearchBookHistoriesAdapter
import a.alt.z.books.ui.search.result.SearchBookResultsFragment
import a.alt.z.books.util.extension.hideKeyboard
import a.alt.z.books.util.extension.showKeyboard
import a.alt.z.books.util.view_binding.viewBinding
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchBookFragment : Fragment(R.layout.fragment_search_book) {

    private val binding by viewBinding(FragmentSearchBookBinding::bind)

    private val viewModel by viewModels<SearchBookViewModel>()

    private val searchHistoriesAdapter = SearchBookHistoriesAdapter(
        onQueryClickAction = { searchHistory ->
            binding.searchTextView.isVisible = false

            binding.searchQueryEditText.apply {
                setText(searchHistory.query)

                clearFocus()

                viewModel.search(searchHistory.query)
            }

            binding.searchResultsFragmentContainer.isVisible = true
        },
        onDeleteButtonClickAction =  { searchHistory -> viewModel.deleteSearchHistory(searchHistory) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            binding.apply {
                searchTextView.isVisible = true

                searchQueryEditText.text.clear()
                searchQueryEditText.clearFocus()

                searchResultsFragmentContainer.isVisible = false
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupObservers()
    }

    private fun initViews() {
        binding.apply {
            searchQueryEditText.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    searchTextView.isVisible = false
                }

                (view as? EditText)?.apply {
                    if (hasFocus) showKeyboard() else hideKeyboard()
                }
            }

            searchQueryEditText.doAfterTextChanged {
                clearQueryButton.isVisible = !it.isNullOrEmpty()
            }

            searchQueryEditText.setOnEditorActionListener { editText, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val query = editText.text.toString()

                    if (query.isNotBlank()) {
                        viewModel.search(query)

                        editText.clearFocus()
                    }
                }

                actionId == EditorInfo.IME_ACTION_SEARCH
            }

            clearQueryButton.setOnClickListener {
                searchQueryEditText.text.clear()
            }

            clearAllRecentSearchesTextView.setOnClickListener {
                viewModel.clearSearchHistories()
            }

            searchHistoriesRecyclerView.adapter = searchHistoriesAdapter
            searchHistoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.searchHistories.collect { searchHistories ->
                        searchHistoriesAdapter.submitList(searchHistories)
                    }
                }

                launch {
                    viewModel.loading.collect {
                        binding.searchResultsFragmentContainer.isVisible = true

                        childFragmentManager
                            .takeIf { it.findFragmentById(R.id.search_results_fragment_container) == null }
                            ?.commit { replace(R.id.search_results_fragment_container, SearchBookResultsFragment()) }
                    }
                }
            }
        }
    }
}