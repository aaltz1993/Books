package a.alt.z.books.domain.use_case.search_book

import a.alt.z.books.di.IODispatcher
import a.alt.z.books.domain.repository.BookRepository
import a.alt.z.books.domain.use_case.FlowUseCase
import a.alt.z.books.model.BookSearchHistory
import a.alt.z.books.util.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBookSearchHistoriesUseCase @Inject constructor(
    @IODispatcher coroutineDispatcher: CoroutineDispatcher,
    private val repository: BookRepository
) : FlowUseCase<Unit, List<BookSearchHistory>>(coroutineDispatcher) {

    override fun execute(parameters: Unit): Flow<Result<List<BookSearchHistory>>> {
        return repository.getSearchHistories().map { Result.Success(it) }
    }
}