package com.example.shopeasee_commercedemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopeasee_commercedemoapp.ui.theme.ShopEaseEcommerceDemoAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.shopeasee_commercedemoapp.viewModels.ProductViewModel

class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopEaseEcommerceDemoAppTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp)) { innerPadding ->
                    ProductListScreen(
                        modifier = Modifier.padding(innerPadding),

                        )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProductListScreen(modifier: Modifier = Modifier, viewModel: ProductViewModel = viewModel()) {
    var expend = remember{mutableStateOf(false)}
    val products by viewModel.product.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var filterList = remember(searchQuery, products) {
        products.filter {
            it.title.contains(searchQuery, ignoreCase = true)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE1BEE7)
                ),
                actions = {
                    IconButton(onClick = {
                        expend.value = true
                    }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "menu",
                            tint = Color.Black
                        )
                    }
                    DropdownMenu(expanded = expend.value,
                        onDismissRequest = {expend.value =false}
                        ) {
                        DropdownMenuItem(
                            text = { Text("Sort by Price") },
                            onClick = {
                                expend.value=false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Sort by Name") },
                            onClick = {
                                expend.value=false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Filter by Category") },
                            onClick = {
                                expend.value=false
                            }
                        )

                    }

                }
            )
        },
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(padding)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                placeholder = { Text("Search") },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "search")
                }
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                content = {
                    items(filterList) { item ->
                        Card(
                            modifier = Modifier.padding(10.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFE1BEE7)
                            )
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = item.image,
                                    contentDescription = "Product Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                )
                                Text(
                                    item.title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(2.dp),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleLarge,

                                    )

                                Text(
                                    text = item.price.toString(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(2.dp),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }
                    }

                })
        }

    }
}

@Preview(showBackground = true, widthDp = 500, heightDp = 1000)
@Composable
fun GreetingPreview() {

    ShopEaseEcommerceDemoAppTheme {
        ProductListScreen()
    }
}