package a.alt.z.books.ui.search

import a.alt.z.books.BuildConfig
import a.alt.z.books.domain.use_case.search_book.*
import a.alt.z.books.model.BookSearchHistory
import a.alt.z.books.model.BookSearchResults
import a.alt.z.books.util.result.Result
import a.alt.z.books.util.result.successOr
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchBookViewModel @Inject constructor(
    private val searchBookUseCase: SearchBookUseCase,
    private val saveBookSearchHistoryUseCase: SaveBookSearchHistoryUseCase,
    getBookSearchHistoriesUseCase: GetBookSearchHistoriesUseCase,
    private val deleteBookSearchHistoryUseCase: DeleteBookSearchHistoryUseCase,
    private val clearBookSearchHistoriesUseCase: ClearBookSearchHistoriesUseCase
) : ViewModel() {

    sealed class LoadParams {

        data class Refresh(val query: String): LoadParams()

        data class Append(val query: String, val index: Int): LoadParams()
    }

    private val refresh = MutableStateFlow<LoadParams.Refresh?>(null)

    private val append = MutableStateFlow<LoadParams.Append?>(null)

    /* PATH #1 LOADING */
    private val _loading = MutableSharedFlow<Unit>()
    val loading = _loading.asSharedFlow()

    /* PATH #2 VALID RESULT */
    private val _searchResults: StateFlow<BookSearchResults?> = refresh.filterNotNull()
        .combine(append) { refresh, append ->
            if (refresh.query != append?.query) {
                _loading.emit(Unit)
                SearchBookParams(refresh.query)
            } else {
                SearchBookParams(append.query, append.index)
            }
        }
        .mapLatest { searchBookUseCase(it) }
        .map { result ->
            if (result is Result.Error) {
                _error.emit(result.exception)
            }
            result.successOr(null)
        }
        .filterNotNull()
        .runningReduce { accumulator, refreshOrAppend ->
            if (accumulator.query == refreshOrAppend.query) {
                if (refreshOrAppend.totalCount != 0) {
                    // PATH #1 APPEND
                    accumulator.copy(data = (accumulator.data + refreshOrAppend.data).distinctBy { it.id })
                } else {
                    // PATH #2 APPEND + NO MORE DATA
                    accumulator
                }
            } else {
                // PATH #3 REFRESH OR REFRESH BUT NO DATA
                refreshOrAppend
            }
        }
        // FOR UNIT TEST
        .apply { if (!BuildConfig.DEBUG) flowOn(Dispatchers.Default) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val searchResults: Flow<BookSearchResults> = _searchResults.filterNotNull()

    /* PATH #3 ERROR */
    private val _error = MutableSharedFlow<Exception>()
    val error = _error.asSharedFlow()

    fun search(query: String) {
        viewModelScope.launch {
            refresh.emit(LoadParams.Refresh(query))

            saveSearchHistory(query)
        }
    }

    fun load(index: Int) {
        viewModelScope.launch {
            val query= requireNotNull(refresh.value?.query)

            append.emit(LoadParams.Append(query, index))
        }
    }

    // BOOK SEARCH HISTORY
    private fun saveSearchHistory(query: String) {
        viewModelScope.launch {
            saveBookSearchHistoryUseCase(BookSearchHistory(query)).let { result ->
                if (result is Result.Error) {
                    Timber.e(result.exception)
                }
            }
        }
    }

    val searchHistories: StateFlow<List<BookSearchHistory>> = getBookSearchHistoriesUseCase(Unit)
        .map { result ->
            if (result is Result.Error) {
                Timber.e(result.exception)
            }
            result.successOr(emptyList()).reversed()
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun deleteSearchHistory(searchHistory: BookSearchHistory) {
        viewModelScope.launch {
            deleteBookSearchHistoryUseCase(searchHistory).let { result ->
                if (result is Result.Error) {
                    Timber.e(result.exception)
                }
            }
        }
    }

    fun clearSearchHistories() {
        viewModelScope.launch {
            clearBookSearchHistoriesUseCase(Unit).let { result ->
                if (result is Result.Error) {
                    Timber.e(result.exception)
                }
            }
        }
    }
}