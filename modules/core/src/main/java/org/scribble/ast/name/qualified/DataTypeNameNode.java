package org.scribble.ast.name.qualified;

import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.ModuleName;

//public class PayloadTypeNameNode extends MemberNameNode implements PayloadElementNameNode, ArgumentNode//, PayloadTypeOrParameterNode
//public class PayloadTypeNameNode extends MemberNameNode implements PayloadElementNameNode, ArgumentNode
//public class DataTypeNameNode extends SimpleNameNode<DataType, DataTypeKind> implements PayloadElementNameNode, ArgumentNode
//public class DataTypeNameNode extends MemberNameNode<DataType, DataTypeKind> implements PayloadElementNameNode//, ArgumentNode
public class DataTypeNameNode extends MemberNameNode<DataTypeKind> implements PayloadElemNameNode//, ArgumentNode
{
	//public PayloadTypeNameNodes(PrimitiveNameNode... ns)
	public DataTypeNameNode(String... elems)
	//public DataTypeNameNode(String identifier)
	{
		super(elems);
		//super(identifier);
	}

	@Override
	protected DataTypeNameNode copy()
	{
		return new DataTypeNameNode(this.elems);
		//return new DataTypeNameNode(this.identifier);
	}

	/*// Basically a copy without the AST
	@Override
	public PayloadTypeNameNode leaveProjection(Projector proj) //throws ScribbleException
	{
		PayloadTypeNameNode projection = new PayloadTypeNameNode(null, getElements());
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}*/
	
	@Override
	public DataType toName()
	{
		//String membname = getLastElement();
		DataType membname = new DataType(getLastElement());
		if (!isPrefixed())
		{
			//return new DataType(membname);
			return membname;
		}
		//ModuleName modname = ModuleNameNodes.toModuleName(getModulePrefix());
		//ModuleName modname = getModulePrefix().toName();
		ModuleName modname = getModuleNamePrefix();
		return new DataType(modname, membname);
		//return new DataType(this.identifier);
	}

	/*@Override
	public PayloadType toPayloadTypeOrParameter()
	{
		return toName();
	}*/

	@Override
	public boolean isDataTypeNameNode()
	{
		return true;
	}

	@Override
	//public Argument<DataTypeKind> toArgument(Scope scope)  // FIXME: shouldn't be scoped
	public Arg<DataTypeKind> toArg()  // FIXME: shouldn't be scoped
	{
		return toPayloadType();
	}

	@Override
	//public PayloadTypeOrParameter toPayloadTypeOrParameter()
	public DataType toPayloadType()
	{
		return toName();
	}
}
