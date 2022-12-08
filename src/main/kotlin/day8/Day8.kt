@file:Suppress("ComplexRedundantLet")

package day8

import util.readLines
import java.util.*

fun main() {

    val testLines = readLines("day8/input_test.txt")
    part1(testLines).let { check(it == 21) }
    part2(testLines).let { check(it == 8) }

    val lines = readLines("day8/input.txt")
    part1(lines).let { println("[Part 1] $it") }
    part2(lines).let { println("[Part 2] $it") }
}

// part 1
private fun part1(lines: List<String>): Int {
    var visible = 0

    val treesMap = lines.map { line -> line.map { it.digitToInt() } }

    for (row in treesMap.withIndex()) {
        for (column in row.value.withIndex()) {
            if (isVisible(treesMap, row.index, column.index)) {
                visible += 1
            }
        }
    }

    return visible
}

fun isVisible(treesMap: List<List<Int>>, row: Int, column: Int): Boolean {
    return column == 0 || row == 0
            || column == treesMap[0].lastIndex || row == treesMap.lastIndex
            || isVisibleFromUp(treesMap, row, column)
            || isVisibleFromDown(treesMap, row, column)
            || isVisibleFromLeft(treesMap, row, column)
            || isVisibleFromRight(treesMap, row, column)
}

private fun isVisibleFromUp(treesMap: List<List<Int>>, row: Int, column: Int): Boolean {
    for (i in 0 until column) {
        if (treesMap[row][i] >= treesMap[row][column]) {
            return false
        }
    }
    return true
}

private fun isVisibleFromDown(treesMap: List<List<Int>>, row: Int, column: Int): Boolean {
    for (i in column + 1..treesMap[0].lastIndex) {
        if (treesMap[row][i] >= treesMap[row][column]) {
            return false
        }
    }
    return true
}

private fun isVisibleFromLeft(treesMap: List<List<Int>>, row: Int, column: Int): Boolean {
    for (i in 0 until row) {
        if (treesMap[i][column] >= treesMap[row][column]) {
            return false
        }
    }
    return true
}

private fun isVisibleFromRight(treesMap: List<List<Int>>, row: Int, column: Int): Boolean {
    for (i in row + 1..treesMap[0].lastIndex) {
        if (treesMap[i][column] >= treesMap[row][column]) {
            return false
        }
    }
    return true
}

// part 2
fun part2(lines: List<String>): Int {
    val treesMap: List<List<Int>> = lines.map { line -> line.map { it.digitToInt() } }

    return treesMap.flatMapIndexed { row, treeRow ->
        treeRow.mapIndexed { column, height -> Triple(row, column, height) }
    }.maxOf { getScenicScore(treesMap, it.first, it.second, it.third) }
}

fun getScenicScore(treesMap: List<List<Int>>, row: Int, column: Int, height: Int): Int {
    return getScoreUp(treesMap, row, column, height) *
            getScoreDown(treesMap, row, column, height) *
            getScoreLeft(treesMap, row, column, height) *
            getScoreRight(treesMap, row, column, height)
}

private fun getScoreUp(treesMap: List<List<Int>>, row: Int, column: Int, height: Int): Int {
    var score = 0
    for (r in row - 1 downTo 0) {
        score++
        if (treesMap[r][column] >= height) {
            break
        }
    }
    return score
}

private fun getScoreDown(treesMap: List<List<Int>>, row: Int, column: Int, height: Int): Int {
    var score = 0
    for (r in row + 1..treesMap[0].lastIndex) {
        score++
        if (treesMap[r][column] >= height) {
            break
        }
    }
    return score
}

private fun getScoreLeft(treesMap: List<List<Int>>, row: Int, column: Int, height: Int): Int {
    var score = 0
    for (c in column - 1 downTo 0) {
        score++
        if (treesMap[row][c] >= height) {
            break
        }
    }
    return score
}

private fun getScoreRight(treesMap: List<List<Int>>, row: Int, column: Int, height: Int): Int {
    var score = 0
    for (c in column + 1..treesMap.lastIndex) {
        score++
        if (treesMap[row][c] >= height) {
            break
        }
    }
    return score
}
