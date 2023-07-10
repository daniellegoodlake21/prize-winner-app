package danielle.projects.prizewinnergame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class SetupQuizBasics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_quiz_basics)


        // load quiz basic info
        val quizFileHandler = QuizFileHandler(filesDir.absolutePath)
        var quizBasicInfo = quizFileHandler.readQuiz()
        val quizTitleInput = findViewById<EditText>(R.id.appCompatEditTextQuizTitle)
        quizTitleInput.setText(quizBasicInfo.quizTitle)
        val timeLimitInput = findViewById<EditText>(R.id.appCompatEditTextTimeLimit)
        timeLimitInput.setText(quizBasicInfo.timeLimit.toString())


        // getting the recycler views by their ids
        val questionRecyclerView = findViewById<RecyclerView>(R.id.question_setup_recycler_view)
        val finalQuestionRecyclerView = findViewById<RecyclerView>(R.id.final_question_recycler_view)
        val prizeRecyclerView = findViewById<RecyclerView>(R.id.prize_setup_recycler_view)

        // this creates a vertical layout Manager
        questionRecyclerView.layoutManager = LinearLayoutManager(this)
        prizeRecyclerView.layoutManager = LinearLayoutManager(this)
        finalQuestionRecyclerView.layoutManager = LinearLayoutManager(this)
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

        // This will pass the ArrayLists to our Adapters
        val questionAdapter = QuestionSetupAdapter(questionData)
        val prizeAdapter = PrizeSetupAdapter(prizeData)
        val finalQuestionAdapter = QuestionSetupAdapter(finalQuestionData)
        // Setting the Adapters with the recycler views
        questionRecyclerView.adapter = questionAdapter
        prizeRecyclerView.adapter = prizeAdapter
        finalQuestionRecyclerView.adapter = finalQuestionAdapter

        // handle save quiz on click
        val btnSaveQuiz = findViewById<Button>(R.id.btnSaveQuiz)
        btnSaveQuiz.setOnClickListener{
            val quizTitle: String = findViewById<EditText>(R.id.appCompatEditTextQuizTitle).text.toString()
            val timeLimit: Int = findViewById<EditText>(R.id.appCompatEditTextTimeLimit).text.toString().toInt()
            quizBasicInfo = QuizBasicInfo(quizTitle, timeLimit)
            quizFileHandler.saveQuiz(quizBasicInfo)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}