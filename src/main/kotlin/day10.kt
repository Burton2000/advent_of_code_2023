import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Day10 {
    companion object {

        private val northOut = arrayOf('|', '7', 'F')
        private val eastOut = arrayOf('-', 'J', '7')
        private val southOut = arrayOf('|', 'L', 'J')
        private val westOut = arrayOf('-', 'F', 'L')

        private fun getNext(matrix: MutableList<MutableList<Char>>, currentPos: Pair<Int,Int>): Pair<Int, Int> {
            val currentChar = matrix[currentPos.first][currentPos.second]
            val up = matrix.getOrNull(currentPos.first-1)?.getOrNull(currentPos.second)
            val down = matrix.getOrNull(currentPos.first+1)?.getOrNull(currentPos.second)
            val left = matrix.getOrNull(currentPos.first)?.getOrNull(currentPos.second-1)
            val right = matrix.getOrNull(currentPos.first)?.getOrNull(currentPos.second+1)

            val dirUp = Pair(currentPos.first-1, currentPos.second)
            val dirDown = Pair(currentPos.first+1, currentPos.second)
            val dirLeft = Pair(currentPos.first, currentPos.second-1)
            val dirRight = Pair(currentPos.first, currentPos.second+1)

            if (currentChar == '|') {
                if (up in northOut) {
                    return dirUp
                }
                if (down in southOut) {
                    return dirDown
                }
            }
            else if (currentChar == '-') {
                if (right in eastOut) {
                    return dirRight
                }
                if (left in westOut) {
                    return dirLeft
                }
            }
            else if (currentChar == 'L') {
                if (up in northOut) {
                    return dirUp
                }
                if (right in eastOut) {
                    return dirRight
                }
            }
            else if (currentChar == 'J') {
                if (up in northOut) {
                    return dirUp
                }
                if (left in westOut) {
                    return dirLeft
                }
            }
            else if (currentChar == '7') {
                if (left in westOut) {
                    return dirLeft
                }
                if (down in southOut) {
                    return dirDown
                }
            }
            else if (currentChar == 'F'){
                if (right in eastOut) {
                    return dirRight
                }
                if (down in southOut) {
                    return dirDown
                }
            }
            return currentPos
        }

        fun part1() {
            val file = File("./src/main/resources/day10/actual.txt")
            val charMatrix = mutableListOf<MutableList<Char>>()

            // Read text into a matrix.
            BufferedReader(FileReader(file)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    val charRow = line.toString().toMutableList()
                    charMatrix += charRow
                }
            }

            // Find S
            var sPosition = Pair(0, 0)
            for (row in charMatrix.indices) {
                for (col in charMatrix[row].indices) {
                    if (charMatrix[row][col] == 'S') {
                        sPosition = Pair(row, col)
                    }
                }
            }

            // Find 2 spaces connected to S
            val up = charMatrix.getOrNull(sPosition.first-1)?.getOrNull(sPosition.second)
            val down = charMatrix.getOrNull(sPosition.first+1)?.getOrNull(sPosition.second)
            val left = charMatrix.getOrNull(sPosition.first)?.getOrNull(sPosition.second-1)
            val right = charMatrix.getOrNull(sPosition.first)?.getOrNull(sPosition.second+1)
            val startPositions = mutableListOf<Pair<Int, Int>>()

            if (up in northOut) {
                startPositions += Pair(sPosition.first-1, sPosition.second)
            }
            if (down in southOut) {
                startPositions += Pair(sPosition.first+1, sPosition.second)
            }
            if (left in westOut) {
                startPositions += Pair(sPosition.first, sPosition.second-1)
            }
            if (right in eastOut) {
                startPositions += Pair(sPosition.first, sPosition.second+1)
            }

            var complete = false
            var cur1 = startPositions[0]
            var cur2 = startPositions[1]
            var next1: Pair<Int, Int>
            var next2: Pair<Int, Int>
            var steps = 1
            while (!complete) {
                next1 = getNext(charMatrix, cur1)
                next2 = getNext(charMatrix, cur2)
                charMatrix[cur1.first][cur1.second] = '.'
                charMatrix[cur2.first][cur2.second] = '.'
                steps++

                if (next1 == next2) {
                    println(steps)
                    complete = true
                } else {
                    cur1 = next1
                    cur2 = next2
                }
            }
        }

        fun part2() {
            val file = File("./src/main/resources/day10/actual.txt")
            val charMatrix = mutableListOf<MutableList<Char>>()
            val charMatrix2 = mutableListOf<MutableList<Char>>()

            // Read text into a matrix.
            BufferedReader(FileReader(file)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    val charRow = line.toString().toMutableList()
                    charMatrix += charRow
                }
            }

            BufferedReader(FileReader(file)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    val charRow = line.toString().toMutableList()
                    charMatrix2 += charRow
                }
            }

            // Find S
            var sPosition = Pair(0, 0)
            for (row in charMatrix.indices) {
                for (col in charMatrix[row].indices) {
                    if (charMatrix[row][col] == 'S') {
                        sPosition = Pair(row, col)
                    }
                }
            }

            // Find 2 spaces connected to S
            val up = charMatrix.getOrNull(sPosition.first-1)?.getOrNull(sPosition.second)
            val down = charMatrix.getOrNull(sPosition.first+1)?.getOrNull(sPosition.second)
            val left = charMatrix.getOrNull(sPosition.first)?.getOrNull(sPosition.second-1)
            val right = charMatrix.getOrNull(sPosition.first)?.getOrNull(sPosition.second+1)
            val startPositions = mutableListOf<Pair<Int, Int>>()

            if (up in northOut) {
                startPositions += Pair(sPosition.first-1, sPosition.second)
            }
            if (down in southOut) {
                startPositions += Pair(sPosition.first+1, sPosition.second)
            }
            if (left in westOut) {
                startPositions += Pair(sPosition.first, sPosition.second-1)
            }
            if (right in eastOut) {
                startPositions += Pair(sPosition.first, sPosition.second+1)
            }

            var complete = false
            var cur1 = startPositions[0]
            var cur2 = startPositions[1]
            var next1: Pair<Int, Int>
            var next2: Pair<Int, Int>
            var steps = 1
            val edgeNodes = mutableListOf<Pair<Int,Int>>(sPosition, cur1, cur2)
            while (!complete) {
                next1 = getNext(charMatrix, cur1)
                next2 = getNext(charMatrix, cur2)
                charMatrix[cur1.first][cur1.second] = '.'
                charMatrix[cur2.first][cur2.second] = '.'
                steps++

                if (next1 == next2) {
                    complete = true
                    edgeNodes+=next1
                } else {
                    edgeNodes+=next1
                    edgeNodes+=next2
                    cur1 = next1
                    cur2 = next2
                }
            }

            var sumIn = 0

            for (i in 0..<charMatrix2.size) {
                var linesPassed = 0
                var lastseen = '.' //L7 //FJ

                for (j in 0..<charMatrix2[i].size) {
                    if (Pair(i, j) in edgeNodes) {
                        if (charMatrix2[i][j]=='|') {
                            lastseen = '|'
                            linesPassed++
                            continue
                        } else if (charMatrix2[i][j]=='7' && lastseen=='L') {
                            lastseen='7'
                            continue
                        } else if (charMatrix2[i][j]=='J' && lastseen=='F') {
                            lastseen='J'
                            continue
                        } else if (charMatrix2[i][j]=='-') {
                            continue
                        } else {
                            linesPassed++
                            lastseen=charMatrix2[i][j]
                            continue
                        }
                    } else {
                        if (linesPassed % 2 != 0) {
                            sumIn++
                        }
                        continue
                    }
                }
            }

            println(sumIn)
        }
    }
}
