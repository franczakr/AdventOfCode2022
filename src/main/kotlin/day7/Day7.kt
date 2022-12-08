package day7

import util.readLines

fun main() {
    val testLines = readLines("day7/input_test.txt")
    check(part1(testLines) == 95437L)
    check(part2(testLines) == 24933642L)

    val lines = readLines("day7/input.txt")
    println("Sum of bytes in dirs with a total size of at most 100000 :  ${part1(lines)}")
    println("Directory to remove has ${part2(lines)} bytes")
}

// part 1
private fun part1(lines: List<String>): Long {
    return DirectoriesSizeCalculator.getDirsSizes(lines)
        .values
        .filter { it <= 100000 }
        .sum()
}

//part 2
fun part2(lines: List<String>): Long {
    val totalSpace = 70000000
    val neededSpace = 30000000

    val dirsSizes = DirectoriesSizeCalculator.getDirsSizes(lines)
    val freeSpace = totalSpace - dirsSizes.getOrDefault("/", 0L)
    val minSpaceToFree = neededSpace - freeSpace

    return dirsSizes
        .values
        .sorted()
        .first { it >= minSpaceToFree }
}