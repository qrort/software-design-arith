package visitor

import tokenizer.Brace
import tokenizer.NumberToken
import tokenizer.OperationToken
import tokenizer.Token

class PrintVisitor(private val tokens: List<Token>) : TokenVisitor {
    fun print() {
        tokens.forEach { it.accept(this) }
        println()
    }

    override fun visit(token: NumberToken) {
        print("$token ")
    }

    override fun visit(token: Brace) {
        print("$token ")
    }

    override fun visit(token: OperationToken) {
        print("$token ")
    }
}