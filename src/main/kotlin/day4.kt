import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import kotlin.math.pow

class Day4 {
    companion object {

        fun part1() {
            val file = File("./src/main/resources/day4/actual.txt")

            var sum = 0.0
            // Read text into a matrix.
            BufferedReader(FileReader(file)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    val lotteryNums = line?.split(":")?.get(1)?.split("|")
                    val winningNums = lotteryNums?.get(0)?.trim()?.split(" ")?.map{ it.trim() }?.toMutableList()
                    val myNums = lotteryNums?.get(1)?.trim()?.split(" ")?.map{ it.trim() }?.toMutableList()
                    winningNums?.removeAll(mutableListOf(""))
                    myNums?.removeAll(mutableListOf(""))

                    val winNumsInt = winningNums?.toMutableList()?.map{it.toInt()}?.asIterable()
                    val myNumsInt = myNums?.toMutableList()?.map{it.toInt()}?.asIterable()
                    val numMatches  = winNumsInt?.intersect(myNumsInt!!.toSet())
                    if (numMatches!!.isNotEmpty()) {
                        sum += 2.0.pow(numMatches.size-1)
                    }
                }
            }
            println(sum)
        }

        fun part2() {
            val file = File("./src/main/resources/day4/actual.txt")
            // Store our total card counts in a Map.
            val cardCount: MutableMap<Int, Int> = mutableMapOf(1 to 1)

            BufferedReader(FileReader(file)).use { br ->
                var line: String?

                var lineNumber = 1

                while (br.readLine().also { line = it } != null) {
                    val lotteryNums = line?.split(":")?.get(1)?.split("|")
                    val winningNums = lotteryNums?.get(0)?.trim()?.split(" ")?.map { it.trim() }?.toMutableList()
                    val myNums = lotteryNums?.get(1)?.trim()?.split(" ")?.map { it.trim() }?.toMutableList()
                    winningNums?.removeAll(mutableListOf(""))
                    myNums?.removeAll(mutableListOf(""))

                    val winNumsInt = winningNums?.toMutableList()?.map { it.toInt() }?.asIterable()
                    val myNumsInt = myNums?.toMutableList()?.map { it.toInt() }?.asIterable()
                    val numMatches = winNumsInt?.intersect(myNumsInt!!.toSet())!!.size

                    lineNumber++
                    cardCount[lineNumber] = cardCount.getOrPut(lineNumber) {0} + 1

                    for (j in 0..<cardCount[lineNumber-1]!!) {
                        for (i in 0..<numMatches) {
                            cardCount[lineNumber+i] = cardCount.getOrPut(lineNumber+i) {0} + 1
                        }
                    }

                }
                // Count how many total cards we have.
                var sum = 0
                for (k in 1..lineNumber-1) {
                    sum += cardCount[k]!!
                }
                println(sum)
            }
        }
    }
}
