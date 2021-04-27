package com.example.demo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun rootSuffer(){
    Surface(modifier = Modifier.width(375.dp)) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = "Hello")
        }
    }
}