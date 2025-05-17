package domain.usecase

import domain.UiResult
import domain.handleResult
import domain.model.HabitModel
import domain.model.HabitSummaryModel
import domain.repo.HabitRepository

class GetHabitSummaryUseCase(
    private val repository: HabitRepository
) : UseCase<UiResult<HabitSummaryModel>, Unit> {
    override suspend fun execute(params: Unit): UiResult<HabitSummaryModel> {
        return handleResult(
            execute = {
                repository.getHabitSummary()
            },
            onSuccess = {
                HabitSummaryModel.fromResponse(it)
            }
        )
    }
}