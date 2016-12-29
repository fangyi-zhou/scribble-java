package org.scribble.ext.f17.ast.global.action;

import java.util.Set;

import org.scribble.ext.f17.ast.F17AstAction;
import org.scribble.sesstype.name.Role;


public abstract class F17GAction extends F17AstAction
{
	public final Role src;
	
	public F17GAction(Role... rs)
	{
		super(rs);
		this.src = rs[0];  // this.src == "first" subj
	}
	
	public abstract Set<Role> getRoles();  // For projection
	
	@Override
	public String toString()
	{
		return this.src.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + super.hashCode();
		result = prime * result + this.src.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof F17GAction))
		{
			return false;
		}
		return super.equals(obj);
	}

	protected abstract boolean canEquals(Object o);
}
