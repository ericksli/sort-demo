package net.swiftzer.sortdemo.currency

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import net.swiftzer.sortdemo.IoDispatcher
import net.swiftzer.sortdemo.db.AppDatabase
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val database: AppDatabase,
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun populateDatabase() = withContext(ioDispatcher) {
        val currencies = context.assets.open("currencies.json").use {
            Json.decodeFromStream<List<CurrencyInfo>>(it)
        }
        database.currencyDao.insertAll(currencies)
    }

    suspend fun getCurrencies(sorted: Boolean): List<CurrencyInfo> = if (sorted) {
        database.currencyDao.getAllCurrenciesSorted()
    } else {
        database.currencyDao.getAllCurrencies()
    }
}
