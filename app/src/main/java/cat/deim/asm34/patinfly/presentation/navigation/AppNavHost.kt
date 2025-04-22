package cat.deim.asm34.patinfly.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cat.deim.asm34.patinfly.domain.models.User
import cat.deim.asm34.patinfly.presentation.login.LoginScreen
import cat.deim.asm34.patinfly.presentation.login.LoginViewModel
import cat.deim.asm34.patinfly.presentation.splash.SplashScreen
import cat.deim.asm34.patinfly.presentation.main.MainScreen
import cat.deim.asm34.patinfly.presentation.profile.ProfileScreen
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cat.deim.asm34.patinfly.presentation.bikelist.BikeListScreen
import cat.deim.asm34.patinfly.presentation.bikelist.BikeListViewModel
import cat.deim.asm34.patinfly.data.repository.BikeRepository
import cat.deim.asm34.patinfly.data.datasource.local.BikeLocalDataSource
import cat.deim.asm34.patinfly.data.datasource.local.SystemPricingPlanLocalDataSource
import cat.deim.asm34.patinfly.data.repository.SystemPricingPlanRepository
import cat.deim.asm34.patinfly.presentation.pricing.PricingScreen
import cat.deim.asm34.patinfly.presentation.pricing.PricingViewModel
import cat.deim.asm34.patinfly.presentation.bikedetail.BikeDetailScreen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AppNavHost(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onTimeout = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Login.route) {
            LoginScreen(viewModel = loginViewModel) {
                navController.navigate(Screen.Main.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
        }

        composable(Screen.Main.route) {
            MainScreen(
                onGoToProfile = { navController.navigate(Screen.Profile.route) },
                onGoToBikes = { navController.navigate(Screen.BikeList.route) },
                onGoToPricing = { navController.navigate(Screen.Pricing.route) }
            )
        }

        composable(Screen.Profile.route) {
            val user = loginViewModel.loggedUser.collectAsState().value

            if (user != null) {
                ProfileScreen(
                    user = user,
                    onLogoutConfirmed = {
                        loginViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true } // Esto limpia toda la pila de navegación
                        }
                    }
                )
            }
        }



        composable(Screen.BikeList.route) {
            val context = LocalContext.current
            val viewModel = remember {
                BikeListViewModel(
                    BikeRepository(
                        BikeLocalDataSource.getInstance(context)
                    )
                )
            }

            BikeListScreen(
                viewModel = viewModel,
                onViewDetails = { bikeId ->
                    navController.navigate(Screen.BikeDetail.createRoute(bikeId))
                }
            )
        }

        composable(Screen.BikeDetail.route) { backStackEntry ->
            val context = LocalContext.current
            val bikeId = backStackEntry.arguments?.getString("bikeId")

            println("🧪 Navegación a detalles con ID: $bikeId")

            val viewModel = remember {
                BikeListViewModel(
                    BikeRepository(BikeLocalDataSource.getInstance(context))
                )
            }

            LaunchedEffect(Unit) {
                viewModel.loadBikes()
            }

            val bikesState = viewModel.bikes.collectAsState()

            val bike = bikesState.value.find { it.uuid == bikeId }

            if (bike != null) {
                BikeDetailScreen(bike = bike) {
                    navController.popBackStack()
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }


        composable(Screen.Pricing.route) {
            val context = LocalContext.current

            val viewModel = remember {
                PricingViewModel(
                    SystemPricingPlanRepository(
                        SystemPricingPlanLocalDataSource.getInstance(context)
                    )
                )
            }

            PricingScreen(
                viewModel = viewModel,
                onBackToMain = {
                    navController.popBackStack(Screen.Main.route, inclusive = false)
                }
            )
        }
    }
}
