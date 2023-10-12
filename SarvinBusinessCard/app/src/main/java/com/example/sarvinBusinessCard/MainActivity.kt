package com.example.sarvinBusinessCard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sarvinBusinessCard.ui.theme.SarvinBusinessCardTheme
import io.github.giangpham96.expandable_text_compose.ExpandableText


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SarvinBusinessCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.tertiaryContainer
                ) {
                    DigitalBusinessCard(
                        "Sarvin Nami",
                        "Android Developer",
                        "Srvn0nm@gmail.com",
                        "I am a 21 year's old Android Developer.\nThis is my business card!\nI am happy to show you this simple project.\n^-^",
                        "https://github.com/srvn-nm"
                    )
                }
            }
        }
    }
}

@Composable
fun DigitalBusinessCard(
    name: String,
    job: String,
    email: String,
    description: String,
    githubLink: String
) {
    Surface(
        color = Color.LightGray,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp, bottom = 150.dp, start = 32.dp, end = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.profile_picture),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(shape = CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = name,
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = job,
                style = TextStyle(fontSize = 18.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = 20.dp, end = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "email icon",
                    modifier = Modifier
                        .size(30.dp),
                    tint = colorResource(id = R.color.black)
                )
                Text(
                    text = email,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f),
                    color = colorResource(id = R.color.black)
                )
                CopyToClipBoardView(
                    text = email,
                    label = "$name Email",
                    modifier = Modifier
                        .size(30.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = 60.dp)
                    .background(Color.Transparent)
            ) {
                ShowLink(
                    link = githubLink,
                    icon = R.drawable.github,
                    part = "My Github Page"
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            DescriptionView(description = description, fontSize = 16.sp)
        }
    }
}

@Composable
fun CopyToClipBoardView(text: String, label: String, modifier: Modifier) {
    var isClicked by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    IconButton(onClick = {
        if (!isClicked) {
            val clipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText(label, text)
            clipboardManager.setPrimaryClip(clipData)
            isClicked = true
        }
    }) {
        Icon(
            imageVector = if (!isClicked) Icons.Default.Add
            else Icons.Default.Check,
            tint = colorResource(id = R.color.black),
            contentDescription = "",
            modifier = modifier
        )
    }
}

@Composable
fun DescriptionView(description: String, fontSize: TextUnit) {

    var descriptionExpandedState by remember { mutableStateOf(true) }
    var colors by remember { mutableStateOf(Color.Gray) }
    var size by remember { mutableStateOf(fontSize) }
    Box(
        modifier = Modifier.background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        easing = LinearOutSlowInEasing,
                        durationMillis = 200
                    )
                )
                .background(Color.LightGray)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    descriptionExpandedState = !descriptionExpandedState
                },
            shape = RoundedCornerShape(0.dp),
        ) {
            if (descriptionExpandedState) {

                var expand by remember {
                    mutableStateOf(false)
                }

                ExpandableText(
                    originalText = description,
                    expandAction = "See More",
                    expand = expand,
                    expandActionColor = Color.DarkGray,
                    fontSize = size,
                    color = colors,
                    limitedMaxLines = 2,
                    animationSpec = spring(),
                    modifier = Modifier
                        .clickable {
                            expand = !expand
                            if (colors == Color.Gray) {
                                colors = Color.Black
                                size = fontSize
                            } else if (colors == Color.Black) {
                                colors = Color.Gray
                                size = fontSize
                            }
                        }
//                        .padding(10.dp)
                        .background(Color.LightGray)
                )
            }
        }
    }
}

@Composable
fun ShowLink(link: String, icon: Int, part: String) {
    Card(
        modifier = Modifier
            .padding(top = 2.5.dp, bottom = 2.5.dp, end = 2.5.dp, start = 10.dp)
            .background(Color.LightGray)
            .wrapContentSize(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(5.dp),
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.DarkGray)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = "link icon",
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .padding(top = 5.dp, bottom = 5.dp)
            )
            AnnotatedString(
                part, part, link, Modifier.padding(top = 5.dp, bottom = 5.dp, end = 5.dp)
            )
        }
    }
}

@Composable
fun AnnotatedString(text: String, annotatedPart: String, url: String, modifier: Modifier) {
    val annotatedString = buildAnnotatedString {
        append(text)

        val start = text.indexOf(annotatedPart)
        val end = start + annotatedPart.length

        addStyle(
            SpanStyle(
                color = Color.LightGray,
                textDecoration = TextDecoration.None
            ), start, end
        )

        addStringAnnotation(
            "url", url, start, end
        )
    }

    val uriHandler = LocalUriHandler.current
    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            val uri = annotatedString.getStringAnnotations(
                "url", offset, offset
            ).firstOrNull()?.item
            if (uri != null) {
                uriHandler.openUri(uri)
            }
        }, style = TextStyle(
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        ), modifier = modifier
    )
}