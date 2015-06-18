package org.scribble.ast;

import org.scribble.ast.name.NameNode;
import org.scribble.ast.name.simple.NonRoleParamNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.Role;

public class NonRoleParamDecl<K extends Kind> extends HeaderParamDecl<K> //implements HeaderParameterDecl// extends HeaderParameterDecl<ParameterNode>
{
	public final K kind;

	public NonRoleParamDecl(K kind, NameNode<K> name)
	{
		super(name);
		this.kind = kind;
	}
	
	@Override
	protected NonRoleParamDecl<K> reconstruct(NameNode<K> namenode)
	{
		ScribDel del = del();
		NonRoleParamDecl<K> pd = new NonRoleParamDecl<>(this.kind, this.name);
		@SuppressWarnings("unchecked")
		NonRoleParamDecl<K> tmp = (NonRoleParamDecl<K>) pd.del(del);
		return tmp;
	}

	@Override
	protected NonRoleParamDecl<K> copy()
	{
		return new NonRoleParamDecl<>(this.kind, (NonRoleParamNode<K>) this.name);
	}

	@Override
	public NonRoleParamDecl<K> project(Role self)
	{
		NonRoleParamNode<K> pn = AstFactoryImpl.FACTORY.NonRoleParamNode(this.kind, this.name.toString());
		return AstFactoryImpl.FACTORY.ParamDecl(this.kind, pn);
	}
	
	@Override
	public String getKeyword()
	{
		if (this.kind.equals(SigKind.KIND))
		{
			return Constants.SIG_KW;
		}
		else if (this.kind.equals(SigKind.KIND))
		{
			return Constants.SIG_KW;
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + this.kind);
		}
	}
}
