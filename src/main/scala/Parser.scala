import scala.util.parsing.combinator._

object Parser extends JavaTokenParsers {

  def parse(code: String ): ParseResult[NodeAssign] = parseAll(assign, code)

  def assign: Parser[NodeAssign] = "var" ~> """[a-z]*""".r ~ "=" ~ expr ^^ {
    case name ~ _ ~ expr  => NodeAssign(name, expr)
  }

  def expr: Parser[NodeExpr] = (text | addsub | array | obj) ^^ {
     content => NodeExpr(content)
  }

  def text: Parser[NodeText] = """'[a-z]*'""".r ^^  {
    text => NodeText(text)
  }

  def number: Parser[Node] = """([1-9][0-9]*)|0""".r ^^ {
    number => NodeNumber(number.toInt)
  }

  def addsub: Parser[Node] = number ~ rep(("+" | "-") ~ number) ^^ {
    case number ~ Nil => number
    case number ~ rhs_ops =>  rhs_ops.foldLeft(number)((currNum, rhs) => rhs match {
      case "+" ~ plusNum => NodeAdd(currNum, plusNum)
      case "-" ~ minusNum => NodeMinus(currNum, minusNum)
      case _ =>  ???
    } )
  }
  def array: Parser[NodeArray] = "[" ~> repsep(expr, ",")  <~ "]" ^^ {
    exprs => NodeArray(exprs)
  }

  def obj: Parser[NodeObject] = "{" ~> repsep(objmember, ",") <~ "}" ^^ {
    exprs => NodeObject(exprs )
  }

  def objmember: Parser[NodeObjectMember] = "'" ~> """[a-z]*""".r ~ "'" ~ ":" ~ expr ^^ {
    case name ~ _ ~ _ ~ member => NodeObjectMember(name, member)
  }


}