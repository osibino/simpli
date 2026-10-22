package com.example.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.service.LecturePreset
import com.example.service.MalawiPresets
import com.example.ui.components.CheckpointStamper
import com.example.ui.theme.Gold600

@Composable
fun LectureInputScreen(
    courseName: String,
    onCourseNameChange: (String) -> Unit,
    lectureDate: String,
    onLectureDateChange: (String) -> Unit,
    handoutText: String,
    onHandoutTextChange: (String) -> Unit,
    rawTranscript: String,
    onRawTranscriptChange: (String) -> Unit,
    onSelectPreset: (LecturePreset) -> Unit,
    onClearAll: () -> Unit,
    onGenerate: () -> Unit,
    isLoading: Boolean,
    forceOffline: Boolean,
    onToggleOffline: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var isRecording by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // University Presets Bar
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Malawi University Presets:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    TextButton(
                        onClick = onClearAll,
                        modifier = Modifier.testTag("clear_inputs_button")
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Clear", style = MaterialTheme.typography.labelSmall)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MalawiPresets.presets.forEach { preset ->
                        FilterChip(
                            selected = courseName == preset.courseName,
                            onClick = { onSelectPreset(preset) },
                            label = {
                                Text(
                                    text = preset.courseName.substringBefore(":"),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }
        }

        // Course Info Fields
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = courseName,
                onValueChange = onCourseNameChange,
                label = { Text("Course Name") },
                placeholder = { Text("e.g. Business Management 301") },
                leadingIcon = {
                    Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(18.dp))
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("course_name_input"),
                singleLine = true
            )

            OutlinedTextField(
                value = lectureDate,
                onValueChange = onLectureDateChange,
                label = { Text("Lecture Date / Session") },
                placeholder = { Text("e.g. 2026-10-21 / Session 4") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("lecture_date_input"),
                singleLine = true
            )
        }

        // Course Handout / Textbook Excerpts
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.MenuBook,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Official Course Handout / Textbook Excerpts",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                text = "Paste syllabus passages, textbook chapters, or definitions to cross-reference (RAG).",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = handoutText,
                onValueChange = onHandoutTextChange,
                placeholder = { Text("Paste official course handout excerpts, textbook references, or syllabus text here...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .testTag("handout_text_input"),
                maxLines = 8
            )
        }

        // Raw Recorded Lecture Transcript with Checkpoints
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.RecordVoiceOver,
                    contentDescription = null,
                    tint = Gold600,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Raw Recorded Lecture Transcript (with Checkpoints & Chichewa)",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                text = "Transcript with [CHECKPOINT - HH:MM] markers and code-switching between English and Chichewa.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Live Lecture Audio Recorder
            com.example.ui.components.LectureRecorderBar(
                isRecording = isRecording,
                onToggleRecording = { isRecording = it },
                onInsertCheckpoint = { tsText ->
                    onRawTranscriptChange(rawTranscript + tsText)
                },
                onTranscribeSnippet = { snippet ->
                    onRawTranscriptChange(rawTranscript + snippet)
                },
                courseName = courseName
            )

            // Checkpoint stamper helper
            CheckpointStamper(
                transcriptText = rawTranscript,
                onInsertText = { toInsert ->
                    onRawTranscriptChange(rawTranscript + toInsert)
                },
                onCleanFluff = {
                    val cleaned = rawTranscript.replace(Regex("(?i)\\b(um|uh|you know|like I said|right\\?)\\b\\s*"), "")
                    onRawTranscriptChange(cleaned)
                }
            )

            OutlinedTextField(
                value = rawTranscript,
                onValueChange = onRawTranscriptChange,
                placeholder = { Text("Paste or type raw recorded lecture transcript here. Include [CHECKPOINT - 00:00] markers and spoken Chichewa...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .testTag("transcript_text_input"),
                maxLines = 12
            )
        }

        // Offline / Online Mode Switch
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = if (forceOffline) Icons.Default.WifiOff else Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = if (forceOffline) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = if (forceOffline) "Campus Offline Synthesizer Mode" else "Gemini AI Academic Engine",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (forceOffline) "Instant local compilation without internet" else "Full server-side multilingual translation",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Switch(
                    checked = !forceOffline,
                    onCheckedChange = { isOnline -> onToggleOffline(!isOnline) },
                    modifier = Modifier.testTag("toggle_offline_mode")
                )
            }
        }

        // Primary Generate Action
        Button(
            onClick = onGenerate,
            enabled = !isLoading && rawTranscript.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .testTag("generate_notes_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.5.dp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("Analyzing Code-Switching & Checkpoints...", fontWeight = FontWeight.Bold)
            } else {
                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Generate University-Grade Notes", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
