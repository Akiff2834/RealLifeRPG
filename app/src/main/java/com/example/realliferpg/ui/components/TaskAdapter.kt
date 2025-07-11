package com.example.realliferpg.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.realliferpg.data.Task

/**
 * Basit görev kartı adaptörü (Compose).
 *
 * @param tasks      Ekranda listelenecek görevler
 * @param onComplete Tamamla butonuna tıklandığında çağrılacak lambda
 */
@Composable
fun TaskAdapter(
    tasks: List<Task>,
    onComplete: (Task) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        tasks.forEach { task ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    if (!task.isCompleted) {
                        Button(onClick = { onComplete(task) }) {
                            Text("Tamamla")
                        }
                    } else {
                        Text("✅ Tamamlandı", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}
