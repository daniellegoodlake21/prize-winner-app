package danielle.projects.prizewinnergame

import android.app.Application

class PrizeWinnerApp: Application() {

    val database by lazy{
        PrizeWinnerDatabase.getInstance(this)
    }

}