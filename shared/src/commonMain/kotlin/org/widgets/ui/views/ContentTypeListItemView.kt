package org.widgets.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import org.widgets.ui.ContentType
import org.widgets.ui.listItemTestTag


@Composable
fun ContentTypeListItemView(
    contentType: ContentType,
    selectedContentType: MutableState<ContentType>,
    fontSize: TextUnit,
    height: Dp
) {
    val isCurrent = selectedContentType.value == contentType

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .clickable { selectedContentType.value = contentType }
            .semantics {
                set(SemanticsProperties.Role, Role.Button)
            }
            .height(height)
            .padding(start = 16.dp)
            .testTag(contentType.listItemTestTag)
    ) {
        val inFocusInteractionSource = remember { MutableInteractionSource() }
        val inFocus by inFocusInteractionSource.collectIsHoveredAsState()
        val textColor = LocalContentColor.current.let {
            when {
                isCurrent -> it
                inFocus -> it.copy(alpha = 0.6f)
                else -> it.copy(alpha = 0.4f)
            }
        }

        Text(
            text = contentType.title,
            color = textColor,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clipToBounds()
                .hoverable(inFocusInteractionSource),
            softWrap = true,
            fontSize = fontSize,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}
