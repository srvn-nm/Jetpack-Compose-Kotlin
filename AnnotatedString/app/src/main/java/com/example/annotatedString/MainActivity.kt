package com.example.annotatedString

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import com.example.annotatedString.ui.theme.AnnotatedStringTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnnotatedStringTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onBackground
                ) {
                    AnnotatedString(
                        "Hello. I am Sarvin. ^-^",
                        "Sarvin",
                        "https://github.com/srvn-nm"
                    )
                }
            }
        }
    }
}

@Composable
fun AnnotatedString(text: String, annotatedPart: String, url: String) {
    val annotatedString = buildAnnotatedString {
        append(text)

        val start = text.indexOf(annotatedPart)
        val end = start + annotatedPart.length

        addStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            ),
            start,
            end
        )

        addStringAnnotation(
            "url",
            url,
            start,
            end
        )
    }

    val uriHandler = LocalUriHandler.current
    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            val uri = annotatedString
                .getStringAnnotations(
                    "url",
                    offset,
                    offset
                ).firstOrNull()?.item
            if (uri != null) {
                uriHandler.openUri(uri)
            }
        })
}