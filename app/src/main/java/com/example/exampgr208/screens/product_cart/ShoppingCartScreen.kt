package com.example.exampgr208.screens.product_cart

import com.example.exampgr208.screens.product_cart.ShoppingCartViewModel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.exampgr208.screens.product_list.ProductItem
import com.example.exampgr208.ui.theme.OffWhite

@Composable
fun ShoppingCartScreen(
    viewModel: ShoppingCartViewModel,
    onBackButtonClick: () -> Unit = {},
    onProductClick: (productId: Int) -> Unit = {},
) {
    val product = viewModel.shoppingCartProducts.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = OffWhite),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBackButtonClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }
            Text(
                text = "Shopping Cart",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Divider()

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(product.value) { product ->
                    ProductItem(
                        product = product,
                        onClick = {
                            onProductClick(product.id)
                        }
                    )
                }
            }

            Button(
                onClick = {
                    viewModel.checkout()
                },
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    "Checkout",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
            }
        }
    }
}