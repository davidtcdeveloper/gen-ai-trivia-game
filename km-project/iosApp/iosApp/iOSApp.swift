import SwiftUI
import FirebaseCore
import ComposeApp

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil) -> Bool {
        FirebaseApp.configure()
        return true
    }
}

@main
struct iOSApp: App {
    init() {
        KoinInitKt.doInitKoin(
            geminiApi: IosGeminiApi(model: PromptKt.defaultGeminiModel,
                                        prompt: PromptKt.defaultQuestionsPrompt
            )
        )
    }

    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
