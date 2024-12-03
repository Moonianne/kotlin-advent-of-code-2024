import kotlin.math.abs

private typealias Report = List<Int>

fun main() {
    fun part1(reports: List<Report>): Int {
        return reports.safeCount()
    }

    fun part2(reports: List<Report>): Int {
        return reports.safeCount(enableDampener = true)
    }

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02").toReports()
    part1(input).println()
    part2(input).println()
}

private fun List<String>.toReports(): List<Report> =
    map { it.split(" ").map { level -> level.toInt() } }

private fun List<Report>.safeCount(enableDampener: Boolean = false): Int =
    count { it.isSafe(enableDampener = enableDampener) }

private fun Report.isSafe(enableDampener: Boolean = false): Boolean {
    fun isSorted(report: Report): Boolean = report == report.sorted() || report == report.sortedDescending()

    fun areLevelsInRange(report: Report): Boolean {
        report.reduce { acc, level ->
            val diff = abs(acc - level)
            if (diff < 1 || diff > 3) return@areLevelsInRange false
            level
        }
        return true
    }

    fun isSafeWithDampener(): Boolean {
        indices.forEach { index ->
            val dampenedReport = toMutableList()
            dampenedReport.removeAt(index)
            if (isSorted(dampenedReport) && areLevelsInRange(dampenedReport)) {
                return@isSafeWithDampener true
            }
        }
        return false
    }

    return (isSorted(this) && areLevelsInRange(this)) || (enableDampener && isSafeWithDampener())
}
