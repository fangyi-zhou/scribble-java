package org.scribble.ast;

import org.scribble.ast.name.NameNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.Name;
import org.scribble.sesstype.name.Role;


public class RoleDecl extends HeaderParamDecl<RoleKind>
{
	public RoleDecl(RoleNode name)
	{
		super(name);
	}

	@Override
	protected RoleDecl copy()
	{
		return new RoleDecl((RoleNode) this.name);
	}

	@Override
	protected RoleDecl reconstruct(NameNode<RoleKind> namenode)
	{
		ScribDel del = del();
		RoleDecl rd = new RoleDecl((RoleNode) namenode);
		rd = (RoleDecl) rd.del(del);
		return rd;
	}

	@Override
	public RoleDecl project(Role self)
	{
		Name<RoleKind> role = this.name.toName();
		RoleNode rn = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, role.toString());
		if (role.equals(self))
		{
			return AstFactoryImpl.FACTORY.SelfRoleDecl(rn);
		}
		return AstFactoryImpl.FACTORY.RoleDecl(rn);
	}
	
	@Override
	public Role getDeclName()
	{
		return (Role) super.getDeclName();
	}
	
	public boolean isSelfRoleDecl()
	{
		return false;
	}

	@Override
	public String getKeyword()
	{
		return Constants.ROLE_KW;
	}
}
