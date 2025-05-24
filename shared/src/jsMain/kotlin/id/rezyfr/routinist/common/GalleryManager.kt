package id.rezyfr.routinist.common

import androidx.compose.runtime.Composable
import id.rezyfr.routinist.common.GalleryManager
import id.rezyfr.routinist.common.SharedImage

@Composable
actual fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager =
    GalleryManager {}


actual class GalleryManager actual constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launch() {}
}
