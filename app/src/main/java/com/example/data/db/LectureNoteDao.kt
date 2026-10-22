package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LectureNoteDao {
    @Query("SELECT * FROM lecture_notes ORDER BY createdAt DESC")
    fun getAllNotes(): Flow<List<LectureNoteEntity>>

    @Query("SELECT * FROM lecture_notes WHERE id = :id")
    suspend fun getNoteById(id: Long): LectureNoteEntity?

    @Query("SELECT * FROM lecture_notes WHERE isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavoriteNotes(): Flow<List<LectureNoteEntity>>

    @Query("SELECT * FROM lecture_notes WHERE courseName LIKE '%' || :query || '%' OR topicTitle LIKE '%' || :query || '%' OR generatedNotesMarkdown LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchNotes(query: String): Flow<List<LectureNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: LectureNoteEntity): Long

    @Update
    suspend fun updateNote(note: LectureNoteEntity)

    @Delete
    suspend fun deleteNote(note: LectureNoteEntity)

    @Query("DELETE FROM lecture_notes WHERE id = :id")
    suspend fun deleteNoteById(id: Long)

    @Query("UPDATE lecture_notes SET isFavorite = NOT isFavorite WHERE id = :id")
    suspend fun toggleFavorite(id: Long)
}
