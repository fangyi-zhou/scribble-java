package org.scribble.del;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.Choice;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.env.UnfoldingEnv;

public abstract class ChoiceDel extends CompoundInteractionNodeDel
{
	public ChoiceDel()
	{

	}

	@Override
	public void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException
	{
		UnfoldingEnv env = unf.peekEnv().enterContext();
		env = env.pushChoiceParent();
		unf.pushEnv(env);
	}

	@Override
	public Choice<?> leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		Choice<?> cho = (Choice<?>) visited;
		List<UnfoldingEnv> benvs =
				cho.blocks.stream().map((b) -> (UnfoldingEnv) b.del().env()).collect(Collectors.toList());
		UnfoldingEnv merged = unf.popEnv().mergeContexts(benvs); 
		unf.pushEnv(merged);
		return (Choice<?>) super.leaveInlinedProtocolUnfolding(parent, child, unf, visited);
	}
}
