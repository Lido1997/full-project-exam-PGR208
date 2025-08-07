package com.example.exampgr208

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.exampgr208.data.ProductRepository
import com.example.exampgr208.screens.order_history.OrderHistoryScreen
import com.example.exampgr208.screens.order_history.OrderHistoryViewModel
import com.example.exampgr208.screens.product_cart.ShoppingCartScreen
import com.example.exampgr208.screens.product_cart.ShoppingCartViewModel
import com.example.exampgr208.ui.theme.ExamPGR208Theme
import com.example.exampgr208.screens.product_details.ProductDetailsScreen
import com.example.exampgr208.screens.product_details.ProductDetailsViewModel
import com.example.exampgr208.screens.product_list.ProductListScreen
import com.example.exampgr208.screens.product_list.ProductListViewModel
import com.example.exampgr208.screens.product_details.Button_Back
import com.example.exampgr208.screens.product_favorites.FavoriteScreen
import com.example.exampgr208.screens.product_favorites.FavoriteViewModel


class MainActivity : ComponentActivity() {
    private val _productListViewModel: ProductListViewModel by viewModels()
    private val _productDetailsViewModel: ProductDetailsViewModel by viewModels()
    private val _favoriteViewModel: FavoriteViewModel by viewModels()
    private val _shoppingCartViewModel: ShoppingCartViewModel by viewModels()
    private val _orderHistoryViewModel: OrderHistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ProductRepository.initiateAppDatabase(applicationContext)

        setContent {
            ExamPGR208Theme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "productListScreen"
                ) {

                    // Product list screen
                    composable(route = "productListScreen"
                    ) {
                        ProductListScreen(
                            viewModel = _productListViewModel,
                            onProductClick = { productId ->
                                navController.navigate("productDetailsScreen/$productId")
                            },
                            navigateToFavoriteList = {
                                navController.navigate("favoriteScreen")
                            },
                            navigateToShoppingCart = {
                                navController.navigate("shoppingCartScreen")
                            },
                            navigateToOrderHistory = {
                                navController.navigate("orderHistoryScreen")
                            }
                        )
                    }

                    // Product details screen
                    composable(
                        route = "productDetailsScreen/{productId}",
                        arguments = listOf(
                            navArgument(name = "productId") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: -1
                        LaunchedEffect(productId) {
                            _productDetailsViewModel.setSelectedProduct(productId)
                        }

                        ProductDetailsScreen(
                            viewModel = _productDetailsViewModel,
                            onBackButtonClick = { navController.popBackStack() },
                            navigateToFavoriteList = {
                                navController.navigate("favoriteScreen")
                            },
                            navigateToShoppingCart = {
                                navController.navigate("shoppingCartScreen")
                            }
                        )

                        Button_Back{ navController.popBackStack()
                        }
                    }

                    // Favorite screen
                    composable(route = "favoriteScreen") {
                        LaunchedEffect(Unit) {
                            _favoriteViewModel.loadFavorites()
                        }

                        FavoriteScreen(
                            viewModel = _favoriteViewModel,
                            onBackButtonClick = { navController.popBackStack() },
                            onProductClick = { productId ->
                                navController.navigate("productDetailsScreen/$productId")
                            }
                        )
                    }

                    // ShoppingCart screen
                    composable(route = "shoppingCartScreen") {
                        LaunchedEffect(Unit) {
                            _shoppingCartViewModel.loadShoppingCart()
                        }

                        ShoppingCartScreen(
                            viewModel = _shoppingCartViewModel,
                            onBackButtonClick = { navController.popBackStack() },
                            onProductClick = { productId ->
                                navController.navigate("productDetailsScreen/$productId")
                            }
                        )
                    }

                    // OrderHistory screen
                    composable(route = "orderHistoryScreen") {
                        LaunchedEffect(Unit) {
                            _orderHistoryViewModel.loadOrderHistory()
                        }

                        OrderHistoryScreen(
                            viewModel = _orderHistoryViewModel,
                            onBackButtonClick = { navController.popBackStack() },
                            onProductClick = { productId ->
                                navController.navigate("productDetailsScreen/$productId")
                            }
                        )
                    }
                }
            }
        }
    }
}
