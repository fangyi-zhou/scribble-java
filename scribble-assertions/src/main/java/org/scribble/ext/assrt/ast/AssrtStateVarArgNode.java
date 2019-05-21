package org.scribble.ext.assrt.ast;

import java.util.List;
import java.util.stream.Collectors;

public interface AssrtStateVarArgNode
{
	List<AssrtAExprNode> getAnnotExprChildren();

	default String annotToString()
	{
		return " @" + getAnnotExprChildren().stream().map(Object::toString)
				.collect(Collectors.joining(", ")) + ";";
	}
}