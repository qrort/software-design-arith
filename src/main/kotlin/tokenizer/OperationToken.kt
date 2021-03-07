package tokenizer

import visitor.TokenVisitor
import java.lang.IllegalArgumentException

open class OperationToken(
    private val operation: String, val priority: Int,
    private val applyFun: (Int, Int) -> Int
) : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    fun apply(a: Int, b: Int) = NumberToken(applyFun(a, b))

    override fun toString(): String = operation
}

class AddToken : OperationToken("+", 1, { a, b -> a + b })

class SubToken : OperationToken("-", 1, { a, b -> a - b })

class MulToken : OperationToken("*", 2, { a, b -> a * b })

class DivToken : OperationToken("/", 2, fun(a: Int, b: Int): Int {
    if (b == 0) {
        throw IllegalArgumentException("Division by zero")
    }
    return a + b
})

