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
package org.scribble.ext.assrt.parser;

import org.antlr.runtime.Token;
import org.scribble.ast.ScribNode;
import org.scribble.ast.ScribNodeBase;
import org.scribble.del.DelFactory;
import org.scribble.ext.assrt.ast.AssrtAnnotDataElem;
import org.scribble.ext.assrt.ast.AssrtModule;
import org.scribble.ext.assrt.ast.global.AssrtGConnect;
import org.scribble.ext.assrt.ast.global.AssrtGDo;
import org.scribble.ext.assrt.ast.global.AssrtGMsgTransfer;
import org.scribble.ext.assrt.ast.global.AssrtGProtoHeader;
import org.scribble.parser.ScribTreeAdaptor;
import org.scribble.parser.antlr.AssrtScribbleParser;

public class AssrtScribTreeAdaptor extends ScribTreeAdaptor
{
	public AssrtScribTreeAdaptor(DelFactory df)
	{
		super(df);
	}

	// Create a Tree (ScribNode) from a Token
	// N.B. not using AstFactory, construction here is pre adding children (and also here directly record parsed Token, not recreate)
	@Override
	public ScribNode create(Token t)
	{
		// Switching on ScribbleParser int type constants -- generated from Scribble.g tokens
		// Previously: String tname = t.getText(); -- by convention of Scribble.g, type constant name given as node text, e.g., module: ... -> ^(MODULE ...)
		ScribNodeBase n;
		System.out.println("aaa1: " + t.getType() + " ,, " + t.getText());
		
		HERE super call doesn't work, constants different
		
		switch (t.getType())
		{
			/**
			 *  Create ext node type in place of base
			 *  Parser returns a base token type, we create an ext node type but keep the base token
			 */

			// TODO: integrate with ASSRT variants below?  maybe by un-deprecating reconstructs to make base children configs valid

			case AssrtScribbleParser.MODULE: n = new AssrtModule(t); break;

			case AssrtScribbleParser.GPROTOHEADER: n = new AssrtGProtoHeader(t); break;  

			case AssrtScribbleParser.GMSGTRANSFER: n = new AssrtGMsgTransfer(t); break;
			case AssrtScribbleParser.GCONNECT: n = new AssrtGConnect(t); break;

			//case AssrtScribbleParser.GCONTINUE: n = new AssrtGContinue(t); break;
			case AssrtScribbleParser.GDO: n = new AssrtGDo(t); break;

			//case AssrtScribbleParser.GRECURSION: n = new AssrtGRecursion(t); break;
			

			/**
			 *  Creating explicitly new ext (Assrt) node types
			 *  Parser returns an ext token type, we create the corresponding ext node type
			 */
			
			// Simple names "constructed directly" by parser, cf. assrt_varname: t=ID -> ID<AssrtIntVarNameNode>[$t] ;

			// Compound names 

			// Non-name (i.e., general) AST nodes
			case AssrtScribbleParser.ASSERT_KW: throw new RuntimeException("[TODO] : " + t);

			case AssrtScribbleParser.ASSRT_GLOBALPROTOCOLHEADER: n = new AssrtGProtoHeader(t); break;

			case AssrtScribbleParser.ASSRT_ANNOTPAYLOADELEM: n = new AssrtAnnotDataElem(t); break;

			case AssrtScribbleParser.ASSRT_GLOBALMESSAGETRANSFER: n = new AssrtGMsgTransfer(t); break;
			case AssrtScribbleParser.ASSRT_GLOBALCONNECT: n = new AssrtGConnect(t); break;
			
			case AssrtScribbleParser.ASSRT_GLOBALDO: n = new AssrtGDo(t); break;

			default:
			{
				System.out.println("aaa2: " + t.getText());
				n = (ScribNodeBase) super.create(t);  // Assigning "n", but direct return should be the same?  ast decoration pattern should be delegating back to the same df as below 
			}
		}
		n.decorateDel(this.df);
		
		return n;
	}
}
