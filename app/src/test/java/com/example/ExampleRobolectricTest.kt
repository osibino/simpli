package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.service.AcademicNoteSynthesizer
import com.example.service.MalawiPresets
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Simplify", appName)
    }

    @Test
    fun `synthesizer generates structured notes from preset`() {
        val preset = MalawiPresets.presets.first()
        val notes = AcademicNoteSynthesizer.synthesizeOfflineNotes(
            courseName = preset.courseName,
            lectureDate = preset.lectureDate,
            handoutText = preset.handoutExcerpts,
            rawTranscript = preset.rawTranscript
        )
        assertNotNull(notes)
        assertTrue(notes.contains("📚 Lecture Notes:"))
        assertTrue(notes.contains("## 1. Executive Summary & Core Objectives"))
        assertTrue(notes.contains("## 2. Detailed Structured Notes"))
        assertTrue(notes.contains("💡 **Local Explanation / Chichewa Insight**"))
        assertTrue(notes.contains("## 3. Handout & Textbook Alignment"))
        assertTrue(notes.contains("## 4. Potential Exam Focus Areas & Review Questions"))
    }
}
