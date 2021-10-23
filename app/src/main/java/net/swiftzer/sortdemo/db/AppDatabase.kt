package net.swiftzer.sortdemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import net.swiftzer.sortdemo.currency.CurrencyInfo

@Database(
    entities = [CurrencyInfo::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val currencyDao: CurrencyDao
}
