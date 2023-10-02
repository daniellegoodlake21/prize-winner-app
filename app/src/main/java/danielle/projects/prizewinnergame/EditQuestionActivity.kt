package danielle.projects.prizewinnergame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import danielle.projects.prizewinnergame.databinding.ActivityEditQuestionBinding
import kotlinx.coroutines.launch

class EditQuestionActivity : EditableImageActivity() {


    private var binding: ActivityEditQuestionBinding? = null

    private var questionDao: QuestionDao? = null
    private var isFinalQuestion: Boolean = false
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

        lifecycleScope.launch {
            loadRecord(questionId!!)
        }


        questionId.let {imageIndex ->

            // load image if any otherwise default question mark image
            val imageLoaded = imageHandler.loadImage(imageView!!, "Question", imageIndex!!)
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
            lifecycleScope.launch {
                Toast.makeText(this@EditQuestionActivity, "Updating Record $questionId", Toast.LENGTH_LONG).show()
                updateRecord(questionId!!)
            }
        }

    }
    private suspend fun loadRecord(questionId: Int)
    {
        // load the question
        Toast.makeText(this@EditQuestionActivity, "Entering Record $questionId", Toast.LENGTH_LONG).show()

        questionDao = (application as PrizeWinnerApp).database.questionDao()
        questionDao?.fetchQuestionById(questionId)?.collect{ question ->
            runOnUiThread {
                binding?.appCompatEditTextQuestionTitle?.setText(question.question)
                binding?.appCompatEditTextQuestionChoiceA?.setText(question.choiceA)
                binding?.appCompatEditTextQuestionChoiceB?.setText(question.choiceB)
                val correctAnswer = if (question.correctAnswer == 0)  "A" else "B"
                binding?.appCompatEditTextCorrectAnswer?.setText(correctAnswer)
            }
            isFinalQuestion = question.finalQuestion
        }

   }

    private suspend fun updateRecord(questionId: Int)
    {
        // save the image
        val imageHandler = ImageHandler()
        imageHandler.saveImage(bitmapImage,this, "Question", questionId)

        // save the updated question
        val currentQuestion: String = binding?.appCompatEditTextQuestionTitle?.text.toString()
        val currentChoiceA: String = binding?.appCompatEditTextQuestionChoiceA?.text.toString()
        val currentChoiceB: String = binding?.appCompatEditTextQuestionChoiceB?.text.toString()
        val correctChoice: String = binding?.appCompatEditTextCorrectAnswer?.text.toString()
        var correct = 0
        if (correctChoice == "B")
        {
            correct = 1
        }
        val questionEntity = QuestionEntity(questionId, currentQuestion, currentChoiceA, currentChoiceB, correct, isFinalQuestion)
        questionDao?.update(questionEntity)
        // go back to overview of quiz page
        val intent = Intent(this@EditQuestionActivity, SetupQuizBasicsActivity::class.java)
        startActivity(intent)
        finish()

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}