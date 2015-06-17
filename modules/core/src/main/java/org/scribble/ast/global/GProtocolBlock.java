package org.scribble.ast.global;

import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ProtocolBlock;
import org.scribble.del.ModelDel;
import org.scribble.sesstype.kind.Global;

//public class GlobalProtocolBlock extends ProtocolBlock<GlobalInteractionSequence> implements GlobalNode
public class GProtocolBlock extends ProtocolBlock<Global> implements GlobalNode
{
	/*public static final Function<ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>, GlobalProtocolBlock>
			toGlobalProtocolBlock =
					(ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>> block)
							-> (GlobalProtocolBlock) block;*/

	/*public static final Function<List<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>, List<GlobalProtocolBlock>>
			toGlobalProtocolBlockList =
					(List<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> blocks)
							//-> blocks.stream().map(GlobalChoice.toGlobalProtocolBlock).collect(Collectors.toList());
							-> Util.listCast(blocks, toGlobalProtocolBlock);*/

	//public GlobalProtocolBlock(GlobalInteractionSequence seq)
	public GProtocolBlock(InteractionSeq<Global> seq)
	{
		//this(t, gis, null, null);
		super(seq);
		//super(ct, gis);
	}

	@Override
	//protected ProtocolBlock<GlobalInteractionSequence> reconstruct(GlobalInteractionSequence seq)
	protected GProtocolBlock reconstruct(InteractionSeq<Global> seq)
	{
		ModelDel del = del();
		//GlobalProtocolBlock gpb = new GlobalProtocolBlock(seq);
		GProtocolBlock gpb = new GProtocolBlock(seq);
		gpb = (GProtocolBlock) gpb.del(del);
		return gpb;
	}

	@Override
	protected GProtocolBlock copy()
	{
		return new GProtocolBlock(this.seq);
	}

	/*public GlobalProtocolBlock(CommonTree ct, GlobalInteractionSequence gis, ProtocolBlockContext bcontext)
	{
		super(ct, gis, bcontext);
	}*/

	/*protected GlobalProtocolBlock(CommonTree ct, GlobalInteractionSequence gis, ProtocolBlockContext bcontext, Env env)
	{
		super(ct, gis, bcontext, env);
	}

	@Override
	protected GlobalProtocolBlock reconstruct(CommonTree ct, GlobalInteractionSequence seq, ProtocolBlockContext bcontext, Env env)
	{
		return new GlobalProtocolBlock(ct, seq, bcontext, env);
	}
	
	@Override
	public GlobalProtocolBlock leaveProjection(Projector proj) //throws ScribbleException
	{
		LocalInteractionSequence lis = (LocalInteractionSequence) ((ProjectionEnv) this.seq.getEnv()).getProjection();
		LocalProtocolBlock projection = new LocalProtocolBlock(null, lis);
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}

	/*@Override
	public GlobalProtocolBlock leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		ProtocolBlock<GlobalInteractionSequence> block = super.leaveContextBuilding(builder);
		return new GlobalProtocolBlock(block.ct, block.seq, block.getContext());
	}*/
	
	/*@Override
	public LocalProtocolBlock project(Projector proj) throws ScribbleException
	{
		/*RoleCollector rc = new RoleCollector(proj.job, proj.getEnv());  // env only used for subprotocol stack
		rc.visit(this.seq);
		if (!rc.getRoles().contains(proj.getRole())) // Handles projection of continue
		{
			return null;
		}*
		LocalInteractionSequence seq = (LocalInteractionSequence) proj.visit(this.seq);
		if (seq == null)
		{
			return null;
		}
		return new LocalProtocolBlock(null, seq);
	}*/
	
	/*@Override
	public GlobalProtocolBlock visitChildren(NodeVisitor nv) throws ScribbleException
	{
		ProtocolBlock<GlobalInteractionSequence> block = super.visitChildren(nv);
		return new GlobalProtocolBlock(block.ct, (GlobalInteractionSequence) block.seq, block.getContext(), block.getEnv());
	}*/
}
