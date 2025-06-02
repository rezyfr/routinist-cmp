package id.rezyfr.routinist.domain.usecase

import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.model.HabitStatsModel
import id.rezyfr.routinist.domain.repo.HabitRepository
import id.rezyfr.routinist.domain.usecase.GetHabitStatsUseCase.Params
import id.rezyfr.routinist.presentation.util.toFormattedString
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

class GetHabitStatsUseCase(
    private val repository: HabitRepository
) : UseCase<List<HabitStatsModel>, Params> {
    override suspend fun execute(params: Params): UiResult<List<HabitStatsModel>> {
        val (from, to) = params
        val (startDate, endDate) = if (from == null || to == null) {
            val currentMoment: Instant = Clock.System.now()
            val sevenDaysAgo: Instant = currentMoment.minus(7, DateTimeUnit.DAY, TimeZone.currentSystemDefault())
            val endDate: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
            val startDate: LocalDateTime = sevenDaysAgo.toLocalDateTime(TimeZone.UTC)
            Pair(startDate.toFormattedString(), endDate.toFormattedString())
        } else {
            Pair(from.toFormattedString(), to.toFormattedString())
        }
        return handleResult(
            execute = {
                repository.getHabitStats(startDate = startDate, endDate = endDate)
            },
            onSuccess = {
                it.map { HabitStatsModel.fromResponse(it) }
            }
        )
    }

    data class Params(
        val from: LocalDateTime? = null,
        val to: LocalDateTime? = null,
    )
}