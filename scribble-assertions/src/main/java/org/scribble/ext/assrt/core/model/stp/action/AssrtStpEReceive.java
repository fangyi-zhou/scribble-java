package org.scribble.ext.assrt.core.model.stp.action;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.scribble.ext.assrt.core.model.endpoint.action.AssrtCoreEReceive;
import org.scribble.ext.assrt.core.model.global.action.AssrtCoreSReceive;
import org.scribble.ext.assrt.type.formula.AssrtArithFormula;
import org.scribble.ext.assrt.type.formula.AssrtBoolFormula;
import org.scribble.ext.assrt.type.formula.AssrtIntVarFormula;
import org.scribble.ext.assrt.type.formula.AssrtSmtFormula;
import org.scribble.model.endpoint.EModelFactory;
import org.scribble.model.global.SModelFactory;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;

public class AssrtStpEReceive extends AssrtCoreEReceive implements AssrtStpEAction
{
	public final Map<AssrtIntVarFormula, AssrtSmtFormula<?>> sigma;
	public final AssrtBoolFormula A;  // aliases this.ass
	
	public AssrtStpEReceive(EModelFactory ef, Role peer, MessageId<?> mid, Payload payload, 
		Map<AssrtIntVarFormula, AssrtSmtFormula<?>> sigma, AssrtBoolFormula A)
	{
		super(ef, peer, mid, payload, A, Collections.emptyList());
		this.sigma = Collections.unmodifiableMap(sigma);
		this.A = A;
	}

	@Override
	public Map<AssrtIntVarFormula, AssrtSmtFormula<?>> getSigma()
	{
		return this.sigma;
	}
	
	@Override
	public AssrtStpESend toDual(Role self)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}

	@Override
	public AssrtCoreSReceive toGlobal(SModelFactory sf, Role self)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}

	@Override
	public List<AssrtArithFormula> getStateExprs()
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}
	
	@Override
	public String toString()
	{
		return this.obj + getCommSymbol() + this.mid + this.payload + "; " + this.sigma + "; " + this.A;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7853;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.sigma.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof AssrtStpEReceive))
		{
			return false;
		}
		AssrtStpEReceive as = (AssrtStpEReceive) o;
		return super.equals(o)  // Does canEquals
				&& this.sigma.equals(as.sigma);
				//&& this.A.equals(as.A);  // Done by via this.ass
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof AssrtStpEReceive;
	}
}
