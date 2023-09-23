package danielle.projects.prizewinnergame

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class EditableImageActivity : AppCompatActivity() {
    var imageView: ImageView? = null
    open val imageSideLength: Int = 200
    var bitmapImage: Bitmap? = null

    protected fun handlePermissions()
    {
        var permission: String = Manifest.permission.READ_EXTERNAL_STORAGE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            permission = Manifest.permission.READ_MEDIA_IMAGES
        }
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
        {
            imagesResultLauncher.launch("image/*")
        }
        else if (checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED)
        {
            if (shouldShowRequestPermissionRationale(permission))
            {
                // only called if user has already denied image access
                showRationaleDialog("Prize Winner requires image access", "Access to images is denied but needed to customise question and prize images.")
            }
            else
            {
                requestPermissions(arrayOf(permission), 1024)
            }
        }


    }
    private fun showRationaleDialog(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setIcon(R.drawable.gift)
        builder.setPositiveButton("Okay"){dialog, _ -> dialog.dismiss()}
        builder.create().show()
    }

    private val imagesResultLauncher: ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.GetContent())
    {
        selectedPhotoUri: Uri? ->
        try {
            selectedPhotoUri?.let {
                if(Build.VERSION.SDK_INT < 28) {

                    @Suppress("DEPRECATION")
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        this.contentResolver,
                        selectedPhotoUri
                    )
                    imageView!!.setImageBitmap(bitmap)
                    bitmapImage = bitmap
                } else {
                    val source = ImageDecoder.createSource(this.contentResolver, selectedPhotoUri)
                    var bitmap = ImageDecoder.decodeBitmap(source)
                    bitmap = Bitmap.createScaledBitmap(bitmap, imageSideLength, imageSideLength, true)
                    imageView!!.setImageBitmap(bitmap)
                    bitmapImage = bitmap
                }
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
}