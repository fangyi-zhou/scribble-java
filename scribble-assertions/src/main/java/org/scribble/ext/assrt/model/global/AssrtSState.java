package org.scribble.ext.assrt.model.global;

import java.util.Map;

import org.scribble.model.MState;
import org.scribble.model.endpoint.EState;
import org.scribble.model.global.SState;
import org.scribble.model.global.SStateErrors;
import org.scribble.sesstype.name.Role;

// FIXME: hashCode/equals
public class AssrtSState extends SState
{
	protected AssrtSState(AssrtSConfig config)
	{
		super(config);
	}
	
	@Override
	public AssrtSStateErrors getErrors()
	{
		SStateErrors errs = super.getErrors();

		Map<Role, EState> unsatAssertion = ((AssrtSConfig) this.config).getUnsatAssertions();   // FIXME: replace cast by something better?
		Map<Role, EState> varsNotInScope = ((AssrtSConfig) this.config).checkHistorySensitivity();

		return new AssrtSStateErrors(errs.stuck, errs.waitFor, errs.orphans, errs.unfinished, unsatAssertion, varsNotInScope);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 5503;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	// FIXME? doesn't use this.id, cf. super.equals
	// Not using id, cf. ModelState -- FIXME? use a factory pattern that associates unique states and ids? -- use id for hash, and make a separate "semantic equals"
	// Care is needed if hashing, since mutable (OK to use immutable config -- cf., ModelState.id)
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof AssrtSState))
		{
			return false;
		}
		return ((AssrtSState) o).canEquals(this) && this.config.equals(((AssrtSState) o).config);
	}

	@Override
	protected boolean canEquals(MState<?, ?, ?, ?> s)
	{
		return s instanceof AssrtSState;
	}
}
