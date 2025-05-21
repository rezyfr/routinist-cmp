package domain.model

import data.remote.response.UnitResponse
import kotlinx.serialization.Serializable

@Serializable
data class UnitModel(
    val id: Int,
    val measurement: String,
    val name: String,
    val symbol: String
) {
    companion object {
        fun fromResponse(response: UnitResponse): UnitModel {
            return UnitModel(
                id = response.id ?: 0,
                measurement = response.measurement.orEmpty(),
                name = response.name.orEmpty(),
                symbol = response.symbol.orEmpty()
            )
        }
    }
}
