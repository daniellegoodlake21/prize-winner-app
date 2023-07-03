package danielle.projects.prizewinnergame

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

open class EditableImageActivity : AppCompatActivity() {
    var imageView: ImageView? = null
    open val imageSideLength: Int = 200
    var bitmapImage: Bitmap? = null

    val getContent = registerForActivityResult(ActivityResultContracts.GetContent())
    {
            selectedPhotoUri: Uri? ->

        try {
            selectedPhotoUri?.let {
                if(Build.VERSION.SDK_INT < 28) {
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
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}