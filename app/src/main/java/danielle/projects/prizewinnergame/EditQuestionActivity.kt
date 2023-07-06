package danielle.projects.prizewinnergame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditQuestionActivity : EditableImageActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_question)
        imageView = findViewById(R.id.imageViewQuestionImage)
        val btnSaveQuestion: Button = findViewById(R.id.btnSaveQuestion)

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
        val btnSelectQuestionImage: Button = findViewById(R.id.btnSelectQuestionImage)
        btnSelectQuestionImage.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            getContent.launch("image/*")
        }

        // save question on click
        btnSaveQuestion.setOnClickListener{
            imageHandler.saveImage(bitmapImage,this, "Question", questionId)
            val q: String = findViewById<EditText>(R.id.appCompatEditTextQuestionTitle).text.toString()
            val a: String = findViewById<EditText>(R.id.appCompatEditTextQuestionChoiceA).text.toString()
            val b: String = findViewById<EditText>(R.id.appCompatEditTextQuestionChoiceB).text.toString()
            val c: String = findViewById<EditText>(R.id.appCompatEditTextCorrectAnswer).text.toString()
            var correct = 0
            if (c == "B")
            {
                correct = 1
            }
            val questionToSave = QuestionViewModel(
                    q,
                    a,
                    b,
                    correct,
                    questionId
                )
            questionFileHandler.saveQuestion(questionToSave)
            // go back to overview of quiz page
            val intent = Intent(this, SetupQuizBasics::class.java)
            startActivity(intent)
        }

        val permissions = Array(2) { android.Manifest.permission.WRITE_EXTERNAL_STORAGE; android.Manifest.permission.READ_EXTERNAL_STORAGE  }
        requestPermissions(permissions,1024)
    }
}