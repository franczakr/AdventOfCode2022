package day5

import util.readFile
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.ceil

fun main() {

    val testLines = readFile("day5/input_test.txt")
    check(part1(testLines) == "CMZ")
    check(part2(testLines) == "MCD")

    val lines = readFile("day5/input.txt")
    println("[Part 1] Crates on top of stacks are: ${part1(lines)}")
    println("[Part 2] Crates on top of stacks are: ${part2(lines)}")
}

// part 1
private fun part1(lines: List<String>): String {
    val (stacks, actions) = parseInput(lines)
    return stacks.performActionsPart1(actions).map { it.peek() }.joinToString("")
}

data class Action(val count: Int, val source: Int, val destination: Int)

private fun parseInput(lines: List<String>): Pair<List<Stack<Char>>, List<Action>> {
    val index = lines.indexOfFirst { it.isBlank() }
    val stacks = parseStacks(lines.subList(0, index))
    val actions = parseActions(lines.subList(index + 1, lines.size))
    return Pair(stacks, actions)
}

private fun parseStacks(lines: List<String>): List<Stack<Char>> {
    val stacks = initStacks(lines)

    lines.reversed().forEach { line ->
        line.chunked(4).forEachIndexed { index, crate ->
            if (crate.length >= 2 && crate[1].isLetter()) {
                stacks[index].push(crate[1])
            }
        }
    }
    return stacks
}

private fun initStacks(lines: List<String>): ArrayList<Stack<Char>> {
    val stacksCount: Int = ceil(lines[lines.lastIndex].length / 4.0).toInt()
    val stacks = arrayListOf<Stack<Char>>()
    for (i in 1..stacksCount) {
        stacks.add(Stack())
    }
    return stacks
}

private fun parseActions(lines: List<String>): List<Action> {
    return lines
        .map { line -> line.filter { it.isDigit() || it.isWhitespace() } }
        .map { line -> line.trim().split("\\s+".toRegex()).map { it.toInt() } }
        .map { Action(it[0], it[1], it[2]) }
}

private fun List<Stack<Char>>.performActionsPart1(actions: List<Action>): List<Stack<Char>> {
    actions.forEach { action ->
        this.performActionPart1(action)
    }
    return this
}

private fun List<Stack<Char>>.performActionPart1(action: Action) {
    for (i in 1..action.count) {
        this[action.destination - 1].push(this[action.source - 1].pop())
    }
}

// part 2
private fun part2(lines: List<String>): String {
    val (stacks, actions) = parseInput(lines)
    return stacks.performActionsPart2(actions).map { it.peek() }.joinToString("")
}

private fun List<Stack<Char>>.performActionsPart2(actions: List<Action>): List<Stack<Char>> {
    actions.forEach { action ->
        this.performActionPart2(action)
    }
    return this
}

private fun List<Stack<Char>>.performActionPart2(action: Action) {
    val cratesToMove: ArrayDeque<Char> = ArrayDeque()
    for (i in 1..action.count) {
        cratesToMove.addFirst(this[action.source - 1].pop())
    }
    this[action.destination - 1].addAll(cratesToMove)
}