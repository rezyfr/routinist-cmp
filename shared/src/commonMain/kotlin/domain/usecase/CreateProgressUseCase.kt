package domain.usecase

import domain.UiResult
import domain.handleResult
import domain.repo.HabitRepository

class CreateProgressUseCase(
    private val habitRepository: HabitRepository
) : UseCase<UiResult<Long>, Pair<Long, Float>> {
    override suspend fun execute(params: Pair<Long, Float>): UiResult<Long> {
        return handleResult(
            execute = { habitRepository.updateProgress(params.first, params.second) },
            onSuccess = {
                it.milestone ?: 0L
            }
        )
    }
}