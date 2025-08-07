package com.example.exampgr208.screens.product_details

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.exampgr208.data.mockProducts

// Button Add to Cart - Button 1
@Composable
fun Button_AddToCart(
    onAddToCartClick: () -> Unit,
    isInShoppingCart: Boolean,
    modifier: Modifier = Modifier
) {
    val buttonText = remember { mutableStateOf(if (isInShoppingCart) "Remove from cart" else "Add to cart") }

    Button(
        onClick = {
            onAddToCartClick()
        },
        modifier = modifier.fillMaxWidth(0.8f)
    ) {
        Text(
            buttonText.value,
            style = MaterialTheme.typography.labelLarge
        )
    }

    LaunchedEffect(isInShoppingCart) {
        buttonText.value = if (isInShoppingCart) "Remove from cart" else "Add to cart"
    }
}

// Button Add to Favorites - Button 2
@Composable
fun Button_AddToFavorites() {
    var checked by remember { mutableStateOf(false) }

    IconButton(
        onClick = { checked = !checked },
        modifier = Modifier

    ) {
        val tint by animateColorAsState(if (checked) Color.Red else Color.Gray,
            label = ""
        )
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Heart shaped button to add to favorites",
            tint = tint,
            modifier = Modifier.size(40.dp)
        )
    }
}

// Button Show Favorites - Button 3
@Composable
fun Button_ShowFavorites() {
    var checked by remember { mutableStateOf(false) }

    IconButton(
        onClick = { checked = !checked },
        modifier = Modifier

            .padding(end = 10.dp)
            .background(
                color = Color.Transparent,
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Heart shaped button to show favorites",
            modifier = Modifier
                .size(30.dp),
            tint = Color.Red
        )
    }
}

// Button Back - Button 4
@Composable
fun Button_Back(onBackButtonClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .clickable { onBackButtonClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back button"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.padding(1.dp),
            text = "Product Details",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

// Button RemoveFromCart - Button 5
@Composable
fun Button_RemoveFromCart(onRemoveFromCartClick: () -> Unit) {
    Button(
        onClick = { onRemoveFromCartClick() },
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Text("Remove from cart", style = MaterialTheme.typography.labelLarge)
    }
}


@Preview
@Composable
fun Button1Preview() {
    Button_AddToCart({}, false)
}

@Preview
@Composable
fun Button2Preview() {
    Button_AddToFavorites()
}

@Preview
@Composable
fun Button3Preview() {
    Button_ShowFavorites()
}

@Preview
@Composable
fun Button4Preview() {
    Button_Back()
}