package a.alt.z.books.domain.use_case

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import a.alt.z.books.util.result.Result

abstract class UseCase<in P, R> constructor(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): Result<R> {
        return try {
            withContext(coroutineDispatcher) {
                Result.Success(execute(parameters))
            }
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    protected abstract suspend fun execute(parameters: P): R
}