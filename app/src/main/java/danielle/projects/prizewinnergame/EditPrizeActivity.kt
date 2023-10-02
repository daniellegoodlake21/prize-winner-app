package danielle.projects.prizewinnergame
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import danielle.projects.prizewinnergame.databinding.ActivityEditPrizeBinding
import kotlinx.coroutines.launch

class EditPrizeActivity : EditableImageActivity() {

    private var binding: ActivityEditPrizeBinding? = null

    private var prizeDao: PrizeDao? = null

    override val imageSideLength = 400

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

            prizeId.let {
                lifecycleScope.launch {
                    loadRecord(prizeId!!)
                }
                // load image if any otherwise default gift image
                val imageLoaded = imageHandler.loadImage(imageView!!, "Prize", it!!)
                if (!imageLoaded)
                {
                    imageView!!.setImageResource(R.drawable.gift)
                }
            }

            // image selection handling on click
            val btnSelectPrizeImage: Button? = binding?.btnSelectPrizeImage
            btnSelectPrizeImage?.setOnClickListener{
                handlePermissions()
           }

        // save question on click
        btnSavePrize?.setOnClickListener{
            imageHandler.saveImage(bitmapImage,this, "Prize", prizeId!!)

            lifecycleScope.launch {
                updateRecord(prizeId)
                val intent = Intent(this@EditPrizeActivity, SetupQuizBasicsActivity::class.java)
                startActivity(intent)
                finish()
            }
            // go back to overview of quiz page

        }
    }

    private suspend fun loadRecord(prizeId: Int)
    {
        // load the prize
        prizeDao = (application as PrizeWinnerApp).database.prizeDao()
        prizeDao?.fetchPrizeById(prizeId)?.collect{ prize ->
            binding?.appCompatEditTextPrizeTitle?.setText(prize.title)
        }
    }

    private suspend fun updateRecord(prizeId: Int)
    {
        // save the image
        val imageHandler = ImageHandler()
        imageHandler.saveImage(bitmapImage,this, "Prize", prizeId)

        // save the updated prize
        val prizeTitle: String = binding?.appCompatEditTextPrizeTitle?.text.toString()
        val prizeEntity = PrizeEntity(prizeId, prizeTitle)

        prizeDao?.update(prizeEntity)
        // go back to overview of quiz page
        val intent = Intent(this@EditPrizeActivity, SetupQuizBasicsActivity::class.java)
        startActivity(intent)

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}