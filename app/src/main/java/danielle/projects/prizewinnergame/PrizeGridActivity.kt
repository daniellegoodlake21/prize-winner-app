package danielle.projects.prizewinnergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PrizeGridActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prize_grid)

        val btnBack: Button = findViewById(R.id.btnBack)
        btnBack.setOnClickListener{
            val intent = Intent(this, PrizeShowcaseActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnStartQuiz: Button = findViewById(R.id.btnStartQuiz)
        btnStartQuiz.setOnClickListener{
            val intent = Intent(this, QuizQuestionActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}