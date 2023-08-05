package com.example.fileFromStoragePicker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.documentfile.provider.DocumentFile
import com.example.fileFromStoragePicker.ui.theme.FileFromStoragePickerTheme
import kotlinx.coroutines.flow.MutableStateFlow

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
                    FileUploadScreen("image")
                }
            }
        }
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
                            Intent.ACTION_OPEN_DOCUMENT, MediaStore.Video.Media.EXTERNAL_CONTENT_URI
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
                    BitmapFactory.decodeStream(context.contentResolver.openInputStream(fileUri))
                painter = BitmapPainter(drawable.value!!.asImageBitmap())
//                    pickedImageUri = null // Reset the state to avoid reloading the image in case of recomposition
            }

            Image(
                painter = painter ?: painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "User Picked Image",
                modifier = Modifier.fillMaxSize()
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