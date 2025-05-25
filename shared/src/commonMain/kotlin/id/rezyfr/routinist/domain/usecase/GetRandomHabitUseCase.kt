package id.rezyfr.routinist.domain.usecase

import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.handleFlowResult
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.model.HabitModel
import id.rezyfr.routinist.domain.repo.HabitRepository
import kotlinx.coroutines.flow.Flow
import id.rezyfr.routinist.presentation.component.core.ProgressBarState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetRandomHabitUseCase(
    private val repository: HabitRepository
) : UseCase<UiResult<List<HabitModel>>, Unit> {
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