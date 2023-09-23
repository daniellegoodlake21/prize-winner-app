package danielle.projects.prizewinnergame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import danielle.projects.prizewinnergame.databinding.ActivitySetupQuizBasicsBinding


class SetupQuizBasics : AppCompatActivity() {

    private var binding: ActivitySetupQuizBasicsBinding? = null

    private var quizTitleInput: EditText? = null
    private var timeLimitInput: EditText? = null
    private var questionRecyclerView: RecyclerView? = null
    private var finalQuestionRecyclerView: RecyclerView? = null
    private var prizeRecyclerView: RecyclerView? = null
    private var btnSaveQuiz: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupQuizBasicsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // load quiz basic info
        val quizFileHandler = QuizFileHandler(filesDir.absolutePath)
        var quizBasicInfo = quizFileHandler.readQuiz()
        quizTitleInput = binding?.appCompatEditTextQuizTitle
        quizTitleInput?.setText(quizBasicInfo.quizTitle)
        timeLimitInput = binding?.appCompatEditTextTimeLimit
        timeLimitInput?.setText(quizBasicInfo.timeLimit.toString())


        // getting the recycler views by their ids
        questionRecyclerView = binding?.questionSetupRecyclerView
        finalQuestionRecyclerView = binding?.finalQuestionRecyclerView
        prizeRecyclerView = binding?.prizeSetupRecyclerView

        // this creates a vertical layout Manager
        questionRecyclerView?.layoutManager = LinearLayoutManager(this)
        prizeRecyclerView?.layoutManager = LinearLayoutManager(this)
        finalQuestionRecyclerView?.layoutManager = LinearLayoutManager(this)
        // ArrayLists of class ItemsViewModels
        val questionData = ArrayList<QuestionViewModel>()
        val finalQuestionData = ArrayList<QuestionViewModel>()
        val prizeData = ArrayList<PrizeViewModel>()
        // These loops will create 20 Views and 9 views respectively containing
        // the images with the count of view
        val questionFileHandler = QuestionFileHandler(filesDir.path)
        val prizeFileHandler = PrizeFileHandler(filesDir.path)
        for (i in 0 until Constants.NUMBER_OF_QUESTIONS) {
            val questionViewModel = questionFileHandler.readQuestion(i)
            questionData.add(questionViewModel)
        }
        for (i in 0 until Constants.NUMBER_OF_PRIZES)
        {
            val prizeViewModel = prizeFileHandler.readPrize(i)
            prizeData.add(prizeViewModel)
        }
        val finalQuestionViewModel = questionFileHandler.readQuestion(-1)
        finalQuestionData.add(finalQuestionViewModel)

        // This will pass the ArrayLists to the Adapters
        val questionAdapter = QuestionSetupAdapter(questionData)
        val prizeAdapter = PrizeSetupAdapter(prizeData)
        val finalQuestionAdapter = QuestionSetupAdapter(finalQuestionData)
        // Setting the Adapters with the recycler views
        questionRecyclerView!!.adapter = questionAdapter
        prizeRecyclerView!!.adapter = prizeAdapter
        finalQuestionRecyclerView!!.adapter = finalQuestionAdapter

        // handle save quiz on click
        btnSaveQuiz = binding?.btnSaveQuiz
        btnSaveQuiz?.setOnClickListener{
            val quizTitle =  binding?.appCompatEditTextQuizTitle?.text.toString()
            val timeLimit =  binding?.appCompatEditTextTimeLimit?.text.toString().toInt()
            quizBasicInfo = QuizBasicInfo(quizTitle, timeLimit)
            quizFileHandler.saveQuiz(quizBasicInfo)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}