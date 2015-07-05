package org.scribble.del;

import org.scribble.ast.Continue;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.InlinedProtocolUnfolder;

public abstract class ContinueDel extends SimpleInteractionNodeDel
{
	public ContinueDel()
	{

	}

	@Override
	public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		Continue<?> cont = (Continue<?>) visited;
		RecVar rv = cont.recvar.toName();
		if (unf.isTodo(rv))
		{
			return unf.getRecVar(rv).clone();
		}
		return cont;
	}
}
