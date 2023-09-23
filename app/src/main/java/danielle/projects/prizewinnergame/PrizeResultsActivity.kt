package danielle.projects.prizewinnergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import danielle.projects.prizewinnergame.databinding.ActivityPrizeResultsBinding

class PrizeResultsActivity : AppCompatActivity() {

    private var binding: ActivityPrizeResultsBinding? = null

    private var prizes: ArrayList<ImageView?>? = null
    private var textViewPrizeResults: TextView? = null
    private var btnTakeTrade: Button? = null
    private var btnTakePrizes: Button? = null
    private var fromFinalQuestionActivity: Boolean? = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrizeResultsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        stopService(Intent(this, QuizTimerService::class.java))
        fromFinalQuestionActivity = intent.extras?.getBoolean(Constants.IS_FINAL_QUESTION_FLAG, false)
        loadResults()
    }

    private fun loadResults()
    {
        prizes = arrayListOf(binding?.imageViewPrize1,
            binding?.imageViewPrize2, binding?.imageViewPrize3, binding?.imageViewPrize4, binding?.imageViewPrize5, binding?.imageViewPrize6,
            binding?.imageViewPrize7, binding?.imageViewPrize8, binding?.imageViewPrize9)
        val prizeIds = Constants.PRIZE_MANAGER.getPrizes()
        val imageHandler = ImageHandler()
        for (i in 0 until prizeIds.size)
        {
            prizes!![i].let {
                imageHandler.loadImage(prizes!![i]!!, "Prize", prizeIds[i])
            }
        }
        // set a relevant results header and hide/change buttons as necessary
        textViewPrizeResults = binding?.textViewPrizeResults

        btnTakeTrade = binding?.btnTakeTrade
        btnTakePrizes = binding?.btnTakePrizes

        btnTakeTrade?.setOnClickListener{
            val intent = Intent(this, FinalQuizQuestionActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnTakePrizes?.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        if (fromFinalQuestionActivity != true)
        {
            if (prizeIds.size == 0)
            {
                textViewPrizeResults?.text = getString(R.string.won_no_prizes)
                btnTakePrizes?.text = getString(R.string.leave_without_prizes)
            }
            else if (prizeIds.size < prizes!!.size)
            {
                textViewPrizeResults?.text = getString(R.string.won_some_prizes)
            }
            else
            {
                textViewPrizeResults?.text = getString(R.string.won_all_prizes)
                btnTakeTrade?.visibility = View.GONE
                btnTakePrizes?.text = getString(R.string.finish)
            }
        }
        else
        {
            // there are only two possibilities here: you win them all or lose them all
            if (prizeIds.size == 0)
            {
                textViewPrizeResults?.text = getString(R.string.won_no_prizes_final)
            }
            else
            {
                textViewPrizeResults?.text = getString(R.string.won_all_prizes_final)
            }
            // regardless of the outcome, the user can now only finish the quiz
            btnTakeTrade?.visibility = View.GONE
            btnTakePrizes?.text = getString(R.string.finish)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}