import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Day7 {
    companion object {

        enum class HandTypes {
            FIVE,
            FOUR,
            FULL,
            THREE,
            TWOPAIR,
            PAIR,
            HIGH
        }

        val cardValue = mapOf(
            "2" to 12,
            "3" to 11,
            "4" to 10,
            "5" to 9,
            "6" to 8,
            "7" to 7,
            "8" to 6,
            "9" to 5,
            "T" to 4,
            "J" to 3,
            "Q" to 2,
            "K" to 1,
            "A" to 0)

        val cardValueJoker = mapOf(
            "J" to 12,
            "2" to 11,
            "3" to 10,
            "4" to 9,
            "5" to 8,
            "6" to 7,
            "7" to 6,
            "8" to 5,
            "9" to 4,
            "T" to 3,
            "Q" to 2,
            "K" to 1,
            "A" to 0)

        data class CamelCardHand(val hand: List<Char>, val bet: Int, val type: HandTypes)

        fun whatHandIsIt(hand: List<Char>): HandTypes {
            if (hand.groupingBy { it }.eachCount().filter { it.value == 5 }.isNotEmpty()) {
                return HandTypes.FIVE
            }
            else if (hand.groupingBy { it }.eachCount().filter { it.value == 4 }.isNotEmpty()) {
                return HandTypes.FOUR
            }
            else if (hand.groupingBy { it }.eachCount().filter { it.value == 3 }.isNotEmpty() &&
                hand.groupingBy { it }.eachCount().filter { it.value == 2 }.isNotEmpty()) {
                return HandTypes.FULL
            }
            else if (hand.groupingBy { it }.eachCount().filter { it.value == 3 }.isNotEmpty()) {
                return HandTypes.THREE
            }
            else if (hand.groupingBy { it }.eachCount().filter { it.value == 2 }.size == 2) {
                return HandTypes.TWOPAIR
            }
            else if (hand.groupingBy { it }.eachCount().filter { it.value == 2 }.size == 1) {
                return HandTypes.PAIR
            }
            else {
                return HandTypes.HIGH
            }
        }

        fun whatHandIsItJoker(hand: List<Char>): HandTypes {
            val jokerCount = hand.count { it == 'J'}

            var handtype = whatHandIsIt(hand)

            if (jokerCount == 1) {
                if (handtype == HandTypes.FOUR) {
                    return HandTypes.FIVE
                }
                if (handtype == HandTypes.THREE) {
                    return HandTypes.FOUR
                }
                if (handtype == HandTypes.TWOPAIR) {
                    return HandTypes.FULL
                }
                if (handtype == HandTypes.PAIR) {
                    return HandTypes.THREE
                }
                if (handtype == HandTypes.HIGH) {
                    return HandTypes.PAIR
                }
            }
            if (jokerCount == 2) {
                if (handtype == HandTypes.FULL) {
                    return HandTypes.FIVE
                }
                if (handtype == HandTypes.TWOPAIR) {
                    return HandTypes.FOUR
                }
                if (handtype == HandTypes.PAIR) {
                    return HandTypes.THREE
                }
            }
            if (jokerCount == 3) {
                if (handtype == HandTypes.THREE) {
                    return HandTypes.FOUR
                }
                if (handtype == HandTypes.FULL) {
                    return HandTypes.FIVE
                }
            }
            if (jokerCount == 4) {
                if (handtype == HandTypes.FOUR) {
                    return HandTypes.FIVE
                }
            }
            return handtype

        }

        fun part1() {
            val file = File("./src/main/resources/day7/actual.txt")

            val allHandsList = mutableListOf<CamelCardHand>()

            // Parse input into a list.
            BufferedReader(FileReader(file)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    val charRow = line.toString().split(" ")
                    val cardsChar = charRow[0].toList()
                    val bet = charRow[1].toInt()
                    var handtype = whatHandIsIt(cardsChar)
                    allHandsList += CamelCardHand(cardsChar, bet, handtype)
                }
            }

            allHandsList.sortWith(compareBy<CamelCardHand> {
                it.type }
                .thenBy { cardValueJoker[it.hand[0].toString()] }
                .thenBy { cardValueJoker[it.hand[1].toString()] }
                .thenBy { cardValueJoker[it.hand[2].toString()] }
                .thenBy { cardValueJoker[it.hand[3].toString()] }
                .thenBy { cardValueJoker[it.hand[4].toString()] })

            var totalWinnings = 0
            for (i in 0..<allHandsList.size) {
                totalWinnings += (allHandsList.size - i) * allHandsList[i].bet
            }
            println(totalWinnings)

        }

        fun part2() {
            val file = File("./src/main/resources/day7/actual.txt")

            val allHandsList = mutableListOf<CamelCardHand>()

            // Parse input into a list.
            BufferedReader(FileReader(file)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    val charRow = line.toString().split(" ")
                    val cardsChar = charRow[0].toList()
                    val bet = charRow[1].toInt()
                    var handtype = whatHandIsItJoker(cardsChar)
                    allHandsList += CamelCardHand(cardsChar, bet, handtype)
                }
            }

            allHandsList.sortWith(compareBy<CamelCardHand> {
                it.type }
                .thenBy { cardValueJoker[it.hand[0].toString()] }
                .thenBy { cardValueJoker[it.hand[1].toString()] }
                .thenBy { cardValueJoker[it.hand[2].toString()] }
                .thenBy { cardValueJoker[it.hand[3].toString()] }
                .thenBy { cardValueJoker[it.hand[4].toString()] })

            var totalWinnings = 0
            for (i in 0..<allHandsList.size) {
                totalWinnings += (allHandsList.size - i) * allHandsList[i].bet
            }
            println(totalWinnings)
        }

    }
}
