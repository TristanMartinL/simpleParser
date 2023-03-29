import scala.Console

object Main {
  def main(args: Array[String]): Unit = {
    args.toList match {
      case "parse"::rest => evaluateProgram(rest.mkString(" "))
      case _ =>
    }
  }

  def evaluateProgram(program: String): Unit = {
    Console.println("Input: ", program)
    val parseResult = Parser.parse(program)
    if (parseResult.successful) {
      Console.println("success")
      Console.println(parseResult.get)
    } else {
      Console.println("\n\nError: " + parseResult.toString)
    }
  }
}