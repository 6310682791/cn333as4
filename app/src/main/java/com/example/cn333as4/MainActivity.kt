package com.example.cn333as4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cn333as4.ui.theme.Cn333as4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Cn333as4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DisplayImage(Modifier)
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeywordForm(onSubmit: (String, Int, Int) -> Unit) {
    var keyword by remember { mutableStateOf("") }
    var width by remember { mutableStateOf(0) }
    var height by remember { mutableStateOf(0) }
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = keyword,
            onValueChange = { keyword = it },
            label = { Text(text = "Enter Keyword") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    keyboardController?.hide()
                }
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = width.toString(),
            onValueChange = { width = it.toIntOrNull() ?: 0 },
            label = { Text(text = "Width") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    keyboardController?.hide()
                }
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = height.toString(),
            onValueChange = { height = it.toIntOrNull() ?: 0 },
            label = { Text(text = "Height") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onSubmit(keyword, width, height)
                    keyboardController?.hide()
                }
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onSubmit(keyword, width, height) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Show Image")
        }
    }
}


@Composable
fun DisplayImage(modifier: Modifier) {
    var keyword by remember { mutableStateOf("") }
    var width by remember { mutableStateOf(0) }
    var height by remember { mutableStateOf(0) }
    val key = keyword.replace(" ", "-")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        KeywordForm { k, w, h ->
            keyword = k
            width = w
            height = h

        }

        if (keyword.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(modifier = Modifier.height(300.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://loremflickr.com/$width/$height/$key")
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Keyword: $keyword",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Cn333as4Theme {
        DisplayImage(Modifier)
    }
}