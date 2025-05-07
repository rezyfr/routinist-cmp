package domain.usecase

import domain.UiResult
import domain.handleResult
import domain.model.HabitModel
import domain.model.HabitSummaryModel
import domain.model.UserHabitModel
import domain.repo.HabitRepository

class GetTodayHabitsUseCase(
    private val repository: HabitRepository
) : UseCase<UiResult<List<UserHabitModel>>, Unit> {
    override suspend fun execute(params: Unit): UiResult<List<UserHabitModel>> {
        return handleResult(
            execute = {
                repository.getTodayHabits()
            },
            onSuccess = {
                it.map {
                    UserHabitModel.fromResponse(it)
                }
            }
        )
    }
}