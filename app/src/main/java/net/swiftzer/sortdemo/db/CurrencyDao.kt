package net.swiftzer.sortdemo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.swiftzer.sortdemo.currency.CurrencyInfo

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currencies")
    suspend fun getAllCurrencies(): List<CurrencyInfo>

    @Query("SELECT * FROM currencies ORDER BY name ASC")
    suspend fun getAllCurrenciesSorted(): List<CurrencyInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<CurrencyInfo>)

    @Query("DELETE FROM currencies")
    suspend fun clear()
}
