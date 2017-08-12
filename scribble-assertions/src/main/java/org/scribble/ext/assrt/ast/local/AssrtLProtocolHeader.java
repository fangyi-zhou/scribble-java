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
package org.scribble.ext.assrt.ast.local;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.local.LProtocolHeader;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.assrt.ast.AssrtArithExpr;
import org.scribble.ext.assrt.ast.AssrtAstFactory;
import org.scribble.ext.assrt.ast.name.simple.AssrtIntVarNameNode;
import org.scribble.ext.assrt.type.formula.AssrtArithFormula;
import org.scribble.ext.assrt.type.name.AssrtDataTypeVar;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.Local;
import org.scribble.visit.AstVisitor;

// Based on AssrtGProtocolHeader
public class AssrtLProtocolHeader extends LProtocolHeader
{
	//public final AssrtAssertion ass;  // null if not specified -- currently duplicated from AssrtGMessageTransfer
	public final List<AssrtIntVarNameNode> annotvars;
	public final List<AssrtArithExpr> annotexprs;

	public AssrtLProtocolHeader(CommonTree source, LProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
	{
		this(source, name, roledecls, paramdecls, //null);
				Collections.emptyList(), Collections.emptyList());
	}

	public AssrtLProtocolHeader(CommonTree source, LProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls, //AssrtAssertion ass)
			List<AssrtIntVarNameNode> annotvars, List<AssrtArithExpr> annotexprs)
	{
		super(source, name, roledecls, paramdecls);
		//this.ass = ass;
		this.annotvars = Collections.unmodifiableList(annotvars);
		this.annotexprs = Collections.unmodifiableList(annotexprs);
	}
	
	// Duplicated from AssrtGProtocolHeader
	// Pre: ass != null
	//public AssrtBinCompFormula getAnnotDataTypeVarInitDecl()
	public Map<AssrtDataTypeVar, AssrtArithFormula> getAnnotDataTypeVarDecls()  // Cf. AssrtAnnotDataTypeElem (no "initializer")
	{
		//return (this.ass == null) ? null : (AssrtBinCompFormula) this.ass.getFormula();
		//return (AssrtBinCompFormula) this.ass.getFormula();
		Iterator<AssrtArithExpr> exprs = this.annotexprs.iterator();
		return this.annotvars.stream().collect(Collectors.toMap(v -> v.toName(), v -> exprs.next().getFormula()));
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new AssrtLProtocolHeader(this.source, getNameNode(), this.roledecls, this.paramdecls, //this.ass);
				this.annotvars, this.annotexprs);
	}
	
	@Override
	public AssrtLProtocolHeader clone(AstFactory af)
	{
		LProtocolNameNode name = getNameNode().clone(af);
		RoleDeclList roledecls = this.roledecls.clone(af);
		NonRoleParamDeclList paramdecls = this.paramdecls.clone(af);
		//AssrtAssertion ass = (this.ass == null) ? null : this.ass.clone(af);

		List<AssrtIntVarNameNode> annotvars = this.annotvars.stream().map(v -> v.clone(af)).collect(Collectors.toList());
		List<AssrtArithExpr> annotexprs = this.annotexprs.stream().map(e -> e.clone(af)).collect(Collectors.toList());

		return ((AssrtAstFactory) af).AssrtLProtocolHeader(this.source, name, roledecls, paramdecls, //ass);
				annotvars, annotexprs);
	}

	@Override
	public AssrtLProtocolHeader reconstruct(ProtocolNameNode<Local> name, RoleDeclList rdl, NonRoleParamDeclList pdl)
	{
		throw new RuntimeException("[assrt] Shouldn't get in here: " + this);
	}

	public AssrtLProtocolHeader reconstruct(ProtocolNameNode<Local> name, RoleDeclList rdl, NonRoleParamDeclList pdl, //AssrtAssertion ass)
			List<AssrtIntVarNameNode> annotvars, List<AssrtArithExpr> annotexprs)
	{
		ScribDel del = del();
		//AssrtLProtocolHeader lph = new AssrtLProtocolHeader(this.source, (LProtocolNameNode) name, rdl, pdl, ass);

		AssrtLProtocolHeader lph = new AssrtLProtocolHeader(this.source, (LProtocolNameNode) name, rdl, pdl, //ass);
				annotvars, annotexprs);

		lph = (AssrtLProtocolHeader) lph.del(del);
		return lph;
	}
	
	@Override
	public LProtocolHeader visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleDeclList rdl = (RoleDeclList) visitChild(this.roledecls, nv);
		NonRoleParamDeclList pdl = (NonRoleParamDeclList) visitChild(this.paramdecls, nv);
		//AssrtAssertion ass = (this.ass == null) ? null : (AssrtAssertion) visitChild(this.ass, nv);

		List<AssrtIntVarNameNode> annotvars = visitChildListWithClassEqualityCheck(this, this.annotvars, nv);
		List<AssrtArithExpr> annotexprs = visitChildListWithClassEqualityCheck(this, this.annotexprs, nv);

		return reconstruct((LProtocolNameNode) this.name, rdl, pdl, //ass);
				annotvars, annotexprs);
	}
	
	@Override
	public String toString()
	{
		Iterator<AssrtArithExpr> exprs = this.annotexprs.iterator();
		return super.toString() + " " //+ this.ass;
				+ "<" + this.annotvars.stream().map(v -> v + " := " + exprs.next()).collect(Collectors.joining(", ")) +  ">";
	}
}
