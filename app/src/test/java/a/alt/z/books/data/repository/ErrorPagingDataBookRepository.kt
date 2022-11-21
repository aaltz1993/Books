package a.alt.z.books.data.repository

import a.alt.z.books.TestData
import a.alt.z.books.domain.repository.BookRepository
import a.alt.z.books.model.BookSearchHistory
import a.alt.z.books.model.BookSearchResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ErrorPagingDataBookRepository : BookRepository {

    override suspend fun saveSearchHistory(searchHistory: BookSearchHistory) {
    }

    override fun getSearchHistories(): Flow<List<BookSearchHistory>> = flow {
    }

    override suspend fun deleteSearchHistory(searchHistory: BookSearchHistory) {
    }

    override suspend fun clearSearchHistories() {
    }

    override suspend fun search(query: String, index: Int): BookSearchResults {
        if (index != 0) {
            throw Exception()
        }

        return BookSearchResults(query, TestData.allBooks.size, TestData.allBooks)
    }
}