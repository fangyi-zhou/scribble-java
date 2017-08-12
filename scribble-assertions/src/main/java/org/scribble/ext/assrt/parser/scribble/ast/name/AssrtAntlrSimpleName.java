package org.scribble.ext.assrt.parser.scribble.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ext.assrt.ast.AssrtAstFactory;
import org.scribble.ext.assrt.ast.name.simple.AssrtIntVarNameNode;
import org.scribble.ext.assrt.type.kind.AssrtVarNameKind;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;

public class AssrtAntlrSimpleName  // Cf. AntlrSimpleName
{
	public static AssrtIntVarNameNode toVarNameNode(CommonTree ct, AssrtAstFactory af)
	{
		return (AssrtIntVarNameNode) af.SimpleNameNode(ct, AssrtVarNameKind.KIND, AntlrSimpleName.getName(ct));
	}
}
