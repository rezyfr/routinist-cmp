package id.rezyfr.routinist.common

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import id.rezyfr.routinist.RoutinistDatabase
import org.koin.core.scope.Scope
import org.w3c.dom.Worker

actual fun Scope.sqlDriverFactory(): SqlDriver {
    val driver = WebWorkerDriver(
        Worker(
            js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")
        )
    )
    RoutinistDatabase.Schema.create(driver)
    return driver
}

