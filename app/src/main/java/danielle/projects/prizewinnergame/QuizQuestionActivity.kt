package danielle.projects.prizewinnergame
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView

class QuizQuestionActivity : TimerDisplayActivity() {

    private var questionFileHandler: QuestionFileHandler? = null
    private var imageHandler: ImageHandler? = null
    private var correctAnswer: Int? = null
    private var questionId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)
        questionFileHandler = QuestionFileHandler(filesDir.absolutePath)
        imageHandler = ImageHandler()
        val extras = intent.extras
        questionId = extras?.getInt(Constants.QUESTION_ID_EXTRA, 0)
        loadQuestion()
    }

    private fun loadQuestion()
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
        val correct = correctAnswer == selectedAnswerIndex
        if (correct)
        {
            window.decorView.setBackgroundColor(getColor(R.color.green))
        }
        else
        {
            window.decorView.setBackgroundColor(getColor(R.color.warning_red))
        }
        val instance = this
        object : CountDownTimer(500, 500)
        {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                questionId = if (questionId!! == Constants.NUMBER_OF_QUESTIONS - 1)
                {
                    0 // cause the questions to loop once run out of questions
                } else {
                    questionId?.plus(1)
                }
                if (correct)
                {
                    val intent = Intent(instance, PrizePickerGridActivity::class.java) // instance used
                    // instead of 'this' keyword because 'this' now refers to the count down timer object
                    intent.putExtra(Constants.QUESTION_ID_EXTRA, questionId)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    window.decorView.setBackgroundColor(getColor(R.color.white))
                    loadQuestion()
                }
            }
        }.start()
    }

}