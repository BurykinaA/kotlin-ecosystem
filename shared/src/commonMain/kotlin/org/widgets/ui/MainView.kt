package org.widgets.ui

//import AuthScreen
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import org.widgets.theme.WidgetGalleryTheme

@Composable
fun MainView() {
    var isAuthenticated by remember { mutableStateOf(false) }
    
    WidgetGalleryTheme {
        Surface {
            ContentPanel()
//            if (isAuthenticated) {
//                ContentPanel()
//            } else {
//                AuthScreen(
//                    onAuthSuccess = { isAuthenticated = true }
//                )
//            }
        }
    }
}