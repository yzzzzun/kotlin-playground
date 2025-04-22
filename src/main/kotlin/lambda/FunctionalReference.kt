package lambda

data class Complex(val real: Double, val imaginary: Double) {
    fun double(): Complex {
        return Complex(real * 2, imaginary * 2)
    }
    fun times(num: Int): Complex {
        return Complex(real * num, imaginary * num)
    }
}

fun zeroComplex() : Complex = Complex(0.0, 0.0)
fun makeComplex(
    real: Double = 0.0,
    imaginary: Double = 0.0,
) = Complex(real, imaginary)

fun Complex.plus(other: Complex): Complex {
    return Complex(real + other.real, imaginary + other.imaginary)
}
fun Int.toComplex() = Complex(this.toDouble(), 0.0)

fun main() {
    val f1: () -> Complex = ::zeroComplex
    val f2: (Double, Double) -> Complex = ::makeComplex

    val f3: (Int) -> Complex = Int::toComplex

    println(f1())
    println(f2(1.0, 2.0))
    produceComplex1(::makeComplex)
    produceComplex2(::makeComplex)
    produceComplex3(::makeComplex)
}


fun produceComplex1(producer: () -> Complex) {
    println(producer)
}
fun produceComplex2(producer: (Double) -> Complex) {
    println(producer)
}
fun produceComplex3(producer: (Double,Double) -> Complex) {
    println(producer)
}

