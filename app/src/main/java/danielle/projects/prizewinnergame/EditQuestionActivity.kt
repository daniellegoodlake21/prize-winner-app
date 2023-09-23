package danielle.projects.prizewinnergame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import danielle.projects.prizewinnergame.databinding.ActivityEditQuestionBinding

class EditQuestionActivity : EditableImageActivity() {


    private var binding: ActivityEditQuestionBinding? = null

    private var btnSelectQuestionImage: Button? = null
    private var btnSaveQuestion: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditQuestionBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        imageView = findViewById(R.id.imageViewQuestionImage)
        btnSaveQuestion = binding?.btnSaveQuestion

        // load values
        val extras = intent.extras

        val questionId = extras?.getString(Constants.QUESTION_ID_EXTRA)?.toInt()

        val imageHandler = ImageHandler()
        val questionFileHandler = QuestionFileHandler(filesDir.path)
        val currentQuestion = questionFileHandler.readQuestion(questionId!!)

        val question = currentQuestion.question
        val textInputQuestionTitle: EditText = findViewById(R.id.appCompatEditTextQuestionTitle)
        textInputQuestionTitle.setText(question)

        val choiceA = currentQuestion.choiceA
        val textInputChoiceA: EditText = findViewById(R.id.appCompatEditTextQuestionChoiceA)
        textInputChoiceA.setText(choiceA)

        val choiceB = currentQuestion.choiceB
        val textInputChoiceB: EditText = findViewById(R.id.appCompatEditTextQuestionChoiceB)
        textInputChoiceB.setText(choiceB)

        val correctAnswer = currentQuestion.correctAnswer

        var correctAnswerString: String? = null
        if (correctAnswer == 0)
        {
            correctAnswerString = "A"
        }
        else if (correctAnswer == 1)
        {
            correctAnswerString = "B"
        }

        val textInputCorrectAnswer: EditText = findViewById(R.id.appCompatEditTextCorrectAnswer)
        textInputCorrectAnswer.setText(correctAnswerString)


        questionId.let {

            // load image if any otherwise default question mark image
            val imageLoaded = imageHandler.loadImage(imageView!!, "Question", it)
            if (!imageLoaded)
            {
                imageView!!.setImageResource(R.drawable.question_mark)
            }
        }
        // image selection handling on click
        btnSelectQuestionImage = binding?.btnSelectQuestionImage
        btnSelectQuestionImage?.setOnClickListener{
            handlePermissions()
        }

        // save question on click
        btnSaveQuestion?.setOnClickListener{
            imageHandler.saveImage(bitmapImage,this, "Question", questionId)

            // despite the warnings which say 'has already been looked up in this method'
            // it is necessary to re-find them in order to get the updated values
            val thisQuestion: String = binding?.appCompatEditTextQuestionTitle?.text.toString()
            val currentChoiceA: String = binding?.appCompatEditTextQuestionChoiceA?.text.toString()
            val currentChoiceB: String = binding?.appCompatEditTextQuestionChoiceB?.text.toString()
            val correctChoice: String = binding?.appCompatEditTextCorrectAnswer?.text.toString()
            var correct = 0
            if (correctChoice == "B")
            {
                correct = 1
            }
            val questionToSave = QuestionViewModel(
                    thisQuestion,
                    currentChoiceA,
                    currentChoiceB,
                    correct,
                    questionId
                )
            questionFileHandler.saveQuestion(questionToSave)
            // go back to overview of quiz page
            val intent = Intent(this, SetupQuizBasics::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}