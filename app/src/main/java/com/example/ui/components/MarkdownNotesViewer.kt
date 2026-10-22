package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CheckpointBlue
import com.example.ui.theme.CheckpointBlueBg
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Gold600

@Composable
fun MarkdownNotesViewer(
    markdownText: String,
    courseName: String,
    lectureDate: String,
    onSaveToLibrary: () -> Unit,
    isSaved: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var selectedTab by remember { mutableStateOf(0) } // 0 = Visual Notes, 1 = Raw Markdown
    var showFlashcardDialog by remember { mutableStateOf(false) }

    // Parse Sections
    val parsedData = remember(markdownText) {
        parseNotesMarkdown(markdownText)
    }

    if (showFlashcardDialog && parsedData.examQuestions.isNotEmpty()) {
        ExamFlashcardDialog(
            questions = parsedData.examQuestions,
            courseName = courseName,
            onDismiss = { showFlashcardDialog = false }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Action Bar
        Surface(
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // View Mode Tabs
                TabRow(
                    selectedTabIndex = selectedTab,
                    modifier = Modifier.width(220.dp),
                    containerColor = Color.Transparent
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Visual") },
                        icon = { Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp)) },
                        modifier = Modifier.testTag("tab_visual_notes")
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Markdown") },
                        icon = { Icon(Icons.Default.Code, contentDescription = null, modifier = Modifier.size(16.dp)) },
                        modifier = Modifier.testTag("tab_raw_markdown")
                    )
                }

                // Action Buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (parsedData.examQuestions.isNotEmpty()) {
                        FilledTonalButton(
                            onClick = { showFlashcardDialog = true },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = Gold600.copy(alpha = 0.15f),
                                contentColor = Gold600
                            ),
                            modifier = Modifier.testTag("open_flashcards_button")
                        ) {
                            Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Exam Quiz (${parsedData.examQuestions.size})")
                        }
                    }

                    IconButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(markdownText))
                            Toast.makeText(context, "Notes copied to clipboard", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.testTag("copy_notes_button")
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy Markdown")
                    }

                    IconButton(
                        onClick = {
                            shareNotes(context, courseName, markdownText)
                        },
                        modifier = Modifier.testTag("share_notes_button")
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }

                    IconButton(
                        onClick = onSaveToLibrary,
                        modifier = Modifier.testTag("save_notes_button")
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Save to Library",
                            tint = if (isSaved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        if (selectedTab == 1) {
            // Raw Markdown View
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Text(
                        text = markdownText,
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        } else {
            // Visual Formatted View
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(4.dp))

                // Hero Title Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = parsedData.topicTitle.ifBlank { "University Lecture Notes" },
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = courseName.ifBlank { parsedData.courseName },
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = lectureDate.ifBlank { parsedData.lectureDate },
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }

                // Section 1: Executive Summary & Core Objectives
                if (parsedData.executiveSummary.isNotBlank()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "1. Executive Summary & Core Objectives",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = parsedData.executiveSummary,
                                style = MaterialTheme.typography.bodyMedium,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }

                // Section 2: Detailed Structured Notes (Segmented by Checkpoints)
                Text(
                    text = "2. Detailed Structured Notes (Segmented by Checkpoints)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                if (parsedData.segments.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "No distinct checkpoint segments parsed.",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                } else {
                    parsedData.segments.forEach { segment ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                // Checkpoint header
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        color = CheckpointBlueBg,
                                        shape = RoundedCornerShape(6.dp),
                                        border = BorderStroke(1.dp, CheckpointBlue.copy(alpha = 0.3f))
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Timer,
                                                contentDescription = null,
                                                tint = CheckpointBlue,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Text(
                                                text = segment.timestamp,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = CheckpointBlue
                                            )
                                        }
                                    }

                                    Text(
                                        text = segment.title,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                // Bullet Points
                                segment.bullets.forEach { bullet ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .padding(top = 8.dp)
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(MaterialTheme.colorScheme.primary)
                                        )
                                        Text(
                                            text = formatBoldText(bullet),
                                            style = MaterialTheme.typography.bodyMedium,
                                            lineHeight = 21.sp
                                        )
                                    }
                                }

                                // Chichewa Callout Blocks
                                segment.callouts.forEach { callout ->
                                    ChichewaCalloutCard(
                                        spokenChichewa = callout.chichewa,
                                        englishTranslation = callout.translation
                                    )
                                }
                            }
                        }
                    }
                }

                // Section 3: Handout & Textbook Alignment
                if (parsedData.alignments.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                    contentDescription = null,
                                    tint = Emerald600
                                )
                                Text(
                                    text = "3. Handout & Textbook Alignment",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Emerald600
                                )
                            }
                            parsedData.alignments.forEach { item ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Emerald600,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = formatBoldText(item),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }

                // Section 4: Potential Exam Focus Areas & Review Questions
                if (parsedData.examQuestions.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.25f)
                        ),
                        border = BorderStroke(1.dp, Gold600.copy(alpha = 0.4f))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Quiz,
                                        contentDescription = null,
                                        tint = Gold600
                                    )
                                    Text(
                                        text = "4. Potential Exam Focus Areas",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Gold600
                                    )
                                }

                                Button(
                                    onClick = { showFlashcardDialog = true },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Gold600
                                    )
                                ) {
                                    Text("Start Quiz")
                                }
                            }

                            parsedData.examQuestions.forEachIndexed { idx, q ->
                                Surface(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Text(
                                            text = "Q${idx + 1}",
                                            fontWeight = FontWeight.Bold,
                                            color = Gold600,
                                            style = MaterialTheme.typography.labelLarge
                                        )
                                        Text(
                                            text = q,
                                            style = MaterialTheme.typography.bodyMedium,
                                            lineHeight = 20.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

private fun shareNotes(context: Context, course: String, notes: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TITLE, "Lecture Notes: $course")
        putExtra(Intent.EXTRA_TEXT, notes)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share University Notes")
    context.startActivity(shareIntent)
}

// Markdown formatting helper for bold text (**term**)
@Composable
private fun formatBoldText(raw: String): AnnotatedString {
    return buildAnnotatedString {
        val parts = raw.split("**")
        for (i in parts.indices) {
            if (i % 2 == 1) {
                // Odd index is bold
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary))
                append(parts[i])
                pop()
            } else {
                append(parts[i])
            }
        }
    }
}

