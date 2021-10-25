package net.swiftzer.sortdemo.currency

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@ActivityRetainedScoped
class CurrencyListHook @Inject constructor() {
    private val _clickedCurrency =
        MutableSharedFlow<CurrencyInfo>(
            replay = 0,
            extraBufferCapacity = 8,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    val clickedCurrency: SharedFlow<CurrencyInfo> = _clickedCurrency
    private val _currencies = MutableStateFlow<List<CurrencyInfo>>(emptyList())
    val currencies: StateFlow<List<CurrencyInfo>> = _currencies

    suspend fun onClickCurrency(currency: CurrencyInfo) {
        _clickedCurrency.emit(currency)
    }

    fun submitCurrencies(list: List<CurrencyInfo>) {
        _currencies.value = list
    }
}
