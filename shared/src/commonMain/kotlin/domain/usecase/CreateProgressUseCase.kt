package domain.usecase

import domain.UiResult
import domain.handleResult
import domain.repo.HabitRepository

class CreateProgressUseCase(
    private val habitRepository: HabitRepository
) : UseCase<UiResult<String>, Pair<Long, Float>> {
    override suspend fun execute(params: Pair<Long, Float>): UiResult<String> {
        return handleResult(
            execute = { habitRepository.updateProgress(params.first, params.second) },
            onSuccess = {
                it
            }
        )
    }
}