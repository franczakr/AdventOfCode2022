package day3

import util.readFile


fun main() {
    val lines = readFile("day3/input.txt")

    part1(lines)
    part2(lines)
}

// part 1

private fun part1(lines: List<String>) {
    val commonItems = lines.map { line ->
        val firstCompartment = line.substring(0, line.length / 2)
        val secondCompartment = line.substring(line.length / 2)

        firstCompartment.first { secondCompartment.contains(it) }
    }

    val priorities = commonItems.map { getItemPriority(it) }

    println("Sum of items that appears in both compartments is ${priorities.sum()}")
}

private fun getItemPriority(it: Char): Int =
    if (it.isLowerCase()) {
        it.minus('a') + 1
    } else {
        it.minus('A') + 27
    }

// part 2

private fun part2(lines: List<String>) {

    val badgesPriorities = lines
        .chunked(3)
        .map { group ->
            group[0].first { group[1].contains(it) && group[2].contains(it) }
        }.map { getItemPriority(it) }

    println("Sum of badges priorities ${badgesPriorities.sum()}")
}