package danielle.projects.prizewinnergame

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QuestionEntity::class, PrizeEntity::class, QuizEntity::class], version = 4)
abstract class PrizeWinnerDatabase: RoomDatabase() {

    abstract fun questionDao():QuestionDao
    abstract fun prizeDao():PrizeDao
    abstract fun quizDao():QuizDao

    companion object{

        @Volatile
        private var INSTANCE: PrizeWinnerDatabase? = null

        fun getInstance(context: Context): PrizeWinnerDatabase {
            synchronized(this){

                var instance = INSTANCE

                if (instance==null)
                {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PrizeWinnerDatabase::class.java,
                        "prize_winner_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}