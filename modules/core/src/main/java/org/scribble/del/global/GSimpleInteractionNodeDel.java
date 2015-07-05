package org.scribble.del.global;

import org.scribble.ast.ScribNode;
import org.scribble.del.SimpleInteractionNodeDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ModelBuilder;
import org.scribble.visit.Projector;


public abstract class GSimpleInteractionNodeDel extends SimpleInteractionNodeDel
{
	public GSimpleInteractionNodeDel()
	{

	}

	@Override
	public void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	{
		//pushVisitorEnv(parent, child, proj);
		pushVisitorEnv(this, proj);
	}

	@Override
	public ScribNode leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		//return popAndSetVisitorEnv(parent, child, proj, visited);
		return popAndSetVisitorEnv(this, proj, visited);
	}

	@Override
	public void enterModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder) throws ScribbleException
	{
		//pushVisitorEnv(parent, child, builder);
		pushVisitorEnv(this, builder);
	}

	@Override
	public ScribNode leaveModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		//return popAndSetVisitorEnv(parent, child, builder, visited);
		return popAndSetVisitorEnv(this, builder, visited);
	}
}
