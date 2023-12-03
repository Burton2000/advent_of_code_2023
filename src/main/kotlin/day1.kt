import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Day1 {
    companion object {
        fun part1() {
            var sum: Int
            val file = File("./src/main/resources/day1/actual.txt")
            BufferedReader(FileReader(file)).use { br ->
                var line: String?
                sum = 0
                while (br.readLine().also { line = it } != null) {
                    val numericVals = mutableListOf<Char>()
                    line?.forEach {
                        if (it.isDigit()) {
                            numericVals.add(it)
                        }
                    }
                    // Combine first and last values to make a number e.g. 1 3 becomes 13
                    sum += (numericVals.first().toString() + numericVals.last().toString()).toInt()
                }
            }
            println(sum)
        }

        // Return list of indexes where sub string exists in a string.
        fun String?.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> {
            val list = mutableListOf<Int>()
            if (this == null || substr.isBlank()) return list

            var i = -1
            while (true) {
                i = indexOf(substr, i + 1, ignoreCase)
                when (i) {
                    -1 -> return list
                    else -> list.add(i)
                }
            }
        }

        fun part2() {
            val numericStrings = arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
            val replacements = arrayOf("o1e", "t2o", "t3e", "f4r", "f5e", "s6x", "s7n", "e8t", "n9e")
            var sum: Int

            val file = File("./src/main/resources/day1/actual.txt")
            BufferedReader(FileReader(file)).use { br ->
                var line: String?
                sum = 0
                while (br.readLine().also { line = it } != null) {
                    val numericVals = mutableListOf<Char>()
                    var mapVals = mutableMapOf<Int, String>()

                    // Find all occurrences of numeric strings in the line and the start indices.
                    for (c in numericStrings) {
                        if (line.indexesOf(c).isNotEmpty()) {
                            for (idx in line.indexesOf(c)) {
                                mapVals[idx] = c
                            }
                        }
                    }
                    mapVals = mapVals.toSortedMap()
                    if (mapVals.isNotEmpty()) {
                        line = line?.replaceFirst(
                            mapVals.entries.first().value,
                            replacements[numericStrings.indexOf(mapVals.entries.first().value)]
                        )

                        line = line?.replace(
                            mapVals.entries.last().value,
                            replacements[numericStrings.indexOf(mapVals.entries.last().value)]
                        )
                    }

                    line?.forEach {
                        if (it.isDigit()) {
                            numericVals.add(it)
                        }
                    }
                    sum += (numericVals.first().toString() + numericVals.last().toString()).toInt()
                }
            }
            println(sum)
        }
    }
}