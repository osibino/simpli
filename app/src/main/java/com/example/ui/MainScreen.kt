package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.MarkdownNotesViewer
import com.example.ui.components.ThemeCustomizerDialog
import com.example.ui.screens.GlossaryScreen
import com.example.ui.screens.LectureInputScreen
import com.example.ui.screens.NotesLibraryScreen
import com.example.ui.theme.Gold600

enum class AppDestination {
    STUDIO,
    LIBRARY,
    GLOSSARY
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val courseName by viewModel.courseName.collectAsStateWithLifecycle()
    val lectureDate by viewModel.lectureDate.collectAsStateWithLifecycle()
    val handoutText by viewModel.handoutText.collectAsStateWithLifecycle()
    val rawTranscript by viewModel.rawTranscript.collectAsStateWithLifecycle()
    val generatedNotes by viewModel.generatedNotesMarkdown.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val forceOffline by viewModel.forceOffline.collectAsStateWithLifecycle()
    val isSaved by viewModel.isSaved.collectAsStateWithLifecycle()
    val savedNotes by viewModel.savedNotes.collectAsStateWithLifecycle()
    val themeSettings by viewModel.themeSettings.collectAsStateWithLifecycle()

    var currentDestination by remember { mutableIntStateOf(0) } // 0 = Studio, 1 = Library, 2 = Glossary
    var phoneStudioTab by remember { mutableIntStateOf(0) } // 0 = Input, 1 = Notes (Phone mode only)
    var showThemeDialog by remember { mutableStateOf(false) }

