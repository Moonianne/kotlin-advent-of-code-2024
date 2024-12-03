import kotlin.math.abs

typealias LocationIds = Pair<List<Int>, List<Int>>

fun main() {
    fun part1(locationIds: LocationIds): Int {
        val (leftList, rightList) = locationIds
        return leftList.sorted().zip(rightList.sorted())
            .sumOf { (left, right) -> abs(left - right) }
    }

    fun part2(locationIds: LocationIds): Int {
        val (leftList, rightList) = locationIds
        return leftList.sumOf { left ->
            left * rightList.count { right -> left == right }
        }
    }

    // Read the input from the `src/Day01.txt` file.
    val locationIds = readInput("Day01").splitToLeftAndRight()
    part1(locationIds).println()
    part2(locationIds).println()
}

private fun List<String>.splitToLeftAndRight(): Pair<List<Int>, List<Int>> = map {
    it.substringBefore(" ").toInt() to it.substringAfterLast(" ").toInt()
}.unzip()
