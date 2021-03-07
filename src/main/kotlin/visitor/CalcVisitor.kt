package visitor

import tokenizer.Brace
import tokenizer.NumberToken
import tokenizer.OperationToken
import tokenizer.Token
import java.lang.IllegalStateException
import java.util.*

class CalcVisitor(private val tokens: List<Token>) : TokenVisitor {
    private val st = Stack<NumberToken>()

    fun calc(): Int {
        tokens.forEach { it.accept(this) }
        return st.pop().number
    }

    override fun visit(token: NumberToken) {
        st.push(token)
    }

    override fun visit(token: Brace) {
        throw IllegalStateException("Unexpected token $token")
    }

    override fun visit(token: OperationToken) {
        val second = st.pop()
        val first = st.pop()
        st.push(token.apply(first.number, second.number))
    }
}