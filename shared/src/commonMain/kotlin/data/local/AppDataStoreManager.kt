package data.local

import common.Context
import common.getData
import common.putData

const val APP_DATASTORE = "id.rezyfr.routinist"

class AppDataStoreManager(val context: Context?) : AppDataStore {

    override suspend fun setValue(
        key: String,
        value: String
    ) {
        context.putData(key, value)
    }

    override suspend fun readValue(
        key: String,
    ): String? {
        return context.getData(key)
    }
}