    if (showThemeDialog) {
        ThemeCustomizerDialog(
            currentSettings = themeSettings,
            onSettingsChanged = viewModel::updateThemeSettings,
            onDismiss = { showThemeDialog = false }
        )
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isExpandedScreen = maxWidth >= 760.dp

        if (isExpandedScreen) {
            // ==========================================
            // PC / TABLET / EXPANDED SCREEN LAYOUT
            // ==========================================
            Row(modifier = Modifier.fillMaxSize()) {
                // Side Navigation Rail for PC
                NavigationRail(
                    modifier = Modifier.fillMaxHeight(),
                    header = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = "Logo",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Simplify",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                ) {
                    NavigationRailItem(
                        selected = currentDestination == 0,
                        onClick = { currentDestination = 0 },
                        icon = { Icon(Icons.Default.EditNote, contentDescription = null) },
                        label = { Text("Studio") },
                        modifier = Modifier.testTag("nav_rail_studio")
                    )

                    NavigationRailItem(
                        selected = currentDestination == 1,
                        onClick = { currentDestination = 1 },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (savedNotes.isNotEmpty()) {
                                        Badge { Text("${savedNotes.size}") }
                                    }
                                }
                            ) {
                                Icon(Icons.Default.Bookmark, contentDescription = null)
                            }
                        },
                        label = { Text("Library") },
                        modifier = Modifier.testTag("nav_rail_library")
                    )

                    NavigationRailItem(
                        selected = currentDestination == 2,
                        onClick = { currentDestination = 2 },
                        icon = { Icon(Icons.Default.Translate, contentDescription = null) },
                        label = { Text("Glossary") },
                        modifier = Modifier.testTag("nav_rail_glossary")
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Theme Customizer Button for PC
                    NavigationRailItem(
                        selected = false,
                        onClick = { showThemeDialog = true },
                        icon = { Icon(Icons.Default.Palette, contentDescription = "Customize Theme") },
                        label = { Text("Theme") },
                        modifier = Modifier.testTag("nav_rail_theme_button")
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                VerticalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

                // Main Content Pane
                Box(modifier = Modifier.fillMaxSize()) {
                    when (currentDestination) {
                        0 -> {
                            // DUAL-PANE SPLIT VIEW FOR PC/DESKTOP
                            Row(modifier = Modifier.fillMaxSize()) {
                                // Left Pane: Input (Handout & Transcript with Checkpoint Stamper & Recorder)
                                Box(
                                    modifier = Modifier
                                        .weight(0.48f)
                                        .fillMaxHeight()
                                ) {
                                    LectureInputScreen(
                                        courseName = courseName,
                                        onCourseNameChange = viewModel::onCourseNameChange,
                                        lectureDate = lectureDate,
                                        onLectureDateChange = viewModel::onLectureDateChange,
                                        handoutText = handoutText,
                                        onHandoutTextChange = viewModel::onHandoutTextChange,
                                        rawTranscript = rawTranscript,
                                        onRawTranscriptChange = viewModel::onRawTranscriptChange,
                                        onSelectPreset = viewModel::applyPreset,
                                        onClearAll = viewModel::clearAll,
                                        onGenerate = viewModel::generateNotes,
                                        isLoading = isLoading,
                                        forceOffline = forceOffline,
                                        onToggleOffline = viewModel::onToggleOffline
                                    )
                                }

                                VerticalDivider(
                                    thickness = 1.5.dp,
                                    color = MaterialTheme.colorScheme.outlineVariant
                                )

                                // Right Pane: Generated Notes with Custom Callouts & Flashcards
                                Box(
                                    modifier = Modifier
                                        .weight(0.52f)
                                        .fillMaxHeight()
                                ) {
                                    if (generatedNotes.isNotBlank()) {
                                        MarkdownNotesViewer(
                                            markdownText = generatedNotes,
                                            courseName = courseName,
                                            lectureDate = lectureDate,
                                            onSaveToLibrary = viewModel::saveCurrentNotes,
                                            isSaved = isSaved
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(32.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Description,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(56.dp),
                                                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                                                )
                                                Text(
                                                    text = "University Notes Preview",
                                                    style = MaterialTheme.typography.titleMedium,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                                Text(
                                                    text = "Select a Malawian university preset or enter lecture transcript to generate structured notes.",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        1 -> {
                            NotesLibraryScreen(
                                notes = savedNotes,
                                onSelectNote = { note ->
                                    viewModel.loadNoteIntoActiveStudio(note)
                                    currentDestination = 0
                                },
                                onToggleFavorite = viewModel::toggleFavorite,
                                onDeleteNote = viewModel::deleteNote
                            )
                        }
                        2 -> {
                            GlossaryScreen()
                        }
                    }
                }
            }
        } else {
            // ==========================================
            // PHONE / COMPACT SCREEN LAYOUT
            // ==========================================
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                                Column {
                                    Text(
                                        text = "Simplify",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Malawi University Academic Scribe",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = { showThemeDialog = true },
                                modifier = Modifier.testTag("theme_button_phone")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Palette,
                                    contentDescription = "Customize Theme & Colors"
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                },
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            selected = currentDestination == 0,
                            onClick = { currentDestination = 0 },
                            icon = { Icon(Icons.Default.EditNote, contentDescription = "Studio") },
                            label = { Text("Studio") },
                            modifier = Modifier.testTag("nav_bottom_studio")
                        )
                        NavigationBarItem(
                            selected = currentDestination == 1,
                            onClick = { currentDestination = 1 },
                            icon = {
                                BadgedBox(
                                    badge = {
                                        if (savedNotes.isNotEmpty()) {
                                            Badge { Text("${savedNotes.size}") }
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.Bookmark, contentDescription = "Library")
                                }
                            },
                            label = { Text("Library") },
                            modifier = Modifier.testTag("nav_bottom_library")
                        )
                        NavigationBarItem(
                            selected = currentDestination == 2,
                            onClick = { currentDestination = 2 },
                            icon = { Icon(Icons.Default.Translate, contentDescription = "Glossary") },
                            label = { Text("Glossary") },
                            modifier = Modifier.testTag("nav_bottom_glossary")
                        )
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (currentDestination) {
                        0 -> {
                            Column(modifier = Modifier.fillMaxSize()) {
                                // Sub-Tabs for Phone Studio
                                TabRow(
                                    selectedTabIndex = phoneStudioTab,
                                    containerColor = MaterialTheme.colorScheme.surface
                                ) {
                                    Tab(
                                        selected = phoneStudioTab == 0,
                                        onClick = { phoneStudioTab = 0 },
                                        text = { Text("1. Transcript & Handout") },
                                        icon = { Icon(Icons.Default.EditNote, contentDescription = null, modifier = Modifier.size(16.dp)) },
                                        modifier = Modifier.testTag("phone_tab_input")
                                    )
                                    Tab(
                                        selected = phoneStudioTab == 1,
                                        onClick = { phoneStudioTab = 1 },
                                        text = { Text("2. University Notes") },
                                        icon = {
                                            BadgedBox(
                                                badge = {
                                                    if (generatedNotes.isNotBlank()) {
                                                        Badge(containerColor = Gold600) { Text("✓") }
                                                    }
                                                }
                                            ) {
                                                Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
                                            }
                                        },
                                        modifier = Modifier.testTag("phone_tab_notes")
                                    )
                                }

                                if (phoneStudioTab == 0) {
                                    LectureInputScreen(
                                        courseName = courseName,
                                        onCourseNameChange = viewModel::onCourseNameChange,
                                        lectureDate = lectureDate,
                                        onLectureDateChange = viewModel::onLectureDateChange,
                                        handoutText = handoutText,
                                        onHandoutTextChange = viewModel::onHandoutTextChange,
                                        rawTranscript = rawTranscript,
                                        onRawTranscriptChange = viewModel::onRawTranscriptChange,
                                        onSelectPreset = { preset ->
                                            viewModel.applyPreset(preset)
                                            phoneStudioTab = 1
                                        },
                                        onClearAll = viewModel::clearAll,
                                        onGenerate = {
                                            viewModel.generateNotes()
                                            phoneStudioTab = 1
                                        },
                                        isLoading = isLoading,
                                        forceOffline = forceOffline,
                                        onToggleOffline = viewModel::onToggleOffline
                                    )
                                } else {
                                    if (generatedNotes.isNotBlank()) {
                                        MarkdownNotesViewer(
                                            markdownText = generatedNotes,
                                            courseName = courseName,
                                            lectureDate = lectureDate,
                                            onSaveToLibrary = viewModel::saveCurrentNotes,
                                            isSaved = isSaved
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(24.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Description,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(48.dp),
                                                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                                )
                                                Text(
                                                    text = "No notes generated yet.",
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                                Text(
                                                    text = "Switch back to '1. Transcript & Handout' and tap 'Generate University-Grade Notes'.",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        1 -> {
                            NotesLibraryScreen(
                                notes = savedNotes,
                                onSelectNote = { note ->
                                    viewModel.loadNoteIntoActiveStudio(note)
                                    currentDestination = 0
                                    phoneStudioTab = 1
                                },
                                onToggleFavorite = viewModel::toggleFavorite,
                                onDeleteNote = viewModel::deleteNote
                            )
                        }
                        2 -> {
                            GlossaryScreen()
                        }
                    }
                }
            }
        }
    }
}
