package com.example.pgr208_exam.screens.ui_components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pgr208_exam.screens.shopping_cart.CartListViewModel


// oppdaterer et rødt tall i handlekurven!
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BadgeNumberWrapperView(
    cartListViewModel: CartListViewModel,
    contentView: @Composable () -> Unit,
    size: Dp = 20.dp,
    fontSize: TextUnit = 11.sp,
    padding: Dp = 4.dp
){
    Box{
        contentView()
        val cartValues = cartListViewModel.cartProducts.value

        if(cartValues.isNotEmpty()){
            Box(
                modifier = Modifier
                    .padding(padding)
                    .size(size = size)
                    .clip(CircleShape)
                    .background(Color.Red)
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = if ( cartValues.size > 99){
                        ".."
                    }
                    else{
                        cartValues.size.toString()
                    },
                    color = Color.White,
                    fontSize = fontSize,
                    maxLines = 1
                )
            }
        }
    }
}