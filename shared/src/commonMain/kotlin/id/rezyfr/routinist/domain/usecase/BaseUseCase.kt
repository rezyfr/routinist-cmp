package id.rezyfr.routinist.domain.usecase

import id.rezyfr.routinist.domain.UiResult
import kotlinx.coroutines.flow.Flow

interface UseCase<out R, in P> {
    suspend fun execute(params: P): UiResult<R> = throw NotImplementedError()
    fun executeFlow(params: P?): Flow<UiResult<R>> = throw NotImplementedError()
}