import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day11 {
    companion object {

        private fun expandUniverse(inMatrix: MutableList<MutableList<Char>>): MutableList<MutableList<Char>> {
            val expandedUniverse = mutableListOf<MutableList<Char>>()

            // Expand rows
            for (row in inMatrix) {
                expandedUniverse += row.toMutableList()  // To copy
                if ('#' !in row) {
                    expandedUniverse += row.toMutableList()  // To copy
                }
            }

            // Expand cols
            var outCol = 0
            for (col in 0..<inMatrix.size) {
                var noGalaxy = true
                for (row in 0..<inMatrix[col].size) {
                    if (inMatrix[row][col] == '#') {
                        noGalaxy=false
                    }
                }
                if (noGalaxy) {
                    for (row in 0..<expandedUniverse.size) {
                        expandedUniverse[row].add(outCol, '.')
                    }
                    outCol++
                }
                outCol++
            }
            return expandedUniverse
        }

        private fun l1distance(a: Pair<Int, Int>, b: Pair<Int, Int>): Int {
            return abs(a.second - b.second) + abs(a.first - b.first)
        }

        private fun l1distanceExpanded(a: Pair<Int, Int>, b: Pair<Int, Int>, expandRowIdx: MutableList<Int>, expandColIdx: MutableList<Int>): Long {
            val l1dist = l1distance(a, b).toLong()

            // See how many times we cross an empty row or col
            var emptyRowsCrossed = 0
            var emptyColsCrossed = 0
            for (i in min(a.first, b.first)..<max(a.first, b.first)) {
                if (i in expandRowIdx) {
                    emptyRowsCrossed++
                }
            }
            for (i in min(a.second, b.second)..<max(a.second, b.second)) {
                if (i in expandColIdx) {
                    emptyColsCrossed++
                }
            }

            return l1dist + (emptyRowsCrossed+emptyColsCrossed) * 999999
        }

        fun part1() {
            val file = File("./src/main/resources/day11/actual.txt")
            val charMatrix = mutableListOf<MutableList<Char>>()

            // Read text into a matrix.
            BufferedReader(FileReader(file)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    val charRow = line.toString().toMutableList()
                    charMatrix += charRow
                }
            }
            val expandedUniverse = expandUniverse(charMatrix)

            val galaxyLocations = mutableListOf<Pair<Int, Int>>()
            for (i in expandedUniverse.indices) {
                for (j in expandedUniverse[i].indices) {
                    if (expandedUniverse[i][j] == '#') {
                        galaxyLocations+= Pair(i, j)
                    }
                }
            }

            var sum = 0
            for (galaxy in 0..<galaxyLocations.size-1) {
                for (otherGalaxy in galaxy+1..<galaxyLocations.size) {
                    sum += l1distance(galaxyLocations[galaxy], galaxyLocations[otherGalaxy])
                }
            }

            println(sum)
        }

        fun part2() {
            val file = File("./src/main/resources/day11/actual.txt")
            val charMatrix = mutableListOf<MutableList<Char>>()

            // Read text into a matrix.
            BufferedReader(FileReader(file)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    val charRow = line.toString().toMutableList()
                    charMatrix += charRow
                }
            }
            val expandRowIdx = mutableListOf<Int>()
            val expandColIdx = mutableListOf<Int>()
            for ((rowIdx, row) in charMatrix.withIndex()) {
                if ('#' !in row) {
                    expandRowIdx += rowIdx
                }
            }

            for (col in 0..<charMatrix.size) {
                var noGalaxy = true
                for (row in 0..<charMatrix[col].size) {
                    if (charMatrix[row][col] == '#') {
                        noGalaxy=false
                    }
                }
                if (noGalaxy) {
                    expandColIdx+=col
                }
            }

            val galaxyLocations = mutableListOf<Pair<Int, Int>>()
            for (i in charMatrix.indices) {
                for (j in charMatrix[i].indices) {
                    if (charMatrix[i][j] == '#') {
                        galaxyLocations += Pair(i, j)
                    }
                }
            }

            var sum = 0L
            for (galaxy in 0..<galaxyLocations.size - 1) {
                for (otherGalaxy in galaxy + 1..<galaxyLocations.size) {
                    sum += l1distanceExpanded(galaxyLocations[galaxy], galaxyLocations[otherGalaxy],
                                              expandRowIdx, expandColIdx)
                }
            }

            println(sum)
        }
    }
}
