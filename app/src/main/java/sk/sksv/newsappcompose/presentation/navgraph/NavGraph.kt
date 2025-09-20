package sk.sksv.newsappcompose.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import sk.sksv.newsappcompose.presentation.home.HomeScreen
import sk.sksv.newsappcompose.presentation.home.HomeViewModel
import sk.sksv.newsappcompose.presentation.onbording.OnBoardingScreen
import sk.sksv.newsappcompose.presentation.onbording.OnBoardingViewModel

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            startDestination = Route.OnBoardingScreen.route,
            route = Route.AppStartNavigation.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel<OnBoardingViewModel>()
                OnBoardingScreen(event = viewModel::onEvent)
            }
        }
        navigation(
            startDestination = Route.NewsNavigatorScreen.route,
            route = Route.NewsNavigation.route
        ) {
            composable(route = Route.NewsNavigatorScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(articles = articles,navigate = {

                })
            }
        }
    }

}