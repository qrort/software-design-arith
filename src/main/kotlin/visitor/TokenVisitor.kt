package visitor

import tokenizer.Brace
import tokenizer.NumberToken
import tokenizer.OperationToken

interface TokenVisitor {
    fun visit(token: NumberToken)
    fun visit(token: Brace)
    fun visit(token: OperationToken)
}