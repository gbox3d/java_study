package exceptionapi

class ex02 {
    class InvalidAgeException(message: String) : RuntimeException(message)

    companion object {
        fun checkAge(age: Int) {
            if (age < 0 || age > 150) {
                throw InvalidAgeException("invalid age: $age")
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            try {
                checkAge(-5)
            } catch (e: InvalidAgeException) {
                println(e.message)
            }
        }
    }
}
