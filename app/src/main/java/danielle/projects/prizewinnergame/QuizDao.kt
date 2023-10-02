package danielle.projects.prizewinnergame
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Insert
    suspend fun insert(quizEntity: QuizEntity)

    @Update
    suspend fun update(quizEntity: QuizEntity)

    @Delete
    suspend fun delete(quizEntity: QuizEntity)

    @Query("SELECT * FROM `quiz-table`")
    fun fetchAllQuizzes(): Flow<List<QuizEntity>>

    @Query("SELECT COUNT(`quiz-id`) FROM `quiz-table`")
    fun fetchQuizCount():Flow<Int>

    @Query("INSERT INTO `quiz-table` (title, timer) VALUES ('Prize Winner', 60)")
    suspend fun insertDefaultQuiz()

}