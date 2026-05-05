package org.widgets.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.widgets.ui.utils.PanelState
import org.widgets.ui.utils.ResizablePanel
import org.widgets.ui.utils.VerticalSplittable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import org.widgets.ui.data.Director
import org.widgets.ui.data.Movie
import org.widgets.ui.data.fetchDistributors
import org.widgets.ui.data.fetchMoviesTitles
import org.widgets.ui.views.ContentTypeListView
import org.widgets.ui.views.InfiniteListView

@Composable
fun ContentPanel() {
    val selectedContentType = rememberSaveable { mutableStateOf(ContentType.LIST_1) }
    val panelState = remember { PanelState() }

    val animatedSize = if (panelState.splitter.isResizing) {
        if (panelState.isExpanded) panelState.expandedSize else panelState.collapsedSize
    } else {
        animateDpAsState(
            if (panelState.isExpanded) panelState.expandedSize else panelState.collapsedSize,
            SpringSpec(stiffness = Spring.StiffnessLow)
        ).value
    }

    var list1 by remember { mutableStateOf(emptyList<String>()) }
    var list2 by remember { mutableStateOf(emptyList<String>()) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                list1 = fetchMoviesTitles()
                list2 = fetchDistributors()
            } catch (e: Exception) {
                println("Error loading data: ${e.message}")
            }
        }
    }

    VerticalSplittable(
        Modifier.fillMaxSize(),
        panelState.splitter,
        onResize = {
            panelState.expandedSize =
                (panelState.expandedSize + it).coerceAtLeast(panelState.expandedSizeMin)
        }
    ) {
        ResizablePanel(
            Modifier.width(animatedSize).fillMaxHeight(),
            title = "Content Types",
            state = panelState
        ) {
            ContentTypeListView(selectedContentType)
        }

        Box {
            Column {
                when (selectedContentType.value) {
                    ContentType.LIST_1 -> InfiniteListView(
                        items = list1,
                        modifier = Modifier.weight(1f)
                    )
                    ContentType.LIST_2 -> InfiniteListView(
                        items = list2,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}




val ContentType.listItemTestTag: String
    get() = "${testTag}_list_item"

