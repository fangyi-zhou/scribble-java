package org.scribble.ext.assrt.core.type.formula;

import org.sosy_lab.java_smt.api.NumeralFormula.IntegerFormula;

public abstract class AssrtAFormula extends AssrtSmtFormula<IntegerFormula>
{
	@Override
	public abstract AssrtAFormula squash();

	// Factor out with AssrtBFormula?
	@Override
	public abstract AssrtAFormula subs(AssrtAVarFormula old,
			AssrtAVarFormula neu);

	// i.e., does not contain any AssrtIntVarFormula
	public abstract boolean isConstant();
}
