package org.davidtiago.genaitriviagame.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.davidtiago.genaitriviagame.model.FirebaseAiConfig

@Composable
fun AiConfigComposable(
    config: FirebaseAiConfig,
    onConfigSet: (FirebaseAiConfig) -> Unit,
) {

    var projectIdValue by remember { mutableStateOf(config.projectId) }
    var appIdValue by remember { mutableStateOf(config.appId) }
    var appNameValue by remember { mutableStateOf(config.appName) }
    var apiKeyValue by remember { mutableStateOf(config.apiKey) }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "AI Configuration",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        OutlinedTextField(
            value = projectIdValue,
            onValueChange = { newText -> projectIdValue = newText },
            label = { Text("Project ID") },
            placeholder = { Text("e.g. project-id-name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = appIdValue,
            onValueChange = { newText -> appIdValue = newText },
            label = { Text("App ID") },
            placeholder = { Text("e.g. 1:662701934234:android:08495af3301250faec6acb") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = appNameValue,
            onValueChange = { newText -> appNameValue = newText },
            label = { Text("App Name") },
            placeholder = { Text("e.g. AppName, DEFAULT also accepted") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = apiKeyValue,
            onValueChange = { newText -> apiKeyValue = newText },
            label = { Text("API Key") },
            placeholder = { Text("e.g. AIcaDfCEzHJrzBjSyjJb0QuCFhyJkBBcTOq8NaP") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {
            onConfigSet(
                FirebaseAiConfig(
                    projectId = projectIdValue,
                    appId = appIdValue,
                    appName = appNameValue,
                    apiKey = apiKeyValue,
                )
            )
        }) {
            Text("Save")
        }
    }
}

@Preview
@Composable
fun AiConfigComposablePreview() {
    MaterialTheme {
        AiConfigComposable(
            config = FirebaseAiConfig(
                projectId = "",
                appId = "",
                appName = "",
                apiKey = "",
            ),
            onConfigSet = {},
        )
    }
}
