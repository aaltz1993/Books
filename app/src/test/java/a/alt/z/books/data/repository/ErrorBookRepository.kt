package a.alt.z.books.data.repository

import a.alt.z.books.domain.repository.BookRepository
import a.alt.z.books.model.BookSearchHistory
import a.alt.z.books.model.BookSearchResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ErrorBookRepository : BookRepository {

    override suspend fun saveSearchHistory(searchHistory: BookSearchHistory) {
        throw Exception("Something goes wrong :(")
    }

    override fun getSearchHistories(): Flow<List<BookSearchHistory>> = flow {
        throw Exception("Something goes wrong :(")
    }

    override suspend fun deleteSearchHistory(searchHistory: BookSearchHistory) {
        throw Exception("Something goes wrong :(")
    }

    override suspend fun clearSearchHistories() {
        throw Exception("Something goes wrong :(")
    }

    override suspend fun search(query: String, index: Int): BookSearchResults {
        throw Exception("Something goes wrong :(")
    }
}