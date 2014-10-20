package scribble2.ast;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import scribble2.ast.name.PrimitiveNameNode;
import scribble2.main.ScribbleException;
import scribble2.visit.NodeVisitor;

public class NameDeclList<T extends NameDecl<? extends PrimitiveNameNode>> extends AbstractNode
{
	public final List<T> decls;
	
	public NameDeclList(CommonTree ct, List<T> decls)
	{
		super(ct);
		this.decls = new LinkedList<>(decls);
	}
	
	public int length()
	{
		return this.decls.size();
	}

	public boolean isEmpty()
	{
		return this.decls.isEmpty();
	}

	/*@Override 
	public NameDeclList checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{
		Set<Name> ns = new HashSet<>();
		Set<Name> dns = new HashSet<>();
		for (ParameterDecl pd : this.decls)
		{
			Name n = pd.namenode.toName();
			Name dn = pd.getDeclarationName();
			if (ns.contains(n))  // FIXME: should also be distinct from payload type names (for arguments)
			{
				throw new ScribbleException("Duplicate parameter declaration: " + n);
			}
			if (dns.contains(dn))
			{
				throw new ScribbleException("Duplicate parameter delcaration: " + dn);
			}
			ns.add(n);
			dns.add(dn);
		}
		return (NameDeclList) super.checkWellFormedness(wfc);
	}
	
	@Override 
	public NameDeclList leave(EnvVisitor nv) throws ScribbleException
	{
		NameDeclList pdl = (NameDeclList) super.leave(nv);
		Env env = nv.getEnv();
		for (ParameterDecl pd : this.decls)
		{
			env.params.addParameter(pd.getDeclarationName(), pd.kind);
		}
		return pdl;
	}*/
	
	@Override
	public NameDeclList<T> visitChildren(NodeVisitor nv) throws ScribbleException
	{
		List<T> nds = visitChildListWithClassCheck(this, this.decls, nv);
		return new NameDeclList<>(this.ct, nds);
	}

	@Override
	public String toString()
	{
		if (isEmpty())
		{
			return "";
		}
		String s = decls.get(0).toString();
		for (T nd : this.decls.subList(1, this.decls.size()))
		{
			s += ", " + nd;
		}
		return s;
	}
					
	/*public static final Function<List<? extends NameDecl>, List<ParameterDecl>> toParameterDeclList =
			(List<? extends NameDecl> nds)
					-> nds.stream().map(ParameterDeclList.toParameterDecl).collect(Collectors.toList());*/
					
	/*public static final Function<List<? extends NameDecl>, List<RoleDecl>> toRoleDeclList = 
			(List<? extends NameDecl> nds) -> { return Util.listCast(nds, toRoleDecl); };
			
	public static final Function<List<? extends NameDecl>, List<ParameterDecl>> toParameterDeclList = 
			(List<? extends NameDecl> nds) -> { return Util.listCast(nds, toParameterDecl); };*/
}
