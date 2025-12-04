import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(
            questionRepository: GeminiQuestionRepository(
                geminiApi: IosGeminiApi(model: PromptKt.defaultGeminiModel,
                                        prompt: PromptKt.defaultQuestionsPrompt
                )
            )
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



