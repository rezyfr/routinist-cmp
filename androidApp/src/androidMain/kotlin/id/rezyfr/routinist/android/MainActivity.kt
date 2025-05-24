package id.rezyfr.routinist.android

import MainView
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import id.rezyfr.routinist.di.appModule
import id.rezyfr.routinist.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class AndroidApp : Application() {

    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        initKoin() {
            androidLogger()
            androidContext(this@AndroidApp)
            modules(appModule())
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // initKoin()

        setContent {
            MainView(application)
        }
    }
}