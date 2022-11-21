package a.alt.z.books.domain.use_case.search_book

import a.alt.z.books.di.IODispatcher
import a.alt.z.books.domain.repository.BookRepository
import a.alt.z.books.domain.use_case.UseCase
import a.alt.z.books.model.BookSearchHistory
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteBookSearchHistoryUseCase @Inject constructor(
    @IODispatcher coroutineDispatcher: CoroutineDispatcher,
    private val repository: BookRepository
) : UseCase<BookSearchHistory, Unit>(coroutineDispatcher) {

    override suspend fun execute(parameters: BookSearchHistory) {
        repository.deleteSearchHistory(parameters)
    }
}