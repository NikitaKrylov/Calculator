package com.example.calculator

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActionButton(text: String, modifier: Modifier, onClick: () -> Unit){
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(15.dp),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        modifier = modifier
    ) {
        Text(text = text, fontSize = 20.sp, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.primary)
    }
}


@Composable
fun NumButton(text: String, modifier: Modifier, onClick: () -> Unit){
    Button(
        onClick = { onClick() },
        border = BorderStroke(1.dp, Color.White),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.6f)),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
    ) {
//        Image(
//            painter = painterResource(id = imageId),
//            contentDescription = "",
//            alignment = Alignment.Center)
        Text(text = text, fontSize = 20.sp, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun SpecialActionButton(imageId: Int? = null, text: String? = null, modifier: Modifier = Modifier, onClick: () -> Unit){
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
            .height(40.dp))
        {
        if (text != null)
            Text(text = text, fontSize = 20.sp, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.primary.copy(alpha = .8f))
        else if (imageId != null)
            Image(painter = painterResource(id = imageId), contentDescription = "")
    }
}