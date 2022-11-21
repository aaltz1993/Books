package a.alt.z.books.domain.use_case

import a.alt.z.books.CoroutineTestRule
import a.alt.z.books.util.result.Result
import app.cash.turbine.test
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FlowUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    @Test fun executeFlowUseCase_ThrowException_ReturnResultError() = runTest {
        val errorUseCase = ErrorFlowUseCase(coroutinesTestRule.testDispatcher)

        val result = errorUseCase(Unit)

        result.test {
            val loading = awaitItem()

            assertTrue(loading == Result.Loading)

            assertTrue(awaitItem() is Result.Error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    class ErrorFlowUseCase constructor(
        coroutineDispatcher: CoroutineDispatcher
    ) : FlowUseCase<Unit, Unit>(coroutineDispatcher) {

        override fun execute(parameters: Unit): Flow<Result<Unit>> = flow {
            throw Exception("Something goes wrong :(")
        }
    }
}