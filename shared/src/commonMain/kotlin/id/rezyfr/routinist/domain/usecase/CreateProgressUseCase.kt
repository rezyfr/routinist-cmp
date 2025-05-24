package id.rezyfr.routinist.domain.usecase

import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.repo.HabitRepository

class CreateProgressUseCase(
    private val habitRepository: HabitRepository
) : UseCase<UiResult<Long?>, Pair<Long, Float>> {
    override suspend fun execute(params: Pair<Long, Float>): UiResult<Long?> {
        return handleResult(
            execute = { habitRepository.updateProgress(params.first, params.second) },
            onSuccess = {
                it.milestone
            }
        )
    }
}