package com.example.kreedaprerana.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector
) {

    OutlinedTextField(

        value = value,

        onValueChange = {
            onValueChange(it)
        },

        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),

        placeholder = {
            Text(
                text = label,
                color = Color.Gray
            )
        },

        leadingIcon = {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Gray
            )
        },

        shape = RoundedCornerShape(20.dp),

        colors = OutlinedTextFieldDefaults.colors(

            focusedTextColor = Color.Black,

            unfocusedTextColor = Color.Black,

            focusedContainerColor = Color(0xFFF1F3F6),

            unfocusedContainerColor = Color(0xFFF1F3F6),

            cursorColor = Color(0xFF2962FF),

            focusedBorderColor = Color.Transparent,

            unfocusedBorderColor = Color.Transparent
        )
    )
}