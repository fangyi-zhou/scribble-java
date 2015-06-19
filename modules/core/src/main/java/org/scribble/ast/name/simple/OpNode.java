package org.scribble.ast.name.simple;

import org.scribble.sesstype.kind.OpKind;
import org.scribble.sesstype.name.Op;


public class OpNode extends SimpleNameNode<OpKind>
{
	public static final String EMPTY_OPERATOR_IDENTIFIER = "";
	
	public OpNode(String identifier)
	{
		super(identifier);
	}

	@Override
	protected OpNode copy()
	{
		return new OpNode(getIdentifier());
	}
	
	@Override
	public Op toName()
	{
		String id = getIdentifier();
		if (id.equals(EMPTY_OPERATOR_IDENTIFIER))
		{
			return Op.EMPTY_OPERATOR;
		}
		return new Op(id);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof OpNode))
		{
			return false;
		}
		return ((OpNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof OpNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 347;
		hash = 31 * super.hashCode();
		return hash;
	}
}
