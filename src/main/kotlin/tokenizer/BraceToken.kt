package tokenizer

import visitor.TokenVisitor

open class Brace(private val brace: String) : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String = brace
}

class CloseBrace : Brace(")")

class OpenBrace : Brace("(")