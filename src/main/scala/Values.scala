sealed trait SimpleValue;

case class SimpleNumber(i: Int) extends SimpleValue
case class SimpleVarList(i: List[SimpleValue]) extends SimpleValue
case class SimpleText(text: String) extends SimpleValue

case class SimpleArray(content: List[SimpleValue]) extends SimpleValue

case class SimpleObject(content: List[SimpleValue]) extends SimpleValue

case class SimpleObjectMember(key: String, content: SimpleValue) extends SimpleValue
