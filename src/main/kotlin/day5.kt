import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Day5 {
    companion object {

        fun part1() {
            val file = File("./src/main/resources/day5/actual.txt")

            val keyNames = mutableListOf("seeds:", "seed-to-soil map:", "soil-to-fertilizer map:",
                "fertilizer-to-water map:","water-to-light map:", "light-to-temperature map:",
                "temperature-to-humidity map:", "humidity-to-location map:")

            var almanacMaps = mutableMapOf<String, MutableList<MutableList<Long>>>()
            // Read text.
            BufferedReader(FileReader(file)).use { br ->
                var line: String?
                var currentMap = ""
                while (br.readLine().also { line = it } != null) {
                    if (line in keyNames) {
                        almanacMaps[line!!] = mutableListOf<MutableList<Long>>()
                        currentMap = line as String
                    } else if (line!!.isNotEmpty()) {
                        val charRow = line.toString().split(" ").map{ it.toLong() }.toMutableList()
                        almanacMaps[currentMap]!!+= charRow
                    }
                }
            }
            println("Parsing complete.")

            var lowestLocation = Long.MAX_VALUE
            var location: Long
            //  destination range start, source range start, range length
            for (seed in almanacMaps["seeds:"]!![0]) {
                location = seed
                for (key in keyNames) {
                    if (key == "seeds:") {
                        continue
                    }

                    // Check if input is in any of the source ranges then calculate destination value.
                    for (i in 0..<almanacMaps[key]!!.size) {
                        if (location >= almanacMaps[key]!![i][1] && location < almanacMaps[key]!![i][1] + almanacMaps[key]!![i][2]) {
                            location = almanacMaps[key]!![i][0] + (location - almanacMaps[key]!![i][1])
                            break
                        }
                    }

                }
                // Keep only the lowest value
                if (location < lowestLocation) {
                    lowestLocation = location
                }
            }
            println(lowestLocation)
        }

        class RangeObject {
            var rangeBelowDown: Long = 0
            var rangeBelowUp: Long = 0
            var rangeMidDown: Long = 0
            var rangeMidUp: Long = 0
            var rangeAboveDown: Long = 0
            var rangeAboveUp: Long = 0
        }

        fun part2() {
            val file = File("./src/main/resources/day5/actual.txt")

            val keyNames = mutableListOf("seeds:", "seed-to-soil map:", "soil-to-fertilizer map:",
                "fertilizer-to-water map:","water-to-light map:", "light-to-temperature map:",
                "temperature-to-humidity map:", "humidity-to-location map:")

            var almanacMaps = mutableMapOf<String, MutableList<MutableList<Long>>>()
            // Read text.
            BufferedReader(FileReader(file)).use { br ->
                var line: String?
                var currentMap = ""
                while (br.readLine().also { line = it } != null) {
                    if (line in keyNames) {
                        almanacMaps[line!!] = mutableListOf<MutableList<Long>>()
                        currentMap = line as String
                    } else if (line!!.isNotEmpty()) {
                        val charRow = line.toString().split(" ").map{ it.toLong() }.toMutableList()
                        almanacMaps[currentMap]!!+= charRow
                    }
                }
            }
            println("Parsing complete.")

            var lowestLocation = Long.MAX_VALUE
            var location: Long
            //  destination range start, source range start, range length
            for (i in 0..<almanacMaps["seeds:"]!![0].size step 2) {
                for (j in 0..<almanacMaps["seeds:"]!![0][i+1]) {
                    location = almanacMaps["seeds:"]!![0][i] + j
                    for (key in keyNames) {
                        if (key == "seeds:") {
                            continue
                        }

                        // Check if input is in any of the source ranges then calculate destination value.
                        for (k in 0..<almanacMaps[key]!!.size) {
                            if (location >= almanacMaps[key]!![k][1] && location < almanacMaps[key]!![k][1] + almanacMaps[key]!![k][2]) {
                                location = almanacMaps[key]!![k][0] + (location - almanacMaps[key]!![k][1])
                                break
                            }
                        }

                    }
                    // Keep only the lowest value
                    if (location < lowestLocation) {
                        lowestLocation = location
                    }
                }
            }
            println(lowestLocation)
        }
    }
}
