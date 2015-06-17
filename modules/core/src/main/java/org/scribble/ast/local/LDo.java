package org.scribble.ast.local;

import org.scribble.ast.Do;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.del.ModelDel;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.LProtocolName;

public class LDo extends Do<Local> implements LSimpleInteractionNode
{
	//public LocalDo(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	//public LDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	public LDo(RoleArgList roleinstans, NonRoleArgList arginstans, LProtocolNameNode proto)
	{
		//super(scope, roleinstans, arginstans, proto);
		super(roleinstans, arginstans, proto);
	}

	@Override
	public LProtocolName getTargetFullProtocolName(ModuleContext mcontext)
	{
		return mcontext.getFullLocalProtocolName(((LProtocolNameNode) this.proto).toName());
	}

	@Override
	//protected Do reconstruct(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	//protected LDo reconstruct(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	protected LDo reconstruct(RoleArgList roleinstans, NonRoleArgList arginstans, ProtocolNameNode<Local> proto)
	{
		ModelDel del = del();
		//LocalDo ld = new LocalDo(scope, roleinstans, arginstans, proto);
		LDo ld = new LDo(roleinstans, arginstans, (LProtocolNameNode) proto);
		ld = (LDo) ld.del(del);
		return ld;
	}

	@Override
	protected LDo copy()
	{
		//return new LocalDo(this.scope, this.roleinstans, this.arginstans, this.proto);
		return new LDo(this.roles, this.args, (LProtocolNameNode) this.proto);
	}
	
	/*@Override
	public GraphBuilder enterGraphBuilding(GraphBuilder builder)
	{
		if (!builder.isCycle())
		{
			//ScopedSubprotocolSignature subsig = builder.getSubprotocolSignature(this);
			ScopedSubprotocolSignature subsig = builder.peekStack();  // SubprotocolVisitor has already entered subprotocol
			SubprotocolSignature noscope = new SubprotocolSignature(subsig.fmn, subsig.roles, subsig.args);  // FIXME: factor better
			builder.setSubprotocolEntry(noscope);
		}
		return builder;
	}

	@Override
	public LocalDo visitForGraphBuilding(GraphBuilder builder)
	{
		if (isEmptyScope())
		{
			if (builder.isCycle())
			{
				//ScopedSubprotocolSignature subsig = builder.getSubprotocolSignature(this);
				ScopedSubprotocolSignature subsig = builder.peekStack();
				SubprotocolSignature noscope = new SubprotocolSignature(subsig.fmn, subsig.roles, subsig.args);  // FIXME: factor better
				builder.setEntry(builder.getSubprotocolEntry(noscope));  // Like continue
			}
			else
			{
				super.visitForGraphBuilding(builder);
			}
			return this;
		}
		else
		{
			throw new RuntimeException("TODO: ");  // Need protocol ref state
		}
	}
	
	@Override
	public LocalDo leaveGraphBuilding(GraphBuilder builder)
	{
		//ScopedSubprotocolSignature subsig = builder.getSubprotocolSignature(this);
		ScopedSubprotocolSignature subsig = builder.peekStack();
		SubprotocolSignature noscope = new SubprotocolSignature(subsig.fmn, subsig.roles, subsig.args);  // FIXME: factor better
		builder.removeSubprotocolEntry(noscope);
		return this;
	}*/

	/*@Override
	public ProtocolName getFullTargetProtocolName(VisitorEnv env)
	{
		return env.getFullLocalProtocolName(new ProtocolName(this.cn.compileName()));
	}
	
	@Override
	protected GlobalProtocolDecl getTargetProtocolDecl(VisitorEnv env) throws ScribbleException
	{
		return env.getLocalProtocolDecl(getFullTargetProtocolName(env));
	}*/

	/*@Override
	public LocalDo substitute(Substitutor subs) throws ScribbleException
	{
		Do doo = super.substitute(subs);
		return new LocalDo(doo.ct, doo.scope, doo.ril, doo.ail, doo.cn);
	}

	@Override
	public LocalDo checkReachability(ReachabilityChecker rc)
			throws ScribbleException
	{
		LocalDo ld = (LocalDo) super.checkReachability(rc);
		// Env env = rc.getEnv();

		// Env tmp = new Env(env);
		// tmp.pushDo(this); // Emulate leave (?)

		/*if (env.stack.isCycle()) 
		{
			Set<Role> roles = new HashSet<>(env.ops.getSources());
			roles.addAll(env.ops.getAllDestinations());
			for (RoleInstantiation ri : this.ril.is)
			{
				Role role = ri.arg.toName();
				if (!roles.contains(role))
				{
					throw new ScribbleException("Unguarded recursive do for role: " + role);  // FIXME: either fix projection or check this on the global
				}
			}
		}*

		return (LocalDo) ld.visitTarget(rc); // Checks to actually visit target or
																					// not
	}*/

