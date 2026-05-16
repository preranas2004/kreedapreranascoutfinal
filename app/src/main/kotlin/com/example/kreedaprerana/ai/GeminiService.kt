package com.example.kreedaprerana.ai

import com.example.kreedaprerana.data.Trial
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

class GeminiService(
    apiKey: String
) {

    private val model = GenerativeModel(

        modelName = "gemini-1.5-flash",

        apiKey = apiKey
    )

    suspend fun analyzePerformance(

        athleteName: String,

        trials: List<Trial>

    ): String {

        if (trials.isEmpty()) {

            return "No trial data available"
        }

        val trialData = trials.joinToString("\n") {

            "${it.trialType}: ${it.value}"
        }

        val prompt = """

            Analyze athlete performance.

            Athlete:
            $athleteName

            Trials:
            $trialData

            Give:

            1. Strengths
            2. Weaknesses
            3. Training recommendation
            4. Potential sport specialization

            Keep response professional and short.

        """.trimIndent()

        return try {

            val response =

                model.generateContent(

                    content {

                        text(prompt)
                    }
                )

            response.text
                ?: "No AI response"

        } catch (e: Exception) {

            "AI Error: ${e.message}"
        }
    }
}