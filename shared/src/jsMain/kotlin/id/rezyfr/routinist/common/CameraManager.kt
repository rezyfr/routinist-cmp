package id.rezyfr.routinist.common

import androidx.compose.runtime.Composable
import id.rezyfr.routinist.common.CameraManager
import id.rezyfr.routinist.common.SharedImage

@Composable
actual fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager {
    return CameraManager { onResult(null) }
}


actual class CameraManager actual constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launch() {
        onLaunch()
    }
}
