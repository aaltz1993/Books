package a.alt.z.books.domain.use_case

import kotlinx.coroutines.CoroutineDispatcher
import a.alt.z.books.util.result.Result
import kotlinx.coroutines.flow.*

abstract class FlowUseCase<in P, R> constructor(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(parameters: P): Flow<Result<R>> = execute(parameters)
        .onStart { emit(Result.Loading) }
        .catch { throwable ->
            val exception = if (throwable is Exception) {
                throwable
            } else {
                Exception(throwable)
            }

            emit(Result.Error(exception))
        }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: P): Flow<Result<R>>
}