@file:Suppress("ComplexRedundantLet")

package day9

import util.readLines
import java.util.*
import kotlin.math.abs
import kotlin.math.max

fun main() {

    val testLines = readLines("day9/input_test.txt")
    part1(testLines).let { check(it == 13) }
    part2(testLines).let { check(it == 1) }

    val lines = readLines("day9/input.txt")
    part1(lines).let { println("[Part 1] $it") }
    part2(lines).let { println("[Part 2] $it") }
}

class Node(var x: Int = 0, var y: Int = 0) {
    fun distanceTo(node: Node): Int = max(abs(x - node.x), abs(y - node.y))

    fun move(direction: String) {
        when (direction) {
            "U" -> y += 1
            "D" -> y -= 1
            "R" -> x += 1
            "L" -> x -= 1
            else -> error("Invalid direction")
        }
    }

    fun moveTo(node: Node) {
        when {
            x > node.x -> x -= 1
            x < node.x -> x += 1
        }
        when {
            y > node.y -> y -= 1
            y < node.y -> y += 1
        }
    }
}

// part 1
private fun part1(lines: List<String>): Int {
    val head = Node(0, 0)
    val tail = Node(0, 0)

    val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()

    lines.forEach { line ->
        val (direction, count) = line.split(" ")

        repeat(count.toInt()) {
            head.move(direction)
            if (tail.distanceTo(head) > 1) {
                tail.moveTo(head)
            }
            visited.add(Pair(tail.x, tail.y))
        }
    }
    return visited.size
}

// part 2
private fun part2(lines: List<String>): Int {
    val rope = MutableList(10) { Node(0, 0) }

    val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()

    lines.forEach { line ->
        val (direction, count) = line.split(" ")

        repeat(count.toInt()) {
            rope[0].move(direction)
            for (i in 1..rope.lastIndex) {
                if (rope[i].distanceTo(rope[i - 1]) > 1) {
                    rope[i].moveTo(rope[i - 1])
                }
            }
            visited.add(rope[rope.lastIndex].let { Pair(it.x, it.y) })
        }
    }
    return visited.size
}

