package com.example.fileFromStoragePicker

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.documentfile.provider.DocumentFile
import com.example.fileFromStoragePicker.ui.theme.FileFromStoragePickerTheme
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.flow.MutableStateFlow
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FileFromStoragePickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    FileUploadScreen("video")
                }
            }
        }
        requestPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO,
        )
    }

    private fun requestPermissions(vararg permissions: String) {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            result.entries.forEach {
                Log.d("MainActivity", "${it.key} = ${it.value}")
            }
        }
        requestPermissionLauncher.launch(permissions.asList().toTypedArray())
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun FileUploadScreen(type: String) {
    val context = LocalContext.current
    var pickedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    lateinit var uri: Uri
    val drawable = MutableStateFlow<Bitmap?>(null)
    var painter by remember { mutableStateOf<Painter?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            println("selected file URI ${it.data?.data}")
            uri = it.data?.data!!
            if (isValidFile(
                    uri, context
                ) || type == "video" || type == "image"
            ) {
                pickedImageUri = uri
            } else {
                Toast.makeText(context, "Wrong file type!", Toast.LENGTH_SHORT).show()
            }
        }
    Surface(
        color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = {
                when (type) {
                    "image" -> {
                        val intent = Intent(
                            Intent.ACTION_OPEN_DOCUMENT,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        ).apply {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        launcher.launch(intent)
                    }

                    "video" -> {
                        val intent = Intent(
                            Intent.ACTION_OPEN_DOCUMENT,
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        ).apply {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        launcher.launch(intent)
                    }

                    "zip/rar" -> {
                        pickZipOrRarFile(launcher = launcher)
                    }
                }
            }) {
                Text("Select File")
            }

            pickedImageUri?.let { fileUri ->
                when (type) {
                    "image" -> {
//        //for opening the image using gallery
//        val intent = Intent(Intent.ACTION_VIEW).apply {
//            setDataAndType(fileUri, context.contentResolver.getType(fileUri))
//            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        }
//        try {
//            context.startActivity(intent)
//        } catch (e: Exception) {
//            Toast.makeText(context, "Unable to open file", Toast.LENGTH_SHORT).show()
//        }
                        //for display it in image view
                        drawable.value =
                            BitmapFactory.decodeStream(
                                context.contentResolver.openInputStream(
                                    fileUri
                                )
                            )
                        painter = BitmapPainter(drawable.value!!.asImageBitmap())
//                    pickedImageUri = null // Reset the state to avoid reloading the image in case of recomposition
                    }
                    "video"->{
                        VideoPlayer(fileUri)
                    }
                }
            }
            if (type == "image") {
                Image(
                    painter = painter ?: painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "User Picked Image",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun VideoPlayer(uri: Uri) {

    val context = LocalContext.current
    val activity = context as Activity

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val videoItem = MediaItem.Builder().setUri(uri).build()
            setMediaItem(videoItem)
            prepare()
        }
    }

    exoPlayer.playWhenReady = false
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.pauseAtEndOfMediaItems = true

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    val isFullscreenButtonClicked = remember {
        mutableStateOf(false)
    }
    val playerView = remember {
        StyledPlayerView(context).apply {
            player = exoPlayer
            setShowNextButton(false)
            setShowPreviousButton(false)
            setFullscreenButtonClickListener {
                isFullscreenButtonClicked.value = !isFullscreenButtonClicked.value
            }
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        }
    }

    if(isFullscreenButtonClicked.value){
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                val parent = playerView.parent as? ViewGroup
                parent?.removeView(playerView)
                playerView
            }
        )
    } else {
        Card(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            AndroidView(
                factory = {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                    val parent = playerView.parent as? ViewGroup
                    parent?.removeView(playerView)
                    playerView
                }
            )
        }
    }
}

private fun pickZipOrRarFile(
    launcher: ActivityResultLauncher<Intent>,
) {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "*/*" // Allows the user to pick any type of file
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    launcher.launch(intent)
}

private fun isValidFile(uri: Uri, context: Context): Boolean {
    val documentFile = DocumentFile.fromSingleUri(context, uri)
    val fileExtension = documentFile?.name?.substringAfterLast('.')
    return fileExtension == "zip" || fileExtension == "rar"
}