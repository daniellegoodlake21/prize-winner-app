package danielle.projects.prizewinnergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import danielle.projects.prizewinnergame.databinding.ActivityMainBinding

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
            val intent = Intent(this, SetupQuizBasics::class.java)
            startActivity(intent)
        }

        btnPlayQuiz = binding?.btnPlayQuiz
        btnPlayQuiz?.setOnClickListener{
            val intent = Intent(this, QuizInstructions::class.java)
            startActivity(intent)
        }

        // set the quiz title from setup quiz
        val quizFileHandler = QuizFileHandler(filesDir.absolutePath)
        val quizTitle = quizFileHandler.readQuiz().quizTitle

        val textViewQuizTitle: TextView = findViewById(R.id.textViewQuizTitle)
        textViewQuizTitle.text = quizTitle
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}