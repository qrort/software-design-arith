package tokenizer

import visitor.TokenVisitor

interface Token {
    fun accept(visitor: TokenVisitor)
}