package danielle.projects.prizewinnergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSetupQuiz: Button = findViewById(R.id.btnSetupQuiz)
        btnSetupQuiz.setOnClickListener{
            val intent = Intent(this, SetupQuizBasics::class.java)
            startActivity(intent)
        }

        val btnPlayQuiz: Button = findViewById(R.id.btnPlayQuiz)
        btnPlayQuiz.setOnClickListener{
            val intent = Intent(this, QuizInstructions::class.java)
            startActivity(intent)
        }

        // set the quiz title from setup quiz
        val quizFileHandler = QuizFileHandler(filesDir.absolutePath)
        val quizTitle = quizFileHandler.readQuiz().quizTitle

        val textViewQuizTitle: TextView = findViewById(R.id.textViewQuizTitle)
        textViewQuizTitle.text = quizTitle
    }
}