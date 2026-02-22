package arraymethod

import java.util.Arrays

class ex03 {
    companion object {
        fun sortArray(arr: IntArray) {
            for (i in 0 until arr.size - 1) {
                for (j in 0 until arr.size - i - 1) {
                    if (arr[j] > arr[j + 1]) {
                        val temp = arr[j]
                        arr[j] = arr[j + 1]
                        arr[j + 1] = temp
                    }
                }
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val data = intArrayOf(8, 3, 5, 1, 9)
            sortArray(data)
            println(Arrays.toString(data))
        }
    }
}
