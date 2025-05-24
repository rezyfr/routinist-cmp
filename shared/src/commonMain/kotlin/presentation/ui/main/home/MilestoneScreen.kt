package presentation.ui.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.component.core.DefaultButton
import presentation.component.core.DefaultScreenUI
import presentation.component.core.Spacer_16dp
import presentation.component.core.Spacer_32dp
import presentation.theme.AppTheme
import presentation.theme.Orange60
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.okay
import routinist.shared.generated.resources.ic_milestone_1
import routinist.shared.generated.resources.ic_milestone_bg

@Composable
fun MilestoneScreen(
    onBackClick: () -> Unit = {},
    milestone: Int
) {
    when (milestone) {
        1 -> {
            DefaultScreenUI(
                titleToolbar = "",
                toolbarColor = Orange60,
                startIconToolbar = Icons.Default.ChevronLeft,
                onClickStartIconToolbar = onBackClick
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().background(Orange60).padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp),
                            painter = painterResource(Res.drawable.ic_milestone_bg),
                            contentDescription = null,
                            contentScale = ContentScale.Inside,
                        )
                        Image(
                            modifier = Modifier
                                .height(256.dp),
                            painter = painterResource(Res.drawable.ic_milestone_1),
                            contentDescription = null,
                            contentScale = ContentScale.Inside,
                        )
                    }

                    Text(
                        "Congrats!\nYou just reached your\nfirst habit goal!",
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer_16dp()
                    Text(
                        "This badge is a symbol of your\ncommitment to yourself. Keep going\nand earn more badges along the way.",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer_32dp()
                    DefaultButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(Res.string.okay),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ),
                    ) { }
                }
            }
        }
    }
}
@Preview
@Composable
fun MilestoneScreenPreview() {
    AppTheme {
        MilestoneScreen({}, 1)
    }
}
