package a.alt.z.books.domain.use_case.search_book

import a.alt.z.books.di.IODispatcher
import a.alt.z.books.domain.repository.BookRepository
import a.alt.z.books.domain.use_case.UseCase
import a.alt.z.books.model.BookSearchResults
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class SearchBookUseCase @Inject constructor(
    @IODispatcher coroutineDispatcher: CoroutineDispatcher,
    private val repository: BookRepository
) : UseCase<SearchBookParams, BookSearchResults>(coroutineDispatcher) {

    override suspend fun execute(parameters: SearchBookParams): BookSearchResults {
        return repository.search(parameters.query, parameters.index)
    }
}

data class SearchBookParams(
    val query: String,
    val index: Int = 0
)