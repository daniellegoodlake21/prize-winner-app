package danielle.projects.prizewinnergame

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz-table")
data class QuizEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="quiz-id")
    var quizId: Int = 0,
    var title: String = "",
    var timer: Int = 0
)
