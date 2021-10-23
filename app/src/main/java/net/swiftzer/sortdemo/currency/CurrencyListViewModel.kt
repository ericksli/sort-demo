package net.swiftzer.sortdemo.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor() : ViewModel() {
    private val _currencies = MutableStateFlow<List<CurrencyInfo>>(emptyList())
    val currencies: StateFlow<List<CurrencyInfo>> = _currencies

    fun updateCurrencies(currencies: List<CurrencyInfo>) {
        _currencies.value = currencies
    }
}
