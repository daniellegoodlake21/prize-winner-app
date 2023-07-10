package danielle.projects.prizewinnergame

import java.io.File
import java.lang.Exception

class QuizFileHandler(directory: String)
{
    private val directory : String
    init
    {
        this.directory = directory
    }

    fun saveQuiz(quizBasicInfo: QuizBasicInfo) : Boolean
    {
        val contents: String = quizBasicInfo.quizTitle + "\n" + quizBasicInfo.timeLimit.toString()
        try {
            val folder = File(directory, "//PrizeWinner")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val filename = "QuizBasicInfo.txt"
            val file = File(folder, filename)
            file.writeText(contents)
            return true
        }
        catch (e: Exception)
        {
            print("Error saving quiz basic info: ")
            e.printStackTrace()
            return false
        }
    }

    fun readQuiz(): QuizBasicInfo
    {
        try {
            val folder = File(directory,"//PrizeWinner")
            val file = File(folder, "QuizBasicInfo.txt")
            if (file.exists())
            {
                var quizTitle: String = file.readLines()[0]
                if (quizTitle == "")
                {
                    quizTitle = "Prize Winner"
                }
                val timeLimit: Int = file.readLines()[1].toInt()
                return QuizBasicInfo(quizTitle, timeLimit)
            }
        }
        catch (e: Exception)
        {
            println("Error loading quiz basic info")
            e.printStackTrace()
        }
        return QuizBasicInfo("Prize Winner", 60)
    }
}