package danielle.projects.prizewinnergame
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class ImageHandler {

    private fun isValidImageType(imageType: String): Boolean
    {
        return (imageType == "Question" || imageType == "Prize")
    }

    fun loadImage(view: ImageView, imageType: String, imageIndex: Int) : Boolean
    {
        if (!isValidImageType(imageType))
        {
            return false
        }
        try {
            val folder = File(view.context.getExternalFilesDir(null)!!.path,  "PrizeWinner/Images/${imageType}s")
            if (!folder.exists())
            {
                return false
            }
            else
            {
                val filename = "${imageType}_$imageIndex.jpg"
                val file = File(folder, filename)
                if (!file.exists()) {
                    return false
                }
                var bitmap: Bitmap = BitmapFactory.decodeFile(file.absolutePath)
                bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true)
                view.setImageBitmap(bitmap)
            }
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            return false
        }
        return true
    }


    fun saveImage(bitmap: Bitmap?, context: Context, imageType: String, imageIndex: Int): Boolean
    {
        bitmap?.let {
            if (!isValidImageType(imageType))
            {
                return false
            }
            val folder = File(context.getExternalFilesDir(null)!!.path, "PrizeWinner/Images/${imageType}s")
            if (!folder.exists())
            {
                folder.mkdirs()
            }
            val file = File(folder, "${imageType}_$imageIndex.jpg")
            try
            {
                if (!file.exists())
                {
                    file.createNewFile()
                }
                val outputStream = FileOutputStream(file)
                val scaledBitmap = Bitmap.createScaledBitmap(it,200, 200,true)
                val bytes = ByteArrayOutputStream()
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                outputStream.write(bytes.toByteArray())
                outputStream.close()
                return true
            }
            catch (e: Exception)
            {
                e.printStackTrace()
                return false
            }
        }
        return false
    }
}