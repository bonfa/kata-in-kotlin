package it.fbonfadelli.playground.bowling

sealed interface Frame {
    fun getFirstRollScore(): Int
    fun getSecondRollScore(): Int?
}

object Strike : Frame {
    fun getScore(nextFrame: Frame, secondNextFrame: Frame?): Int =
        10 + nextFrame.getFirstRollScore() + (nextFrame.getSecondRollScore() ?: secondNextFrame?.getFirstRollScore()
        ?: 0)

    override fun getFirstRollScore(): Int = 10

    override fun getSecondRollScore(): Int? = null
}

class Spare(private val firstRollScore: Int) : Frame {
    fun getScore(nextFrame: Frame): Int =
        10 + nextFrame.getFirstRollScore()

    override fun getFirstRollScore(): Int =
        firstRollScore

    override fun getSecondRollScore(): Int = 10 - firstRollScore
}

class NorStrikeNorSpare(
    private val firstRollScore: Int,
    private val secondRollScore: Int
) : Frame {
    fun getScore(): Int =
        firstRollScore + secondRollScore

    override fun getFirstRollScore(): Int =
        firstRollScore

    override fun getSecondRollScore(): Int =
        secondRollScore
}

class LastFrame(
    private val firstRollScore: Int,
    private val secondRollScore: Int,
    private val thirdRollScore: Int = 0,
) : Frame {
    fun getScore(): Int =
        firstRollScore + secondRollScore + thirdRollScore

    override fun getFirstRollScore(): Int =
        firstRollScore

    override fun getSecondRollScore(): Int =
        secondRollScore
}