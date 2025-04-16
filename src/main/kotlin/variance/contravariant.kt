package variance

class Consumer<in T> {
    fun consume(item: T) {
        println("Consuming item: $item")
    }
}

fun main() {
    val numberConsumer: Consumer<Number> = Consumer()
    numberConsumer.consume(2.7)

    val intConsumer:Consumer<Int> = numberConsumer
    intConsumer.consume(2)

    val floatConsumer:Consumer<Float> = numberConsumer
    floatConsumer.consume(2.8F)

    val anyConsumer: Consumer<Any> = Consumer()
    anyConsumer.consume("Hello")

    val stringConsumer: Consumer<String> = anyConsumer
    stringConsumer.consume("World")

    val charConsumer: Consumer<Char> = anyConsumer
    charConsumer.consume('A')
}