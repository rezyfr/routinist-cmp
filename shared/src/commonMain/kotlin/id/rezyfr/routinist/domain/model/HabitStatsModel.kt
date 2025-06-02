package id.rezyfr.routinist.domain.model

import id.rezyfr.routinist.data.remote.response.HabitStatsResponse

data class HabitStatsModel(
    val date: String,
    val success: Int,
    val total: Int
) {
    companion object {
        fun fromResponse (response: HabitStatsResponse): HabitStatsModel {
            return HabitStatsModel(
                date = response.date.orEmpty(),
                success = response.success ?: 0,
                total = response.total ?: 0
            )
        }
    }
}
