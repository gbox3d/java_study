package oop2

class ex03 {
    interface Movable {
        fun move()
    }

    interface Attackable {
        fun attack()
    }

    class Hero : Movable, Attackable {
        override fun move() {
            println("Hero moves fast")
        }

        override fun attack() {
            println("Hero attacks")
        }
    }

    class Monster : Movable, Attackable {
        override fun move() {
            println("Monster moves slowly")
        }

        override fun attack() {
            println("Monster attacks")
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val hero = Hero()
            val monster = Monster()
            hero.move()
            hero.attack()
            monster.move()
            monster.attack()
        }
    }
}
