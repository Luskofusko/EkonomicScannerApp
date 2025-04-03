package com.example.presentation.composable

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun MainComposable(navController: NavHostController) {
    Button(onClick = { navController.navigate("receiptList") }) {
        Text("Go to Receipt List")
    }
}
