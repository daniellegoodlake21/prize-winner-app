package danielle.projects.prizewinnergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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
    }
}