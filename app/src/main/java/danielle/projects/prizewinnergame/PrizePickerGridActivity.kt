package danielle.projects.prizewinnergame

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import danielle.projects.prizewinnergame.databinding.ActivityPrizePickerGridBinding

class PrizePickerGridActivity : TimerDisplayActivity(), View.OnClickListener {

    private var binding: ActivityPrizePickerGridBinding? = null

    private var prizes : ArrayList<ImageView?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrizePickerGridBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        prizes = arrayListOf(binding?.imageViewPrize1,
        binding?.imageViewPrize2, binding?.imageViewPrize3, binding?.imageViewPrize4, binding?.imageViewPrize5, binding?.imageViewPrize6,
        binding?.imageViewPrize7, binding?.imageViewPrize8, binding?.imageViewPrize9)
        for (prize in prizes!!)
        {
            prize?.setOnClickListener(this)
        }
        greyOutPreviouslyPickedPrizes()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        return
    }
    private fun greyOutPreviouslyPickedPrizes()
    {
        for (i in 0 until Constants.NUMBER_OF_PRIZES)
        {
            if (Constants.PRIZE_MANAGER.prizePreviouslySelected(i))
            {
                prizes!![i]?.isClickable = false
                prizes!![i]?.setImageResource(R.drawable.unknown_prize_selected)
            }
        }
    }

    override fun onClick(v: View?) {
        if (v is ImageView)
        {
            when(v.id)
            {
                R.id.imageViewPrize1 -> Constants.PRIZE_MANAGER.winPrize(0)
                R.id.imageViewPrize2 -> Constants.PRIZE_MANAGER.winPrize(1)
                R.id.imageViewPrize3 -> Constants.PRIZE_MANAGER.winPrize(2)
                R.id.imageViewPrize4 -> Constants.PRIZE_MANAGER.winPrize(3)
                R.id.imageViewPrize5 -> Constants.PRIZE_MANAGER.winPrize(4)
                R.id.imageViewPrize6 -> Constants.PRIZE_MANAGER.winPrize(5)
                R.id.imageViewPrize7 -> Constants.PRIZE_MANAGER.winPrize(6)
                R.id.imageViewPrize8 -> Constants.PRIZE_MANAGER.winPrize(7)
                R.id.imageViewPrize9 -> Constants.PRIZE_MANAGER.winPrize(8)
                else -> return
            }
            val allPrizesWon = Constants.PRIZE_MANAGER.allPrizesSelected()
            if (allPrizesWon)
            {
                val intent = Intent(this, PrizeResultsActivity::class.java)
                intent.putExtra(Constants.IS_FINAL_QUESTION_FLAG, false)
                startActivity(intent)
            }
            else
            {
                val extras = intent.extras
                val nextQuestionId = extras?.getInt(Constants.QUESTION_ID_EXTRA, 0)
                val intent = Intent(this, QuizQuestionActivity::class.java)
                intent.putExtra(Constants.QUESTION_ID_EXTRA, nextQuestionId)
                startActivity(intent)
            }
            finish()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}