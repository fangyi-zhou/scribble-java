package org.scribble2.parser.ast.name.simple;

import org.antlr.runtime.Token;

public class ParameterNode extends SimpleNameNode //implements MessageNode//, PayloadTypeOrParameterNode
{
	//public final Kind kind;
	
	public ParameterNode(Token t, String name)//, Kind kind)
	{
		super(t, name);
		//this.kind = kind;
	}
	
	/*// Only useful for MessageSignatureDecls -- FIXME: integrate sig decls properly
	@Override
	public ParameterNode leaveProjection(Projector proj) //throws ScribbleException
	{
		ParameterNode projection = new ParameterNode(null, toName().toString(), this.kind);
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}
	
	@Override
	public ArgumentNode substitute(Substitutor subs)
	{
		return subs.getArgumentSubstitution(toName());
	}
	
	@Override
	public Parameter toName()
	{
		return new Parameter(this.kind, this.identifier);
	}

	@Override
	public PayloadTypeOrParameter toPayloadTypeOrParameter()
	{
		if (this.kind != Kind.TYPE)
		{
			throw new RuntimeException("Not a type-kind parameter: " + this);
		}
		return toName();
	}
	
	/*@Override
	public Operator getOperator()
	{
		return new Operator(toString());
	}* /

	@Override
	public Parameter toArgument()
	{
		return toName();
	}

	@Override
	public Parameter toMessage()
	{
		return toName();
	}

	@Override
	public boolean isMessageSignatureNode()
	{
		return false;
	}

	@Override
	public boolean isPayloadTypeNode()
	{
		return false;
	}

	@Override 
	public boolean isParameterNode()
	{
		return true;
	}

	@Override
	public boolean isAmbiguousNode()
	{
		return false;
	}*/
}
