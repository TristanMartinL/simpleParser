import scala.util.parsing.combinator._

object Parser extends JavaTokenParsers {

  // Parse the input string (code) and start with the rule for assign
  def parse(code: String): ParseResult[NodeAssign] = parseAll(assign, code)

  /** parse a variable declaration:
   *
   * "var"       matches the symbols "var"
   *
   *  ~          tilde concatenates match patterns
   *  ~> or <~   concatenates different match patterns, but ignores the left side/right side in further processing (with a normal tilde, we would have to write "case  _ ~ name ~ _ ~ expr  => NodeAssign(name, expr)")
   *             -> both operators skip whitespace, so the following input will be accepted: var      x     =     1
   *
   *  """        start a regex expression, .r transforms the parsed regex into a string
   *  expr       tells the parser to match with the rule for expr
   *
   *  ^^         match operator, puts all matched parts into the variables, declared after the case keyword
   *
  **/
  def assign: Parser[NodeAssign] = "var" ~> """[a-z]+""".r ~ "=" ~ expr  ^^ {
    /**
     *  process matched input: grab the name of the declared variable (matched by the regex), skip the equals symbol (=) and take whatever expr could parse
     *
     *  return a node, which represents the variable assignment. The node content is the name of the variable and the Node for the expression on the right side
     */
    case name ~ _ ~ expr  => NodeAssign(name, expr)
  }

  /**
   * process an expression. An expression can be a text string, a number, an array or an object.
   *
   * Note, that the order of statements is important. The parser will try to match on parser after the other.
   *
   * return a generic expression Node, which is a wrapper for the concrete content (NodeText, NodeNumber, NodeArray, NodeObject)
   */
  def expr: Parser[NodeExpr] = (text | number | array | obj) ^^ {
     content => NodeExpr(content)
  }

  // parse a string: single quote, a character, characters, numbers and whitespace mixed, single quote
  def text: Parser[NodeText] = "'" ~> """[a-zA-Z][a-zA-Z0-9\x20]*""".r <~ "'" ^^  {
    text => NodeText(text)
  }

  // parse numbers, zeros at the start (01, 042) are not allowed (only if the number is zero)
  def number: Parser[NodeNumber] = """([1-9][0-9]*)|0""".r ^^ {
    // we matched a string, use .toInt to get the integer value
    number => NodeNumber(number.toInt)
  }

  // an array contains several expressions, it is also possible to mix them e.g. [0, 'text', { }]
  // repsep is a function of the JavaTokenParser class. It allows us to parse an arbitrary amount of expr, each separated by a comma
  def array: Parser[NodeArray] = "[" ~> repsep(expr, ",")  <~ "]" ^^ {
    // we get a list of expressions and put it into the array node
    exprs => NodeArray(exprs)
  }

  // an object consists out of an arbitrary amount of members, e.g. { first: 1,  second: { inner: 2 }, third: [ 3 ]  }
  def obj: Parser[NodeObject] = "{" ~> repsep(objmember, ",") <~ "}" ^^ {
    // same as above, but with object members
    members => NodeObject(members)
  }

  // each member has one name and an expression
  def objmember: Parser[NodeObjectMember] = """[a-z]+""".r  ~ ":" ~ expr ^^ {
    case name ~ _ ~  member => NodeObjectMember(name, member)
  }


}