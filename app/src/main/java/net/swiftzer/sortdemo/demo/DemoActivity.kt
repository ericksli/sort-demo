package net.swiftzer.sortdemo.demo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.swiftzer.sortdemo.R
import net.swiftzer.sortdemo.currency.CurrencyListFragment
import net.swiftzer.sortdemo.databinding.DemoActivityBinding

private const val TAG_LIST = "list"

@AndroidEntryPoint
class DemoActivity : AppCompatActivity() {
    private val viewModel by viewModels<DemoViewModel>()
    private lateinit var binding: DemoActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DemoActivityBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)

        var fragment = supportFragmentManager.findFragmentByTag(TAG_LIST) as? CurrencyListFragment
        if (fragment == null) {
            supportFragmentManager.commit {
                val f = CurrencyListFragment()
                replace(binding.listFragment.id, f, TAG_LIST)
                fragment = f
            }
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.clickedCurrency.collect {
                    Toast.makeText(
                        this@DemoActivity,
                        getString(R.string.clicked, it.name),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
