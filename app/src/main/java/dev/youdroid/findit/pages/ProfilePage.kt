@file:OptIn(ExperimentalMaterial3Api::class)

package dev.youdroid.findit.pages

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.youdroid.findit.R
import dev.youdroid.findit.ui.theme.FindItTheme

@Composable
fun ProfilePage() {

    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    val scrollState = rememberScrollState()

    Scaffold {
        Box {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp)
                    .scrollable(scrollState, Orientation.Vertical),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = { textChanges -> name = textChanges },
                    placeholder = { Text("Name") },
                    label = { Text("User Name") },
                    shape = MaterialTheme.shapes.extraLarge,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = { textChanges -> email = textChanges },
                    placeholder = { Text("Email") },
                    label = { Text("Email Address") },
                    shape = MaterialTheme.shapes.extraLarge,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )
            }

            BuildSubmitButton(Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Composable
private fun BuildSubmitButton(modifier: Modifier = Modifier) {
    ElevatedButton(modifier = modifier
        .padding(16.dp)
        .height(60.dp)
        .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        onClick = { }) {
        Text(
            text = stringResource(R.string.button_done),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePagePrev() {
    FindItTheme {
        ProfilePage()
    }
}