package org.widgets.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.widgets.platform.VerticalScrollbar
import org.widgets.ui.utils.withoutWidthConstraints


@Composable
fun InfiniteListView(
    items: List<String>,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        val scrollState = rememberLazyListState()

        LazyColumn(
            modifier = Modifier.fillMaxSize().withoutWidthConstraints(),
            state = scrollState
        ) {
            items(items) { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 14.sp
                )
            }
        }

        VerticalScrollbar(
            Modifier.align(Alignment.CenterEnd),
            scrollState,
            items.size,
            24.dp
        )
    }
}
