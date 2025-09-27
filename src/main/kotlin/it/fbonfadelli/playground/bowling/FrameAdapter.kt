package it.fbonfadelli.playground.bowling

object FrameAdapter {
    fun rollScoresToFrames(rollScores: List<Int>): List<Frame> {
        val frames = mutableListOf<Frame>()

        var i = 0
        var prevScore: Int? = null
        while (prevScore != null || i < rollScores.size - 3) {
            val rollScore = rollScores[i]
            when {
                rollScore == 10 -> {
                    frames.add(Strike)
                    prevScore = null
                    i++
                }
                prevScore == null -> {
                    prevScore = rollScore
                    i++
                }
                prevScore + rollScore == 10 -> {
                    frames.add(Spare(prevScore))
                    prevScore = null
                    i++
                }
                else -> {
                    frames.add(NorStrikeNorSpare(prevScore, rollScore))
                    prevScore = null
                    i++
                }
            }
        }

        frames.add(LastFrame(rollScores[i], rollScores[i + 1], rollScores.getOrNull(i + 2) ?: 0))

        return frames
    }
}