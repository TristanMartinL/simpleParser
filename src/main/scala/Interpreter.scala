import scala.language.postfixOps
import Utils._


class Interpreter (reader: () => SimpleValue, writer: String => _) {

  def interpret(program: NodeBlock): SimpleValue = {
    interpret(new Environments(), program)
  }

  private def getValue(e: Environments, name: String) = {
     e(name)
  }

  def interpret(e: Environments, n: Node) : SimpleValue = n match {
    case NodeBlock(list) => SimpleVarList( list.map(item => interpret(e, item)))
    case NodeVarAccess(id) => {
      id match {
        case NodeId(name) => {
          getValue(e, name)
        }
        case _ => throw new Exception();
      }
    }
    case NodeAdd(lhs, rhs) => (interpret(e, lhs) , interpret(e, rhs)) match {
      case (SimpleNumber(lhs), SimpleNumber(rhs)) => SimpleNumber(lhs + rhs)
      case (_, _) => throw new Exception("Invalid operands for add node")
    }
    case NodeSub(lhs, rhs) => (interpret(e, lhs), interpret(e, rhs)) match {
      case (SimpleNumber(lhs), SimpleNumber(rhs)) => SimpleNumber(lhs - rhs)
      case (_, _) => throw new Exception("Invalid operands for sub node")
    }
    case NodeMult(lhs, rhs) => (interpret(e, lhs), interpret(e, rhs)) match {
      case (SimpleNumber(lhs), SimpleNumber(rhs)) => SimpleNumber(lhs * rhs)
      case (_, _) => throw new Exception("Invalid operands for mult node")
    }
    case NodeDiv(lhs, rhs) => (interpret(e, lhs), interpret(e, rhs)) match {
      case (SimpleNumber(lhs), SimpleNumber(rhs)) => SimpleNumber(lhs / rhs)
      case (_, _) => throw new Exception("Invalid operands for div node")
    }
    case NodeAssign(varid, expr) => (varid, interpret(e, expr)) match {
      case (NodeId(varid), value) => {
        e.addOne(varid, value);
        value
      }
    }
    case NodeExpr(content) => interpret(e, content)
    case NodeText(content) => SimpleText(content)
    case NodeNumber(content) => SimpleNumber(content)
    case NodeArray(content) => SimpleArray( content.map(item => interpret(e, item)))
    case NodeObject(content) => SimpleObject(content.map(item =>  interpret(e, item)))
    case NodeObjectMember(id, content) => SimpleObjectMember(id, interpret(e, content))

  }
}
