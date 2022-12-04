package day4

import util.readFile

fun main() {

    val testLines = readFile("day4/input_test.txt")
    check(part1(testLines) == 2)
    check(part2(testLines) == 4)

    val lines = readFile("day4/input.txt")
    println("Number of sections that fully overlaps: ${part1(lines)}")
    println("Number of sections that overlaps: ${part2(lines)}")
}

// part 1
private fun part1(lines: List<String>): Int {
    return lines.count { line ->
        val sections = parseSections(line)
        isFullOverlap(sections[0], sections[1])
    }
}

private fun parseSections(line: String) = line
    .split(',')
    .map {
        it.substringBefore('-').toInt() to it.substringAfter('-').toInt()
    }

private fun isFullOverlap(firstRange: Pair<Int, Int>, secondRange: Pair<Int, Int>) =
    (firstRange.first >= secondRange.first && firstRange.second <= secondRange.second)
            || (firstRange.first <= secondRange.first && firstRange.second >= secondRange.second)

// part 2
private fun part2(lines: List<String>): Int {
    return lines.count { line ->
        val sections = parseSections(line)
        isOverlap(sections[0], sections[1])
    }
}

private fun isOverlap(firstRange: Pair<Int, Int>, secondRange: Pair<Int, Int>) =
    firstRange.first in secondRange.first..secondRange.second
            || secondRange.first in firstRange.first..firstRange.second