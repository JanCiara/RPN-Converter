package com.example.android_test_n2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_test_n2.ui.theme.Android_TEST_n2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android_TEST_n2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RpnCalculatorScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RpnCalculatorScreen(modifier: Modifier = Modifier) {
    // Mutable states to hold the input and results
    var infixExpression by remember { mutableStateOf("") }
    var rpnResult by remember { mutableStateOf("") }
    var calculationResult by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "Kalkulator RPN",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // TextField for user to input infix expression
        TextField(
            value = infixExpression,
            onValueChange = { infixExpression = it },
            label = { Text("Podaj wyrażenie w notacji infix") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to trigger RPN conversion and calculation
        Button(
            onClick = {
                try {
                    val rpnStack = InfixToRpnConverter.infixToRpn(infixExpression)
                    rpnResult = rpnStack.joinToString(" ")
                    calculationResult = RpnCalculator.RpnToResult(rpnStack).toString()
                    errorMessage = ""
                } catch (e: Exception) {
                    errorMessage = "Błąd: ${e.message}"
                    rpnResult = ""
                    calculationResult = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Oblicz", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the RPN result in a Card
        if (rpnResult.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Wyrażenie w RPN:",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(text = rpnResult)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Display the calculation result in a Card
        if (calculationResult.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Wynik:",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(text = calculationResult)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Display any error messages
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RpnCalculatorScreenPreview() {
    Android_TEST_n2Theme {
        RpnCalculatorScreen()
    }
}
