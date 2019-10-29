package org.scribble.ext.assrt.core.type.formula;

import java.util.Collections;
import java.util.Set;

import org.scribble.ext.assrt.core.type.name.AssrtIntVar;
import org.scribble.ext.assrt.util.JavaSmtWrapper;
import org.sosy_lab.java_smt.api.IntegerFormulaManager;
import org.sosy_lab.java_smt.api.NumeralFormula.IntegerFormula;

// String literal
public class AssrtStrValFormula extends AssrtAFormula
{
	public final String val;

	protected AssrtStrValFormula(String s)
	{
		this.val = s;
	}

	@Override
	public AssrtStrValFormula squash()
	{
		return AssrtFormulaFactory.AssrtStrVal(this.val);
	}

	@Override
	public AssrtStrValFormula subs(AssrtIntVarFormula old, AssrtIntVarFormula neu)  // FIXME: mismatch between Str and ArithFormula?
	{
		return this;
	}

	@Override
	public boolean isConstant()
	{
		return true;
	}
		
	@Override
	public String toSmt2Formula()
	{
		return "\"" + this.val + "\"";
	}
	
	@Override
	public IntegerFormula toJavaSmtFormula()
	{
		IntegerFormulaManager fmanager = JavaSmtWrapper.getInstance().ifm;
		return fmanager.makeNumber(this.val);  
	}
	
	@Override
	public Set<AssrtIntVar> getIntVars()
	{
		return Collections.emptySet();	
	}
	
	@Override
	public String toString()
	{
		return "\"" + this.val + "\"";
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof AssrtStrValFormula))
		{
			return false;
		}
		return super.equals(this)  // Does canEqual
				&& this.val == ((AssrtStrValFormula) o).val;
	}
	
	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof AssrtStrValFormula;
	}

	@Override
	public int hashCode()
	{
		int hash = 6911;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.val.hashCode();
		return hash;
	}
}
