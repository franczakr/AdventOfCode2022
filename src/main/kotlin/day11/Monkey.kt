package day11

import util.removePrefixTrim


data class Monkey(val items: ArrayDeque<Item>, val inspectOperation: Operation, val throwingTest: Test) {

    var inspectCounter: Long = 0

    fun inspectItem(item: Item, worryLevelModifier: (Long) -> Long): Int {
        inspectCounter++
        inspectOperation.apply(item)
        item.worryLevel = worryLevelModifier(item.worryLevel)
        return throwingTest.getNextMonkey(item)
    }

    companion object {
        fun fromText(lines: List<String>): Monkey {
            val items = lines[1].removePrefixTrim("Starting items:").split(", ").map { Item(it.toLong()) }
            val operation = Operation.of(lines[2])
            val test = Test.of(lines.subList(3, 6))
            return Monkey(ArrayDeque(items), operation, test)
        }
    }


    data class Item(var worryLevel: Long)

    data class Operation(val first: Long?, val operation: String, val second: Long?) {

        fun apply(x: Item) {
            x.worryLevel = when (operation) {
                "+" -> getFirst(x) + getSecond(x)
                "*" -> getFirst(x) * getSecond(x)
                else -> error("Invalid operation")
            }
        }


        private fun getFirst(x: Item): Long = first ?: x.worryLevel

        private fun getSecond(x: Item): Long = second ?: x.worryLevel

        companion object {
            fun of(string: String): Operation {
                val (firstStr, operation, secondStr) = string.removePrefixTrim("Operation: new =").split(" ")
                val first = if (firstStr == "old") null else firstStr.toLong()
                val second = if (secondStr == "old") null else secondStr.toLong()
                return Operation(first, operation, second)
            }
        }
    }

    data class Test(val divisor: Long, val passed: Int, val failed: Int) {
        fun getNextMonkey(item: Item) = if (item.worryLevel % divisor == 0L) passed else failed

        companion object {
            fun of(text: List<String>): Test {
                val divisor = text[0].removePrefixTrim("Test: divisible by").toLong()
                val passed = text[1].removePrefixTrim("If true: throw to monkey").toInt()
                val failed = text[2].removePrefixTrim("If false: throw to monkey").toInt()
                return Test(divisor, passed, failed)
            }
        }
    }

}

