package id.rezyfr.routinist.common

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import id.rezyfr.routinist.RoutinistDatabase
import org.koin.core.scope.Scope

actual fun Scope.sqlDriverFactory(): SqlDriver {
    return NativeSqliteDriver(RoutinistDatabase.Schema, "routinist.db")
}
