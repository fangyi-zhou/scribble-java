package org.scribble.ext.assrt.core.model.endpoint.action;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ext.assrt.core.model.endpoint.AssrtCoreEModelFactory;
import org.scribble.ext.assrt.core.model.global.AssrtCoreSModelFactory;
import org.scribble.ext.assrt.core.model.global.action.AssrtCoreSReceive;
import org.scribble.ext.assrt.model.endpoint.action.AssrtEReceive;
import org.scribble.ext.assrt.type.formula.AssrtArithFormula;
import org.scribble.ext.assrt.type.formula.AssrtBoolFormula;
import org.scribble.model.endpoint.EModelFactory;
import org.scribble.model.global.SModelFactory;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;

public class AssrtCoreEReceive extends AssrtEReceive implements AssrtCoreEAction
{
	// Annot needed -- e.g. mu X(x:=..) . mu Y(y:=..) ... X<123> -- rec var X will be discarded, so edge action needs to record which var is being updated
	/*public final AssrtDataTypeVar annot;  // Not null (by AssrtCoreGProtocolTranslator)
	public final AssrtArithFormula expr;*/
	public final List<AssrtArithFormula> stateexprs;
	
	public AssrtCoreEReceive(EModelFactory ef, Role peer, MessageId<?> mid, Payload payload, AssrtBoolFormula bf,
			//AssrtDataTypeVar annot, AssrtArithFormula expr)
			List<AssrtArithFormula> stateexprs)
	{
		super(ef, peer, mid, payload, bf);
		//this.annot = annot;list)
	
		this.stateexprs = Collections.unmodifiableList(stateexprs);
	}
	
	@Override
	public AssrtCoreESend toDual(Role self)
	{
		return ((AssrtCoreEModelFactory) this.ef).newAssrtCoreESend(self, this.mid, this.payload, this.ass, //this.annot, 
				this.stateexprs);
	}

	@Override
	public AssrtCoreSReceive toGlobal(SModelFactory sf, Role self)
	{
		return ((AssrtCoreSModelFactory) sf).newAssrtCoreSReceive(self, this.peer, this.mid, this.payload, this.ass, //this.annot,
				this.stateexprs);
	}

	/*@Override
	public AssrtDataTypeVar getAnnotVar()
	{
		return this.annot;
	}*/

	@Override
	//public AssrtArithFormula getArithExpr()
	public List<AssrtArithFormula> getStateExprs()
	{
		//return this.expr;
		return this.stateexprs;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 6763;
		hash = 31 * hash + super.hashCode();
		//hash = 31 * hash + this.annot.hashCode();
		hash = 31 * hash + this.stateexprs.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof AssrtCoreEReceive))
		{
			return false;
		}
		AssrtCoreEReceive as = (AssrtCoreEReceive) o;
		return super.equals(o)  // Does canEquals
				//&& this.annot.equals(as.annot)
				&& this.stateexprs.equals(as.stateexprs);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof AssrtCoreEReceive;
	}
	
	@Override
	public String toString()
	{
		//return super.toString() + "@" + this.ass + ";";
		return super.toString()
				//+ ((this.annot.toString().startsWith("_dum")) ? "" : "<" + this.annot + " := " + this.expr + ">");  // FIXME
				+ (this.stateexprs.isEmpty() ? "" : "<" + this.stateexprs.stream().map(Object::toString).collect(Collectors.joining(", ")) + ">");
	}
}
