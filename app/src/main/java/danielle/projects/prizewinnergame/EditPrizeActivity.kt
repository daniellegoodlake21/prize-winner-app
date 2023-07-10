package danielle.projects.prizewinnergame
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditPrizeActivity : EditableImageActivity() {

    override val imageSideLength = 400
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_prize)

        imageView = findViewById(R.id.imageViewPrizeImage)
        val btnSavePrize: Button = findViewById(R.id.btnSavePrize)

        // load values
        val extras = intent.extras

        val prizeId = extras?.getString("prizeId")?.toInt()

        val imageHandler = ImageHandler()

        val prizeFileHandler = PrizeFileHandler(filesDir.path)
        val currentPrize = prizeFileHandler.readPrize(prizeId!!)

        val prizeTitle = currentPrize.title
        val textInputPrizeTitle: EditText = findViewById(R.id.appCompatEditTextPrizeTitle)
        textInputPrizeTitle.setText(prizeTitle)

        prizeId.let {

            // load image if any otherwise default gift image
            val imageLoaded = imageHandler.loadImage(imageView!!, "Prize", it)
            if (!imageLoaded)
            {
                imageView!!.setImageResource(R.drawable.gift)
            }
        }

        // image selection handling on click
        val btnSelectPrizeImage: Button = findViewById(R.id.btnSelectPrizeImage)
        btnSelectPrizeImage.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            getContent.launch("image/*")
        }

        // save question on click
        btnSavePrize.setOnClickListener{
            imageHandler.saveImage(bitmapImage,this, "Prize", prizeId)
            val t: String = findViewById<EditText>(R.id.appCompatEditTextPrizeTitle).text.toString()
            val prizeToSave = PrizeViewModel(t, prizeId)
            prizeFileHandler.savePrize(prizeToSave)
            // go back to overview of quiz page
            val intent = Intent(this, SetupQuizBasics::class.java)
            startActivity(intent)
        }

        val permissions = Array(2) { android.Manifest.permission.WRITE_EXTERNAL_STORAGE; android.Manifest.permission.READ_EXTERNAL_STORAGE  }
        requestPermissions(permissions,1024)
    }
}