package danielle.projects.prizewinnergame

import java.io.File
import java.lang.Exception

class PrizeFileHandler(directory: String)
{
    private val directory : String
    init
    {
        this.directory = directory
    }

    fun savePrize(prize: PrizeViewModel) : Boolean
    {
        val contents: String = prize.title
        try {
            val folder = File(directory, "//PrizeWinner//Prizes")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val filename = "PrizeFile_${prize.id}.txt"
            val file = File(folder, filename)
            file.writeText(contents)
            return true
        }
        catch (e: Exception)
        {
            print("Error saving prize: ")
            e.printStackTrace()
            return false
        }
    }

    fun readPrize(prizeId: Int): PrizeViewModel
    {
        try {
            val folder = File(directory,"//PrizeWinner//Prizes")
            val file = File(folder, "PrizeFile_$prizeId.txt")
            if (file.exists())
            {
                val title: String = file.readLines()[0]
                return PrizeViewModel(title, prizeId)
            }
        }
        catch (e: Exception)
        {
            println("Error loading prize")
            e.printStackTrace()
        }
        return PrizeViewModel("Default Prize Title", prizeId)
    }
}