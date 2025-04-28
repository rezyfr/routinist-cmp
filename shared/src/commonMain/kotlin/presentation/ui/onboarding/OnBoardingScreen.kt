package presentation.ui.onboarding


import androidx.compose.animation.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import presentation.component.ButtonType
import presentation.component.DefaultScreenUI
import presentation.component.IconButton
import presentation.component.Spacer_12dp
import presentation.component.Spacer_16dp
import presentation.component.Spacer_24dp
import presentation.component.Spacer_32dp
import presentation.component.Spacer_8dp
import presentation.theme.PagerDotColor
import presentation.theme.Primary40
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.ic_login
import routinist.shared.generated.resources.img_onboarding_background
import routinist.shared.generated.resources.img_onboarding_circle
import routinist.shared.generated.resources.img_onboarding_illustration_1
import routinist.shared.generated.resources.img_onboarding_illustration_2
import routinist.shared.generated.resources.img_onboarding_illustration_3
import routinist.shared.generated.resources.onboarding_consent
import routinist.shared.generated.resources.onboarding_continue
import routinist.shared.generated.resources.tagline_desc_1
import routinist.shared.generated.resources.tagline_desc_2
import routinist.shared.generated.resources.tagline_desc_3
import routinist.shared.generated.resources.tagline_title_1
import routinist.shared.generated.resources.tagline_title_2
import routinist.shared.generated.resources.tagline_title_3

@Composable
fun OnBoardingScreen(
    navigateToRegister: () -> Unit
) {
    val pagerState = rememberPagerState { 3 }
    val onBoardingContent = arrayOf<Triple<DrawableResource, StringResource, StringResource>>(
        Triple(
            Res.drawable.img_onboarding_illustration_1,
            Res.string.tagline_title_1,
            Res.string.tagline_desc_1
        ),
        Triple(
            Res.drawable.img_onboarding_illustration_2,
            Res.string.tagline_title_2,
            Res.string.tagline_desc_2
        ),
        Triple(
            Res.drawable.img_onboarding_illustration_3,
            Res.string.tagline_title_3,
            Res.string.tagline_desc_3
        ),
    )
    DefaultScreenUI(
        gradientBackground = true
    ) {
        Image(
            painter = painterResource(Res.drawable.img_onboarding_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            alpha = 0.2f
        )
        Image(
            painter = painterResource(Res.drawable.img_onboarding_circle),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer_16dp()
            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically
            ) { page ->
                val content = onBoardingContent[page]
                OnBoardingPagerContent(
                    imagePainter = content.first,
                    title = content.second,
                    desc = content.third
                )
            }
            Spacer_32dp()

            Box(
                modifier = Modifier.wrapContentWidth().padding(start = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                DotsIndicator(
                    totalDots = 3,
                    selectedIndex = pagerState.currentPage,
                    dotSize = 8.dp
                )
            }

            Spacer(Modifier.weight(1f))
            IconButton(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                text = stringResource(Res.string.onboarding_continue),
                style = MaterialTheme.typography.bodyMedium,
                icon = vectorResource(Res.drawable.ic_login),
                type = ButtonType.Secondary
            ) {

            }

            Spacer_12dp()

            Text(
                stringResource(Res.string.onboarding_consent),
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Primary40
                )
            )
            Spacer_32dp()
        }
    }
}
@Composable
fun OnBoardingPagerContent(
    imagePainter: DrawableResource,
    title: StringResource,
    desc: StringResource
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .height(500.dp),
            painter = painterResource(imagePainter),
            contentDescription = null,
            contentScale = ContentScale.Inside,
        )
        Spacer_24dp()
        Text(
            stringResource(title),
            style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
        Spacer_8dp()
        Text(
            stringResource(desc),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onPrimary,
                lineHeight = 20.sp
            )
        )
    }
}
@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = MaterialTheme.colorScheme.onPrimary,
    unSelectedColor: Color = PagerDotColor,
    dotSize: Dp = 8.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (index in 0 until totalDots) {
            val color by remember(selectedIndex) { derivedStateOf { Animatable(unSelectedColor) } }
            val size by animateDpAsState(if (index == selectedIndex) 20.dp else dotSize)

            LaunchedEffect(selectedIndex) {
                color.animateTo(
                    if (index == selectedIndex) selectedColor else unSelectedColor,
                    animationSpec = tween(300)
                )
            }

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Dot(size = size, color = color.value, shape = RoundedCornerShape(16.dp))
            }
        }
    }
}
@Composable
private fun Dot(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    size: Dp = 8.dp,
    color: Color
) {
    Box(
        modifier = modifier
            .padding(horizontal = 3.dp)
            .height(8.dp)
            .width(size)
            .clip(shape)
            .background(color)
    )
}