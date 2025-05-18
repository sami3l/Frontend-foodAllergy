package com.useapi.foodallergydetector.ui.evaluate

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.useapi.foodallergydetector.data.network.RetrofitClient
import com.useapi.foodallergydetector.ui.viewmodel.ScanState
import com.useapi.foodallergydetector.ui.viewmodel.ScanViewModel
import com.useapi.foodallergydetector.ui.viewmodel.ScanViewModelFactory

@Composable
fun EvaluateScreen(
    onShowHistory: () -> Unit
) {
    // instantiate the ScanViewModel via our factory
    val factory = ScanViewModelFactory(RetrofitClient.scanApi)
    val vm: ScanViewModel = viewModel(factory = factory)

    var barcode by remember { mutableStateOf("") }
    val state by vm.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Evaluate Product", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = barcode,
            onValueChange = { barcode = it },
            label = { Text("Barcode") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { vm.evaluate(barcode.trim()) },
            modifier = Modifier.fillMaxWidth(),
            enabled = state != ScanState.Loading
        ) {
            Text("Run Evaluation")
        }

        Spacer(Modifier.height(24.dp))

        when (state) {
            is ScanState.Loading -> CircularProgressIndicator()

            is ScanState.Error -> Text(
                text = (state as ScanState.Error).message,
                color = MaterialTheme.colorScheme.error
            )

            is ScanState.Success -> {
                val result = (state as ScanState.Success).result

                Text("Detected Allergens:", style = MaterialTheme.typography.titleMedium)
                Text(result.detectedAllergens.joinToString(", ").ifBlank { "None" })

                Spacer(Modifier.height(8.dp))
                Text("Risk Level: ${result.riskLevel}", style = MaterialTheme.typography.bodyLarge)

                Spacer(Modifier.height(12.dp))
                result.imageUrl?.let { url ->
                    Text("Image URL:", style = MaterialTheme.typography.titleSmall)
                    Text(url, style = MaterialTheme.typography.bodySmall)
                }
            }

            else -> { /* Idle: no-op */ }
        }

        Spacer(Modifier.weight(1f))

        OutlinedButton(onClick = onShowHistory) {
            Text("View History")
        }
    }
}
