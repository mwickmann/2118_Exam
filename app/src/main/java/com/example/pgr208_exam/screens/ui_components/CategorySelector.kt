package com.example.pgr208_exam.screens.ui_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// ui for kategori
@Composable
fun CategorySelector(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(categories) { category ->
            Button(
                onClick = { onCategorySelected(category) },
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (category == selectedCategory) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = if (category == selectedCategory) Color.White else Color.Black
                ),
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 20.dp)
                    .height(70.dp)
            ) {
                Text(
                    text = category,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

