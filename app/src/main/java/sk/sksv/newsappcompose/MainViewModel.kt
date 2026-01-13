package sk.sksv.newsappcompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import sk.sksv.newsappcompose.data.local.NewsDao
import sk.sksv.newsappcompose.domain.usecases.app_entry.AppEntryUseCases
import sk.sksv.newsappcompose.presentation.navgraph.Route
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
    private val newsDao: NewsDao
) : ViewModel() {
    var splashCondition by mutableStateOf(true)
        private set

    var databaseReady by mutableStateOf(false)
        private set

    var startDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    init {
        checkDatabaseReadiness()
    }

    private fun checkDatabaseReadiness() {
        viewModelScope.launch {
            try {
                // Try to perform a simple query to check if database is ready
                newsDao.checkDatabaseReady()
                databaseReady = true
                
                // Once database is ready, proceed with app entry check
                appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
                    startDestination = if (shouldStartFromHomeScreen) {
                        Route.NewsNavigation.route
                    } else {
                        Route.AppStartNavigation.route
                    }
                    delay(300)
                    splashCondition = false
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                // If database check fails, retry after a delay
                delay(500)
                checkDatabaseReadiness()
            }
        }
    }
}