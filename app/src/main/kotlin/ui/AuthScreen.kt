package com.example.kreedaprerana.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kreedaprerana.ui.components.AuthTextField
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AuthScreen(
    onLoginSuccess: () -> Unit,
    onSignupClick: () -> Unit
) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var error by remember {
        mutableStateOf("")
    }

    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "KREEDA PRERANA",

            fontSize = 30.sp,

            fontWeight = FontWeight.Bold,

            color = Color(0xFF07143F)
        )

        Spacer(modifier = Modifier.height(40.dp))

        AuthTextField(
            value = email,

            onValueChange = {
                email = it
            },

            label = "Email",

            icon = Icons.Default.Email
        )

        Spacer(modifier = Modifier.height(20.dp))

        AuthTextField(
            value = password,

            onValueChange = {
                password = it
            },

            label = "Password",

            icon = Icons.Default.Lock
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(

            onClick = {

                auth.signInWithEmailAndPassword(
                    email,
                    password
                )
                    .addOnSuccessListener {

                        onLoginSuccess()
                    }
                    .addOnFailureListener {

                        error = it.message.toString()
                    }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            shape = RoundedCornerShape(20.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF07143F)
            )
        ) {

            Text("LOGIN")
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            onClick = {
                onSignupClick()
            }
        ) {

            Text(
                text = "Create Account"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = error,

            color = Color.Red
        )
    }
}