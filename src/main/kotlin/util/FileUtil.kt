package util

import java.io.File

fun readFile(fileName: String) = File("src/main/kotlin/$fileName").readLines()