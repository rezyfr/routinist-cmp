package id.rezyfr.routinist.common

import app.cash.sqldelight.db.SqlDriver
import id.rezyfr.routinist.RoutinistDatabase
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.koin.core.scope.Scope

actual fun Scope.sqlDriverFactory(): SqlDriver {
    val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    RoutinistDatabase.Schema.create(driver)
    return driver
}
