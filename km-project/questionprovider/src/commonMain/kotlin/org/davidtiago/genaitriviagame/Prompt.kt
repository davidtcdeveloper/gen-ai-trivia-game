package org.davidtiago.genaitriviagame

const val defaultQuestionsPrompt = "" +
        "Generate 5 trivia questions with multiple-choice answers." +
        "Questions should be about general knowledge topics. Do not create long questions. Randomize the subject of each question." +
        "Do not add math challenges. Each question should have 4 choices, only one correct." +
        "The right answer should repeated in each question. Randomize the correct option index." +
        "Do not generate multiple questions with the same answer." +
        "List questions using the JSON format below. " +
        "Do not output anything other than JSON. Do not output prefixes, markers or any formatter like ```json" +
        "[\n" +
        "  {\n" +
        "    \"text\": \"What gas do plants primarily absorb from the atmosphere for photosynthesis?\",\n" +
        "    \"options\": [\"Methane\", \"Nitrogen\", \"Oxygen\", \"Carbon Dioxide\"],\n" +
        "    \"correctAnswer\": \"Carbon Dioxide\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"text\": \"How many continents are generally recognized on Earth?\",\n" +
        "    \"options\": [\"Seven\", \"Five\", \"Six\", \"Eight\"],\n" +
        "    \"correctAnswer\": \"Seven\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"text\": \"Which of the following words is an example of a verb?\",\n" +
        "    \"options\": [\"Slowly\", \"Jump\", \"Beautiful\", \"Table\"],\n" +
        "    \"correctAnswer\": \"Jump\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"text\": \"Which ancient civilization is famously associated with the construction of large pyramids?\",\n" +
        "    \"options\": [\"Roman\", \"Egyptian\", \"Greek\", \"Incan\"],\n" +
        "    \"correctAnswer\": \"Egyptian\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"text\": \"Which of these colors is NOT considered a primary color in traditional art theory?\",\n" +
        "    \"options\": [\"Blue\", \"Red\", \"Yellow\", \"Green\"],\n" +
        "    \"correctAnswer\": \"Green\"\n" +
        "  }\n" +
        "]"
