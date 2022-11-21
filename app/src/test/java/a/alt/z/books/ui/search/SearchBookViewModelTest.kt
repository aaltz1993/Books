package a.alt.z.books.ui.search

import a.alt.z.books.CoroutineTestRule
import a.alt.z.books.TestData
import a.alt.z.books.data.repository.*
import a.alt.z.books.domain.repository.BookRepository
import a.alt.z.books.domain.use_case.search_book.*
import a.alt.z.books.model.Book
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchBookViewModelTest {

    companion object {
        private const val QUERY = "QUERY"
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test fun searchBook_Started_TriggerLoading() = runTest {
        val viewModel = createSearchBookViewModel(coroutineTestRule.testDispatcher, NotEmptyBookRepository())

        viewModel.loading.test {
            viewModel.search(QUERY)

            assertNotNull(awaitItem())
        }
    }

    @Test fun searchBook_SucceededWithData_HasNotEmptySearchResults() = runTest {
        val viewModel = createSearchBookViewModel(coroutineTestRule.testDispatcher, NotEmptyBookRepository())

        viewModel.searchResults.filterNotNull().test {
            viewModel.search(QUERY)

            assertEquals(TestData.allBooks, awaitItem().data)
        }
    }

    @Test fun searchBook_SucceededWithNoData_HasEmptySearchResults() = runTest {
        val viewModel = createSearchBookViewModel(coroutineTestRule.testDispatcher, EmptyBookRepository())

        viewModel.searchResults.filterNotNull().test {
            viewModel.search(QUERY)

            assertEquals(emptyList<Book>(), awaitItem().data)
        }
    }

    @Test fun searchBook_Failed_PropagateError() = runTest {
        val viewModel = createSearchBookViewModel(coroutineTestRule.testDispatcher, ErrorBookRepository())

        viewModel.error.test {

            viewModel.search(QUERY)

            assertNull(viewModel.searchResults.first())

            assertNotNull(awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test fun appendPagingData_Started_NotTriggerLoading() = runTest {
        val viewModel = createSearchBookViewModel(coroutineTestRule.testDispatcher, NotEmptyBookRepository())

        viewModel.loading.test {
            viewModel.search(QUERY)

            assertNotNull(awaitItem())

            viewModel.load(3)

            expectNoEvents()

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test fun appendPagingData_SucceededWithData_AccumulatedSearchResults() = runTest {
        val viewModel = createSearchBookViewModel(coroutineTestRule.testDispatcher, PagingDataBookRepository())

        viewModel.searchResults.filterNotNull().test {
            // WHEN: USER SEARCH
            viewModel.search(QUERY)

            val searchResults = awaitItem()

            // THEN: SEARCH RESULTS
            assertEquals(listOf(TestData.book1, TestData.book2), searchResults.data)

            // WHEN: USER SCROLL END OF CONTENTS + LOAD NEXT PAGE
            viewModel.load(2)

            val finalSearchResults = awaitItem()

            // THEN: LOAD PAGING RESULTS
            assertEquals(TestData.allBooks, finalSearchResults.data)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test fun appendPagingData_SucceededWithNoData_NotDistinctSearchResults() = runTest {
        val viewModel = createSearchBookViewModel(coroutineTestRule.testDispatcher, EmptyPagingDataBookRepository())

        viewModel.searchResults.filterNotNull().test {
            // WHEN: USER SEARCH
            viewModel.search(QUERY)

            val searchResults = awaitItem()

            // THEN: SEARCH RESULTS
            assertEquals(listOf(TestData.book1, TestData.book2), searchResults.data)

            // WHEN: USER SCROLL END OF CONTENTS + LOAD NEXT PAGE
            viewModel.load(2)

            // THEN: EXPECT NOT CHANGED
            expectNoEvents()

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test fun appendPagingData_Failed_PropagateError() = runTest {
        val viewModel = createSearchBookViewModel(coroutineTestRule.testDispatcher, ErrorPagingDataBookRepository())

        viewModel.error.test {
            // WHEN: USER SEARCH & LOAD PAGING DATA
            viewModel.search(QUERY)

            viewModel.load(2)

            // THEN: EXPECT ERROR
            assertNotNull(awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun createSearchBookViewModel(coroutineDispatcher: CoroutineDispatcher, repository: BookRepository): SearchBookViewModel {
        val searchBookUseCase = SearchBookUseCase(coroutineDispatcher, repository)
        val saveBookSearchHistoryUseCase = SaveBookSearchHistoryUseCase(coroutineDispatcher, repository)
        val getBookSearchHistoriesUseCase = GetBookSearchHistoriesUseCase(coroutineDispatcher, repository)
        val deleteBookSearchHistoriesUseCase = DeleteBookSearchHistoryUseCase(coroutineDispatcher, repository)
        val clearBookSearchHistoriesUseCase = ClearBookSearchHistoriesUseCase(coroutineDispatcher, repository)

        return SearchBookViewModel(
            searchBookUseCase,
            saveBookSearchHistoryUseCase,
            getBookSearchHistoriesUseCase,
            deleteBookSearchHistoriesUseCase,
            clearBookSearchHistoriesUseCase
        )
    }
}