package day10

import util.readLines
import kotlin.math.abs

fun main() {

    val testLines = readLines("day10/input_test.txt")
    part1(testLines).also { check(it == 13140) }
    part2(testLines).also { prettyPrint(it) }

    val lines = readLines("day10/input.txt")
    part1(lines).also { println("[Part 1] $it") }
    part2(lines).also { println("[Part 2]") }.also { prettyPrint(it) }
}

private fun prettyPrint(it: List<List<Char>>) {
    println(it.joinToString("\n") { it.joinToString("") })
}

// part 1
private fun part1(lines: List<String>): Int {
    val cyclesToMeasure = listOf(20, 60, 100, 140, 180, 220)
    var cycle = 1
    var x = 1

    var signalStrengthsSum = 0

    lines.forEach { line ->
        when {
            line == "noop" -> {
                signalStrengthsSum += addIfNeeded(cyclesToMeasure, cycle, x)
                cycle++
            }

            line.startsWith("addx") -> {
                signalStrengthsSum += addIfNeeded(cyclesToMeasure, cycle, x)
                cycle++
                signalStrengthsSum += addIfNeeded(cyclesToMeasure, cycle, x)
                cycle++
                x += line.removePrefix("addx ").toInt()
            }

            else -> error("Invalid command")
        }
    }

    return signalStrengthsSum
}

private fun addIfNeeded(cyclesToMeasure: List<Int>, cycle: Int, x: Int) =
    if (cycle in cyclesToMeasure) x * cycle else 0


// part 2
private fun part2(lines: List<String>): List<List<Char>> {
    val screen: MutableList<MutableList<Char>> = mutableListOf()

    var cycle = 1
    var x = 1

    lines.forEach { line ->
        when {
            line == "noop" -> {
                draw(screen, cycle, x)
                cycle++
            }

            line.startsWith("addx") -> {
                draw(screen, cycle, x)
                cycle++
                draw(screen, cycle, x)
                cycle++
                x += line.removePrefix("addx ").toInt()
            }

            else -> error("Invalid command")
        }
    }

    return screen
}

fun draw(screen: MutableList<MutableList<Char>>, cycle: Int, x: Int) {
    val pixelNum = cycle % 40 - 1
    if (pixelNum == 0) screen.add(mutableListOf())
    val pixel = if (abs(pixelNum - x) <= 1) '#' else '.'
    screen[screen.lastIndex].add(pixel)
}

