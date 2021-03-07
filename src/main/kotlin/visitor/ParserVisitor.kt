package visitor

import tokenizer.*
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.util.*
import kotlin.collections.ArrayList

class ParserVisitor(private val tokens: List<Token>) : TokenVisitor {
    private val st = Stack<Token>()
    private val res = ArrayList<Token>()
    private var cur = 0

    private fun nextToken(): Token? {
        if (cur + 1 >= tokens.size)
            return null
        return tokens[cur + 1]
    }

    fun parse(): List<Token> {
        tokens.forEach { it.accept(this); cur++ }
        while (!st.empty()) {
            if (st.peek() !is OperationToken) {
                throw IllegalArgumentException("Unexpected token: ${st.peek()}")
            }
            res.add(st.pop())
        }
        return res
    }

    override fun visit(token: NumberToken) {
        res.add(token)
    }

    override fun visit(token: Brace) {
        when (token) {
            is OpenBrace -> {
                when (val t = nextToken()) {
                    null -> throw IllegalArgumentException("Unexpected end after open brace")
                    is OperationToken -> throw IllegalArgumentException("Unexpected operation '$t' after open brace")
                    is CloseBrace -> throw IllegalArgumentException("Unexpected close brace after open brace")
                    is NumberToken -> st.push(token)
                }
            }
            is CloseBrace -> {
                when (val t = nextToken()) {
                    is NumberToken -> throw IllegalArgumentException("Unexpected number '$t' after close brace")
                    is OpenBrace -> throw IllegalArgumentException("Unexpected open brace after close brace")
                }
                while (!st.empty() && st.peek() !is OpenBrace) {
                    res.add(st.pop())
                }
                if (st.empty()) {
                    throw IllegalStateException("Cant find matching brace")
                } else {
                    st.pop()
                }
            }
        }
    }

    override fun visit(token: OperationToken) {
        when (nextToken()) {
            null -> throw IllegalArgumentException("Unexpected end after operation '$token'")
            is CloseBrace -> throw IllegalArgumentException("Unexpected close brace after operation '$token'")
        }
        while (!st.empty() && st.peek() is OperationToken && (st.peek() as OperationToken).priority > token.priority) {
            res.add(st.pop())
        }
        st.push(token)
    }
}