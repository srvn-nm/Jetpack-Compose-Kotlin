package com.example.fileFromStoragePicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fileFromStoragePicker.ui.theme.FileFromStoragePickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FileFromStoragePickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FileUploadScreen("image")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FileUploadScreen(type: String) {
    val context = LocalContext.current
    var pickedImageUri by remember { mutableStateOf<Uri?>(null) }
    var filePicker by remember { mutableStateOf(false) }
    var fileUploader by remember { mutableStateOf(false) }
    lateinit var uri: Uri
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = {
                filePicker = true
            }) {
                Text("Select File")
            }
        }
    }
    if (filePicker) {
        val launcher =
            rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                println("selected file URI ${it.data?.data}")
                uri = it.data?.data!!
                if (isValidFile(
                        uri,
                        context
                    ) || type == "video" || type == "image"
                ) {
                    pickedImageUri = uri
                    fileUploader = true
                } else {
                    Toast.makeText(context, "Wrong file type!", Toast.LENGTH_SHORT).show()
                }
            }
        pickedImageUri?.let {
            Text(it.toString())
        }
        Button(
            onClick = {
                when (type) {
                    "image" -> {
                        val intent = Intent(
                            Intent.ACTION_OPEN_DOCUMENT,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                            .apply {
                                addCategory(Intent.CATEGORY_OPENABLE)
                            }
                        launcher.launch(intent)
                    }

                    "video" -> {
                        val intent = Intent(
                            Intent.ACTION_OPEN_DOCUMENT,
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        )
                            .apply {
                                addCategory(Intent.CATEGORY_OPENABLE)
                            }
                        launcher.launch(intent)
                    }

                    "zip/rar" -> {
                        pickZipOrRarFile(launcher = launcher)
                    }
                }
            }
        ) {
            Text("Select")
        }
    }
    if (fileUploader){
        //todo
        val apiStatus = uploadFileToServer(uri, context)
    }
}