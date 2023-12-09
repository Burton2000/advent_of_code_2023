import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Day8 {
    companion object {

        fun part1() {
            val file = File("./src/main/resources/day8/actual.txt")
            var instructions = mutableListOf<Char>()
            var elements = mutableMapOf<String, Pair<String, String>>()
            // Read text into a matrix.
            BufferedReader(FileReader(file)).use { br ->
                var line: String?
                var lineNum = 0

                // Parse input.
                while (br.readLine().also { line = it } != null) {
                    if (lineNum == 0){
                        instructions = line.toString().toMutableList()
                    }
                    else if (lineNum == 1) {
                        lineNum++
                        continue
                    }
                    else {
                        var splitLine = line!!.split("=")
                        var inputs = splitLine[1].trim().trim('(', ')').split(',')
                        elements[splitLine[0].trim()] = Pair(inputs[0].trim(), inputs[1].trim())
                    }
                    lineNum++
                }
            }
            var steps = 0
            var instructionStep = 0
            var currentLocation = "AAA"
            val endLocation = "ZZZ"
            var action: Char
            while (currentLocation != endLocation) {
                if (instructionStep == instructions.size) {
                    instructionStep = 0
                }
                action = instructions[instructionStep]

                if (action == 'L') {
                    currentLocation = elements.get(currentLocation)!!.first
                } else {
                    currentLocation = elements.get(currentLocation)!!.second
                }
                steps++
                instructionStep++
            }
            println(steps)
        }

        // https://www.baeldung.com/kotlin/lcm
        fun findLCM(a: Long, b: Long): Long {
            val larger = if (a > b) a else b
            val maxLcm = a * b
            var lcm = larger
            while (lcm <= maxLcm) {
                if (lcm % a == 0L && lcm % b == 0L) {
                    return lcm
                }
                lcm += larger
            }
            return maxLcm
        }

        fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
            var result = numbers[0]
            for (i in 1 until numbers.size) {
                result = findLCM(result, numbers[i])
            }
            return result
        }

        fun part2() {
            val file = File("./src/main/resources/day8/actual.txt")
            var instructions = mutableListOf<Char>()
            var elements = mutableMapOf<String, Pair<String, String>>()
            // Read text into a matrix.
            BufferedReader(FileReader(file)).use { br ->
                var line: String?
                var lineNum = 0

                // Parse input.
                while (br.readLine().also { line = it } != null) {
                    if (lineNum == 0) {
                        instructions = line.toString().toMutableList()
                    } else if (lineNum == 1) {
                        lineNum++
                        continue
                    } else {
                        var splitLine = line!!.split("=")
                        var inputs = splitLine[1].trim().trim('(', ')').split(',')
                        elements[splitLine[0].trim()] = Pair(inputs[0].trim(), inputs[1].trim())
                    }
                    lineNum++
                }
            }

            // Get all starting locations (those ending with 'A')
            var currentLocation = mutableListOf<String>()
            for (ele in elements) {
                if (ele.key[2] == 'A') {
                    currentLocation+=ele.key
                }
            }

            var steps = 0L
            var instructionStep = 0
            //var currentLocation = mutableListOf("11A", "22A")

            var action: Char
            var complete = false
            var cycles = mutableListOf<Long>()
            while (cycles.size != currentLocation.size) {
                if (instructionStep == instructions.size) {
                    instructionStep = 0
                }
                action = instructions[instructionStep]

                if (action == 'L') {
                    for (i in currentLocation.indices) {
                        currentLocation[i] = elements.get(currentLocation[i])!!.first
                    }
                } else {
                    for (i in currentLocation.indices) {
                        currentLocation[i] = elements.get(currentLocation[i])!!.second
                    }
                }
                steps++
                instructionStep++

                for (j in currentLocation.indices) {
                    if (currentLocation[j][2] == 'Z') {
                        cycles+=steps
                    }
                }

            }
            // LCM of:
            //13019
            //14681
            //16897
            //18559
            //19667
            //20221
            println(findLCMOfListOfNumbers(cycles))
        }

    }
}
