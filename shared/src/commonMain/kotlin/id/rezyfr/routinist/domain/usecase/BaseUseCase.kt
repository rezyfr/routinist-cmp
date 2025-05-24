package id.rezyfr.routinist.domain.usecase

import kotlinx.coroutines.flow.Flow

interface UseCase<out R, in P> {
    suspend fun execute(params: P): R = throw NotImplementedError()
    fun executeFlow(params: P?): Flow<R> = throw NotImplementedError()
}