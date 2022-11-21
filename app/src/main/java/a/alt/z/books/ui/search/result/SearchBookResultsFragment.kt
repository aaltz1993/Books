package a.alt.z.books.ui.search.result

import a.alt.z.books.R
import a.alt.z.books.databinding.FragmentSearchBookResultsBinding
import a.alt.z.books.ui.search.SearchBookViewModel
import a.alt.z.books.util.view_binding.viewBinding
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException

@AndroidEntryPoint
class SearchBookResultsFragment : Fragment(R.layout.fragment_search_book_results) {

    private val binding by viewBinding(FragmentSearchBookResultsBinding::bind)

    private val viewModel by viewModels<SearchBookViewModel>(ownerProducer = { requireParentFragment() })

    private val adapter = SearchBookResultsAdapter { url ->
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (exception: Exception) {
            Timber.e(exception)
            // TODO
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupObservers()
    }

    private fun initViews() {
        binding.apply {
            searchResultsRecyclerView.adapter = adapter
            searchResultsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            searchResultsRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastCompletelyVisibleItemPosition = (recyclerView.layoutManager as? LinearLayoutManager)?.findLastCompletelyVisibleItemPosition()

                    if (lastCompletelyVisibleItemPosition != null && lastCompletelyVisibleItemPosition >= adapter.itemCount - 10) {
                        viewModel.load(adapter.itemCount)
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loading.collect {
                        binding.loadingView.apply {
                            root.isVisible = true

                            (loadingIndicatorImageView.drawable as? AnimatedVectorDrawable)?.start()
                        }
                    }
                }

                launch {
                    viewModel.searchResults.collectLatest { results ->
                        binding.apply {
                            searchResultsTotalCountTextView.text = requireContext().getString(
                                R.string.search_results_total_count,
                                results.totalCount
                            )

                            adapter.submitList(results.data) {
                                loadingView.root.isVisible = false
                            }

                            noSearchResultView.root.isVisible = results.data.isEmpty()
                        }
                    }
                }

                launch {
                    viewModel.error.collect { exception ->
                        Timber.e(exception)

                        binding.loadingView.root.isVisible = false

                        val message = when (exception) {
                            is UnknownHostException -> requireContext().getString(R.string.network_connection_failed_message)
                            else -> requireContext().getString(R.string.unexpected_error_message)
                        }

                        Snackbar
                            .make(binding.root, message, Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }
}