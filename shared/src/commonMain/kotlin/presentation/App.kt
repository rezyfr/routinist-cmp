package presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.fetch.NetworkFetcher
import common.Context
import di.appModule
import org.koin.compose.KoinApplication
import presentation.navigation.AppNavigation
import presentation.theme.AppTheme
import presentation.ui.main.MainNav
import presentation.ui.onboarding.OnBoardingNav

@OptIn(ExperimentalCoilApi::class)
@Composable
internal fun App(context: Context?) {
    KoinApplication(application = {
        modules(appModule())
    }) {
        setSingletonImageLoaderFactory { context ->
            ImageLoader.Builder(context)
                .components {
                    add(NetworkFetcher.Factory())
                }
                .build()
        }

        AppTheme {
            val navigator = rememberNavController()
            Box(modifier = Modifier.fillMaxSize()) {
                NavHost(
                    navController = navigator,
                    startDestination = AppNavigation.OnBoarding,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable<AppNavigation.OnBoarding> {
                        OnBoardingNav(
                            navigateToMain = {
                                navigator.popBackStack()
                                navigator.navigate(AppNavigation.Main)
                            }
                        )
                    }

                    composable<AppNavigation.Main> {
                        MainNav()
                    }
                }
            }
        }
    }
}




