package a.alt.z.books.data.datasource

import a.alt.z.books.data.database.dao.SearchHistoryDao
import a.alt.z.books.data.database.model.SearchHistoryEntity
import a.alt.z.books.model.BookSearchHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface BooksLocalDataSource {

    suspend fun saveSearchHistory(searchHistory: BookSearchHistory)

    fun getSavedSearchHistories(): Flow<List<BookSearchHistory>>

    suspend fun deleteSearchHistory(searchHistory: BookSearchHistory)

    suspend fun clearSearchHistories()
}

@Singleton
class DefaultBooksLocalDataSource @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao
) : BooksLocalDataSource {

    override suspend fun saveSearchHistory(searchHistory: BookSearchHistory) {
        searchHistoryDao.saveSearchHistory(transform(searchHistory))
    }

    override fun getSavedSearchHistories(): Flow<List<BookSearchHistory>> {
        return searchHistoryDao.getSavedSearchHistories().map { it.map(::transform) }
    }

    override suspend fun deleteSearchHistory(searchHistory: BookSearchHistory) {
        searchHistoryDao.deleteSearchHistory(transform(searchHistory))
    }

    override suspend fun clearSearchHistories() {
        searchHistoryDao.clearSearchHistories()
    }

    private fun transform(searchHistory: SearchHistoryEntity) = with(searchHistory) {
        BookSearchHistory(query)
    }

    private fun transform(searchHistory: BookSearchHistory) = with(searchHistory) {
        SearchHistoryEntity(query)
    }
}