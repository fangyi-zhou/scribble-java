package org.scribble.ext.assrt.model.endpoint.action;

import org.scribble.ext.assrt.type.formula.AssrtBoolFormula;
import org.scribble.ext.assrt.type.formula.AssrtTrueFormula;

public interface AssrtEAction
{
	AssrtBoolFormula getAssertion();
	
	default String assertionToString()
	{
		AssrtBoolFormula ass = getAssertion();
		return ass.equals(AssrtTrueFormula.TRUE) ? "" : ("@\"" + ass + "\"");
		//return ass.equals(AssrtTrueFormula.TRUE) ? "" : ("@" + ass + ";");
	}
}