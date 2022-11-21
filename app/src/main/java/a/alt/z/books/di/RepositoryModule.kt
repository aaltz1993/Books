package a.alt.z.books.di

import a.alt.z.books.data.repository.BookDataRepository
import a.alt.z.books.domain.repository.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindsBookRepository(repository: BookDataRepository): BookRepository
}