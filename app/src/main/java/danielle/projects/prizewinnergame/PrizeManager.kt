package danielle.projects.prizewinnergame

object PrizeManager {

    private var prizes: MutableList<Int>? = null
    private var availablePrizes: MutableList<Int>? = null

    init {
        this.prizes = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        this.availablePrizes = this.prizes
        this.prizes!!.shuffle()
    }

    fun winPrize(prizeIndex: Int)
    {
        this.availablePrizes?.set(prizeIndex, -1)
    }

    fun prizePreviouslySelected(prizeIndex: Int): Boolean
    {
        if (availablePrizes != null && prizeIndex < this.availablePrizes!!.size)
        {
            return this.availablePrizes!![prizeIndex] == -1
        }
        return false
    }

}