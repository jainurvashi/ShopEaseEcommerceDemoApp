package com.example.shopeasee_commercedemoapp.screen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.shopeasee_commercedemoapp.viewModels.ProductViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProductListScreen(viewModel: ProductViewModel = viewModel(),navController:NavHostController) {
    var expend = remember{ mutableStateOf(false) }
    val products by viewModel.product.collectAsState()
    val category by viewModel.categoryList.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var shorof by remember { mutableStateOf("") }
    var filteroff by remember { mutableStateOf("") }
    var isBottomSheetShow by remember { mutableStateOf(false) }
    var  filterList = remember(searchQuery, products) {
        products.filter {
            it.title.contains(searchQuery, ignoreCase = true)
        }
    }
    if(shorof=="price"){
        filterList = remember(shorof, products) {
            products.sortedBy { it.price }
        }
    }else if(shorof=="name"){
        filterList = remember(shorof, products) {

            products.sortedBy { it.title }
        }
    }
    if(filteroff != ""){
        filterList = remember(filteroff, products) {
            products.filter {
                it.category.contains(filteroff, ignoreCase = true)
            }
        }
    }
    if(isBottomSheetShow){
        BottomSheet(category){category->
            isBottomSheetShow = false
            filteroff = category
        }
    }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column{
                        Text("Er Jain", style = MaterialTheme.typography.titleSmall)
                        Card(colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE7E0FC)
                        )) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp,0.dp,8.dp,0.dp)) {
                                Icon(Icons.Filled.AttachMoney,
                                    contentDescription = "coins", tint = Color.White,
                                    modifier = Modifier.background(shape = CircleShape, color = Color(0xFFFFD700)).size(19.dp))
                                Spacer(modifier=Modifier.padding(2.dp))
                                Text("0 coins", style = TextStyle(fontWeight = FontWeight.Bold), color = Color(0xFF7D58BA),)
                            }

                        }
                    }
                   },


                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "favourite", tint = Color.Red)
                    }
                    IconButton(onClick = {
                        navController.navigate("CartScreen")
                    }) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "favourite", tint = Color(0xFF9F2089))

                    }
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
                                shorof = "price"
                                filteroff =""
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Sort by Name") },
                            onClick = {
                                expend.value=false
                                shorof = "name"
                                filteroff =""
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Filter by Category") },
                            onClick = {
                                expend.value=false
                                viewModel.getCategories()
                                isBottomSheetShow = true
                            }
                        )

                    }

                },
                navigationIcon = {
                    Icon(Icons.Filled.AccountCircle, contentDescription = "icon",
                        tint = Color(0xFFE1BEE7), modifier = Modifier.size(40.dp))
                }

            )
        },
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(padding)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it
                    shorof = ""
                    filteroff =""},
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text("Search") },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "search", tint = Color(0xFF757575))
                },
                trailingIcon = {
                    Row (modifier=Modifier.padding(10.dp,0.dp)){
                        Icon(Icons.Default.Mic, contentDescription = "mic", tint = Color(0xFF757575))
                        Spacer(modifier = Modifier.padding(5.dp))
                        Icon(Icons.Default.CameraEnhance, contentDescription = "mic", tint = Color(0xFF757575))
                    }
                }
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                content = {
                    items(filterList) { item ->
                        Card(
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable {
                                    val intent = Intent(context, DetailActivity::class.java)
                                    intent.putExtra("productId", item.id)
                                    context.startActivity(intent)
                                },
                            elevation = CardDefaults.cardElevation(4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF9F2089),

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
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.White

                                    )

                                Text(
                                    text = item.price.toString(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(2.dp),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleSmall,
                                            color = Color.White
                                )
                            }
                        }
                    }

                })
        }

    }
}

