package org.scribble2.parser.ast.name.qualified;

import org.antlr.runtime.tree.CommonTree;

public class PayloadTypeNameNode extends MemberNameNodes //implements PayloadTypeOrParameterNode
{
	//public PayloadTypeNameNodes(PrimitiveNameNode... ns)
	public PayloadTypeNameNode(CommonTree ct, String... ns)
	{
		super(ct, ns);
	}

	/*// Basically a copy without the AST
	@Override
	public PayloadTypeNameNode leaveProjection(Projector proj) //throws ScribbleException
	{
		PayloadTypeNameNode projection = new PayloadTypeNameNode(null, getElements());
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}
	
	@Override
	public PayloadType toName()
	{
		String membname = getLastElement();
		if (!isPrefixed())
		{
			return new PayloadType(membname);
		}
		//ModuleName modname = ModuleNameNodes.toModuleName(getModulePrefix());
		ModuleName modname = getModulePrefix().toName();
		return new PayloadType(modname, membname);
	}

	@Override
	public PayloadTypeOrParameter toPayloadTypeOrParameter()
	{
		return toName();
	}

	@Override
	public PayloadType toArgument()
	{
		return toName();
	}

	public boolean isMessageSignatureNode()
	{
		return false;
	}

	@Override
	public boolean isPayloadTypeNode()
	{
		return true;
	}

	@Override 
	public boolean isParameterNode()
	{
		return false;
	}

	@Override
	public boolean isAmbiguousNode()
	{
		return false;
	}*/
}
