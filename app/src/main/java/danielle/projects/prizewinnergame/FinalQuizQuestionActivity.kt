package danielle.projects.prizewinnergame
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import danielle.projects.prizewinnergame.databinding.ActivityFinalQuizQuestionBinding

class FinalQuizQuestionActivity : QuizQuestionActivity() {

    private var binding: ActivityFinalQuizQuestionBinding? = null

    private var textViewQuestionTitle: TextView? = null
    private var textViewChoiceA: TextView? = null
    private var textViewChoiceB: TextView? = null
    private var imageViewQuestionImage: ImageView? = null
    override var questionId: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFinalQuizQuestionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        questionId = -1
        loadQuestion()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        return
    }
    override fun loadQuestion()
    {
        val question: QuestionViewModel = questionFileHandler!!.readQuestion(questionId!!)

        textViewQuestionTitle = binding?.textViewQuestionTitle
        textViewQuestionTitle?.text = question.question

        textViewChoiceA = binding?.textViewChoiceA
        textViewChoiceA?.text = question.choiceA
        textViewChoiceA?.setOnClickListener{
            handleAnswer(0)
        }

        textViewChoiceB = binding?.textViewChoiceB
        textViewChoiceB?.text = question.choiceB
        textViewChoiceB?.setOnClickListener{
            handleAnswer(1)
        }

        imageViewQuestionImage = binding?.imageViewQuestionImage
        imageHandler!!.loadImage(imageViewQuestionImage!!, "Question", questionId!!)

        correctAnswer = question.correctAnswer
    }

    private fun handleAnswer(selectedAnswerIndex: Int)
    {
        val correct = setScreenBackgroundColor(selectedAnswerIndex)
        object : CountDownTimer(500, 500)
        {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val intent = Intent(this@FinalQuizQuestionActivity, PrizeResultsActivity::class.java)
                if (correct)
                {
                    for (i in 0 until Constants.NUMBER_OF_PRIZES)
                    {
                        Constants.PRIZE_MANAGER.winPrize(i) // ensure all prizes are won
                    }
                }
                else
                {
                    Constants.PRIZE_MANAGER.resetPrizes()
                }
                intent.putExtra(Constants.IS_FINAL_QUESTION_FLAG, true)
                startActivity(intent)
                finish()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}