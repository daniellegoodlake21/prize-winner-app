package danielle.projects.prizewinnergame
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


open class TimerDisplayActivity : AppCompatActivity() {

    protected fun startTimer()
    {
        lifecycleScope.launch {
            val quizDao = (application as PrizeWinnerApp).database.quizDao()
            quizDao.fetchAllQuizzes().collect{ quizAsList ->
                val timeLimit = quizAsList[0].timer
                val serviceIntent = Intent(this@TimerDisplayActivity, QuizTimerService::class.java)
                serviceIntent.putExtra(Constants.TIME_LIMIT_IN_SECONDS, timeLimit)
                startService(serviceIntent)
            }
        }

    }
    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateTimer(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(broadcastReceiver, IntentFilter(QuizTimerService.COUNTDOWN_BROADCAST_INTENT))
        registerReceiver(broadcastReceiver, IntentFilter(QuizTimerService.COUNTDOWN_FINISH_BROADCAST_INTENT))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
    }

    override fun onStop()
    {
        try {
            unregisterReceiver(broadcastReceiver)
        }
        catch (e: Exception) {
            // onPause could have stopped receiver already
        }
        super.onStop()
    }

    override fun onDestroy() {
        stopService(Intent(this, QuizTimerService::class.java))
        super.onDestroy()
    }

    private fun updateTimer(intent: Intent)
    {
        if (intent.action == QuizTimerService.COUNTDOWN_BROADCAST_INTENT)
        {
            val textViewTimeLimit: TextView = findViewById(R.id.textViewTimeLimit)
            val secondsRemaining = intent.getIntExtra(Constants.SECONDS_REMAINING_EXTRA, 0)
            textViewTimeLimit.text = secondsRemaining.toString()
        }
        else if (intent.action == QuizTimerService.COUNTDOWN_FINISH_BROADCAST_INTENT)
        {
            val resultIntent = Intent(this, PrizeResultsActivity::class.java)
            intent.putExtra(Constants.IS_FINAL_QUESTION_FLAG, false)
            startActivity(resultIntent)
            finish()
        }
    }
}