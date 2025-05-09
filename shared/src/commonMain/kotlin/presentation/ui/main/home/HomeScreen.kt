package presentation.ui.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.model.HabitProgressModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.component.core.DefaultScreenUI
import presentation.component.core.Spacer_16dp
import presentation.component.core.Spacer_4dp
import presentation.component.core.Spacer_8dp
import presentation.component.ui.CalendarCard
import presentation.component.ui.HabitProgressItem
import presentation.component.ui.TextViewAll
import presentation.theme.Black40
import presentation.theme.Blue10
import presentation.theme.BorderColor
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.home_slogan
import routinist.shared.generated.resources.habits

@Composable
fun HomeScreen() {
    DefaultScreenUI() {
        HomeContent()
    }
}
@Composable
fun HomeContent() {
    Column(Modifier.fillMaxSize()) {
        HomeHeaderSection()
        Spacer_16dp()
        HomeMainSection()
    }
}
@Composable
fun HomeHeaderSection(modifier: Modifier = Modifier) {
    Column(modifier.background(Color.White)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "Hi, John\uD83D\uDC4B ",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer_4dp()
                Text(
                    stringResource(Res.string.home_slogan),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Black40
                )
            }

            Box(
                Modifier.size(48.dp)
                    .background(color = Blue10, shape = CircleShape)
            ) {
                Text(
                    "😇",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Box(Modifier.fillMaxWidth(1f).height(1.dp).background(BorderColor))
    }
}
@Composable
fun HomeMainSection() {
    LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(
            arrayOf(
                Pair("3", "SAT"),
                Pair("4", "SUN"),
                Pair("5", "MON"),
                Pair("6", "TUE"),
                Pair("7", "WED"),
                Pair("8", "THU"),
                Pair("9", "FRI")
            )
        ) {
            CalendarCard(
                date = it.first,
                day = it.second,
                isSelected = it.first == "3"
            )
        }
    }
    Spacer_16dp()
    TextViewAll(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        text = stringResource(Res.string.habits)
    )
    Spacer_4dp()
    LazyColumn(
        Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            arrayOf(
                HabitProgressModel(
                    id = 1,
                    name = "Read book",
                    icon = "📚",
                    progress = 30f,
                    goal = 100f,
                    measurement = "minutes"
                ), HabitProgressModel(
                    id = 2,
                    name = "Drink the water",
                    icon = "\uD83D\uDCA7",
                    progress = 500f,
                    goal = 2000f,
                    measurement = "ML"
                ), HabitProgressModel(
                    id = 3,
                    name = "Meditate",
                    icon = "\uD83E\uDDD8\uD83C\uDFFB\u200D♂\uFE0F",
                    progress = 30f,
                    goal = 30f,
                    measurement = "ML"
                )
            )
        ) {
            HabitProgressItem(data = it) {
            }
        }
    }
}
@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
