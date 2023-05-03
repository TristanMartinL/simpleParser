/**
 * This file contains all building blocks of our parse tree.
 *
 * We will arrange them during the parsing process in the Parser file
 */

sealed trait Node
case class NodeId(name: String) extends Node
case class NodeBlock(content: List[NodeAssign]) extends Node

case class NodeVarAccess(id: NodeId) extends Node

case class NodeAdd(left: Node, right: Node) extends Node

case class NodeSub(left: Node, right: Node) extends Node

case class NodeMult(left: Node, right: Node) extends Node

case class NodeDiv(left: Node, right: Node) extends Node
case class NodeAssign(id: NodeId, expr: NodeExpr) extends Node
case class NodeExpr(content: Node) extends Node
case class NodeText(text: String) extends Node
case class NodeNumber(number: Integer) extends Node
case class NodeArray(content: List[NodeExpr]) extends Node
case class NodeObject(content: List[NodeObjectMember]) extends Node
case class NodeObjectMember(id: String, expr: NodeExpr) extends Node
