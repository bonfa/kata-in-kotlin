package it.fbonfadelli.playground.bowling

class Bowling(private val bowlingGame: BowlingGame,
              private val frameAdapter: FrameAdapter) {

    fun totalScore(rollScores: List<Int>): Int =
        bowlingGame.totalScore(frameAdapter.rollScoresToFrames(rollScores))
}
