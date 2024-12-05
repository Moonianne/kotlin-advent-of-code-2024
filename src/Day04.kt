private const val XMAS_LENGTH: Int = 4
private val XMAS: Array<Char> = arrayOf('X', 'M', 'A', 'S')
private val XMAS_REVERSED: Array<Char> = XMAS.reversedArray()
private val MAS: Array<Char> = arrayOf('M', 'A', 'S')
private val MAS_REVERSED: Array<Char> = MAS.reversedArray()

fun main() {
    fun part1(input: Array<Array<Char>>): Int {
        return XmasCounter(input).count()
    }

    fun part2(input: Array<Array<Char>>): Int {
        return XmasCounter(input).countMasCrosses()
    }


    // Read the input from the `src/Day04.txt` file.
    val input = readInput("Day04").map { line -> line.toCharArray().toTypedArray() }.toTypedArray()
    part1(input).println()
    part2(input).println()
}

private class XmasCounter(
    private val puzzle: Array<Array<Char>>,
) {
    private val xLimit: Int = puzzle[0].lastIndex
    private var yLimit: Int = puzzle.lastIndex

    fun countMasCrosses(): Int {
        var count = 0
        var x = 1
        var y = 1

        fun IntProgression.isMas(): Boolean {
            val yRange = y - 1..y + 1
            return takeIf { it.last in 0..xLimit }
                ?.zip(yRange)
                ?.map { (x, y) -> puzzle[y][x] }
                ?.contentEqualsMas() ?: false
        }

        fun goToNextCoordinates() {
            when {
                (x + 1 == xLimit) -> {
                    y++
                    x = 1
                }

                else -> x++
            }
        }

        while (y < yLimit) {
            val xRange = (x - 1..x + 1)

            if (xRange.println().isMas() && xRange.reversed().isMas()) {
                count++
            }
            goToNextCoordinates()
        }
        return count
    }

    fun count(): Int = countHorizontal() + countVertical() + countDiagonals()

    private fun countHorizontal(): Int {
        var count = 0
        var x = 0
        var y = 0
        fun goToNextCoordinates(foundXmas: Boolean) {
            when {
                (!foundXmas && x + 3 >= xLimit) || (foundXmas && x + 6 > xLimit) -> {
                    y++
                    x = 0
                }

                foundXmas -> x += 3
                else -> x++
            }
        }

        while (x <= xLimit && y <= yLimit) {
            if (puzzle[y].copyOfRange(x, x + XMAS_LENGTH).contentEqualsXmas()) {
                count++
                goToNextCoordinates(true)
            } else {
                goToNextCoordinates(false)
            }
        }

        return count
    }

    private fun countVertical(): Int {
        var count = 0
        var x = 0
        var y = 0

        fun goToNextCoordinates(foundXmas: Boolean) {
            when {
                (!foundXmas && y + 3 >= yLimit) || (foundXmas && y + 6 > yLimit) -> {
                    x++
                    y = 0
                }

                foundXmas -> y += 3
                else -> y++
            }
        }

        while (x <= xLimit && y <= yLimit) {
            if ((y..<y + XMAS_LENGTH).map { puzzle[it][x] }.contentEqualsXmas()) {
                count++
                goToNextCoordinates(true)
            } else {
                goToNextCoordinates(false)
            }
        }
        return count
    }

    private fun countDiagonals(): Int {
        var count = 0
        var x = 0
        var y = 0

        fun IntProgression.countXmas() {
            val yRange = y..(y + 3)
            takeIf { it.last in 0..xLimit }
                ?.zip(yRange)
                ?.map { (x, y) -> puzzle[y][x] }
                ?.contentEqualsXmas()
                ?.let { if (it) count++ }
        }

        fun goToNextCoordinates() {
            when {
                (x >= xLimit) -> {
                    y++
                    x = 0
                }

                else -> x++
            }
        }

        while (y + 3 <= yLimit) {
            ((x - 3)..x).reversed().countXmas()
            (x..(x + 3)).countXmas()
            goToNextCoordinates()
        }
        return count
    }

    private fun Array<Char>.contentEqualsXmas(): Boolean = contentEquals(XMAS) || contentEquals(XMAS_REVERSED)
    private fun List<Char>.contentEqualsXmas(): Boolean = toTypedArray().contentEqualsXmas()
    private fun List<Char>.contentEqualsMas(): Boolean = toTypedArray().run {
        contentEquals(MAS) || contentEquals(MAS_REVERSED)
    }

    private fun Array<Char>.print(): Array<Char> = also { it.contentDeepToString().println() }
}