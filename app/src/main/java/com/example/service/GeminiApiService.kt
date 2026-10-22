package com.example.service

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiApiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    suspend fun generateLectureNotes(
        courseName: String,
        lectureDate: String,
        handoutText: String,
        rawTranscript: String,
        forceOffline: Boolean = false
    ): Result<String> = withContext(Dispatchers.IO) {
        if (forceOffline) {
            val offlineNotes = AcademicNoteSynthesizer.synthesizeOfflineNotes(
                courseName = courseName,
                lectureDate = lectureDate,
                handoutText = handoutText,
                rawTranscript = rawTranscript
            )
            return@withContext Result.success(offlineNotes)
        }

        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            Log.w("GeminiApiService", "Gemini API key is unconfigured, using local university note synthesizer")
            val offlineNotes = AcademicNoteSynthesizer.synthesizeOfflineNotes(
                courseName = courseName,
                lectureDate = lectureDate,
                handoutText = handoutText,
                rawTranscript = rawTranscript
            )
            return@withContext Result.success(offlineNotes)
        }

        // Try primary model (gemini-3.5-flash as per skill, or gemini-2.5-flash)
        val models = listOf("gemini-3.5-flash", "gemini-2.5-flash")
        var lastException: Exception? = null

        for (model in models) {
            try {
                val systemPrompt = AcademicNoteSynthesizer.buildSystemInstruction()
                val userPrompt = AcademicNoteSynthesizer.buildUserPrompt(
                    courseName = courseName,
                    lectureDate = lectureDate,
                    handoutText = handoutText,
                    rawTranscript = rawTranscript
                )

                val requestJson = JSONObject().apply {
                    // Contents
                    val contentsArray = JSONArray().apply {
                        put(JSONObject().apply {
                            put("role", "user")
                            put("parts", JSONArray().apply {
                                put(JSONObject().put("text", userPrompt))
                            })
                        })
                    }
                    put("contents", contentsArray)

                    // System Instruction
                    put("systemInstruction", JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", systemPrompt))
                        })
                    })

                    // Generation config
                    put("generationConfig", JSONObject().apply {
                        put("temperature", 0.25)
                        put("topP", 0.95)
                    })
                }

                val url = "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$apiKey"
                val body = requestJson.toString().toRequestBody(jsonMediaType)
                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .build()

                val response = client.newCall(request).execute()
                val responseBodyString = response.body?.string() ?: ""

                if (!response.isSuccessful) {
                    Log.e("GeminiApiService", "Model $model returned error HTTP ${response.code}: $responseBodyString")
                    lastException = Exception("HTTP ${response.code}: $responseBodyString")
                    continue
                }

                val jsonResponse = JSONObject(responseBodyString)
                val candidates = jsonResponse.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val candidate = candidates.getJSONObject(0)
                    val contentObj = candidate.optJSONObject("content")
                    val parts = contentObj?.optJSONArray("parts")
                    if (parts != null && parts.length() > 0) {
                        val text = parts.getJSONObject(0).optString("text")
                        if (text.isNotBlank()) {
                            return@withContext Result.success(text)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("GeminiApiService", "Exception calling Gemini API with $model: ${e.message}", e)
                lastException = e
            }
        }

        // Graceful fallback to our academic synthesizer
        Log.i("GeminiApiService", "Online API calls failed, serving synthesized academic notes.")
        val fallbackNotes = AcademicNoteSynthesizer.synthesizeOfflineNotes(
            courseName = courseName,
            lectureDate = lectureDate,
            handoutText = handoutText,
            rawTranscript = rawTranscript
        )
        Result.success(fallbackNotes)
    }
}
