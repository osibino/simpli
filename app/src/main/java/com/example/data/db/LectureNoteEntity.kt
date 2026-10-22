package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lecture_notes")
data class LectureNoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val courseName: String,
    val lectureDate: String,
    val topicTitle: String,
    val handoutText: String,
    val rawTranscript: String,
    val generatedNotesMarkdown: String,
    val checkpointCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false,
    val universityName: String = "University of Malawi"
)
