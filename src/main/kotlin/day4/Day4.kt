package day4

import util.readFile

fun main() {

    val lines = readFile("day4/input.txt")

    part1(lines)
    part2(lines)
}

// part 1
private fun part1(lines: List<String>) {
    lines.filter { line ->
        val sections = parseSections(line)
        isFullOverlap(sections[0], sections[1])
    }.let { println("Number of sections that fully overlaps: ${it.size}") }
}

private fun parseSections(line: String) = line
    .split(',')
    .map {
        it.substringBefore('-').toInt() to it.substringAfter('-').toInt()
    }

private fun isFullOverlap(section1: Pair<Int, Int>, section2: Pair<Int, Int>) =
    (section1.first >= section2.first && section1.second <= section2.second)
            || (section1.first <= section2.first && section1.second >= section2.second)

// part 2
private fun part2(lines: List<String>) {
    lines.filter { line ->
        val sections = parseSections(line)
        isOverlap(sections[0], sections[1])
    }.let { println("Number of sections that overlaps: ${it.size}") }
}

private fun isOverlap(section1: Pair<Int, Int>, section2: Pair<Int, Int>) =
    (section2.first <= section1.first && section1.first <= section2.second)
            || (section2.first <= section1.second && section1.first <= section2.second)

