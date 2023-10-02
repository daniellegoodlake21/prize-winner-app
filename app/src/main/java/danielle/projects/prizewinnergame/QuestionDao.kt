package danielle.projects.prizewinnergame
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Insert
    suspend fun insert(questionEntity: QuestionEntity)

    @Update
    suspend fun update(questionEntity: QuestionEntity)

    @Delete
    suspend fun delete(questionEntity: QuestionEntity)

    @Query("SELECT * FROM `question-table` WHERE `final-question` = 0")
    fun fetchAllTimedQuestions(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM `question-table`")
    fun fetchAllQuestions(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM `question-table` WHERE id=:id")
    fun fetchQuestionById(id:Int):Flow<QuestionEntity>

    @Query("SELECT * FROM `question-table` WHERE `final-question` = 1")
    fun fetchFinalQuestion():Flow<List<QuestionEntity>>

    @Query("INSERT INTO `question-table` (question, `choice-a`, `choice-b`, `correct-answer`, `final-question`) VALUES ('Default Question Title', 'Default Choice A', 'Default Choice B', 0, 0)")
    suspend fun insertDefaultQuestion()

    @Query("INSERT INTO `question-table` (question, `choice-a`, `choice-b`, `correct-answer`, `final-question`) VALUES ('Default Question Title', 'Default Choice A', 'Default Choice B', 0, 1)")
    suspend fun insertDefaultFinalQuestion()

    @Query("SELECT COUNT(id) FROM `question-table` WHERE `final-question` = 0")
    fun fetchTimedQuestionCount():Flow<Int>

    @Query("SELECT COUNT(id) FROM `question-table` WHERE `final-question` = 1")
    fun fetchFinalQuestionCount():Flow<Int>
}