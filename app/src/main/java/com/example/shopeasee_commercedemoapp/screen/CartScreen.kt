package com.example.shopeasee_commercedemoapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shopeasee_commercedemoapp.R

@Composable
fun CartScreen(navController: NavHostController) {
  Column(horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
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
