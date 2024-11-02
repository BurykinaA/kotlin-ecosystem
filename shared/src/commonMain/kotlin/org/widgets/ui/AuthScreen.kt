import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//
//@Composable
//fun AuthScreen(
//    onAuthSuccess: () -> Unit
//) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Button(
//            onClick = {
//                // Инициализация Google Sign In
//                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestEmail()
//                    .requestId()
//                    .requestProfile()
//                    .build()
//
//                // Запуск процесса авторизации
//                val signInIntent = GoogleSignIn.getClient(context, gso).signInIntent
//                launcher.launch(signInIntent)
//            }
//        ) {
//            Text("Войти через Google")
//        }
//    }
//}
//
//// Обработчик результата авторизации
//private val launcher = rememberLauncherForActivityResult(
//    contract = ActivityResultContracts.StartActivityForResult()
//) { result ->
//    try {
//        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//        val account = task.getResult(ApiException::class.java)
//        // Успешная авторизация
//        onAuthSuccess()
//    } catch (e: ApiException) {
//        // Ошибка авторизации
//        println("Google sign in failed: ${e.message}")
//    }
//}