/**
 * This file contains all building blocks of our parse tree.
 *
 * We will arrange them during the parsing process in the Parser file
 */

sealed trait Node
case class NodeAssign(id: String, expr: NodeExpr) extends Node
case class NodeExpr(content: Node) extends Node
case class NodeText(text: String) extends Node
case class NodeNumber(number: Integer) extends Node
case class NodeArray(content: List[NodeExpr]) extends Node
case class NodeObject(content: List[NodeObjectMember]) extends Node
case class NodeObjectMember(id: String, expr: NodeExpr) extends Node
