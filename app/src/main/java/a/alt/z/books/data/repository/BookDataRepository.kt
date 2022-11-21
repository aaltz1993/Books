package a.alt.z.books.data.repository

import a.alt.z.books.data.datasource.BooksLocalDataSource
import a.alt.z.books.data.datasource.BooksNetworkDataSource
import a.alt.z.books.domain.repository.BookRepository
import a.alt.z.books.model.BookSearchHistory
import a.alt.z.books.model.BookSearchResults
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookDataRepository @Inject constructor(
    private val localDataSource: BooksLocalDataSource,
    private val networkDataSource: BooksNetworkDataSource
) : BookRepository {

    override suspend fun saveSearchHistory(searchHistory: BookSearchHistory) {
        localDataSource.saveSearchHistory(searchHistory)
    }

    override fun getSearchHistories(): Flow<List<BookSearchHistory>> {
        return localDataSource.getSavedSearchHistories()
    }

    override suspend fun deleteSearchHistory(searchHistory: BookSearchHistory) {
        localDataSource.deleteSearchHistory(searchHistory)
    }

    override suspend fun clearSearchHistories() {
        localDataSource.clearSearchHistories()
    }

    override suspend fun search(query: String, index: Int): BookSearchResults {
        return networkDataSource.searchBook(query, index)
    }
}