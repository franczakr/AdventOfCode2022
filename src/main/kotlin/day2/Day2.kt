package day2

import util.readFile

fun main() {
    val lines = readFile("day2/input.txt")
    part1(lines)
    part2(lines)
}

// part 1

fun part1(lines: List<String>) {

    val pointsSum = lines.sumOf { line ->
        val enemyPick = parseEnemyPick(line[0])
        val myPick = parseMyPick(line[2])
        calculatePoints(myPick, enemyPick)
    }

    println("Sum of points in first puzzle part is $pointsSum")
}

enum class Shape(val points: Int) { ROCK(1), PAPER(2), SCISSORS(3) }

enum class Result(val points: Int) { WIN(6), DRAW(3), LOST(0) }

private fun parseEnemyPick(pick: Char): Shape = when (pick) {
    'A' -> Shape.ROCK
    'B' -> Shape.PAPER
    'C' -> Shape.SCISSORS
    else -> throw IllegalArgumentException()
}

private fun parseMyPick(pick: Char): Shape = when (pick) {
    'X' -> Shape.ROCK
    'Y' -> Shape.PAPER
    'Z' -> Shape.SCISSORS
    else -> throw IllegalArgumentException()
}

private fun calculatePoints(myPick: Shape, enemyPick: Shape): Int =
    calculatePointsForResult(myPick, enemyPick) + myPick.points

private fun calculatePointsForResult(myPick: Shape, enemyPick: Shape): Int {
    if (enemyPick == myPick) return Result.DRAW.points
    return when (myPick) {
        Shape.ROCK -> if (enemyPick == Shape.PAPER) Result.LOST.points else Result.WIN.points
        Shape.PAPER -> if (enemyPick == Shape.SCISSORS) Result.LOST.points else Result.WIN.points
        Shape.SCISSORS -> if (enemyPick == Shape.ROCK) Result.LOST.points else Result.WIN.points
    }
}


// part 2

fun part2(lines: List<String>) {

    val pointsSum = lines.sumOf { line ->
        val enemyPick = parseEnemyPick(line[0])
        val myPick = getMyPick(enemyPick, line[2])
        calculatePoints(myPick, enemyPick)
    }

    println("Sum of points in second puzzle part is $pointsSum")
}

private fun parseResult(result: Char): Result = when (result) {
    'X' -> Result.LOST
    'Y' -> Result.DRAW
    'Z' -> Result.WIN
    else -> throw IllegalArgumentException()
}

private fun getMyPick(enemyPick: Shape, result: Char): Shape =
    when (parseResult(result)) {
        Result.WIN -> when (enemyPick) {
            Shape.ROCK -> Shape.PAPER
            Shape.PAPER -> Shape.SCISSORS
            Shape.SCISSORS -> Shape.ROCK
        }

        Result.LOST -> when (enemyPick) {
            Shape.ROCK -> Shape.SCISSORS
            Shape.PAPER -> Shape.ROCK
            Shape.SCISSORS -> Shape.PAPER
        }

        Result.DRAW -> enemyPick
    }
