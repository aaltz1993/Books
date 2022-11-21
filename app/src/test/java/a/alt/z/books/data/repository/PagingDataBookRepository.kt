package a.alt.z.books.data.repository

import a.alt.z.books.TestData
import a.alt.z.books.domain.repository.BookRepository
import a.alt.z.books.model.BookSearchHistory
import a.alt.z.books.model.BookSearchResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PagingDataBookRepository : BookRepository {

    override suspend fun saveSearchHistory(searchHistory: BookSearchHistory) {
    }

    override fun getSearchHistories(): Flow<List<BookSearchHistory>> = flow {
    }

    override suspend fun deleteSearchHistory(searchHistory: BookSearchHistory) {
    }

    override suspend fun clearSearchHistories() {
    }

    override suspend fun search(query: String, index: Int): BookSearchResults {
        val data = if (index == 0) {
            listOf(TestData.book1, TestData.book2)
        } else {
            listOf(TestData.book3)
        }

        return BookSearchResults(query, data.size, data)
    }
}