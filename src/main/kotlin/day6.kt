class Day6 {
    companion object {

        fun part1() {
            val timeInput = listOf(62, 64, 91, 90)
            val distanceInput = listOf(553, 1010, 1473, 1074)
            var recordsBeat = 0
            var finalAnswer = 1

            for (raceIdx in timeInput.indices) {
                for (time in 0..< timeInput[raceIdx]) {
                    var distance = time * (timeInput[raceIdx]-time)
                    if (distance > distanceInput[raceIdx]) {
                        recordsBeat++
                    }
                }
                finalAnswer*=recordsBeat
                recordsBeat=0
            }
            println(finalAnswer)
        }

        fun part2() {
            val timeInput: Long = 62649190
            val distanceInput: Long = 553101014731074
            var recordsBeat = 0
            var distance: Long
            for (time in 0..< timeInput) {
                distance = time * (timeInput-time)
                if (distance > distanceInput) {
                    recordsBeat++
                }
            }
            println(recordsBeat)
        }

    }
}
