package danielle.projects.prizewinnergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class PrizePickerGridActivity : AppCompatActivity(), View.OnClickListener {

    private var prizes : ArrayList<ImageView>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prize_picker_grid)
        prizes = arrayListOf(findViewById(R.id.imageViewPrize1),
        findViewById(R.id.imageViewPrize2), findViewById(R.id.imageViewPrize3),
        findViewById(R.id.imageViewPrize4), findViewById(R.id.imageViewPrize5),
        findViewById(R.id.imageViewPrize6), findViewById(R.id.imageViewPrize7),
        findViewById(R.id.imageViewPrize8), findViewById(R.id.imageViewPrize9))
        for (prize in prizes!!)
        {
            prize.setOnClickListener(this)
        }
        greyOutPreviouslyPickedPrizes()
    }

    private fun greyOutPreviouslyPickedPrizes()
    {
        for (i in 0 until Constants.NUMBER_OF_PRIZES)
        {
            if (Constants.prizeManager.prizePreviouslySelected(i))
            {
                prizes!![i].setImageResource(R.drawable.unknown_prize_selected)
                prizes!![i].isClickable = false
            }
        }
    }

    override fun onClick(v: View?) {
        if (v is ImageView)
        {
            when(v.id)
            {
                R.id.imageViewPrize1 -> Constants.prizeManager.winPrize(0)
                R.id.imageViewPrize2 -> Constants.prizeManager.winPrize(1)
                R.id.imageViewPrize3 -> Constants.prizeManager.winPrize(2)
                R.id.imageViewPrize4 -> Constants.prizeManager.winPrize(3)
                R.id.imageViewPrize5 -> Constants.prizeManager.winPrize(4)
                R.id.imageViewPrize6 -> Constants.prizeManager.winPrize(5)
                R.id.imageViewPrize7 -> Constants.prizeManager.winPrize(6)
                R.id.imageViewPrize8 -> Constants.prizeManager.winPrize(7)
                R.id.imageViewPrize9 -> Constants.prizeManager.winPrize(8)
                else -> return
            }
            val extras = intent.extras
            val nextQuestionId = extras?.getInt(Constants.QUESTION_ID_EXTRA, 0)
            val intent = Intent(this, QuizQuestionActivity::class.java)
            intent.putExtra(Constants.QUESTION_ID_EXTRA, nextQuestionId)
            startActivity(intent)
            finish()
        }
    }
}