package net.swiftzer.sortdemo.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.swiftzer.sortdemo.currency.CurrencyListHook
import net.swiftzer.sortdemo.currency.CurrencyRepository
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val currencyListHook: CurrencyListHook,
) : ViewModel() {
    private val request = Channel<Boolean>(
        capacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val clickedCurrency = currencyListHook.clickedCurrency

    init {
        viewModelScope.launch {
            request.receiveAsFlow()
                .map { sorted ->
                    if (!sorted) {
                        repository.populateDatabase()
                    }
                    repository.getCurrencies(sorted)
                }
                .collect { currencyListHook.submitCurrencies(it) }
        }
    }

    fun onClickLoadData() {
        viewModelScope.launch {
            request.send(false)
        }
    }

    fun onClickSort() {
        viewModelScope.launch {
            request.send(true)
        }
    }
}
