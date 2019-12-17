package org.scribble.ext.assrt.core.type.formula;

import java.util.Map;

import org.scribble.core.type.name.DataName;
import org.scribble.ext.assrt.core.type.name.AssrtIntVar;

// FIXME: factor out with Int -- record "type" as a field
public class AssrtStrVarFormula extends AssrtAVarFormula
{
	protected AssrtStrVarFormula(String name)
	{
		super(name);
	}

	@Override
	public AssrtStrVarFormula disamb(Map<AssrtIntVar, DataName> env)
	{
		throw new RuntimeException("Won't get in here: " + this);  // Should not be re-disambiguating 
	}
	
	// i.e., to "type"
	@Override
	public AssrtIntVar toName()
	{
		return new AssrtIntVar(this.name, "String");
	}

	@Override
	public AssrtStrVarFormula squash()
	{
		return AssrtFormulaFactory.AssrtStrVar(this.name);
	}
	
	@Override
	public String toString()
	{
		return this.name; 
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof AssrtStrVarFormula))
		{
			return false;
		}
		return super.equals(this)  // Does canEqual
				&& this.name.equals(((AssrtStrVarFormula) o).name);
	}
	
	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof AssrtStrVarFormula;
	}

	@Override
	public int hashCode()
	{
		int hash = 9923;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
}
