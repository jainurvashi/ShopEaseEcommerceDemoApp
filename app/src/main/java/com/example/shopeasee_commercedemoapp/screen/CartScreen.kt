package com.example.shopeasee_commercedemoapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.shopeasee_commercedemoapp.CartViewModelFactory
import com.example.shopeasee_commercedemoapp.R
import com.example.shopeasee_commercedemoapp.data.model.CartProductModel
import com.example.shopeasee_commercedemoapp.viewModels.CartViewModel

@Composable
fun CartScreen(navController: NavHostController, innerPadding: PaddingValues) {
  Column(horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center, modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight()) {
      Image(
          painter = painterResource(id = R.drawable.empty_shopping),
          contentDescription = "My Image",
          contentScale = ContentScale.Crop,
      )
      Text("Your Cart is Empty", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
Spacer(modifier = Modifier.padding(3.dp))
      Text("just relex let us help you some first class products!",style = MaterialTheme.typography.labelSmall,color = Color.Gray)
      Spacer(modifier = Modifier.padding(3.dp))
      Button(onClick = {
          navController.navigate("home") {
              popUpTo(0) { inclusive = true } // removes everything from backstack
              launchSingleTop = true
          }
}, colors = ButtonDefaults.buttonColors(
    containerColor = Color(0xFF9F2089),
),
    shape = RectangleShape) {
    Text("Start Shopping", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold))
}
  }

}

@Composable
fun CartDataScreen(
    innerPadding: PaddingValues,
    cartItem: List<CartProductModel>,
    cartViewModel: CartViewModel
) {
    Column(modifier = Modifier.padding(innerPadding)) {
        LazyColumn { items(cartItem) { items->
            Card(modifier = Modifier.padding(bottom = 4.dp, top = 4.dp), colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),) {
                Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(8.dp)) {
                    AsyncImage(model = items.productModel.image, contentDescription = "productImage", modifier = Modifier.size(80.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(items.productModel.title, style = MaterialTheme.typography.bodyLarge)
                        Text("₹"+items.productModel.price.toString(), style = MaterialTheme.typography.bodyMedium)
                        Row {
                            Text("Size:Free size", style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.padding(3.dp))
                            Text("Qty: "+items.quantity, style = MaterialTheme.typography.bodySmall)

                        }
                    }
                  }

            }
            } }
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
       Spacer(modifier = Modifier.padding(8.dp))
        Text("Price Details ("+ cartItem.size+ " items)", modifier = Modifier.padding(8.dp))

        Row(modifier = Modifier.padding(8.dp)) {
            Text("Total Product Price", color = Color.Gray, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
     Text("+ ₹"+cartViewModel.totalPrice())
        }
        Button(onClick = {
         cartViewModel.clearCart()
        }, colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF9F2089),
        ),
            shape = RectangleShape, modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp),) {
            Text("Remove", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold))
        }

    }
}

@Preview(showBackground = true, widthDp = 200, heightDp = 200)
@Composable
private fun previewww() {
 //   CartDataScreen(innerPadding)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold(navController: NavHostController) {
    val context = LocalContext.current
    val cartViewModel: CartViewModel = viewModel(factory = CartViewModelFactory(context))
    val cartItem = cartViewModel.cartItems
    androidx.compose.material3.Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Cart") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("home") {
                            popUpTo(0) { inclusive = true } // removes everything from backstack
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            Icons.Default.ArrowBack, contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                }

            )
        },
    ) { innerPadding ->
if(cartItem.isEmpty()){
    CartScreen(navController,innerPadding)
}else{
    CartDataScreen(innerPadding,cartItem,cartViewModel)
}


    }
}