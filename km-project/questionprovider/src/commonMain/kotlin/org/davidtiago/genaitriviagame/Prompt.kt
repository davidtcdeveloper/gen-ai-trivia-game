package org.davidtiago.genaitriviagame

//TODO Isolate gemini-specific values
const val defaultGeminiModel = "gemini-3.6-flash"

const val defaultQuestionsPrompt = "" +
        "Generate 5 trivia questions about general knowledge topics with multiple-choice answers." +
        "Be very random in the topics." +
        "Do not add math challenges. " +
        "Each question should have 4 choices, only one correct." +
        "The right answer should repeated in each question. Randomize the correct option index." +
        "Do not output anything other than JSON." +
        "Below is a sample JSON format to be generated. " +
        "[\n" +
        "  {\n" +
        "    \"text\": \"This is the question text\",\n" +
        "    \"options\": [\"Option\", \"Different Option\", \"Another Option\", \"Correct Option\"],\n" +
        "    \"correctAnswer\": \"Correct Option\"\n" +
        "  },\n" +
        "  // Repeat the JSON object" +
        "]"
