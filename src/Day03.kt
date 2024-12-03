private const val DONT: String = "don't()"
private const val DO: String = "do()"
private val mulRegex = Regex("mul\\([0-9]+,[0-9]+\\)")

fun main() {
    fun part1(input: String): Int = input.sumOfMuls()

    fun part2(input: String): Int = removeDisabledBlocks(input).sumOfMuls()

    // Read the input from the `src/Day03.txt` file.
    val input = readInputAsString("Day03")
    part1(input).println()
    part2(input).println()
}

private fun String.sumOfMuls(): Int {
    return mulRegex.findAll(this)
        .map { matchResult -> matchResult.value }
        .toList()
        .sumOf {
            it.substring(4).dropLast(1).run {
                substringBefore(",").toInt() * substringAfter(",").toInt()
            }
        }
}

private fun removeDisabledBlocks(line: String): String {
    var currentLine = line
    var indexOfDont = currentLine.indexOf(DONT)

    while (indexOfDont != -1) {
        val indexOfDo = currentLine.indexOf(DO, indexOfDont).let {
            if (it == -1) currentLine.lastIndex else it
        }
        currentLine = currentLine.removeRange(indexOfDont..indexOfDo)
        indexOfDont = currentLine.indexOf(DONT)
    }
    return currentLine
}

private fun String.indexOf(string: String, startIndex: Int = 0): Int {
    return indexOfAny(listOf(string), startIndex)
}