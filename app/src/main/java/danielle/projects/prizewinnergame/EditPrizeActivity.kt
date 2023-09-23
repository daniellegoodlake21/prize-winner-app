package danielle.projects.prizewinnergame
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.AppCompatEditText
import danielle.projects.prizewinnergame.databinding.ActivityEditPrizeBinding

class EditPrizeActivity : EditableImageActivity() {

    private var binding: ActivityEditPrizeBinding? = null

    override val imageSideLength = 400
    private var textInputPrizeTitle: AppCompatEditText? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityEditPrizeBinding.inflate(layoutInflater)
            setContentView(binding?.root)

            imageView = binding?.imageViewPrizeImage
            val btnSavePrize: Button? = binding?.btnSavePrize

            // load values
            val extras = intent.extras

            val prizeId = extras?.getString("prizeId")?.toInt()

            val imageHandler = ImageHandler()

            val prizeFileHandler = PrizeFileHandler(filesDir.path)
            val currentPrize = prizeFileHandler.readPrize(prizeId!!)

            val prizeTitle = currentPrize.title
            textInputPrizeTitle = binding?.appCompatEditTextPrizeTitle
            textInputPrizeTitle?.setText(prizeTitle)

            prizeId.let {

                // load image if any otherwise default gift image
                val imageLoaded = imageHandler.loadImage(imageView!!, "Prize", it)
                if (!imageLoaded)
                {
                    imageView!!.setImageResource(R.drawable.gift)
                }
            }

            // image selection handling on click
            val btnSelectPrizeImage: Button? = binding?.btnSelectPrizeImage
            btnSelectPrizeImage?.setOnClickListener{
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                getContent.launch("image/*")
        }

        // save question on click
        btnSavePrize?.setOnClickListener{
            imageHandler.saveImage(bitmapImage,this, "Prize", prizeId)
            val title: String = binding?.appCompatEditTextPrizeTitle?.text.toString()
            val prizeToSave = PrizeViewModel(title, prizeId)
            prizeFileHandler.savePrize(prizeToSave)
            // go back to overview of quiz page
            val intent = Intent(this, SetupQuizBasics::class.java)
            startActivity(intent)
        }

        val permissions = Array(2) { android.Manifest.permission.WRITE_EXTERNAL_STORAGE; android.Manifest.permission.READ_EXTERNAL_STORAGE  }
        requestPermissions(permissions,1024)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}