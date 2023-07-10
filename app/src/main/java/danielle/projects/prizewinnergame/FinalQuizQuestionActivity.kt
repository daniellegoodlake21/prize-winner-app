package danielle.projects.prizewinnergame
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView

class FinalQuizQuestionActivity : QuizQuestionActivity() {

    override var questionId: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_quiz_question)
        loadQuestion()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        return
    }
    override fun loadQuestion()
    {
        val question: QuestionViewModel = questionFileHandler!!.readQuestion(questionId!!)

        val textViewQuestionTitle: TextView = findViewById(R.id.textViewQuestionTitle)
        textViewQuestionTitle.text = question.question

        val textViewChoiceA: TextView = findViewById(R.id.textViewChoiceA)
        textViewChoiceA.text = question.choiceA
        textViewChoiceA.setOnClickListener{
            handleAnswer(0)
        }

        val textViewChoiceB: TextView = findViewById(R.id.textViewChoiceB)
        textViewChoiceB.text = question.choiceB
        textViewChoiceB.setOnClickListener{
            handleAnswer(1)
        }

        val imageViewQuestionImage: ImageView = findViewById(R.id.imageViewQuestionImage)
        imageHandler!!.loadImage(imageViewQuestionImage, "Question", questionId!!)

        correctAnswer = question.correctAnswer
    }

    private fun handleAnswer(selectedAnswerIndex: Int)
    {
        val correct = setScreenBackgroundColor(selectedAnswerIndex)
        val instance = this
        object : CountDownTimer(500, 500)
        {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val intent = Intent(instance, PrizeResultsActivity::class.java)
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

}