package id.rezyfr.routinist.presentation.component.core.chart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.pow
import androidx.compose.ui.graphics.drawscope.DrawStyle as CanvasDrawStyle

data class Line(
    val label: String,
    val values: List<Double>,
    val color: Brush,
    val firstGradientFillColor: Color? = null,
    val secondGradientFillColor: Color? = null,
    val drawStyle: DrawStyle = DrawStyle.Stroke(2.dp),
    val strokeAnimationSpec: AnimationSpec<Float> = tween(2000),
    val gradientAnimationSpec: AnimationSpec<Float> = tween(2000),
    val gradientAnimationDelay: Long = 1000,
    val dotProperties: DotProperties? = null,
    val popupProperties: PopupProperties? = null,
    val curvedEdges: Boolean? = null,
    val strokeProgress: Animatable<Float, AnimationVector1D> = Animatable(0f),
    val gradientProgress: Animatable<Float, AnimationVector1D> = Animatable(0f),
    val viewRange: ViewRange = ViewRange()
)

data class DotProperties(
    val enabled: Boolean = false,
    val radius: Dp = 3.dp,
    val color: Brush = SolidColor(Color.Unspecified),
    val strokeWidth: Dp = 2.dp,
    val strokeColor: Brush = SolidColor(Color.Unspecified),
    val strokeStyle: StrokeStyle = StrokeStyle.Normal,
    val animationEnabled: Boolean = true,
    val animationSpec: AnimationSpec<Float> = tween(300)
)

sealed class StrokeStyle {
    data object Normal : StrokeStyle()
    data class Dashed(val intervals: FloatArray = floatArrayOf(10f, 10f), val phase: Float = 10f) :
        StrokeStyle() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            other as Dashed

            if (!intervals.contentEquals(other.intervals)) return false
            if (phase != other.phase) return false

            return true
        }

        override fun hashCode(): Int {
            var result = intervals.contentHashCode()
            result = 31 * result + phase.hashCode()
            return result
        }
    }

    val pathEffect: PathEffect?
        get() {
            return when (this) {
                is StrokeStyle.Normal -> {
                    null
                }

                is StrokeStyle.Dashed -> {
                    PathEffect.dashPathEffect(intervals = intervals, phase = phase)
                }
            }
        }
}

sealed class DrawStyle() {
    data class Stroke(val width: Dp = 2.dp, val strokeStyle: StrokeStyle = StrokeStyle.Normal) :
        DrawStyle()

    data object Fill : DrawStyle()
}

data class ViewRange(
    val startIndex: Int = 0,
    val endIndex: Int = Int.MAX_VALUE
)

sealed class AnimationMode {
    data class Together(val delayBuilder: (index: Int) -> Long = { 0 }) : AnimationMode()
    data object OneByOne : AnimationMode()
}

data class LineProperties(
    val enabled: Boolean = true,
    val style: StrokeStyle = StrokeStyle.Normal,
    val color: Brush = SolidColor(Color.Gray),
    val thickness: Dp = (.5).dp,
)

data class DividerProperties(
    val enabled: Boolean = true,
    val xAxisProperties: LineProperties = LineProperties(),
    val yAxisProperties: LineProperties = LineProperties()
)

data class GridProperties(
    val enabled: Boolean = true,
    val xAxisProperties: AxisProperties = AxisProperties(),
    val yAxisProperties: AxisProperties = AxisProperties()
) {
    data class AxisProperties(
        val enabled: Boolean = true,
        val style: StrokeStyle = StrokeStyle.Normal,
        val color: Brush = SolidColor(Color.Gray),
        val thickness: Dp = (.5).dp,
        val lineCount: Int = 5
    )
}

data class ZeroLineProperties(
    val enabled: Boolean = false,
    val style: StrokeStyle = StrokeStyle.Normal,
    val color: Brush = SolidColor(Color.Gray),
    val thickness: Dp = (.5).dp,
    val animationSpec: AnimationSpec<Float> = tween(durationMillis = 1000, delayMillis = 300),
    val zType: ZType = ZType.Under
) {
    enum class ZType {
        Under, Above
    }
}

sealed class IndicatorProperties(
    open val enabled: Boolean,
    open val textStyle: TextStyle,
    open val count: IndicatorCount,
    open val position: IndicatorPosition,
    open val padding: Dp,
    open val contentBuilder: (Double) -> String,
    open val indicators: List<Double> = emptyList()
)

data class HorizontalIndicatorProperties(
    override val enabled: Boolean = true,
    override val textStyle: TextStyle = TextStyle.Default.copy(fontSize = 12.sp),
    override val count: IndicatorCount = IndicatorCount.CountBased(count = 5),
    override val position: IndicatorPosition.Horizontal = IndicatorPosition.Horizontal.Start,
    override val padding: Dp = 12.dp,
    override val contentBuilder: (Double) -> String = {
        it.format(1)
    },
    override val indicators: List<Double> = emptyList(),
) : IndicatorProperties(
    enabled = enabled,
    textStyle = textStyle,
    count = count,
    position = position,
    contentBuilder = contentBuilder,
    padding = padding,
    indicators = indicators
)

sealed interface IndicatorPosition {
    enum class Vertical : IndicatorPosition {
        Top,
        Bottom
    }

    enum class Horizontal : IndicatorPosition {
        Start,
        End
    }
}

sealed class IndicatorCount {
    data class CountBased(val count: Int) : IndicatorCount()
    data class StepBased(val stepBy: Double) : IndicatorCount()
}

fun Double.format(decimalPlaces: Int): String {
    require(decimalPlaces >= 0) { "Decimal places must be non-negative." }

    if (decimalPlaces == 0) {
        return this.toInt().toString() // Handle whole numbers efficiently
    }
    val factor = 10.0.pow(decimalPlaces.toDouble())
    val roundedValue = kotlin.math.round(this * factor) / factor
    return roundedValue.toString()
}

data class LabelProperties(
    val enabled: Boolean,
    val textStyle: TextStyle = TextStyle.Default.copy(fontSize = 12.sp, textAlign = TextAlign.End),
    val padding: Dp = 12.dp,
    val labels: List<String> = listOf(),
    val builder: (@Composable (modifier: Modifier, label: String, shouldRotate: Boolean, index: Int) -> Unit)? = null,
    val rotation: Rotation = Rotation()
) {
    data class Rotation(
        val mode: Mode = Mode.IfNecessary,
        val degree: Float = -45f,
        val padding: Dp? = null
    ) {
        enum class Mode {
            Force, IfNecessary
        }
    }
}

data class PopupProperties(
    val enabled: Boolean = true,
    val animationSpec: AnimationSpec<Float> = tween(400),
    val duration: Long = 1500,
    val textStyle: TextStyle = TextStyle.Default.copy(fontSize = 12.sp),
    val containerColor: Color = Color(0xff313131),
    val cornerRadius: Dp = 6.dp,
    val contentHorizontalPadding: Dp = 4.dp,
    val contentVerticalPadding: Dp = 2.dp,
    val mode: Mode = Mode.Normal,
    val contentBuilder: (dataIndex: Int, valueIndex: Int, value: Double) -> String = { dataIndex, valueIndex, value ->
        value.format(1)
    }
) {
    sealed class Mode {
        data object Normal : Mode()
        data class PointMode(val threshold: Dp = 16.dp) : Mode()
    }
}