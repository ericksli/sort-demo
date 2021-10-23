package net.swiftzer.sortdemo.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.swiftzer.sortdemo.theme.AppTheme

@AndroidEntryPoint
class CurrencyListFragment : Fragment() {

    private val viewModel by viewModels<CurrencyListViewModel>()
    private var callback: CurrencyListCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkParentHasImplementedCallback()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            AppTheme {
                CurrencyListScreen(
                    viewModel = viewModel,
                    onClickCurrency = { callback?.onClickCurrency(it) }
                )
            }
        }
    }

    fun updateList(currencies: List<CurrencyInfo>) {
        viewModel.updateCurrencies(currencies)
    }

    private fun checkParentHasImplementedCallback() {
        callback = if (parentFragment != null) {
            require(parentFragment is CurrencyListCallback) { "Parent fragment must implement CurrencyListCallback" }
            parentFragment as CurrencyListCallback
        } else {
            require(requireActivity() is CurrencyListCallback) { "Parent activity must implement CurrencyListCallback" }
            requireActivity() as CurrencyListCallback
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback = null
    }
}
