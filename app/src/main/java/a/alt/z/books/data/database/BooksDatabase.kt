package a.alt.z.books.data.database

import a.alt.z.books.data.database.dao.SearchHistoryDao
import a.alt.z.books.data.database.model.SearchHistoryEntity
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [SearchHistoryEntity::class],
    version = BooksDatabase.VERSION
)
abstract class BooksDatabase : RoomDatabase() {

    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        const val VERSION = 1

        private const val DATABASE_NAME = "books-db"

        fun build(context: Context): BooksDatabase {
            return Room
                .databaseBuilder(context, BooksDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}