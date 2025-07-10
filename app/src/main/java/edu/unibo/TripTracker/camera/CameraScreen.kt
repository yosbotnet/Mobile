package edu.unibo.tracker.camera

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import edu.unibo.tracker.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import java.io.File
var imageUri : Uri? = null


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(navController: NavController){

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    var preview by remember { mutableStateOf<Preview?>(null) }
    val executor = ContextCompat.getMainExecutor(context)
    val cameraProvider = cameraProviderFuture.get()
    var camera = cameraProvider.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA)
    var photoName by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var photoToString = photoName.text
    var onMediaCaptured = { _: Uri -> Unit }
    var photoFile = File(
        context.getDirectory(),
        "${photoToString}.png"
    )
    val photoUri = Uri.fromFile(photoFile)


    Scaffold(topBar = { TopBarCamera(navController) })
    {

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f), horizontalAlignment = Alignment.CenterHorizontally) {
           // Text("$photoUri")
            AndroidView(
                modifier = Modifier.fillMaxSize(0.90f),
                factory = { ctx ->
                    val previewView = PreviewView(ctx)
                    cameraProviderFuture.addListener({
                        imageCapture = ImageCapture.Builder()
                            .setTargetRotation(previewView.display.rotation)
                            .build()

                        val cameraSelector = CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()

                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            imageCapture,
                            preview
                        )
                    }, executor)
                    preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    previewView
                }
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                OutlinedTextField(
                    value = photoName,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(0.4f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    label = { Text("Nome file") },
                    placeholder = { Text(text = "Nome file") },
                    onValueChange = {
                        photoName = it
                    },
                )
                Spacer(modifier = Modifier.width(12.dp))
                Button(onClick = {
                    val imgCapture = imageCapture ?: return@Button


                    imgCapture.takePicture(
                        outputOptions,
                        executor,
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                onMediaCaptured(photoUri)
                                imageUri = photoUri
                                Toast.makeText(context, "Image saved", Toast.LENGTH_SHORT).show()
                            }

                            override fun onError(exception: ImageCaptureException) {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    )
                }) {
                    Text(text = "Scatta")
                }
            }
        }

    }
}


//Store the capture image
fun Context.getDirectory(): File {
    val mediaDir = this.externalMediaDirs.firstOrNull()?.let {
        File(it, this.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else this.filesDir
}


@Composable
fun TopBarCamera(navController: NavController) {
    TopAppBar(
        title = { Text("Camera", color = MaterialTheme.colors.onPrimary) },
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 4.dp,
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        })
}


/*
val emptyImageUri = Uri.parse("file://dev/null")
var imageUri by remember { mutableStateOf(emptyImageUri) }
var string : String? = null
if (imageUri != emptyImageUri) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(imageUri),
            contentDescription = "Captured image"
        )
        string = imageUri.toString()
        Text(string!!)

        Button(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = {
                imageUri = emptyImageUri
            }
        ) {
            Text("Remove image")
        }
    }
} else {
    Column() {
        CameraCapture(
            modifier = Modifier.fillMaxWidth(),
            onImageFile = { file ->
                imageUri = file.toUri()
                string = imageUri.toString()
            })
    }
}
}*/

