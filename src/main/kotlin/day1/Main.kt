package day1

import util.readFile

fun main() {

    val lines = readFile("day1/input.txt")

    val caloriesSums = lines.fold(arrayListOf<Int>().apply { add(0) }) { result, line ->
        if (line.isBlank())
            result.add(0)
        else
            result[result.lastIndex] += line.toInt()
        result
    }

    val maxCalories = caloriesSums.max()
    println("Elf ${caloriesSums.indexOf(maxCalories)} carry the most calories - $maxCalories")
    println("Top three elves with most calories carry total ${caloriesSums.sortedDescending().take(3).sum()}")
}

