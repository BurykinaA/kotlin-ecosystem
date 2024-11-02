package org.widgets.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import org.widgets.platform.VerticalScrollbar
import org.widgets.ui.ContentType
import org.widgets.ui.utils.withoutWidthConstraints


@Composable
fun ContentTypeListView(selectedContentType: MutableState<ContentType>) {
    Box {
        with(LocalDensity.current) {
            val scrollState = rememberLazyListState()

            val fontSize = 14.sp
            val lineHeight = fontSize.toDp() * 1.5f

            val sortedItems = ContentType.sortedValues
            LazyColumn(
                modifier = Modifier.fillMaxSize().withoutWidthConstraints(),
                state = scrollState
            ) {
                items(sortedItems) {
                    ContentTypeListItemView(it, selectedContentType, fontSize, lineHeight)
                }
            }

            VerticalScrollbar(
                Modifier.align(Alignment.CenterEnd),
                scrollState,
                sortedItems.size,
                lineHeight
            )
        }
    }

}