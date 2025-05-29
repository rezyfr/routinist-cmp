package id.rezyfr.routinist.domain.usecase

import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.model.HabitModel
import id.rezyfr.routinist.domain.repo.HabitRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first

class GetRandomHabitUseCase(
    private val repository: HabitRepository
) : UseCase<List<HabitModel>, Unit> {
    override fun executeFlow(params: Unit?): Flow<UiResult<List<HabitModel>>> {
        return callbackFlow<UiResult<List<HabitModel>>> {
            repository.getRandomHabits().first().let { result ->
                if (result.isNotEmpty()) {
                    trySend(UiResult.Success(result.map { HabitModel.fromEntity(it) }))
                }
            }
            val apiResult = repository.fetchRandomHabits()

            apiResult.onSuccess {
                repository.savePopularHabits(it.map { HabitModel.fromResponse(it) })
            }

            awaitClose()
        }
    }
}