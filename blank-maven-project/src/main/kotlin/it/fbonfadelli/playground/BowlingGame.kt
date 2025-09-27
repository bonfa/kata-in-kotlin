package it.fbonfadelli.playground

class BowlingGame(private val frames: List<Frame>) {
    fun totalScore(): Int {
        var totalScore = 0
        for (i in 0 until frames.size) {
            totalScore += when (val frame = frames[i]) {
                is LastFrame -> frame.getScore()
                is NorStrikeNorSpare -> frame.getScore()
                is Spare -> frame.getScore(frames[i + 1])
                is Strike -> frame.getScore(frames[i + 1], frames.getOrNull(i + 2))
            }
        }
        return totalScore
    }
}