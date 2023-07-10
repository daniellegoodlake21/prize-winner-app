package danielle.projects.prizewinnergame

import java.io.File
import java.lang.Exception

class QuestionFileHandler(directory: String)
{
    private val directory : String
    init
    {
        this.directory = directory
    }

    fun saveQuestion(question: QuestionViewModel) : Boolean
    {
        val contents: String = question.question + "\n" + question.choiceA + "\n" + question.choiceB + "\n" + question.correctAnswer.toString()
        try {
            val folder = File(directory, "//PrizeWinner//Questions")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            var filename = "QuestionFile_${question.questionId}.txt"
            if (question.questionId == -1)
            {
                filename = "QuestionFile_FinalQuestion.txt"
            }
            val file = File(folder, filename)
            file.writeText(contents)
            return true
        }
        catch (e: Exception)
        {
            print("Error saving question: ")
            e.printStackTrace()
            return false
        }
    }

    fun readQuestion(questionId: Int): QuestionViewModel
    {
        try {
            val folder = File(directory,"//PrizeWinner//Questions")
            var filename = "QuestionFile_$questionId.txt"
            if (questionId == -1)
            {
                filename = "QuestionFile_FinalQuestion.txt"
            }
            val file = File(folder, filename)
            if (file.exists())
            {
                val contents: List<String> = file.readLines()
                // get all question attributes
                val question = contents[0]
                val choiceA = contents[1]
                val choiceB = contents[2]
                val correctAnswer = contents[3]
                return QuestionViewModel(question, choiceA, choiceB, correctAnswer.toInt(), questionId)
            }
        }
        catch (e: Exception)
        {
            println("Error loading question")
            e.printStackTrace()
        }
        return QuestionViewModel("Default Question Title", "Default Choice A", "Default Choice B", 0, questionId)
    }
}