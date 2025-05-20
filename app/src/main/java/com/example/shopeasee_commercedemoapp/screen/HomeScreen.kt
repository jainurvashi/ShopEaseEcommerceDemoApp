package com.example.shopeasee_commercedemoapp.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.platform.LocalContext
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
                onValueChange = { searchQuery = it
                    shorof = ""
                    filteroff =""},
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
                                containerColor = Color(0xFFE1BEE7),

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BottomSheet(items: List<String>,onDismissReq:(String)->Unit) {
    ModalBottomSheet(
        onDismissRequest = {},
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    ) {
        FlowRow {
            items.forEach { item ->
                Card(modifier = Modifier.padding(10.dp).clickable {
                    onDismissReq(item.toString())
                }) {
                    Text(item, modifier = Modifier.padding(10.dp))
                }

            }
        }
    }

}

@Preview(showBackground = true, widthDp = 500, heightDp = 1000)
@Composable
private fun prevvv() {
val item = arrayListOf("efcae","cfaca","efcae","cfaca","efcae","cfaca")
    BottomSheet(item){

    }
}