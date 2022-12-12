package day11

import util.readLines

fun main() {

    val testLines = readLines("day11/input_test.txt")
    part1(testLines).also { check(it == 10605L) }
    part2(testLines).also { check(it == 2713310158) }

    val lines = readLines("day11/input.txt")
    part1(lines).also { println("[Part 1] $it") }
    part2(lines).also { println("[Part 2] $it") }
}

private fun List<String>.parseMonkeys(): List<Monkey> {
    return this.chunked(7).map { Monkey.fromText(it) }
}

//part 1
private fun part1(lines: List<String>): Long {
    val monkeys = lines.parseMonkeys()

    repeat(20) {
        monkeys.forEach { monkey ->
            while (monkey.items.isNotEmpty()) {
                val item = monkey.items.removeFirst()
                val nextMonkey = monkey.inspectItem(item) { x -> x / 3 }
                monkeys[nextMonkey].items.addLast(item)
            }
        }
    }

    return monkeys.map { it.inspectCounter }.sortedDescending().let { it[0] * it[1] }
}

//part 2
private fun part2(lines: List<String>): Long {
    val monkeys = lines.parseMonkeys()
    val commonMod = monkeys.map { it.throwingTest.divisor }.reduce(Long::times)

    repeat(10_000) {
        monkeys.forEach { monkey ->
            while (monkey.items.isNotEmpty()) {
                val item = monkey.items.removeFirst()
                val nextMonkey = monkey.inspectItem(item) { x -> x % commonMod }
                monkeys[nextMonkey].items.addLast(item)
            }
        }
    }

    return monkeys.map { it.inspectCounter }.sortedDescending().take(2).reduce(Long::times)
}