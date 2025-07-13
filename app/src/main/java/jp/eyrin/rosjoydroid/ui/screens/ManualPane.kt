package jp.eyrin.rosjoydroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.eyrin.rosjoydroid.model.GamepadButton
import jp.eyrin.rosjoydroid.ui.components.AxisRow

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ManualPane(
    axesState: MutableState<FloatArray>,
    btnState: MutableState<IntArray>
) {
    val axisNames = listOf("Lx", "Ly", "Rx", "Ry", "LT", "RT")
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(axesState.value.toList()) { i, v ->
            AxisRow(i, axisNames.getOrNull(i) ?: "Ax$i", v) { new ->
                axesState.value = axesState.value.clone().also { it[i] = new }
            }
        }
        item { Spacer(Modifier.height(12.dp)) }
        item {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                btnState.value.forEachIndexed { i, on ->
                    val name = GamepadButton.entries.getOrNull(i)?.name ?: "Btn$i"
                    FilterChip(
                        selected = on == 1,
                        onClick = {
                            btnState.value =
                                btnState.value.clone().also { it[i] = if (on == 1) 0 else 1 }
                        },
                        label = {
                            Text("$i: $name", style = MaterialTheme.typography.bodySmall)
                        }
                    )
                }
                Spacer(Modifier.weight(1f))
                Button(onClick = {
                    axesState.value = FloatArray(axesState.value.size)
                    btnState.value = IntArray(btnState.value.size)
                }) { Text("Reset All") }
            }
        }
    }
}
