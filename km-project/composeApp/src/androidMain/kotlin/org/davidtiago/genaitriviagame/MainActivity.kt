package org.davidtiago.genaitriviagame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.davidtiago.genaitriviagame.gemini.AndroidGeminiApi
import org.davidtiago.genaitriviagame.repository.InMemoryMockQuestionRepository
import org.davidtiago.genaitriviagame.repository.gemini.GeminiQuestionRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: Dependency Injection
        setContent {
            App(
                GeminiQuestionRepository(
                    AndroidGeminiApi(
                        model = defaultGeminiModel,
                        prompt = defaultQuestionsPrompt,
                    )
                )
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(InMemoryMockQuestionRepository())
}