package variance

class Box<out T>

open class Dog
class Puppy : Dog()

val dogBox: Box<Dog> = Box<Puppy>()
//val puppyBox: Box<Puppy> = Box<Dog>()

/*************************************************/

class Box2<in T>

open class Dog2
class Puppy2 : Dog2()

//val dogBox2: Box2<Dog2> = Box<Puppy2>()
val puppyBox2: Box2<Puppy2> = Box2<Dog2>()

/*************************************************/

interface Animal{
    fun pet()
}

class Cat(val name: String) : Animal{
    override fun pet() {
        println("$name says meow")
    }
}

fun petAnimals(animals: List<Animal>) {
    for (animal in animals) {
        animal.pet()
    }
}

fun main() {
    val animals = listOf(Cat("Tom"), Cat("Jerry"))
    petAnimals(animals)
}