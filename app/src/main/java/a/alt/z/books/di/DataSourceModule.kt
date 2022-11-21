package a.alt.z.books.di

import a.alt.z.books.data.datasource.BooksLocalDataSource
import a.alt.z.books.data.datasource.DefaultBooksLocalDataSource
import a.alt.z.books.data.datasource.BooksNetworkDataSource
import a.alt.z.books.data.datasource.DefaultBooksNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds @Singleton
    abstract fun bindsBookRemoteDataSource(remoteDataSource: DefaultBooksNetworkDataSource): BooksNetworkDataSource

    @Binds @Singleton
    abstract fun bindsBookLocalDataSource(remoteDataSource: DefaultBooksLocalDataSource): BooksLocalDataSource
}
