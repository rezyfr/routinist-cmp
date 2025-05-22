package presentation.component.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.composeunstyled.ProgressIndicator
import domain.model.HabitProgressModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.content
import presentation.component.core.IconButton
import presentation.component.core.Spacer_12dp
import presentation.theme.Black10
import presentation.theme.Black40
import presentation.theme.Green100
import presentation.util.noDecimal
import kotlin.math.roundToInt

const val ANIMATION_DURATION = 200
@Composable
fun HabitProgressItem(
    modifier: Modifier = Modifier,
    data: HabitProgressModel,
    isLoading: Boolean = false,
    onClickFinish: (HabitProgressModel) -> Unit,
    onClickAdd: (HabitProgressModel) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val anchors = DraggableAnchors<DragValue> {
        DragValue.Start at -180f
        DragValue.Center at 0f
    }
    val swipeState = remember {
        AnchoredDraggableState<DragValue>(
            initialValue = DragValue.Center,
            anchors = anchors,
            positionalThreshold = {
                0.3f
            },
            velocityThreshold = {
                0f
            },
            snapAnimationSpec = tween(ANIMATION_DURATION),
            decayAnimationSpec = exponentialDecay()
        )
    }

    SwipeToRevealItem(
        swipeState = swipeState,
        backgroundContent = {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Black10),
                modifier = Modifier.fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    } else {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    coroutineScope.launch {
                                        swipeState.animateTo(DragValue.Center)
                                    }
                                    onClickFinish(data)
                                }
                                .size(20.dp),
                            tint = Green100
                        )
                    }

                    Text(
                        text = "Finish",
                        style = MaterialTheme.typography.labelMedium,
                        color = Black40
                    )
                }
            }
        },
        foregroundContent = {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = modifier,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Black10)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                ) {
                    Box(Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            gapSize = 0.dp,
                            progress = {
                                data.progress / data.goal
                            },
                            trackColor = Black10,
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 2.dp
                        )
                        Text(data.icon, style = MaterialTheme.typography.bodyMedium)
                    }
                    Spacer_12dp()
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = data.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "${data.progress.noDecimal()}/${data.goal.noDecimal()} ${data.unit}",
                            style = MaterialTheme.typography.labelMedium,
                            color = Black40
                        )
                    }

                    IconButton(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Default.Add
                    ) {
                        onClickAdd.invoke(data)
                    }
                }
            }
        }
    )
}

enum class DragValue { Start, Center }
@Composable
fun SwipeToRevealItem(
    swipeState: AnchoredDraggableState<DragValue>,
    backgroundContent: @Composable () -> Unit,
    foregroundContent: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .anchoredDraggable(
                state = swipeState,
                orientation = Orientation.Horizontal,
            ),
        contentAlignment = Alignment.CenterEnd
    ) {
        backgroundContent()
        Box(modifier = Modifier.offset {
            IntOffset(x = swipeState.requireOffset().roundToInt(), y = 0)
        }.fillMaxSize()) {
            foregroundContent()
        }
    }
}