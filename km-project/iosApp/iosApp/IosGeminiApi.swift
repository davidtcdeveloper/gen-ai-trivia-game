
//
// Created by David Tiago Conceição on 27/11/25.
// Copyright (c) 2025 orgName. All rights reserved.
//

import ComposeApp
import FirebaseAILogic
import Foundation

class IosGeminiApi: GeminiApi {
    func getQuestions() async throws -> String {
        let ai = FirebaseAI.firebaseAI(backend: .googleAI())
        let model = ai.generativeModel(modelName: "gemini-2.5-flash")
        let response = try await model.generateContent(PromptKt.defaultQuestionsPrompt)
        return response.text ?? ""
    }
}
