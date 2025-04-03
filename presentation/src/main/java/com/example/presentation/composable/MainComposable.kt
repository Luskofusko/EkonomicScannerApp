package com.example.presentation.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MainComposable(navController: NavHostController) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        item {
            Button(onClick = { navController.navigate("receiptList") }) {
                Text("Go to Receipt List")
            }
        }

        item {
            Button(onClick = { navController.navigate("cameraScreen") }) {
                Text("Take a Receipt Photo")
            }
        }

    }

}
