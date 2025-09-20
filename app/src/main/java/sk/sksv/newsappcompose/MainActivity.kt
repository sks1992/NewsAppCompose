package sk.sksv.newsappcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import sk.sksv.newsappcompose.domain.usecases.AppEntryUseCases
import sk.sksv.newsappcompose.presentation.onbording.OnBoardingScreen
import sk.sksv.newsappcompose.presentation.onbording.OnBoardingViewModel
import sk.sksv.newsappcompose.ui.theme.NewsAppComposeTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appEntryUseCases: AppEntryUseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        lifecycleScope.launch {
            appEntryUseCases.readAppEntry().collect {
                Log.d("TAG", it.toString())
            }
        }
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            NewsAppComposeTheme {
                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                    val viewModel: OnBoardingViewModel = hiltViewModel<OnBoardingViewModel>()
                    OnBoardingScreen(event = viewModel::onEvent)
                    // OnBoardingScreen(event = { viewModel.onEvent(it) })
                }
            }
        }
    }
}