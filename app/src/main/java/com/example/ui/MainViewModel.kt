package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.db.LectureNoteEntity
import com.example.service.GeminiApiService
import com.example.service.LecturePreset
import com.example.service.MalawiPresets
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val dao = db.lectureNoteDao()
    private val apiService = GeminiApiService()

    // Default with the first preset (UNIMA Macroeconomics)
    private val initialPreset = MalawiPresets.presets.first()

    private val _courseName = MutableStateFlow(initialPreset.courseName)
    val courseName: StateFlow<String> = _courseName.asStateFlow()

    private val _lectureDate = MutableStateFlow(initialPreset.lectureDate)
    val lectureDate: StateFlow<String> = _lectureDate.asStateFlow()

    private val _handoutText = MutableStateFlow(initialPreset.handoutExcerpts)
    val handoutText: StateFlow<String> = _handoutText.asStateFlow()

    private val _rawTranscript = MutableStateFlow(initialPreset.rawTranscript)
    val rawTranscript: StateFlow<String> = _rawTranscript.asStateFlow()

    private val _generatedNotesMarkdown = MutableStateFlow("")
    val generatedNotesMarkdown: StateFlow<String> = _generatedNotesMarkdown.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _forceOffline = MutableStateFlow(false)
    val forceOffline: StateFlow<Boolean> = _forceOffline.asStateFlow()

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved.asStateFlow()

    val savedNotes: StateFlow<List<LectureNoteEntity>> = dao.getAllNotes()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val prefs = application.getSharedPreferences("simplify_prefs", android.content.Context.MODE_PRIVATE)

    private val initialMode = try {
        com.example.ui.theme.ThemeMode.valueOf(prefs.getString("theme_mode", com.example.ui.theme.ThemeMode.SYSTEM.name) ?: com.example.ui.theme.ThemeMode.SYSTEM.name)
    } catch (e: Exception) {
        com.example.ui.theme.ThemeMode.SYSTEM
    }

    private val initialPalette = try {
        com.example.ui.theme.ColorPalette.valueOf(prefs.getString("color_palette", com.example.ui.theme.ColorPalette.NAVY.name) ?: com.example.ui.theme.ColorPalette.NAVY.name)
    } catch (e: Exception) {
        com.example.ui.theme.ColorPalette.NAVY
    }

    private val _themeSettings = MutableStateFlow(com.example.ui.theme.ThemeSettings(mode = initialMode, palette = initialPalette))
    val themeSettings: StateFlow<com.example.ui.theme.ThemeSettings> = _themeSettings.asStateFlow()

    fun updateThemeSettings(settings: com.example.ui.theme.ThemeSettings) {
        _themeSettings.value = settings
        prefs.edit()
            .putString("theme_mode", settings.mode.name)
            .putString("color_palette", settings.palette.name)
            .apply()
    }

    init {
        // Pre-generate notes for the initial preset so user sees instant results
        generateNotes()
    }

    fun onCourseNameChange(value: String) {
        _courseName.value = value
        _isSaved.value = false
    }

    fun onLectureDateChange(value: String) {
        _lectureDate.value = value
        _isSaved.value = false
    }

    fun onHandoutTextChange(value: String) {
        _handoutText.value = value
        _isSaved.value = false
    }

    fun onRawTranscriptChange(value: String) {
        _rawTranscript.value = value
        _isSaved.value = false
    }

    fun onToggleOffline(force: Boolean) {
        _forceOffline.value = force
    }

    fun applyPreset(preset: LecturePreset) {
        _courseName.value = preset.courseName
        _lectureDate.value = preset.lectureDate
        _handoutText.value = preset.handoutExcerpts
        _rawTranscript.value = preset.rawTranscript
        _isSaved.value = false
        generateNotes()
    }

    fun clearAll() {
        _courseName.value = ""
        _lectureDate.value = ""
        _handoutText.value = ""
        _rawTranscript.value = ""
        _generatedNotesMarkdown.value = ""
        _isSaved.value = false
    }

    fun generateNotes() {
        if (_rawTranscript.value.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _isSaved.value = false

            val result = apiService.generateLectureNotes(
                courseName = _courseName.value,
                lectureDate = _lectureDate.value,
                handoutText = _handoutText.value,
                rawTranscript = _rawTranscript.value,
                forceOffline = _forceOffline.value
            )

            result.onSuccess { notes ->
                _generatedNotesMarkdown.value = notes
                _isLoading.value = false
            }.onFailure { error ->
                _errorMessage.value = error.message ?: "Failed to generate lecture notes."
                _isLoading.value = false
            }
        }
    }

    fun saveCurrentNotes() {
        if (_generatedNotesMarkdown.value.isBlank()) return

        viewModelScope.launch {
            val checkpointsCount = Regex("\\[CHECKPOINT\\s*-\\s*[^\\]]+\\]", RegexOption.IGNORE_CASE)
                .findAll(_rawTranscript.value).count()

            val titleMatch = Regex("#\\s*(?:📚)?\\s*Lecture Notes:\\s*(.+)", RegexOption.IGNORE_CASE)
                .find(_generatedNotesMarkdown.value)
            val topic = titleMatch?.groupValues?.getOrNull(1)?.trim()
                ?: _courseName.value.ifBlank { "University Lecture" }

            val entity = LectureNoteEntity(
                courseName = _courseName.value.ifBlank { "General Academic" },
                lectureDate = _lectureDate.value.ifBlank { "2026 Academic Session" },
                topicTitle = topic,
                handoutText = _handoutText.value,
                rawTranscript = _rawTranscript.value,
                generatedNotesMarkdown = _generatedNotesMarkdown.value,
                checkpointCount = checkpointsCount
            )
            dao.insertNote(entity)
            _isSaved.value = true
        }
    }

    fun toggleFavorite(id: Long) {
        viewModelScope.launch {
            dao.toggleFavorite(id)
        }
    }

    fun deleteNote(note: LectureNoteEntity) {
        viewModelScope.launch {
            dao.deleteNote(note)
        }
    }

    fun loadNoteIntoActiveStudio(note: LectureNoteEntity) {
        _courseName.value = note.courseName
        _lectureDate.value = note.lectureDate
        _handoutText.value = note.handoutText
        _rawTranscript.value = note.rawTranscript
        _generatedNotesMarkdown.value = note.generatedNotesMarkdown
        _isSaved.value = true
    }
}
