package it.fbonfadelli.playground.bowling

object BowlingGame {

    fun totalScore(frames: List<Frame>): Int =
        frames.mapIndexed { i, frame -> frameScore(frame, frames, i) }.sum()

    private fun frameScore(
        frame: Frame,
        frames: List<Frame>,
        framePosition: Int
    ): Int = when (frame) {
        is LastFrame -> frame.getScore()
        is NorStrikeNorSpare -> frame.getScore()
        is Spare -> frame.getScore(frames[framePosition + 1])
        is Strike -> frame.getScore(frames[framePosition + 1], frames.getOrNull(framePosition + 2))
    }
}