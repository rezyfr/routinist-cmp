package id.rezyfr.routinist.presentation.component.core.chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.geometry.Size
import kotlin.math.floor
import kotlin.math.pow

internal data class PathData(
    val path: Path,
    val xPositions: List<Double>,
    val startIndex: Int,
    val endIndex: Int
)

internal fun DrawScope.getLinePath(
    dataPoints: List<Float>,
    maxValue: Float,
    minValue: Float,
    rounded: Boolean = true,
    size: Size? = null,
    startIndex: Int,
    endIndex: Int
): PathData {
    val _size = size ?: this.size
    val path = Path()
    if (dataPoints.isEmpty()) return PathData(
        path = path,
        xPositions = emptyList(),
        0,
        Int.MAX_VALUE
    )
    val calculateHeight = { value: Float ->
        calculateOffset(
            maxValue = maxValue.toDouble(),
            minValue = minValue.toDouble(),
            total = _size.height,
            value = value
        )
    }

    if (startIndex == 0) {
        path.moveTo(0f, (_size.height - calculateHeight(dataPoints[0])).toFloat())
    } else {
        val x = (startIndex * (_size.width / (dataPoints.size - 1)))
        val y = _size.height - calculateHeight(dataPoints[startIndex]).toFloat()
        path.moveTo(x, y)
    }
    val xPositions = mutableListOf<Double>()
    for (i in 0 until dataPoints.size - 1) {
        val x1 = (i * (_size.width / (dataPoints.size - 1)))
        val y1 = _size.height - calculateHeight(dataPoints[i]).toFloat()
        val x2 = ((i + 1) * (_size.width / (dataPoints.size - 1)))
        val y2 = _size.height - calculateHeight(dataPoints[i + 1]).toFloat()

        if (i in startIndex..<endIndex) {
            if (rounded) {
                val cx = (x1 + x2) / 2f
                path.cubicTo(x1 = cx, y1 = y1, x2 = cx, y2 = y2, x3 = x2, y3 = y2)
            } else {
                path.cubicTo(x1, y1, x1, y1, (x1 + x2) / 2, (y1 + y2) / 2)
                path.cubicTo((x1 + x2) / 2, (y1 + y2) / 2, x2, y2, x2, y2)
            }
        }

        xPositions.add(x1.toDouble())
    }
    xPositions.add(_size.width.toDouble())
    return PathData(path = path, xPositions = xPositions, startIndex, endIndex)
}

fun calculateOffset(
    maxValue: Double,
    minValue: Double,
    total: Float,
    value: Float
): Double {
    val range = maxValue - minValue
    val percentage = (value - minValue) / range
    val offset = total * percentage
    return offset
}

fun split(
    count: IndicatorCount,
    minValue: Double,
    maxValue: Double,
): List<Double> {
    return when (count) {
        is IndicatorCount.CountBased -> {
            val step = (maxValue - minValue) / (count.count - 1)
            val result = (0 until count.count).map { (maxValue - it * step) }
            result
        }

        is IndicatorCount.StepBased -> {
            val result = mutableListOf<Double>()
            var cache = maxValue
            while (cache > minValue) {
                result.add(cache.coerceAtLeast(minValue))
                cache -= count.stepBy
            }
            result
        }
    }
}

internal fun getPopupValue(
    points: List<Double>,
    fraction: Double,
    rounded: Boolean = false,
    size: Size,
    minValue: Double,
    maxValue: Double
): Value {
    val index = fraction * (points.count() - 1)
    val roundedIndex = floor(index).toInt()
    return if (fraction == 1.0) {
        val lastPoint = points.last()
        val offset = Offset(
            x = size.width,
            y = size.height - calculateOffset(
                minValue = minValue,
                maxValue = maxValue,
                total = size.height,
                value = lastPoint.toFloat()
            ).toFloat()
        )
        Value(calculatedValue = points.last(), offset = offset)
    } else {
        if (rounded && points.count() > 1) {
            val calculateHeight = { value: Double ->
                calculateOffset(
                    maxValue = maxValue,
                    minValue = minValue,
                    total = size.height,
                    value = value.toFloat()
                )
            }
            val x1 = (roundedIndex * (size.width / (points.size - 1)))
            val x2 = ((roundedIndex + 1) * (size.width / (points.size - 1)))
            val y1 = size.height - calculateHeight(points[roundedIndex])
            val y2 = size.height - calculateHeight(points[roundedIndex + 1])
            val cx = (x1 + x2) / 2f

            val areaFraction = roundedIndex.toDouble() / (points.size - 1)

            val t = (fraction - areaFraction) * (points.size - 1)

            val outputY = ((1 - t).pow(3) * (y1) +
                    3 * t * (1 - t).pow(2) * (y1) +
                    3 * (1 - t) * t.pow(2) * (y2) +
                    t.pow(3) * y2).toFloat()
            val outputX = ((1 - t).pow(3) * (x1) +
                    3 * t * (1 - t).pow(2) * (cx) +
                    3 * (1 - t) * t.pow(2) * (cx) +
                    t.pow(3) * x2).toFloat()
            val calculatedValue = calculateValue(
                minValue = minValue,
                maxValue = maxValue,
                total = size.height,
                offset = size.height - outputY
            )

            Value(calculatedValue = calculatedValue, offset = Offset(x = outputX, y = outputY))
        } else {
            val p1 = points[roundedIndex]
            val p2 = points.getOrNull(roundedIndex + 1) ?: p1
            val calculatedValue = ((p2 - p1) * (index - roundedIndex) + p1)
            val offset = Offset(
                x = if (points.count() > 1) (fraction * size.width).toFloat() else 0f,
                y = size.height - calculateOffset(
                    minValue = minValue,
                    maxValue = maxValue,
                    total = size.height,
                    value = calculatedValue.toFloat()
                ).toFloat()
            )
            Value(calculatedValue = calculatedValue, offset = offset)
        }
    }
}

