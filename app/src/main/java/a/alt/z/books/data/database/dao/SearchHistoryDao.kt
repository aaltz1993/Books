package a.alt.z.books.data.database.dao

import a.alt.z.books.data.database.model.SearchHistoryEntity
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSearchHistory(searchHistory: SearchHistoryEntity)

    @Query("SELECT * FROM SearchHistoryEntity")
    fun getSavedSearchHistories(): Flow<List<SearchHistoryEntity>>

    @Delete
    suspend fun deleteSearchHistory(searchHistory: SearchHistoryEntity)

    @Query("DELETE FROM SearchHistoryEntity")
    suspend fun clearSearchHistories()
}