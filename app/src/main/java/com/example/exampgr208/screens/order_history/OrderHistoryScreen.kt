package com.example.exampgr208.screens.order_history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.exampgr208.data.OrderHistory
import com.example.exampgr208.screens.product_favorites.FavoriteViewModel
import com.example.exampgr208.screens.product_list.ProductItem
import com.example.exampgr208.ui.theme.OffWhite

@Composable
fun OrderHistoryScreen(
    viewModel: OrderHistoryViewModel,
    onBackButtonClick: () -> Unit = {},
    onProductClick: (productId: Int) -> Unit = {},
) {

    LaunchedEffect(Unit) {
        viewModel.loadOrderHistory()
    }
    val orderHistory = viewModel.orderHistory.collectAsState()

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
                text = "Order History",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Divider()

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(orderHistory.value) { order ->
                OrderHistoryItem(
                    orderHistory = order,
                    onClick = { onProductClick(order.orderId)
                    }
                )
            }
        }
    }
}

@Composable
fun OrderHistoryItem(
    orderHistory: OrderHistory,
    onClick: () -> Unit = {},
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "Order ID: ${orderHistory.orderId}",
        )

        // Handle click event
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .clickable(onClick = onClick),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "View Details",
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )
        }
    }
}