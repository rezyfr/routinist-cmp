package presentation.ui.main.bottomsheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.HabitProgressModel
import org.jetbrains.compose.resources.stringResource
import presentation.component.core.ButtonSize
import presentation.component.core.DefaultButton
import presentation.component.core.DefaultTextField
import presentation.component.core.Spacer_12dp
import presentation.component.core.Spacer_16dp
import presentation.theme.Black10
import presentation.theme.Black40
import presentation.ui.main.MainEvent
import presentation.ui.main.MainState
import presentation.util.noDecimal
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.progress
import routinist.shared.generated.resources.progress_hint
import routinist.shared.generated.resources.update_progress

@Composable
fun CreateProgressSheetContent(
    data: HabitProgressModel?,
    events: (MainEvent) -> Unit = {},
    state: MainState.ProgressSheetState,
) {
    if (data == null) return
    Row(
        modifier = Modifier.fillMaxWidth(),
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
    }
    Spacer_16dp()
    DefaultTextField(
        modifier = Modifier.fillMaxWidth(),
        label = stringResource(Res.string.progress),
        hint = stringResource(Res.string.progress_hint),
        value = state.progress,
        suffix = data.unit,
        onValueChange = {
            events(MainEvent.OnProgressChange(it.toIntOrNull() ?: 0))
        }
    )
    Spacer_16dp()
    DefaultButton(
        progressBarState = state.progressBarState,
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(Res.string.update_progress),
        size = ButtonSize.Large,
        onClick = {
            events(MainEvent.UpdateProgress)
        }
    )
}
