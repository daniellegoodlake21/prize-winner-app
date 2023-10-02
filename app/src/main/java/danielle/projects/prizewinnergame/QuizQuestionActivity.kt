package danielle.projects.prizewinnergame
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import danielle.projects.prizewinnergame.databinding.ActivityQuizQuestionBinding
import kotlinx.coroutines.launch

open class QuizQuestionActivity : TimerDisplayActivity() {

    private var binding: ActivityQuizQuestionBinding? = null

    protected var imageHandler: ImageHandler? = null
    protected var correctAnswer: Int? = null
    open var questionId: Int? = null

    private var textViewQuestionTitle: TextView? = null
    private var textViewChoiceA: TextView? = null
    private var textViewChoiceB: TextView? = null
    private var imageViewQuestionImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        imageHandler = ImageHandler()
        val extras = intent.extras
        // if id has been overridden (as is the case for the final question) it does not need to be assigned here
        if (questionId == null)
        {
            questionId = extras?.getInt(Constants.QUESTION_ID_EXTRA, 0)
        }
        lifecycleScope.launch {
            loadQuestion()
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        return
    }
    protected open suspend fun loadQuestion()
    {
        val questionDao = (application as PrizeWinnerApp).database.questionDao()
        questionDao.fetchAllTimedQuestions().collect{ timedQuestions ->
            val question: QuestionEntity = timedQuestions[questionId!!]

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
            imageViewQuestionImage?.let {
                imageHandler!!.loadImage(imageViewQuestionImage!!, "Question", question.id)
            }

            correctAnswer = question.correctAnswer
        }

    }

    protected fun setScreenBackgroundColor(selectedAnswerIndex: Int): Boolean
    {
        val correct = correctAnswer == selectedAnswerIndex
        if (correct)
        {
            window.decorView.setBackgroundColor(getColor(R.color.green))
        }
        else
        {
            window.decorView.setBackgroundColor(getColor(R.color.warning_red))
        }
        return correct
    }
    private fun handleAnswer(selectedAnswerIndex: Int)
    {
        val correct = setScreenBackgroundColor(selectedAnswerIndex)
        object : CountDownTimer(500, 500)
        {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                lifecycleScope.launch {
                    val questionDao = (application as PrizeWinnerApp).database.questionDao()
                    questionDao.fetchTimedQuestionCount().collect{timedQuestionCount ->
                        questionId = if (questionId!! == timedQuestionCount - 1)
                        {
                            0 // cause the questions to loop once run out of questions
                        }
                        else
                        {
                            questionId?.plus(1)
                        }
                        if (correct)
                        {
                            val intent = Intent(this@QuizQuestionActivity, PrizePickerGridActivity::class.java)
                            intent.putExtra(Constants.QUESTION_ID_EXTRA, questionId)
                            startActivity(intent)
                            finish()
                        }
                        else
                        {
                            window.decorView.setBackgroundColor(getColor(R.color.white))
                            lifecycleScope.launch {
                                loadQuestion()
                            }

                        }
                    }
                }

            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}