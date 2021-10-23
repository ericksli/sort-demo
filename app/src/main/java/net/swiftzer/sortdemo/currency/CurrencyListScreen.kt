package net.swiftzer.sortdemo.currency

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.swiftzer.sortdemo.R
import net.swiftzer.sortdemo.theme.AppTheme
import java.util.*


@Composable
fun CurrencyListScreen(
    viewModel: CurrencyListViewModel,
    onClickCurrency: (CurrencyInfo) -> Unit = {},
) {
    val currencies by viewModel.currencies.collectAsState()

    if (currencies.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(id = R.string.empty_list),
                style = MaterialTheme.typography.body1,
            )
        }
    } else {
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            itemsIndexed(
                items = currencies,
                key = { _, item -> item.id }
            ) { i, currency ->
                CurrencyListItem(
                    currency = currency,
                    showDivider = i != currencies.indices.last,
                    onClick = { onClickCurrency(currency) },
                )
            }
        }
    }
}

@Composable
fun CurrencyIcon(modifier: Modifier = Modifier, text: String) {
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(RoundedCornerShape(25))
            .background(MaterialTheme.colors.primary),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun CurrencyListItem(
    currency: CurrencyInfo,
    showDivider: Boolean = true,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CurrencyIcon(text = currency.symbol.first().uppercase(Locale.ENGLISH))
            Spacer(modifier = Modifier.width(16.dp))
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.onSurface) {
                Text(text = currency.name, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.weight(1f))
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = currency.symbol)
                    Icon(Icons.Default.NavigateNext, contentDescription = null)
                }
            }
        }
        if (showDivider) {
            Divider(
                modifier = Modifier.align(Alignment.BottomCenter),
                startIndent = 64.dp,
            )
        }
    }
}

@Composable
@Preview(name = "Light mode", showBackground = true)
@Preview(name = "Dark mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PreviewCurrencyListItem() {
    AppTheme {
        CurrencyListItem(currency = CurrencyInfo("BTC", "Bitcoin", "BTC"))
    }
}
