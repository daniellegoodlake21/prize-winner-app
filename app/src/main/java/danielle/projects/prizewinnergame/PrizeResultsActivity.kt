package danielle.projects.prizewinnergame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class PrizeResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prize_results)
        loadResults()
    }

    private fun loadResults()
    {
        val prizes: ArrayList<ImageView> = arrayListOf(findViewById(R.id.imageViewPrize1),
            findViewById(R.id.imageViewPrize2), findViewById(R.id.imageViewPrize3),
            findViewById(R.id.imageViewPrize4), findViewById(R.id.imageViewPrize5),
            findViewById(R.id.imageViewPrize6), findViewById(R.id.imageViewPrize7),
            findViewById(R.id.imageViewPrize8), findViewById(R.id.imageViewPrize9))
        val prizeIds = Constants.prizeManager.getPrizes()
        val imageHandler = ImageHandler()
        for (i in 0 until prizeIds.size)
        {
            imageHandler.loadImage(prizes[i], "Prize", prizeIds[i])
        }
        // set a relevant results header
        val textViewPrizeResults: TextView = findViewById(R.id.textViewPrizeResults)
        if (prizeIds.size == 0)
        {
            textViewPrizeResults.text = "You didn't win any prizes. But you can answer one final question with unlimited time to win them all!"
        }
        else if (prizeIds.size < prizes.size)
        {
            textViewPrizeResults.text = "You won some awesome prizes! But you can trade them for one final question in hopes of winning them all... but if you answer incorrectly, you lose the prizes you already have. Take the trade?"
        }
        else
        {
            textViewPrizeResults.text = "You have won all of these amazing prizes! Congratulations!"
        }
    }

}