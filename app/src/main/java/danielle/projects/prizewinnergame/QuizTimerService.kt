package danielle.projects.prizewinnergame

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder

class QuizTimerService: Service()
{

    var broadcastIntent = Intent(COUNTDOWN_BROADCAST_INTENT)
    var broadcastFinishIntent = Intent(COUNTDOWN_FINISH_BROADCAST_INTENT)
    var duration: Int = 0

    private lateinit var timer: CountDownTimer

    private fun createTimer(): CountDownTimer = object : CountDownTimer((duration * 1000).toLong(), duration.toLong())
    {
        override fun onTick(millisUntilFinished: Long)
        {
            broadcastIntent.putExtra(Constants.SECONDS_REMAINING_EXTRA, (millisUntilFinished / 1000).toInt())
            sendBroadcast(broadcastIntent)
        }

        override fun onFinish()
        {
            sendBroadcast(broadcastFinishIntent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {
        if (intent != null)
        {
            if (intent.extras != null)
            {
                duration = intent.extras!!.getInt(Constants.TIME_LIMIT_IN_SECONDS)
                timer = createTimer()
                timer.start()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object
    {
        const val COUNTDOWN_BROADCAST_INTENT = "danielle.projects.prizewinner.countdown_br"
        const val COUNTDOWN_FINISH_BROADCAST_INTENT = "danielle.projects.prizewinner.countdown_finish_br"
    }

}