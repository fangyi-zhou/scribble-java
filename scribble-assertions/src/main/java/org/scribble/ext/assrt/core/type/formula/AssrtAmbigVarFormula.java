package org.scribble.ext.assrt.core.type.formula;

import org.scribble.ext.assrt.core.type.name.AssrtPayElemType;

public class AssrtAmbigVarFormula extends AssrtAVarFormula
{
	protected AssrtAmbigVarFormula(String name)
	{
		super(name);
	}
	
	// i.e., to "type"
	@Override
	public AssrtPayElemType<?> toName()
	{
		throw new RuntimeException("Shouldn't get in here: " + name);
	}

	@Override
	public AssrtAmbigVarFormula squash()
	{
		//return AssrtFormulaFactory.AssrtIntVar(this.name);
		throw new RuntimeException("Shouldn't get in here: " + name);
	}

	@Override
	public AssrtAmbigVarFormula subs(AssrtAVarFormula old, AssrtAVarFormula neu)
	{
		//return this.equals(old) ? neu : this;
		throw new RuntimeException("Shouldn't get in here: " + name);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof AssrtAmbigVarFormula))
		{
			return false;
		}
		return super.equals(this)  // Does canEqual
				&& this.name.equals(((AssrtAmbigVarFormula) o).name);
	}
	
	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof AssrtAmbigVarFormula;
	}

	@Override
	public int hashCode()
	{
		int hash = 9463;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
}
