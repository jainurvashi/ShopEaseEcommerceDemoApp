package com.example.shopeasee_commercedemoapp.screen

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.shopeasee_commercedemoapp.data.model.ProductModel
import com.example.shopeasee_commercedemoapp.ui.theme.ShopEaseEcommerceDemoAppTheme
import com.example.shopeasee_commercedemoapp.viewModels.DetailedViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.shopeasee_commercedemoapp.data.model.Rating

class DetailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val productId = intent.getIntExtra("productId", -1)

        enableEdgeToEdge()
        setContent {
            val viewModel: DetailedViewModel = viewModel()
            LaunchedEffect(productId) {
                viewModel.getDetail(productId)
            }
            val context = LocalContext.current
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
                            product!!
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier,product:ProductModel) {
    Column{
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
            item {
                AsyncImage(model = product.image, contentDescription = "product Image")
            }
            item {
                Text(
                    text = product.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            item {
                Text(
                    text = product.category,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            item {
                Text(
                    text = product.price.toString(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    color = Color.Green,
                    style = MaterialTheme.typography.bodyMedium
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

        Button(onClick = {},
            modifier = Modifier.height(70.dp).fillMaxWidth().padding(10.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE1BEE7))) { Text("Add to Cart", color = Color.Black)
        }
    }


}

@Preview(showBackground = true, widthDp = 500, heightDp = 1000)
@Composable
private fun previewww() {
    val product = ProductModel(
        id = 1,
        title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
        price = 109.95,
        description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
        category = "men's clothing",
        image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
        rating = Rating(
            rate = 3.9,
            count = 120
        )
    )
    Greeting(
        product= product
    )
}