// Parser for the required Markdown structure
data class ParsedNotes(
    val topicTitle: String,
    val courseName: String,
    val lectureDate: String,
    val executiveSummary: String,
    val segments: List<ParsedSegment>,
    val alignments: List<String>,
    val examQuestions: List<String>
)

data class ParsedSegment(
    val timestamp: String,
    val title: String,
    val bullets: List<String>,
    val callouts: List<ParsedCallout>
)

data class ParsedCallout(
    val chichewa: String,
    val translation: String
)

private fun parseNotesMarkdown(markdown: String): ParsedNotes {
    var topic = ""
    var course = ""
    var date = ""
    var summary = ""
    val segments = mutableListOf<ParsedSegment>()
    val alignments = mutableListOf<String>()
    val examQuestions = mutableListOf<String>()

    val lines = markdown.lines()
    var currentSection = 0 // 1 = Summary, 2 = Notes, 3 = Alignments, 4 = Exam

    var curSegmentTs = "00:00"
    var curSegmentTitle = "Introduction"
    val curBullets = mutableListOf<String>()
    val curCallouts = mutableListOf<ParsedCallout>()

    var inCallout = false
    var curChichewa = ""
    var curTranslation = ""

    fun flushSegment() {
        if (curBullets.isNotEmpty() || curCallouts.isNotEmpty()) {
            segments.add(
                ParsedSegment(
                    timestamp = curSegmentTs,
                    title = curSegmentTitle,
                    bullets = curBullets.toList(),
                    callouts = curCallouts.toList()
                )
            )
            curBullets.clear()
            curCallouts.clear()
        }
    }

    fun flushCallout() {
        if (curChichewa.isNotBlank() || curTranslation.isNotBlank()) {
            curCallouts.add(ParsedCallout(curChichewa, curTranslation))
            curChichewa = ""
            curTranslation = ""
        }
        inCallout = false
    }

    for (line in lines) {
        val trimmed = line.trim()

        // Match Top Title
        if (trimmed.startsWith("# ") && topic.isBlank()) {
            topic = trimmed.removePrefix("# ").replace("📚", "").replace("Lecture Notes:", "").trim()
            continue
        }

        // Match Metadata
        if (trimmed.contains("**Course:**", ignoreCase = true)) {
            course = trimmed.substringAfter("**Course:**").replace("*", "").trim()
            continue
        }
        if (trimmed.contains("**Date:**", ignoreCase = true)) {
            date = trimmed.substringAfter("**Date:**").replace("*", "").trim()
            continue
        }

        // Match Main Section Headers
        if (trimmed.contains("## 1.", ignoreCase = true) || trimmed.contains("Executive Summary", ignoreCase = true)) {
            flushCallout()
            flushSegment()
            currentSection = 1
            continue
        }
        if (trimmed.contains("## 2.", ignoreCase = true) || trimmed.contains("Detailed Structured Notes", ignoreCase = true)) {
            flushCallout()
            flushSegment()
            currentSection = 2
            continue
        }
        if (trimmed.contains("## 3.", ignoreCase = true) || trimmed.contains("Handout & Textbook Alignment", ignoreCase = true)) {
            flushCallout()
            flushSegment()
            currentSection = 3
            continue
        }
        if (trimmed.contains("## 4.", ignoreCase = true) || trimmed.contains("Potential Exam Focus", ignoreCase = true)) {
            flushCallout()
            flushSegment()
            currentSection = 4
            continue
        }

        when (currentSection) {
            1 -> {
                if (trimmed.startsWith("*") || trimmed.startsWith("-")) {
                    val text = trimmed.removePrefix("*").removePrefix("-").trim()
                    if (summary.isBlank()) summary = text else summary += " $text"
                } else if (trimmed.isNotBlank()) {
                    if (summary.isBlank()) summary = trimmed else summary += " $trimmed"
                }
            }
            2 -> {
                // Check if segment header: ### [CHECKPOINT - ...] ... or ### ...
                if (trimmed.startsWith("###")) {
                    flushCallout()
                    flushSegment()
                    val headerText = trimmed.removePrefix("###").trim()
                    val tsMatch = Regex("\\[CHECKPOINT\\s*-\\s*([^\\]]+)\\]", RegexOption.IGNORE_CASE).find(headerText)
                    if (tsMatch != null) {
                        curSegmentTs = tsMatch.groupValues[1]
                        curSegmentTitle = headerText.replace(tsMatch.value, "").trim().removePrefix("-").trim()
                    } else {
                        curSegmentTitle = headerText
                    }
                    continue
                }

                // Check Callout Block start
                if (trimmed.startsWith(">") && (trimmed.contains("Chichewa", ignoreCase = true) || trimmed.contains("Local Explanation", ignoreCase = true))) {
                    flushCallout()
                    inCallout = true
                    continue
                }

                if (inCallout && trimmed.startsWith(">")) {
                    val calloutLine = trimmed.removePrefix(">").trim()
                    if (calloutLine.contains("Spoken Chichewa", ignoreCase = true)) {
                        curChichewa = calloutLine.substringAfter(":").replace("\"", "").replace("*", "").trim()
                    } else if (calloutLine.contains("Translation", ignoreCase = true)) {
                        curTranslation = calloutLine.substringAfter(":").replace("\"", "").replace("*", "").trim()
                    } else if (calloutLine.isNotBlank()) {
                        if (curTranslation.isNotBlank()) curTranslation += " $calloutLine"
                    }
                    continue
                } else if (inCallout && !trimmed.startsWith(">")) {
                    flushCallout()
                }

                // Regular bullet points in Section 2
                if (trimmed.startsWith("*") || trimmed.startsWith("-")) {
                    val bulletText = trimmed.removePrefix("*").removePrefix("-").trim()
                    curBullets.add(bulletText)
                }
            }
            3 -> {
                if (trimmed.startsWith("*") || trimmed.startsWith("-")) {
                    val alignText = trimmed.removePrefix("*").removePrefix("-").trim()
                    alignments.add(alignText)
                } else if (trimmed.isNotBlank()) {
                    alignments.add(trimmed)
                }
            }
            4 -> {
                if (trimmed.startsWith("*") || trimmed.startsWith("-") || Regex("^[0-9]+\\.").containsMatchIn(trimmed)) {
                    val qText = trimmed.replace(Regex("^[-*0-9.]+\\s*"), "").trim()
                    if (qText.length > 5) {
                        examQuestions.add(qText)
                    }
                }
            }
        }
    }

    flushCallout()
    flushSegment()

    return ParsedNotes(
        topicTitle = topic,
        courseName = course,
        lectureDate = date,
        executiveSummary = summary,
        segments = segments,
        alignments = alignments,
        examQuestions = examQuestions
    )
}
