package com.example.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CheckpointBlue
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Gold600

@Composable
fun CheckpointStamper(
    transcriptText: String,
    onInsertText: (String) -> Unit,
    onCleanFluff: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Analyze transcript
    val checkpointMatches = Regex("\\[CHECKPOINT\\s*-\\s*[^\\]]+\\]", RegexOption.IGNORE_CASE).findAll(transcriptText).count()
    val hasChichewa = listOf(
        "mukapita", "pamenepa", "tiyeni", "chifukwa", "mwachitsanzo", "amalonda",
        "anthu", "boma", "kuti", "ndalama", "ngongole", "makalata", "bokosi",
        "bwalo", "alamulo", "lonjezo", "nsikidzi", "mankhwala", "choncho"
    ).any { transcriptText.contains(it, ignoreCase = true) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Status and Counters Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = "Checkpoints",
                        tint = CheckpointBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "$checkpointMatches Checkpoints",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = CheckpointBlue
                    )
                }

                if (hasChichewa) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Translate,
                            contentDescription = "Chichewa Code-Switching",
                            tint = Gold600,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Chichewa Code-Switch Detected",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Gold600
                        )
                    }
                }
            }

            // Quick Insertion Toolbar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AssistChip(
                    onClick = {
                        val minutes = (checkpointMatches * 12).toString().padStart(2, '0')
                        onInsertText("\n\n[CHECKPOINT - $minutes:00] ")
                    },
                    label = { Text("+ [CHECKPOINT]") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        labelColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.testTag("add_checkpoint_button")
                )

                AssistChip(
                    onClick = { onInsertText(" Mwachitsanzo, ") },
                    label = { Text("Mwachitsanzo (e.g.)") },
                    colors = AssistChipDefaults.assistChipColors(
                        labelColor = Gold600
                    ),
                    modifier = Modifier.testTag("insert_chichewa_example_tag")
                )

                AssistChip(
                    onClick = { onInsertText(" Tiyeni tione: ") },
                    label = { Text("Tiyeni tione (Let's examine)") },
                    colors = AssistChipDefaults.assistChipColors(
                        labelColor = Gold600
                    )
                )

                AssistChip(
                    onClick = { onInsertText(" Ndiye pamenepa, ") },
                    label = { Text("Ndiye pamenepa (At this juncture)") },
                    colors = AssistChipDefaults.assistChipColors(
                        labelColor = Gold600
                    )
                )

                AssistChip(
                    onClick = onCleanFluff,
                    label = { Text("Clean Fluff (um/uh)") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.CleaningServices,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                    },
                    modifier = Modifier.testTag("clean_fluff_button")
                )
            }
        }
    }
}
