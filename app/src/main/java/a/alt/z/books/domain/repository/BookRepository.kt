package a.alt.z.books.domain.repository

import a.alt.z.books.model.BookSearchHistory
import a.alt.z.books.model.BookSearchResults
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun saveSearchHistory(searchHistory: BookSearchHistory)

    fun getSearchHistories(): Flow<List<BookSearchHistory>>

    suspend fun deleteSearchHistory(searchHistory: BookSearchHistory)

    suspend fun clearSearchHistories()

    suspend fun search(query: String, index: Int) : BookSearchResults
}