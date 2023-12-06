import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Day3 {
    companion object {

        fun checkNeighbors(matrix: List<List<Char>>, row: Int, col: Int): Boolean {
            val directions = listOf(
                Pair(-1, 0), Pair(1, 0), Pair(0, 1), Pair(0, -1),
                Pair(-1, 1), Pair(-1, -1), Pair(1, 1), Pair(1, -1)
            )

            for (dir in directions) {
                try {
                    if (matrix[row + dir.first][col + dir.second] != '.'
                        && !matrix[row + dir.first][col + dir.second].isDigit()
                    ) {
                        return true
                    }
                } catch (e: IndexOutOfBoundsException) {
                    continue
                }
            }

            return false
        }

        fun checkNeighborsNum(matrix: List<List<Char>>, row: Int, col: Int): Boolean {
            val directions = listOf(
                Pair(-1, 0), Pair(1, 0), Pair(0, 1), Pair(0, -1),
                Pair(-1, 1), Pair(-1, -1), Pair(1, 1), Pair(1, -1)
            )

            for (dir in directions) {
                try {
                    if (matrix[row + dir.first][col + dir.second].isDigit()) {
                        return true
                    }
                } catch (e: IndexOutOfBoundsException) {
                    continue
                }
            }
            return false
        }

        fun getNeighborNumProd(matrix: List<List<Char>>, row: Int, col: Int) : Int {
            var sumProd = 0
            var numList = mutableListOf<Int>()

            // check left
            if (col != 0) {
                var step = 1
                var num = ""
                while (col-step>-1 && matrix[row][col-step].isDigit()) {
                    num = matrix[row][col-step].plus(num)
                    step+=1
                }
                if (num.isNotEmpty()) {
                    numList+=num.toInt()
                }
            }

            // check right
            if (col != matrix.size-1) {
                var step = 1
                var num = ""
                while (col+step<matrix.size && matrix[row][col+step].isDigit()) {
                    num+=matrix[row][col+step]
                    step+=1
                }
                if (num.isNotEmpty()) {
                    numList+=num.toInt()
                }
            }

            // check above
            if (row != 0) {
                var nums = ""
                nums+=matrix[row-1][col]

                // above right
                var step = 1
                while (col+step<matrix.size && matrix[row-1][col+step].isDigit()) {
                    nums+=matrix[row-1][col+step]
                    step+=1
                }
                // above left
                step = 1
                while (col-step>-1 && matrix[row-1][col-step].isDigit()) {
                    nums = matrix[row-1][col-step].plus(nums)
                    step+=1
                }

                if (nums.isNotEmpty() && nums != ".") {
                    var splitAbove = nums.split(".")
                    if (splitAbove.isNotEmpty()) {
                        for(num in splitAbove) {
                            if (num.isNotEmpty()) {
                                numList += num.toInt()
                            }
                        }
                    }
                }
            }

            // check below
            if (row != matrix.size-1) {
                var nums = ""
                nums+=matrix[row+1][col]

                // below right
                var step = 1
                while (col+step<matrix.size && matrix[row+1][col+step].isDigit()) {
                    nums+=matrix[row+1][col+step]
                    step+=1
                }
                // below left
                step = 1
                while (col-step>-1 && matrix[row+1][col-step].isDigit()) {
                    nums = matrix[row+1][col-step].plus(nums)
                    step+=1
                }
                if (nums.isNotEmpty() && nums != ".") {
                    var splitAbove = nums.split(".")
                    if (splitAbove.isNotEmpty()) {
                        for(num in splitAbove) {
                            if (num.isNotEmpty()) {
                                numList+=num.toInt()
                            }
                        }
                    }
                }
            }

            if (numList.size == 2) {
                sumProd = numList[0] * numList[1]
            }
            return sumProd
        }


        fun part1() {
            val file = File("./src/main/resources/day3/actual.txt")
            val charMatrix = mutableListOf<MutableList<Char>>()

            // Read text into a matrix.
            BufferedReader(FileReader(file)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    val charRow = line.toString().toMutableList()
                    charMatrix += charRow
                }
            }

            var sum = 0
            for (i in charMatrix.indices) {
                var stringNum = ""
                var symbolAdjacent = false

                for (j in charMatrix[i].indices) {
                    if (charMatrix[i][j].isDigit()) {
                        stringNum += charMatrix[i][j]
                        if (!symbolAdjacent) {
                            symbolAdjacent = checkNeighbors(charMatrix, i, j)
                        }

                        if (j == charMatrix.size - 1) {
                            if (symbolAdjacent) {
                                sum += stringNum.toInt()
                            }
                        }
                        continue
                    }

                    if (stringNum.isNotEmpty()) {
                        if (symbolAdjacent) {
                            sum += stringNum.toInt()
                        }
                        stringNum = ""
                        symbolAdjacent = false
                    }
                }
            }
            print(sum)
        }

        fun part2() {
            val file = File("./src/main/resources/day3/actual.txt")
            val charMatrix = mutableListOf<MutableList<Char>>()

            // Read text into a matrix.
            BufferedReader(FileReader(file)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    val charRow = line.toString().toMutableList()
                    charMatrix += charRow
                }
            }

            // Get a list of stars touching numbers.
            val starNumList = mutableListOf<Pair<Int, Int>>()
            for (i in charMatrix.indices) {
                for (j in charMatrix[i].indices) {
                    if (charMatrix[i][j] == '*') {
                        if (checkNeighborsNum(charMatrix, i, j)) {
                            starNumList += Pair(i, j)
                        }
                    }
                }
            }

            // Get product of stars touching exactly 2 numbers
            var sum = 0
            for (star in starNumList) {
                sum += getNeighborNumProd(charMatrix, star.first, star.second)
            }
            println(sum)

        }
    }
}
