package id.rezyfr.routinist.domain.usecase

import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.model.HabitSummaryModel
import id.rezyfr.routinist.domain.repo.HabitRepository

class GetHabitSummaryUseCase(
    private val repository: HabitRepository
) : UseCase<HabitSummaryModel, Unit> {
    override suspend fun execute(params: Unit): UiResult<HabitSummaryModel> {
        return handleResult(
            execute = {
                repository.getHabitSummary()
            },
            onSuccess = {
                HabitSummaryModel.Companion.fromResponse(it)
            }
        )
    }
}