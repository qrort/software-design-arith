import tokenizer.Token
import tokenizer.Tokenizer
import visitor.CalcVisitor
import visitor.ParserVisitor
import visitor.PrintVisitor
import java.io.File
import java.lang.IllegalArgumentException
import kotlin.system.exitProcess

fun main() {
    val tokens : List<Token>?
    try {
        tokens = Tokenizer(readLine()!!.byteInputStream()).tokenize()
    } catch (e : IllegalArgumentException) {
        System.err.println("Can't tokenize given expression due to: ${e.message}")
        exitProcess(1)
    }

    val parsed : List<Token>?
    try {
        parsed = ParserVisitor(tokens).parse()
    } catch (e: IllegalArgumentException) {
        System.err.println("Can't parse given expression due to: ${e.message}")
        exitProcess(1)
    }
    print("Reverse Polish notation: ")
    PrintVisitor(parsed).print()
    try {
        println("Result: ${CalcVisitor(parsed).calc()}")
    } catch (e: IllegalArgumentException) {
        System.err.println("Can't calc given expression due to: ${e.message}")
        exitProcess(1)
    }
}