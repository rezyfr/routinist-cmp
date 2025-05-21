package presentation.ui.habit.create

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.model.HabitModel
import domain.model.UnitModel
import kotlinx.coroutines.flow.onEach
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import presentation.component.core.DefaultScreenUI
import presentation.component.core.Spacer_4dp
import presentation.component.core.Spacer_8dp
import presentation.theme.AppTheme
import presentation.theme.Black10
import presentation.theme.Black100
import presentation.theme.Black60
import presentation.ui.main.bottomsheet.PopularHabitCard
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.create_habit_title
import routinist.shared.generated.resources.habit

@Composable
fun CreateHabitScreen(
    viewModel: CreateHabitViewModel = koinInject(),
    onBackClick: () -> Unit = {}
) {
    LaunchedEffect(viewModel) {
        viewModel.action.onEach { effect ->
        }.collect {}
    }

    DefaultScreenUI(
        errors = viewModel.errors,
        progressBarState = viewModel.state.value.progressBarState,
        titleToolbar = stringResource(Res.string.create_habit_title),
        startIconToolbar = Icons.Default.ChevronLeft,
        onClickStartIconToolbar = onBackClick
    ) {
        CreateHabitContent(
            state = viewModel.state.value,
            events = viewModel::setEvent,
        )
    }
}
@Composable
fun CreateHabitContent(
    state: CreateHabitState,
    events: (CreateHabitEvent) -> Unit
) {
    val selectedItem = remember { mutableStateOf<Int?>(null) }

    Column(Modifier.fillMaxSize()) {
        Text(stringResource(Res.string.habit), style = MaterialTheme.typography.labelSmall)
        Spacer_8dp()
        LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(state.popularHabits, { it.id }) { habit ->
                val isSelected = selectedItem.value == habit.id

                PopularHabitCard(
                    Modifier.width(128.dp),
                    isSelected = isSelected,
                    habit = habit,
                    onClick = {
                        selectedItem.value = habit.id
                        events.invoke(CreateHabitEvent.OnHabitChosen)
                    }
                )
            }
        }
    }
}