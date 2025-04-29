package di


import data.local.AppDataStore
import data.local.AppDataStoreManager
import common.Context
import org.koin.dsl.module

fun appModule(context: Context?) = module {
    single<AppDataStore> { AppDataStoreManager(context) }
}