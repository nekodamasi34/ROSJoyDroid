package jp.eyrin.rosjoydroid.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AxisInvertSetting(
    label: String,
    invertX: Boolean,
    invertY: Boolean,
    onInvertXChange: (Boolean) -> Unit,
    onInvertYChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = invertX, onCheckedChange = onInvertXChange)
            Text("X", modifier = Modifier.padding(end = 8.dp))
            Checkbox(checked = invertY, onCheckedChange = onInvertYChange)
            Text("Y")
        }
    }
}
