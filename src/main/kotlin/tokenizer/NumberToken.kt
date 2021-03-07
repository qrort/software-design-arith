package tokenizer

import visitor.TokenVisitor

class NumberToken(val number: Int) : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String = "$number"
}