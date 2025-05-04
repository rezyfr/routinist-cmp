package domain.usecase

import domain.UiResult
import domain.handleResult
import domain.model.HabitModel
import domain.repo.HabitRepository

class GetRandomHabitUseCase(
    private val repository: HabitRepository
) : UseCase<UiResult<List<HabitModel>>, Unit> {
    override suspend fun execute(params: Unit): UiResult<List<HabitModel>> {
        return handleResult(
            execute = {
                repository.getRandomHabits()
            },
            onSuccess = {
                it.map {
                    HabitModel.fromResponse(it)
                }
            }
        )
    }
}