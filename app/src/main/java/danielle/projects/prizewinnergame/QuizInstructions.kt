package danielle.projects.prizewinnergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class QuizInstructions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_instructions)

        val btnNext: Button = findViewById(R.id.btnNext)
        btnNext.setOnClickListener{
            val intent = Intent(this, PrizeShowcaseActivity::class.java)
            startActivity(intent)
        }

        val btnExit: Button = findViewById(R.id.btnExit)
        btnExit.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}