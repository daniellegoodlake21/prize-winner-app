package danielle.projects.prizewinnergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class PrizeResultsActivity : AppCompatActivity() {

    private var fromFinalQuestionActivity: Boolean? = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prize_results)
        stopService(Intent(this, QuizTimerService::class.java))
        fromFinalQuestionActivity = intent.extras?.getBoolean(Constants.IS_FINAL_QUESTION_FLAG, false)
        loadResults()
    }

    private fun loadResults()
    {
        val prizes: ArrayList<ImageView> = arrayListOf(findViewById(R.id.imageViewPrize1),
            findViewById(R.id.imageViewPrize2), findViewById(R.id.imageViewPrize3),
            findViewById(R.id.imageViewPrize4), findViewById(R.id.imageViewPrize5),
            findViewById(R.id.imageViewPrize6), findViewById(R.id.imageViewPrize7),
            findViewById(R.id.imageViewPrize8), findViewById(R.id.imageViewPrize9))
        val prizeIds = Constants.PRIZE_MANAGER.getPrizes()
        val imageHandler = ImageHandler()
        for (i in 0 until prizeIds.size)
        {
            imageHandler.loadImage(prizes[i], "Prize", prizeIds[i])
        }
        // set a relevant results header and hide/change buttons as necessary
        val textViewPrizeResults: TextView = findViewById(R.id.textViewPrizeResults)

        val btnTakeTrade: Button = findViewById(R.id.btnTakeTrade)
        val btnTakePrizes: Button = findViewById(R.id.btnTakePrizes)

        btnTakeTrade.setOnClickListener{
            val intent = Intent(this, FinalQuizQuestionActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnTakePrizes.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        if (fromFinalQuestionActivity != true)
        {
            if (prizeIds.size == 0)
            {
                textViewPrizeResults.text = getString(R.string.won_no_prizes)
                btnTakePrizes.text = getString(R.string.leave_without_prizes)
            }
            else if (prizeIds.size < prizes.size)
            {
                textViewPrizeResults.text = getString(R.string.won_some_prizes)
            }
            else
            {
                textViewPrizeResults.text = getString(R.string.won_all_prizes)
                btnTakeTrade.visibility = View.GONE
                btnTakePrizes.text = getString(R.string.finish)
            }
        }
        else
        {
            // there are only two possibilities here: you win them all or lose them all
            if (prizeIds.size == 0)
            {
                textViewPrizeResults.text = getString(R.string.won_no_prizes_final)
            }
            else
            {
                textViewPrizeResults.text = getString(R.string.won_all_prizes_final)
            }
            // regardless of the outcome, the user can now only finish the quiz
            btnTakeTrade.visibility = View.GONE
            btnTakePrizes.text = getString(R.string.finish)
        }

    }

}