package danielle.projects.prizewinnergame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import danielle.projects.prizewinnergame.databinding.ActivitySetupQuizBasicsBinding
import kotlinx.coroutines.launch


class SetupQuizBasicsActivity : AppCompatActivity() {

    private var binding: ActivitySetupQuizBasicsBinding? = null

    private var quizTitleInput: EditText? = null
    private var timeLimitInput: EditText? = null
    private var questionRecyclerView: RecyclerView? = null
    private var finalQuestionRecyclerView: RecyclerView? = null
    private var prizeRecyclerView: RecyclerView? = null
    private var btnSaveQuiz: Button? = null
    private var btnAddNewQuestion: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupQuizBasicsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // load quiz basic info
        lifecycleScope.launch {
            loadQuizBasicInfo()
        }


        // getting the recycler views by their ids
        questionRecyclerView = binding?.questionSetupRecyclerView
        prizeRecyclerView = binding?.prizeSetupRecyclerView
        finalQuestionRecyclerView = binding?.finalQuestionSetupRecyclerView

        // create vertical layout managers
        questionRecyclerView?.layoutManager = LinearLayoutManager(this)
        prizeRecyclerView?.layoutManager = LinearLayoutManager(this)

        questionRecyclerView?.adapter = QuestionSetupAdapter(arrayListOf(), this)
        finalQuestionRecyclerView?.adapter = QuestionSetupAdapter(arrayListOf(), this)
        prizeRecyclerView?.adapter = PrizeSetupAdapter(emptyList())

        setupQuestions()
        setupPrizes()

        // handle save quiz on click
        btnSaveQuiz = binding?.btnSaveQuiz
        btnSaveQuiz?.setOnClickListener{
            val quizTitle =  binding?.appCompatEditTextQuizTitle?.text.toString()
            val timeLimit =  binding?.appCompatEditTextTimeLimit?.text.toString().toInt()
            val quizDao = (application as PrizeWinnerApp).database.quizDao()
            lifecycleScope.launch {
                quizDao.update(QuizEntity(1, quizTitle, timeLimit))
                val intent = Intent(this@SetupQuizBasicsActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
        // handle add new question on click
        btnAddNewQuestion = binding?.btnAddNewQuestion
        btnAddNewQuestion?.setOnClickListener{
            lifecycleScope.launch {
                insertRecord()
            }
        }
    }

    private suspend fun loadQuizBasicInfo()
    {
        val quizDao = (application as PrizeWinnerApp).database.quizDao()
        quizDao.fetchQuizCount().collect{ quizCount ->
            if (quizCount == 0)
            {
                quizDao.insertDefaultQuiz()
            }
            quizDao.fetchAllQuizzes().collect{ quizAsList ->
                quizTitleInput = binding?.appCompatEditTextQuizTitle
                quizTitleInput?.setText(quizAsList[0].title)
                timeLimitInput = binding?.appCompatEditTextTimeLimit
                timeLimitInput?.setText(quizAsList[0].timer.toString())
            }
        }

    }
    suspend fun deleteRecord(context: Context, questionEntity: QuestionEntity)
    {
        val questionDao = (application as PrizeWinnerApp).database.questionDao()
        questionDao.delete(questionEntity)
        questionDao.fetchAllTimedQuestions().collect{ timedQuestions ->
            questionRecyclerView?.adapter = QuestionSetupAdapter(timedQuestions as ArrayList<QuestionEntity>, this@SetupQuizBasicsActivity)
            (questionRecyclerView?.parent as CardView).height.minus(70)
        }
        val imageHandler = ImageHandler()
        imageHandler.deleteImage(questionEntity.id, context)
    }
    private suspend fun insertRecord()
    {
        val questionDao = (application as PrizeWinnerApp).database.questionDao()
        questionDao.insertDefaultQuestion()
        questionDao.fetchAllTimedQuestions().collect{ timedQuestions ->
            questionRecyclerView?.adapter = QuestionSetupAdapter(timedQuestions as ArrayList<QuestionEntity>, this)
            (questionRecyclerView?.parent as CardView).height.plus(70)
        }
    }
    private fun setupPrizes() {
        val prizeDao = (application as PrizeWinnerApp).database.prizeDao()
        lifecycleScope.launch {
            // insert prizes until there are 9 default prizes, if not already generated
            prizeDao.fetchPrizeCount().collect{prizeCount ->
                if (prizeCount < Constants.NUMBER_OF_PRIZES)
                {
                    for (i in prizeCount until Constants.NUMBER_OF_PRIZES)
                    {
                        prizeDao.insertDefaultPrize()
                    }
                }
                prizeDao.fetchAllPrizes().collect{prizes ->
                    prizeRecyclerView?.adapter = PrizeSetupAdapter(prizes)
                }
            }

        }
    }

    private fun setupQuestions() {
        val questionDao = (application as PrizeWinnerApp).database.questionDao()
        lifecycleScope.launch {

            // insert timed questions until there are 9 (the minimum and default) if not 9 minimum already
            questionDao.fetchTimedQuestionCount().collect{ timedQuestionCount ->
                if (timedQuestionCount < Constants.NUMBER_OF_TIMED_QUESTIONS)
                {
                    for (i in timedQuestionCount until Constants.NUMBER_OF_TIMED_QUESTIONS)
                    {
                        questionDao.insertDefaultQuestion()
                    }
                }
                questionDao.fetchAllTimedQuestions().collect{ timedQuestions ->
                    questionRecyclerView?.adapter = QuestionSetupAdapter(timedQuestions as ArrayList<QuestionEntity>, this@SetupQuizBasicsActivity)
                }
            }

        }

        lifecycleScope.launch{
            // insert final/trade-off question if not there already
            questionDao.fetchFinalQuestionCount().collect{ finalQuestionCount ->
                if (finalQuestionCount == 0)
                {
                    questionDao.insertDefaultFinalQuestion()
                }
                questionDao.fetchFinalQuestion().collect { newFinalQuestionAsList ->
                    finalQuestionRecyclerView?.adapter = QuestionSetupAdapter(newFinalQuestionAsList as ArrayList<QuestionEntity>, this@SetupQuizBasicsActivity)
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}