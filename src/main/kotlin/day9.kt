import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Day9 {
    companion object {

        fun List<Long>.isAllZero(): Boolean {
            var isAllZero = true
            for (ele in this) {
                if (ele != 0L) {
                    isAllZero = false
                }
            }
            return isAllZero
        }

        fun findNextValue(rowIdx: Int, inputMaitrx: MutableList<MutableList<Long>>, backwards: Boolean = false): Long {
            var diffMatrix = mutableListOf<MutableList<Long>>()
            diffMatrix += inputMaitrx[rowIdx]

            var complete = false
            var step = 0

            // Calculate differences until they stop changing.
            while (!complete) {
                var diffMatrixRow = mutableListOf<Long>()
                for (j in 1..<diffMatrix[step].size) {
                    diffMatrixRow += diffMatrix[step][j] - diffMatrix[step][j-1]
                }
                diffMatrix += diffMatrixRow
                if (diffMatrixRow.isAllZero()) {
                    complete = true
                }
                step++
            }

            diffMatrix[diffMatrix.size-1]+=0
            for (i in diffMatrix.size-2 downTo 0) {
                if (backwards) {
                    diffMatrix[i] +=
                        diffMatrix[i][0] - diffMatrix[i+1][diffMatrix[i+1].size-1]
                } else {
                    diffMatrix[i] +=
                        diffMatrix[i][diffMatrix[i].size-1] + diffMatrix[i+1][diffMatrix[i+1].size-1]
                }

            }

            return diffMatrix[0][diffMatrix[0].size-1]
        }

        fun part1() {
            val file = File("./src/main/resources/day9/actual.txt")
            val inputMatrix = mutableListOf<MutableList<Long>>()

            // Read text into a matrix.
            BufferedReader(FileReader(file)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    val intRow = line!!.split(" ").map { it.toLong() }
                    inputMatrix += intRow.toMutableList()
                }
            }

            var sum = 0L
            for (rowIdx in 0..<inputMatrix.size) {
                sum+=findNextValue(rowIdx, inputMatrix)
            }
            println(sum)
        }

        fun part2() {
            val file = File("./src/main/resources/day9/actual.txt")
            val inputMatrix = mutableListOf<MutableList<Long>>()

            // Read text into a matrix.
            BufferedReader(FileReader(file)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    val intRow = line!!.split(" ").map { it.toLong() }
                    inputMatrix += intRow.toMutableList()
                }
            }

            var sum = 0L
            for (rowIdx in 0..<inputMatrix.size) {
                sum += findNextValue(rowIdx, inputMatrix, backwards = true)
            }
            println(sum)
        }
    }
}
