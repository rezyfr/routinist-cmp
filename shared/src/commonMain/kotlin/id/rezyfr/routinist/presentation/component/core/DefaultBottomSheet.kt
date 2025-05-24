package id.rezyfr.routinist.presentation.component.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composables.core.BottomSheet
import com.composables.core.BottomSheetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultBottomSheet(
    title: String = "",
    sheetState: BottomSheetState,
    onCloseClick: () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    BottomSheet(
        state = sheetState,
        modifier = Modifier
            .fillMaxWidth().clip(RoundedCornerShape(24.dp)).background(Color.White),
    ) {
        Column(Modifier.padding(24.dp)) {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Text(
                        title,
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                actions = {
                    IconButton(
                        imageVector = Icons.Default.Close,
                        onClick = {
                            onCloseClick.invoke()
                        },
                    )
                },
            )
            Spacer_32dp()
            content()
            Spacer_16dp()
        }
    }
}