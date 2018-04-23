package org.scribble.ext.go.core.ast.global;

import java.util.Set;

import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreRec;
import org.scribble.ext.go.core.ast.RPCoreSyntaxException;
import org.scribble.ext.go.core.ast.local.RPCoreLEnd;
import org.scribble.ext.go.core.ast.local.RPCoreLRecVar;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.main.GoJob;
import org.scribble.type.kind.Global;
import org.scribble.type.name.RecVar;

public class RPCoreGRec extends RPCoreRec<RPCoreGType, Global> implements RPCoreGType
{
	public RPCoreGRec(RecVar recvar, RPCoreGType body)
	{
		super(recvar, body);
	}
	
	@Override
	public boolean isWellFormed(GoJob job, GProtocolDecl gpd)
	{
		return this.body.isWellFormed(job, gpd);
	}
	
	@Override
	public Set<RPIndexedRole> getParamRoles()
	{
		return this.body.getParamRoles();
	}

	@Override
	//public ParamCoreLType project(ParamCoreAstFactory af, Role r, Set<ParamRange> ranges) throws ParamCoreSyntaxException
	public RPCoreLType project(RPCoreAstFactory af, RPRoleVariant subj) throws RPCoreSyntaxException
	{
		//ParamCoreLType proj = this.body.project(af, r, ranges);
		RPCoreLType proj = this.body.project(af, subj);
		if (proj instanceof RPCoreLRecVar)
		{
			RPCoreLRecVar rv = (RPCoreLRecVar) proj;
			return rv.recvar.equals(this.recvar) ? RPCoreLEnd.END : rv;
		}
		else
		{	
			return af.ParamCoreLRec(this.recvar, proj);
		}
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPCoreGRec))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof RPCoreGRec;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 2333;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
}