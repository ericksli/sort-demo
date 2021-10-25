package net.swiftzer.sortdemo.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val currencyListHook: CurrencyListHook,
) : ViewModel() {
    val currencies: StateFlow<List<CurrencyInfo>> = currencyListHook.currencies

    fun onClickCurrency(currency: CurrencyInfo) {
        viewModelScope.launch {
            currencyListHook.onClickCurrency(currency)
        }
    }
}
