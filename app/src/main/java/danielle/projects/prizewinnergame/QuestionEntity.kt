package danielle.projects.prizewinnergame

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question-table")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var question: String = "",
    @ColumnInfo(name="choice-a")
    var choiceA: String = "",
    @ColumnInfo(name="choice-b")
    var choiceB: String = "",
    @ColumnInfo(name="correct-answer")
    var correctAnswer: Int = 0,
    @ColumnInfo(name="final-question")
    var finalQuestion: Boolean = false
)
