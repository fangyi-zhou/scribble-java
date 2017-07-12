/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.parser.scribble.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.parser.scribble.ScribParser;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;
import org.scribble.util.ScribParserException;

public class AntlrGRecursion
{
	public static final int RECURSIONVAR_CHILD_INDEX = 0;
	public static final int BLOCK_CHILD_INDEX = 1;

	public static GRecursion parseGRecursion(ScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		RecVarNode recvar = AntlrSimpleName.toRecVarNode(getRecVarChild(ct), af);
		GProtocolBlock block = (GProtocolBlock) parser.parse(getBlockChild(ct), af);
		return af.GRecursion(ct, recvar, block);
	}

	public static final CommonTree getRecVarChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(RECURSIONVAR_CHILD_INDEX);
	}

	public static final CommonTree getBlockChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(BLOCK_CHILD_INDEX);
	}
}