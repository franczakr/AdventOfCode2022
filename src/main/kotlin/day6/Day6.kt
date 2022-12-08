package day6

import util.readText

fun main() {

    val testText = readText("day6/input_test.txt")
    check(part1(testText) == 7)
    check(part2(testText) == 19)

    val text = readText("day6/input.txt")
    println("Marker found after ${part1(text)} characters")
    println("Message found after ${part2(text)} characters")
}

// part 1
private fun part1(signal: String): Int {
    return getFirstNDistinctCharactersIndex(signal, 4)
}


private fun getFirstNDistinctCharactersIndex(string: String, n: Int): Int {
    for (i in n..string.lastIndex) {
        if (string.substring(i - n, i).toSet().size == n)
            return i
    }
    error("No marker found")
}

// part 2
private fun part2(signal: String): Int {
    return getFirstNDistinctCharactersIndex(signal, 14)
}
