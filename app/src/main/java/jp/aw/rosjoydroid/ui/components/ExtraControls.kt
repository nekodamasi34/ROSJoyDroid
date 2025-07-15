package jp.aw.rosjoydroid.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * ui/components/ExtraControls.kt
 *
 * This file defines composable UI components for configuring and monitoring extra gamepad axes and buttons.
 *
 * Features:
 *  - Displays additional axes as sliders for manual adjustment
 *  - Shows extra buttons as filter chips for toggling state
 *  - Useful for testing, diagnostics, or supporting controllers with more than standard inputs
 */

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExtraControls(
    axesState: MutableStateFlow<FloatArray>,
    btnState: MutableStateFlow<IntArray>,
    baseAxisOffset: Int,
    baseButtonOffset: Int,
    modifier: Modifier = Modifier
) {
    val axes by axesState.collectAsState()
    val btns by btnState.collectAsState()

    LazyColumn(modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(axes.size) { i ->
            AxisRow(i, "ExtraAxis", axes[i]) { new ->
                axesState.value = axesState.value.clone().also { it[i] = new }
            }
        }
        item {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                btns.forEachIndexed { i, on ->
                    FilterChip(
                        selected = on == 1,
                        onClick = {
                            btnState.value =
                                btnState.value.clone().also { it[i] = if (on == 1) 0 else 1 }
                        },
                        label = {
                            Text(
                                "${baseButtonOffset + i}: ExtraBtn",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    )
                }
            }
        }
    }
}