internal data class Value(
    val calculatedValue: Double,
    val offset: Offset,
)

fun calculateValue(minValue: Double, maxValue: Double, total: Float, offset:Float): Double {
    val percentage = offset / total
    val range = maxValue - minValue
    val value = minValue + percentage * range
    return value
}

fun DrawScope.drawGridLines(
    dividersProperties: DividerProperties,
    indicatorPosition: IndicatorPosition,
    gridEnabled: Boolean,
    xAxisProperties: GridProperties.AxisProperties,
    yAxisProperties: GridProperties.AxisProperties,
    size: Size? = null,
    xPadding: Float = 0f,
    yPadding: Float = 0f
) {

    val _size = size ?: this.size

    val xAxisPathEffect = xAxisProperties.style.pathEffect
    val yAxisPathEffect = yAxisProperties.style.pathEffect


    if (xAxisProperties.enabled && gridEnabled) {
        for (i in 0 until xAxisProperties.lineCount) {
            val y = _size.height.spaceBetween(itemCount = xAxisProperties.lineCount, index = i)
            drawLine(
                brush = xAxisProperties.color,
                start = Offset(0f + xPadding, y + yPadding),
                end = Offset(_size.width + xPadding, y + yPadding),
                strokeWidth = xAxisProperties.thickness.toPx(),
                pathEffect = xAxisPathEffect
            )
        }
    }
    if (yAxisProperties.enabled && gridEnabled) {
        for (i in 0 until yAxisProperties.lineCount) {
            val x = _size.width.spaceBetween(itemCount = yAxisProperties.lineCount, index = i)
            drawLine(
                brush = yAxisProperties.color,
                start = Offset(x + xPadding, 0f + yPadding),
                end = Offset(x + xPadding, _size.height + yPadding),
                strokeWidth = yAxisProperties.thickness.toPx(),
                pathEffect = yAxisPathEffect
            )
        }
    }
    if (dividersProperties.xAxisProperties.enabled && dividersProperties.enabled) {
        val y = if (indicatorPosition == IndicatorPosition.Vertical.Top) 0f else _size.height
        drawLine(
            brush = dividersProperties.xAxisProperties.color,
            start = Offset(0f + xPadding, y + yPadding),
            end = Offset(_size.width + xPadding, y + yPadding),
            strokeWidth = dividersProperties.xAxisProperties.thickness.toPx(),
            pathEffect = dividersProperties.xAxisProperties.style.pathEffect
        )
    }
    if (dividersProperties.yAxisProperties.enabled && dividersProperties.enabled) {
        val x = if (indicatorPosition == IndicatorPosition.Horizontal.End)  _size.width else  0f
        drawLine(
            brush = dividersProperties.yAxisProperties.color,
            start = Offset(x + xPadding, 0f + yPadding),
            end = Offset(x + xPadding, _size.height + yPadding),
            strokeWidth = dividersProperties.yAxisProperties.thickness.toPx(),
            pathEffect = dividersProperties.yAxisProperties.style.pathEffect
        )
    }
}

fun Float.spaceBetween(itemCount: Int, index: Int): Float {
    if (itemCount == 1) return 0f
    val itemSize = this / (itemCount - 1)
    val positions = (0 until itemCount).map { it * itemSize }
    val result = positions[index]
    return result
}
