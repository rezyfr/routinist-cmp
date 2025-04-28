package di


import business.core.AppDataStore
import business.core.AppDataStoreManager
import common.Context
import org.koin.dsl.module

fun appModule(context: Context?) = module {
    single<AppDataStore> { AppDataStoreManager(context) }
}