interface IBaseNumber {
    val value: Int
    fun printValue()
}

class PrimeNumber(override val value: Int) : IBaseNumber {
    override fun printValue() {
        println("Prime Number: $value")
    }
}

class OddNumber(override val value: Int) : IBaseNumber {
    val divisors: List<Int> = findDivisors(value)

    override fun printValue() {
        println("Odd Number: $value, Divisors: $divisors")
    }

    private fun findDivisors(num: Int): List<Int> {
        return (1..num).filter { num % it == 0 }
    }
}

class EvenNumber(override val value: Int) : IBaseNumber {
    val divisors: List<Int> = findDivisors(value)

    override fun printValue() {
        println("Even Number: $value, Divisors: $divisors")
    }

    private fun findDivisors(num: Int): List<Int> {
        return (1..num).filter { num % it == 0 }
    }
}

class PrimeNumberProcessor(val range: IntRange) {

    fun evaluateNumbers(): EvaluationResult {
        val primes = mutableListOf<PrimeNumber>()
        val evens = mutableListOf<EvenNumber>()
        val odds = mutableListOf<OddNumber>()

        for (num in range) {
            when (val type = validateNumber(num)) {
                NumberType.PRIME -> primes.add(PrimeNumber(num))
                NumberType.EVEN -> evens.add(EvenNumber(num))
                NumberType.ODD -> odds.add(OddNumber(num))
            }
        }

        return EvaluationResult(primes, evens, odds)
    }

    private fun validateNumber(num: Int): NumberType {
        return when {
            isPrime(num) -> NumberType.PRIME
            num % 2 == 0 -> NumberType.EVEN
            else -> NumberType.ODD
        }
    }

    private fun isPrime(num: Int): Boolean {
        if (num < 2) return false
        for (i in 2..Math.sqrt(num.toDouble()).toInt()) {
            if (num % i == 0) return false
        }
        return true
    }
}

enum class NumberType {
    PRIME, EVEN, ODD
}

data class EvaluationResult(
    val primes: List<PrimeNumber>,
    val evens: List<EvenNumber>,
    val odds: List<OddNumber>
)

fun main() {
    val processor = PrimeNumberProcessor(1..20)
    val result = processor.evaluateNumbers()

    result.primes.forEach { it.printValue() }
    result.evens.forEach { it.printValue() }
    result.odds.forEach { it.printValue() }
}
