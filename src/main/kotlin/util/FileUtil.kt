package util

import java.io.File

fun readLines(fileName: String) = File(getFileName(fileName)).readLines()
fun readText(fileName: String) = File(getFileName(fileName)).readText()

private fun getFileName(fileName: String) = "src/main/kotlin/$fileName"
