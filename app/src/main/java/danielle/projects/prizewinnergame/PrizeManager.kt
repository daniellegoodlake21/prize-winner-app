package danielle.projects.prizewinnergame

object PrizeManager {

    private var prizes: MutableList<Int>? = null
    private var availablePrizes: MutableList<Int>? = null

    init {
        this.prizes = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        this.availablePrizes = this.prizes!!.toMutableList()
        this.prizes!!.shuffle()
    }

    fun winPrize(prizeIndex: Int)
    {
        availablePrizes?.set(prizeIndex, -1)
    }

    fun prizePreviouslySelected(prizeIndex: Int): Boolean
    {
        if (availablePrizes != null && prizeIndex < availablePrizes!!.size)
        {
            return availablePrizes!![prizeIndex] == -1
        }
        return false
    }

    fun allPrizesSelected(): Boolean
    {
        if (availablePrizes != null) {
            for (prize in availablePrizes!!)
            {
                if (prize != -1)
                {
                    return false
                }
            }
        }
        return true
    }

    fun getPrizes(): MutableList<Int>
    {
        val wonPrizes = mutableListOf<Int>()
        if (availablePrizes != null && prizes != null)
        {
            for (i in 0 until Constants.NUMBER_OF_PRIZES)
            {
                if (availablePrizes!![i] == -1)
                {
                    val prizeId = prizes!![i]
                    wonPrizes.add(prizeId)
                }
            }
        }
        return wonPrizes
    }

    fun resetPrizes()
    {
        this.availablePrizes = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        this.prizes!!.shuffle()
    }

}