import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Day2 {
    companion object {
        fun part1() {
            val maxCubeCount = mutableMapOf("red" to 12, "green" to 13, "blue" to 14)
            var pssibleIndexTotal = 0
            var index = 1
            val file = File("./src/main/resources/day2/actual.txt")
            BufferedReader(FileReader(file)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    var possible = true
                    val draws = line?.split(":")?.get(1)?.split(";")
                    if (draws != null) {
                        for (draw in draws) {
                            val cubes = draw.trim().replace(",", "").split(" ")
                            for (x in cubes.indices) {
                                if (cubes[x].toIntOrNull() != null) {
                                    if (cubes[x].toIntOrNull()!! > maxCubeCount.get(cubes[x+1])!!) {
                                        possible = false
                                        break
                                    }
                                }
                            }
                        }
                    }
                    if (possible) {
                        pssibleIndexTotal += index
                    }
                    index+=1
                }
            }
            print(pssibleIndexTotal)
        }

        fun part2() {
            var powerSum = 0

            val file = File("./src/main/resources/day2/actual.txt")
            BufferedReader(FileReader(file)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    val minCubeCount = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)

                    val draws = line?.split(":")?.get(1)?.split(";")
                    if (draws != null) {
                        for (draw in draws) {
                            val cubes = draw.trim().replace(",", "").split(" ")
                            for (x in cubes.indices) {
                                if (cubes[x].toIntOrNull() != null) {
                                    if (cubes[x].toIntOrNull()!! > minCubeCount[cubes[x+1]]!!) {
                                        minCubeCount[cubes[x+1]] = cubes[x].toInt()
                                    }
                                }
                            }
                        }
                    }
                    powerSum += minCubeCount.values.reduce{acc, i -> acc * i}
                }
            }
            print(powerSum)
        }
    }
}
