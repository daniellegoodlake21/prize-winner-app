package danielle.projects.prizewinnergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import danielle.projects.prizewinnergame.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private var btnSetupQuiz: Button? = null
    private var btnPlayQuiz: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        btnSetupQuiz = binding?.btnSetupQuiz
        btnSetupQuiz?.setOnClickListener{
            val intent = Intent(this, SetupQuizBasicsActivity::class.java)
            startActivity(intent)
        }

        btnPlayQuiz = binding?.btnPlayQuiz
        btnPlayQuiz?.setOnClickListener{
            val intent = Intent(this, QuizInstructions::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            // set the quiz title from setup quiz
            val quizDao = (application as PrizeWinnerApp).database.quizDao()
            quizDao.fetchQuizCount().collect { quizCount ->
                if (quizCount == 0) {
                    quizDao.insertDefaultQuiz()
                }
                quizDao.fetchAllQuizzes().collect { quizAsList ->
                    val quizTitle = quizAsList[0].title
                    binding?.textViewQuizTitle?.text = quizTitle
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}