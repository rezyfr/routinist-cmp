package id.rezyfr.routinist.domain.usecase

import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.model.HabitProgressModel
import id.rezyfr.routinist.domain.repo.HabitRepository

class GetTodayHabitProgressesUseCase(
    private val repository: HabitRepository
) : UseCase<List<HabitProgressModel>, Unit> {
    override suspend fun execute(params: Unit): UiResult<List<HabitProgressModel>> {
        return handleResult(
            execute = {
                repository.getTodayHabitProgresses()
            },
            onSuccess = {
                it.map { HabitProgressModel.fromResponse(it) }
            }
        )
    }
}