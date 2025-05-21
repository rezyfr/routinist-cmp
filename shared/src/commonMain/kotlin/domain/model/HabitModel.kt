package domain.model

import data.remote.response.HabitResponse

data class HabitModel(
    val defaultGoal: Int,
    val id: Int,
    val icon: String,
    val measurement: String,
    val name: String,
    val units: List<UnitModel>,
    val color: Long = 0xFFFFFFFF,
    var isSelected: Boolean = false
) {
    companion object {
        fun fromResponse(response: HabitResponse): HabitModel {
            return HabitModel(
                defaultGoal = response.defaultGoal ?: 0,
                id = response.id ?: 0,
                icon = response.icon.orEmpty(),
                measurement = response.measurement.orEmpty(),
                name = response.name.orEmpty(),
                color = response.color ?: 0xFFFFFFFF,
                units = response.units.map { UnitModel.fromResponse(it) }
            )
        }
    }
}