	/*@Override
	public LocalDo leave(EnvVisitor nv) throws ScribbleException
	{
		Env env = nv.getEnv();
		// if (tmp.stack.isCycle())
		if (env.stack.isCycle()) // Have to check before doing subprotocol env pop
		{
			nv.getEnv().reach.setDoExitable(false);
		}

		return (LocalDo) super.leave(nv);
	}*/

	/*@Override
	public LocalDo visitChildren(NodeVisitor nv) throws ScribbleException
	{
		Do doo = super.visitChildren(nv);
		return new LocalDo(doo.ct, doo.scope, doo.ril, doo.ail, doo.cn);
	}*/

	/*@Override
	public ProtocolName getFullTargetProtocolName(Env env)
			throws ScribbleException
	{
		return env
				.getFullLocalProtocolName(new ProtocolName(this.cn.compileName()));
	}

	@Override
	public LocalProtocolDecl getTargetProtocolDecl(Env env)
			throws ScribbleException
	{
		return env.getLocalProtocolDecl(getFullTargetProtocolName(env));
	}*/

	/*@Override
	public Do visitTarget(EnvVisitor nv) throws ScribbleException
	{
		Env env = nv.getEnv();
		ProtocolDecl pd = getTargetProtocolDecl(env);
		Substitutor subs = new Substitutor(env.job, this.ril, pd.rdl, this.ail, pd.pdl);
		Node substituted = subs.visit(pd.getBody());

		// FIXME: projected visibility (probably better to make the modules upfront)
		nv.visit(substituted);
		return this;
	}*/

	/*@Override
	public void toGraph(GraphBuilder gb) throws ScribbleException
	{
		ProtocolName pn = new ProtocolName(this.cn.toString());
		StateReference ref = new StateReference(pn);
		if (this.scope.toName().equals(Scope.EMPTY_SCOPE))
		{
			List<Role> roles = new LinkedList<>();
			List<Argument> args = new LinkedList<>();
			for (RoleInstantiation ri : this.ril.is)
			{
				roles.add(ri.arg.toName());
			}
			for (ArgumentInstantiation ai: this.ail.is)
			{
				args.add(ai.arg.toArgument());
			}
			SubprotocolSignature sig = new SubprotocolSignature(pn, roles, args);
			if (gb.isBuilt(sig))
			{
				gb.getRoot().edges.put(P2PCommunicationSig.TAU, gb.getSubprotocol(sig));
			}
			else
			{
				gb.addSubprotocol(sig, gb.getRoot());

				LocalProtocolDecl lpd = gb.job.getProjection(pn);
				Substitutor subs = new Substitutor(gb.job, this.ril, lpd.rdl, this.ail, lpd.pdl);
				LocalProtocolBlock substituted = (LocalProtocolBlock) subs.visit(lpd.getBody());
				substituted.toGraph(gb);

				gb.removeSubprotocol(sig);
			}
		}
		else
		{
			try
			{
				gb.addTodo(pn, gb.job.getProjection(pn));
			}
			catch (ScribbleException e)
			{
				throw new RuntimeException(e);
			}

			Scope scope = this.scope.toName();
			NestedState ns = new NestedState(ref, scope, Collections.<InterruptSig>emptyList());
			//gb.getRoot().edges.put(P2PCommunicationSig.ENTER, ns);   // As in Interruptible
			gb.getRoot().edges.put(new EnterScopeSig(scope), ns);   // As in Interruptible
			ns.edges.put(P2PCommunicationSig.TAU, gb.getTerm());  // Not using exit
		}
		/*else
		{
			gb.getRoot().edges.put(P2PCommunicationSig.EMPTY_ENTER, ref);
			ref.edges.put(P2PCommunicationSig.TAU, gb.getTerm());
		}*
	}*/
}
