package com.example.exampgr208.screens.product_details

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.exampgr208.ui.theme.OffWhite

@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel,
    onBackButtonClick: () -> Unit = {},
    navigateToFavoriteList: () -> Unit = {},
    navigateToShoppingCart: () -> Unit = {}
) {
    val productState = viewModel.selectedProduct.collectAsState()
    val loading = viewModel.loading.collectAsState()
    val isFavorite = viewModel.isFavorite.collectAsState()

    if (loading.value) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val product = productState.value
    if(product == null) {
        Text(text = "Failed to get product details.")
        return
    }

// Section on top of screen
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 6.dp)
                .background(color = OffWhite),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = { viewModel.loadProducts() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Refresh products",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(
                onClick = { navigateToShoppingCart() }
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Shopping cart screen",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(
                onClick = { navigateToFavoriteList() }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite screen",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Divider()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(state = rememberScrollState()),
        ) {

            // Product category
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${product.category}",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )

            // Product images
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
            ) {
                items(product.images) { imageUrl ->
                    AsyncImage(
                        modifier = Modifier
                            .width(300.dp)
                            .aspectRatio(1f),
                        model = imageUrl,
                        contentScale = ContentScale.Crop,
                        contentDescription = "Image of ${product.title}"
                    )
                }
            }


            // Product brand
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${product.brand}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )

            // Product name
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            // Product price
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                // Product rating
                Text(
                    text = "${product.rating}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.LightGray
                )
            }

            // Product description
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${product.description}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            // Bottom section of screen
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Add to cart & Add to favorites buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    // Button AddToCart
                    Button_AddToCart(
                        onAddToCartClick = { viewModel.updateShoppingCart(product.id) },
                        isInShoppingCart = viewModel.isInShoppingCart.collectAsState().value
                    )

                    // Button AddToFavorites
                    IconButton(
                        onClick = { viewModel.updateFavorite(product.id) }
                    ) {
                        Icon(
                            imageVector = if (isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Heart shaped button to add to favorites",
                            tint = Color.Red,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
    }
}



