package danielle.projects.prizewinnergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import danielle.projects.prizewinnergame.databinding.ActivityQuizInstructionsBinding

class QuizInstructions : AppCompatActivity() {

    private var binding: ActivityQuizInstructionsBinding? = null

    private var btnNext: Button? = null
    private var btnExit: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizInstructionsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        btnNext =  binding?.btnNext
        btnNext?.setOnClickListener{
            val intent = Intent(this, PrizeShowcaseActivity::class.java)
            startActivity(intent)
        }

        btnExit = binding?.btnExit
        btnExit?.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}