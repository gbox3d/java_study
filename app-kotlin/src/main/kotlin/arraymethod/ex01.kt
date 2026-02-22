package arraymethod

import java.util.Arrays

class ex01 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val scores = intArrayOf(90, 85, 95)
            println("array object: $scores")
            println("array values: ${Arrays.toString(scores)}")
            println(scores.joinToString(" "))
        }
    }
}
