package day12

import util.readLines

fun main() {

    val testLines = readLines("day12/input_test.txt")
    part1(testLines).also { check(it == 31) }
    part2(testLines).also { check(it == 29) }

    val lines = readLines("day12/input.txt")
    part1(lines).also { println("[Part 1] $it") }
    part2(lines).also { println("[Part 2] $it") }
}

// part 1
private fun part1(lines: List<String>): Int {
    val map: Array<CharArray> = lines.map { it.toCharArray() }.toTypedArray()

    val startRow = map.indexOfFirst { it.contains('S') }
    val startColumn = map[startRow].indexOf('S')

    return bfs(map, Pair(startRow, startColumn))
}

fun bfs(map: Array<CharArray>, startPosition: Pair<Int, Int>): Int {
    val visitQueue = ArrayDeque(listOf(startPosition))
    val shortestPaths = Array(map.size) { IntArray(map[0].size) { Int.MAX_VALUE } }
    shortestPaths[startPosition] = 0

    while (!visitQueue.isEmpty()) {
        val currentPosition = visitQueue.removeFirst()

        for (newPosition in currentPosition.getAccessibleNeighbors(map)) {
            if (shortestPaths[newPosition] > shortestPaths[currentPosition] + 1) {
                shortestPaths[newPosition] = shortestPaths[currentPosition] + 1
                visitQueue.addLast(newPosition)
            }
        }
    }

    val endRow = map.indexOfFirst { it.contains('E') }
    val endColumn = map[endRow].indexOf('E')

    return shortestPaths[endRow to endColumn]
}

private fun Pair<Int, Int>.getAccessibleNeighbors(map: Array<CharArray>): List<Pair<Int, Int>> {
    return listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)
        .map { this.first + it.first to this.second + it.second }
        .filter { it.first in 0..map.lastIndex }
        .filter { it.second in 0..map[0].lastIndex }
        .filter {
            map[it].getHeight() <= map[this].getHeight() + 1
        }
}

private fun Char.getHeight(): Int {
    return when (this) {
        in 'a'..'z' -> this.code
        'S' -> 'a'.code
        'E' -> 'z'.code
        else -> error("Invalid char")
    }
}

private operator fun Array<CharArray>.get(pos: Pair<Int, Int>): Char {
    return this[pos.first][pos.second]
}

private operator fun Array<IntArray>.get(pos: Pair<Int, Int>): Int {
    return this[pos.first][pos.second]
}

private operator fun Array<IntArray>.set(pos: Pair<Int, Int>, value: Int) {
    this[pos.first][pos.second] = value
}


// part 2
private fun part2(lines: List<String>): Int {
    val map: Array<CharArray> = lines.map { it.toCharArray() }.toTypedArray()

    return map.flatMapIndexed { row, line -> line.mapIndexed { column, char -> Triple(row, column, char) } }
        .filter { it.third == 'a' }
        .map { it.first to it.second }
        .minOf { bfs(map, it) }
}
