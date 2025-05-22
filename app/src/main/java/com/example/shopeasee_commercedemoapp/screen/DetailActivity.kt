package com.example.shopeasee_commercedemoapp.screen

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.shopeasee_commercedemoapp.data.model.ProductModel
import com.example.shopeasee_commercedemoapp.ui.theme.ShopEaseEcommerceDemoAppTheme
import com.example.shopeasee_commercedemoapp.viewModels.DetailedViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.shopeasee_commercedemoapp.CartViewModelFactory
import com.example.shopeasee_commercedemoapp.viewModels.CartViewModel

class DetailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val productId = intent.getIntExtra("productId", -1)

        enableEdgeToEdge()
        setContent {
            val viewModel: DetailedViewModel = viewModel()
            val context = LocalContext.current
            val cartViewModel: CartViewModel = viewModel(factory = CartViewModelFactory(context))
            LaunchedEffect(productId) {
                viewModel.getDetail(productId)
            }
            val product by viewModel.productDetail
            ShopEaseEcommerceDemoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Product Detail") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFFE1BEE7)
                            ),
                            navigationIcon = {
                                IconButton(onClick = {
                                    (context as Activity).finish()

                                }) {
                                    Icon(Icons.Default.ArrowBack, contentDescription = "Back",
                                        tint = Color.Black)
                                }
                            }

                        )
                    },) { innerPadding ->

                    if(product!=null){
                        Greeting(
                             modifier = Modifier
                                 .padding(innerPadding)        // Scaffold ka padding
                                .systemBarsPadding(),
                            product!!,
                            cartViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier, product: ProductModel, cartViewModel: CartViewModel) {
    val quantity = remember { mutableStateOf(0) }
    Column{
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
            item {
                AsyncImage(model = product.image, contentDescription = "product Image")
            }
            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = product.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f),
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyLarge
                    )
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "wishlist", tint = Color.Gray)
Spacer(modifier = Modifier.padding(8.dp))
                        Icon(Icons.Default.Share, contentDescription = "share", tint = Color.Gray)
                }

            }
            item {
                Text(
                    text = "₹"+product.price.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge,

                )
            }
            item {
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                )
            }
        }
        if (quantity.value == 0) {
            Button(
                onClick = {
                    cartViewModel.addToCart(product,quantity.value)
                    quantity.value = 1
                },
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE1BEE7))
            ) {
                Text("Add to Cart", color = Color.Black)
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    quantity.value -= 1
                    cartViewModel.addToCart(product,quantity.value)
                }) { Icon(Icons.Default.Remove, contentDescription = "remove") }

                Text(" ${quantity.value} added", modifier = Modifier.padding(5.dp))

                IconButton(onClick = {
                    quantity.value += 1
                    cartViewModel.addToCart(product,quantity.value)
                }) {
                    Icon(Icons.Default.Add, contentDescription = "add")
                }

            }
        }
    }
}
