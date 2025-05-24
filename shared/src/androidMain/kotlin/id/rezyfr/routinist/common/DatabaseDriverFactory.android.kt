package id.rezyfr.routinist.common

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import id.rezyfr.routinist.RoutinistDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

actual fun Scope.sqlDriverFactory(): SqlDriver {
    return AndroidSqliteDriver(RoutinistDatabase.Schema, androidContext(), "routinist.db")
}
