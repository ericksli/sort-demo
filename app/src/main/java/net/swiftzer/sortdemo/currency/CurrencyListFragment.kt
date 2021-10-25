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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            AppTheme {
                CurrencyListScreen(viewModel = viewModel)
            }
        }
    }
}
