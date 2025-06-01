package id.rezyfr.routinist.domain.model

import id.rezyfr.routinist.data.remote.response.HabitResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import migrations.HabitEntity

@Serializable
data class HabitModel(
    val defaultGoal: Int,
    val id: Long,
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

        fun fromEntity(entity: HabitEntity): HabitModel {
            return HabitModel(
                defaultGoal = entity.goal.toInt(),
                id = entity.id,
                icon = entity.icon,
                measurement = entity.measurement,
                name = entity.name,
                color = entity.color,
                units = Json.decodeFromString(entity.units)
            )
        }
    }
}
