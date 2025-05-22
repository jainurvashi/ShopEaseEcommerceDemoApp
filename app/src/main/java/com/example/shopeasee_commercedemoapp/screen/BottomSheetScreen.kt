package com.example.shopeasee_commercedemoapp.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
                    onDismissReq(item)
                }) {
                    Text(item, modifier = Modifier.padding(10.dp))
                }

            }
        }
    }

}