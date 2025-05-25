package id.rezyfr.routinist.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import routinist.shared.generated.resources.*
import routinist.shared.generated.resources.Res


@Composable
fun CerealTypography(): Typography {
    val cereal = FontFamily(
        Font(
            resource = Res.font.cereal_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.cereal_medium,
            weight = FontWeight.Medium,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.cereal_book,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.cereal_medium,
            weight = FontWeight.Black,
            style = FontStyle.Normal
        ),
    )

    return Typography(
        displayLarge = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
            fontFamily = cereal,
            lineHeight = 56.sp
        ),
        displayMedium = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            fontFamily = cereal,
            lineHeight = 48.sp
        ),
        displaySmall = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            fontFamily = cereal,
            lineHeight = 40.sp
        ),
        headlineLarge = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            fontFamily = cereal,
            lineHeight = 40.sp
        ),
        headlineMedium = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            fontFamily = cereal,
            lineHeight = 32.sp
        ),
        headlineSmall = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            fontFamily = cereal
        ),
        titleMedium = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            fontFamily = cereal
        ),
        bodyLarge = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = cereal
        ),
        bodyMedium = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = cereal
        ),
        labelMedium = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = cereal
        ),
        labelSmall = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            fontFamily = cereal,
            lineHeight = 16.sp
        ),
    )
}