package com.kotlindemo.activity.behavior.camera

import android.Manifest
import android.content.Context
import android.graphics.Matrix
import android.hardware.Camera
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Rational
import android.view.Surface
import android.widget.Toast
import androidx.camera.core.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import com.kotlindemo.utility.ToastManager
import kotlinx.android.synthetic.main.activity_camerax.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Manish Patel on 7/18/2019.
 */
//https://blog.mindorks.com/getting-started-with-camerax
//https://developer.android.com/jetpack/androidx/releases/camera
//https://medium.com/@hitherejoe/exploring-camerax-on-android-camera-view-daae6dfaa4ec
//https://codelabs.developers.google.com/codelabs/camerax-getting-started/#2
//https://github.com/android/camera/tree/master/CameraXBasic

class CameraActivity : ParentActivity() {

    private var lensFacing = CameraX.LensFacing.BACK
    private lateinit var outputDirectory: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camerax)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Determine the output directory
        outputDirectory = getOutputDirectory(applicationContext)

        //view_finder.post { startCamera() }
    }

    private fun startCamera() {
        val metrics = DisplayMetrics().also { view_finder.display.getRealMetrics(it) }
        //val screenSize = Camera.Size(metrics.widthPixels, metrics.heightPixels)
        val screenAspectRatio = Rational(metrics.widthPixels, metrics.heightPixels)

        val previewConfig = PreviewConfig.Builder().apply {
            setLensFacing(lensFacing)
            //setTargetResolution(screenSize)
            setTargetAspectRatio(screenAspectRatio)
            //setTargetRotation(windowManager.defaultDisplay.rotation)
            setTargetRotation(view_finder.display.rotation)
        }.build()

        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener {
            view_finder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        // Create configuration object for the image capture use case
        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                setLensFacing(lensFacing)
                setTargetAspectRatio(screenAspectRatio)
                setTargetRotation(view_finder.display.rotation)
                setCaptureMode(ImageCapture.CaptureMode.MAX_QUALITY)
            }.build()

        // Build the image capture use case and attach button click listener
        val imageCapture = ImageCapture(imageCaptureConfig)
        btn_take_picture.setOnClickListener {

            /*val file = File(
                Environment.getExternalStorageDirectory().toString() +
                        "${MainActivity.folderPath}${System.currentTimeMillis()}.jpg"
            )*/

            // Create output file to hold the image
            val photoFile = createFile(outputDirectory, FILENAME, PHOTO_EXTENSION)

            imageCapture.takePicture(photoFile, object : ImageCapture.OnImageSavedListener {
                override fun onError(
                    error: ImageCapture.UseCaseError,
                    message: String, exc: Throwable?
                ) {
                    val msg = "Photo capture failed: $message"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()

                }

                override fun onImageSaved(file: File) {
                    ToastManager.getInstance().showToast("Photo capture successfully: ${file.absolutePath}")
                }
            })

        }

        CameraX.bindToLifecycle(this, preview, imageCapture)
    }

    private fun updateTransform() {
        val matrix = Matrix()
        val centerX = view_finder.width / 2f
        val centerY = view_finder.height / 2f

        val rotationDegrees = when (view_finder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)
        view_finder.setTransform(matrix)
    }

    override fun onResume() {
        super.onResume()

        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    view_finder.post { startCamera() }
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    // check for permanent denial of permission
                    if (response.isPermanentlyDenied) {
                        // navigate user to app settings
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }

            }).check()
    }

    companion object {
        val TAG: String = CameraActivity.javaClass::class.java.simpleName
        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_EXTENSION = ".jpg"

        /** Helper function used to create a timestamped file */
        private fun createFile(baseFolder: File, format: String, extension: String) =
            File(
                baseFolder, SimpleDateFormat(format, Locale.US)
                    .format(System.currentTimeMillis()) + extension
            )

        /** Use external media if it is available, our app's file directory otherwise */
        fun getOutputDirectory(context: Context): File {
            val appContext = context.applicationContext
            val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
                File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() }
            }
            return if (mediaDir != null && mediaDir.exists())
                mediaDir else appContext.filesDir
        }
    }


}