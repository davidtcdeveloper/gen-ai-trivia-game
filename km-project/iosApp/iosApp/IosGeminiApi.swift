import ComposeApp
import FirebaseAILogic
import Foundation

class IosGeminiApi: GeminiApi {

    let model: String
    let prompt: String

    init(model: String, prompt: String) {
        self.model = model
        self.prompt = prompt
    }

    func getQuestions() async throws -> String {
        let ai = FirebaseAI.firebaseAI(backend: .googleAI())
        let model = ai.generativeModel(modelName: self.model)
        let response = try await model.generateContent(self.prompt)
        return response.text ?? ""
    }
}
