package presentation

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.fetch.NetworkFetcher
import common.Context
import di.appModule
import org.koin.compose.KoinApplication
import presentation.theme.AppTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
internal fun App(context: Context?) {
    KoinApplication(application = {
        modules(appModule(context))
    }) {
        setSingletonImageLoaderFactory { context ->
            ImageLoader.Builder(context)
                .components {
                    add(NetworkFetcher.Factory())
                }
                .build()
        }

        AppTheme {
        }
    }
}




