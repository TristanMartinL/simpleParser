import scala.Console

object Main {

  /***
   *  Parser to match different variable assignments in JavaScript style.
   *
   *  type "run parse <expr>" in the sbt shell to try different variable declarations and observe the resulting parse tree!
   *  use for <expr> e.g.
   *
   *  var ex = 1
   *  var ex = 'SecondExample'
   *  var ex = [ 0, 1, 2, 'A string in an array' ]
   *  var ex = { first: 1,  second: { inner: 2 }, third: [ 3 ]  }
   *  var avariablewithalongname = {}
   *
   */
  def main(args: Array[String]): Unit = {
    args.toList match {
      // for now, we only want to parse the input. mkString(" ") puts all parameters into a single string
      case "parse"::rest => evaluateProgram(rest.mkString(" "))
      case _ =>
    }
  }

  def evaluateProgram(program: String): Unit = {
    Console.println("Input: ", program)
    val parseResult = Parser.parse(program)
    if (parseResult.successful) {
      Console.println("success")
      // print the parse tree
      Console.println(parseResult.get)
    } else {
      Console.println("\n\nError: " + parseResult.toString)
    }
  }
}