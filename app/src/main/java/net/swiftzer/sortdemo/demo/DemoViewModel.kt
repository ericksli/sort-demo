package net.swiftzer.sortdemo.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.swiftzer.sortdemo.currency.CurrencyInfo
import net.swiftzer.sortdemo.currency.CurrencyRepository
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val repository: CurrencyRepository,
) : ViewModel() {
    private val request = Channel<Boolean>(
        capacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val currencies: Flow<List<CurrencyInfo>> = request.receiveAsFlow()
        .map { sorted ->
            if (!sorted) {
                repository.populateDatabase()
            }
            repository.getCurrencies(sorted)
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
