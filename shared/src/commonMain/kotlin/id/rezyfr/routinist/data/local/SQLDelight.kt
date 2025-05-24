package id.rezyfr.routinist.data.local

import app.cash.sqldelight.db.SqlDriver
import id.rezyfr.routinist.RoutinistDatabase

internal fun createDatabase(driver: SqlDriver): RoutinistDatabase {
    return RoutinistDatabase(driver)
}