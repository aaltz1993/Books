package a.alt.z.books.di

import a.alt.z.books.data.database.BooksDatabase
import a.alt.z.books.data.database.dao.SearchHistoryDao
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides @Singleton
    fun providesDatabase(@ApplicationContext context: Context): BooksDatabase = BooksDatabase.build(context)

    @Provides @Singleton
    fun providesSearchHistoryDao(database: BooksDatabase): SearchHistoryDao = database.searchHistoryDao()
}