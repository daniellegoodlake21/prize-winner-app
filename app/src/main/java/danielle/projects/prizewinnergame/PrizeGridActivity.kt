package danielle.projects.prizewinnergame
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import danielle.projects.prizewinnergame.databinding.ActivityPrizeGridBinding

class PrizeGridActivity : TimerDisplayActivity() {

    private var binding: ActivityPrizeGridBinding? = null

    private var btnBack: Button? = null
    private var btnStartQuiz: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrizeGridBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        btnBack = binding?.btnBack
        btnBack?.setOnClickListener{
            val intent = Intent(this, PrizeShowcaseActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnStartQuiz = binding?.btnStartQuiz
        btnStartQuiz?.setOnClickListener{
            Constants.PRIZE_MANAGER.resetPrizes() // in case the quiz has been played before
            // without exiting the app
            val intent = Intent(this, QuizQuestionActivity::class.java)
            intent.putExtra(Constants.QUESTION_ID_EXTRA, 0) // start with the first
            // question
            startTimer()
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}