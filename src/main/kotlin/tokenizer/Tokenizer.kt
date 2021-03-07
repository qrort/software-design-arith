package tokenizer

import java.io.InputStream

class Tokenizer(inputStream: InputStream) {
    private val reader = inputStream
    private var tokens = ArrayList<Token>()
    private var curChar: Char? = null
    private var startState = Start()

    fun tokenize(): List<Token> {
        curChar = nextChar()
        startState.next()
        return tokens
    }

    private interface State {
        fun next()
    }

    fun nextChar(): Char? {
        val r = reader.read()
        if (r == -1) {
            return null
        }
        return r.toChar()
    }

    private fun charToToken(c: Char): Token? {
        return when (c) {
            '+' -> AddToken()
            '-' -> SubToken()
            '*' -> MulToken()
            '/' -> DivToken()
            '(' -> OpenBrace()
            ')' -> CloseBrace()
            else -> null
        }
    }

    private inner class Start : State {
        override fun next() {
            when (curChar) {
                null -> {
                    return
                }
                '+', '-', '*', '/', '(', ')' -> {
                    tokens.add(charToToken(curChar!!)!!)
                    curChar = nextChar()
                    startState.next()
                }
                in '0'..'9' -> {
                    Number().next()
                }
                else -> {
                    require(curChar!!.isWhitespace()) { "Illegal character $curChar" }
                    curChar = nextChar()
                    startState.next()
                }
            }
        }
    }

    private inner class Number : State {
        private var buf = ""
        override fun next() {
            while (curChar != null && curChar!!.isDigit()) {
                buf += curChar
                curChar = nextChar()
            }
            tokens.add(NumberToken(Integer.parseInt(buf)))
            Start().next()
        }
    }
}