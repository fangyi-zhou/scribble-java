 //$ java -cp scribble-parser/lib/antlr-3.5.2-complete.jar org.antlr.Tool -o scribble-assertions/target/generated-sources/antlr3 scribble-assertions/src/main/antlr3/org/scribble/parser/antlr/Assertions.g

// Windows:
//$ java -cp scribble-parser/lib/antlr-3.5.2-complete.jar org.antlr.Tool -o scribble-assertions/target/generated-sources/antlr3/org/scribble/parser/antlr scribble-assertions/src/main/antlr3/org/scribble/parser/antlr/Assertions.g
//$ mv scribble-assertions/target/generated-sources/antlr3/org/scribble/parser/antlr/Assertions.tokens scribble-assertions/target/generated-sources/antlr3/


grammar Assertions;  // TODO: rename AssrtExt(Id), or AssrtAnnotation


/*
 * TODO:
 * - refactor AssrtAntlrToFormulaParser ito AssertionsTreeAdaptor? -- and set ASTLabelType=ScribNodeBase?
 * - 
 */

options
{
	language = Java;
	output = AST;
	ASTLabelType = CommonTree;
	//ASTLabelType = ScribNodeBase;
}

tokens
{
	/*
	 * Parser input constants (lexer output; keywords, Section 2.4)
	 */
	TRUE_KW = 'True';
	FALSE_KW = 'False';


	/*
	 * Parser output "node types" (corresponding to the various syntactic
	 * categories) i.e. the labels used to distinguish resulting AST nodes.
	 * The value of these token variables doesn't matter, only the token
	 * (i.e. variable) names themselves are used (for AST node root text
	 * field)
	 */
	//EMPTY_LIST = 'EMPTY_LIST';
	
	// TODO: rename EXT_... (or ANNOT_...)
	ROOT; 
	
	BOOLEXPR; 
	COMPEXPR; 
	ARITHEXPR; 
	NEGEXPR;
	
	/*UNFUN;
	UNFUNARGLIST;*/

	INTVAR; 
	INTVAL; 
	NEGINTVAL; 

	TRUE;
	FALSE;
	
	ASSRT_STATEVARDECL_LIST;
	ASSRT_STATEVARDECL;
	ASSRT_STATEVARDECLLISTASSERTION;
	ASSRT_STATEVARARG_LIST;
	//ASSRT_EMPTYASS;
	ASSRT_STATEVARANNOTNODE;
}


@parser::header
{
	package org.scribble.parser.antlr;
	
  import org.scribble.ast.ScribNodeBase;

  import org.scribble.ext.assrt.ast.AssrtAExprNode;
  import org.scribble.ext.assrt.ast.AssrtBExprNode;
	import org.scribble.ext.assrt.ast.AssrtStateVarAnnotNode;
	import org.scribble.ext.assrt.ast.AssrtStateVarDeclList;
	import org.scribble.ext.assrt.core.type.formula.AssrtAFormula;
	import org.scribble.ext.assrt.core.type.formula.AssrtBFormula;
	import org.scribble.ext.assrt.core.type.formula.AssrtSmtFormula;
  import org.scribble.ext.assrt.parser.assertions.AssertionsTreeAdaptor;
	import org.scribble.ext.assrt.parser.assertions.AssrtAntlrToFormulaParser;

	import org.scribble.ext.assrt.ast.AssrtStateVarArgList;
  import org.scribble.ext.assrt.ast.name.simple.AssrtIntVarNameNode;
}

@lexer::header
{
	package org.scribble.parser.antlr;
}

@parser::members
{
	@Override    
	public void displayRecognitionError(String[] tokenNames, 
			RecognitionException e)
	{
		super.displayRecognitionError(tokenNames, e);
  	System.exit(1);
	}
  
	public static AssrtBFormula parseAssertion(String source) 
			throws RecognitionException
	{
		source = source.substring(1, source.length()-1);  // Remove enclosing quotes -- cf. AssrtScribble.g EXTID
		AssertionsLexer lexer = new AssertionsLexer(new ANTLRStringStream(source));
		AssertionsParser parser = new AssertionsParser(
				new CommonTokenStream(lexer));
		AssrtSmtFormula<?> res = AssrtAntlrToFormulaParser
				.getInstance().parse((CommonTree) parser.bool_root().getTree());
		if (!(res instanceof AssrtBFormula))
		{
			System.out.println("Invalid assertion syntax: " + source);
			System.exit(1);
		}
		return (AssrtBFormula) res;
	}

	// TODO: refactor with above
	public static AssrtBFormula parseAssertion(CommonTree tree) 
			throws RecognitionException
	{
		AssrtSmtFormula<?> res = AssrtAntlrToFormulaParser
				.getInstance().parse(tree);
		if (!(res instanceof AssrtBFormula))
		{
			System.out.println("Invalid assertion syntax: " + tree);
			System.exit(1);
		}
		return (AssrtBFormula) res;
	}

	public static AssrtAFormula parseArithAnnotation(String source) 
			throws RecognitionException
	{
		source = source.substring(1, source.length()-1);  // Remove enclosing quotes -- cf. AssrtScribble.g EXTID
		AssertionsLexer lexer = new AssertionsLexer(new ANTLRStringStream(source));
		AssertionsParser parser = new AssertionsParser(new CommonTokenStream(lexer));
		//return (CommonTree) parser.arith_expr().getTree();
		AssrtAFormula res = (AssrtAFormula) AssrtAntlrToFormulaParser
				.getInstance().parse((CommonTree) parser.arith_root().getTree());
		return res;
	}

	/*public static CommonTree parseStateVarDeclList(String source) 
			throws RecognitionException
	{
		source = source.substring(1, source.length()-1);  // Remove enclosing quotes -- cf. AssrtScribble.g EXTID
		AssertionsLexer lexer = new AssertionsLexer(new ANTLRStringStream(source));
		AssertionsParser parser = new AssertionsParser(
				new CommonTokenStream(lexer));
		parser.setTreeAdaptor(new AssertionsTreeAdaptor());
		return (CommonTree) parser.statevardecllist().getTree();
	}*/
  
	// TODO: return "helper" annot node
	// t is an EXTID token
	//public static AssrtBExprNode parseStateVarDeclList(Token t) 
	public static AssrtStateVarAnnotNode parseStateVarDeclList(Token t) 
			throws RecognitionException
	{
		String source = t.getText();
		source = source.substring(1, source.length()-1);  // Remove enclosing quotes -- cf. AssrtScribble.g EXTID
		AssertionsLexer lexer = new AssertionsLexer(new ANTLRStringStream(source));
		AssertionsParser parser = new AssertionsParser(
				new CommonTokenStream(lexer));
		parser.setTreeAdaptor(new AssertionsTreeAdaptor());
		//AssrtSmtFormula<?> res = AssrtAntlrToFormulaParser.getInstance().parse((CommonTree) parser.statevardecllist().getTree());

		CommonTree tree = (CommonTree) parser.statevardecllist().getTree();

		//System.out.println("aaa: " + tree.getChild(1) + " ,, " + ((CommonTree) tree.getChild(1)).getToken());
		
		return (AssrtStateVarAnnotNode) tree;

		/*
		CommonTree tmp = (CommonTree) tree.getChild(0);  // ASSRT_STATEVARDECLLISTASSERTION
		System.out.println("aaa: " + tmp);

		//if (tmp.getChildCount() == 0)
		{
			AssrtSmtFormula<?> res = AssrtAntlrToFormulaParser
					.getInstance().parse((CommonTree) tmp.getChild(0));
			if (!(res instanceof AssrtBFormula))
			{
				System.out.println("Invalid assertion syntax: " + source);// + " ,, " + res.getChild(0) + " ,, " + res.getChild(0).getClass()); 
				System.exit(1);
			}
			//return new AssrtBExprNode(t.getType(), t, (AssrtBFormula) res);
			return new AssrtStateVarAnnotNode(t, new AssrtStateVarDeclList(t), new AssrtBExprNode(t.getType(), t, (AssrtBFormula) res));
					// Cf. adaptor.addChild(root_0, new AssrtBExprNode(EXTID, t, AssertionsParser.parseAssertion((t!=null?t.getText():null))));
					// i.e., EXTID, t, ...
		}
		/*else
		{
			return new Ass
		}*/
	}

	/*public static AssrtStateVarAnnotNode parseStateVarAnnot(String source) 
			throws RecognitionException
	{
		source = source.substring(1, source.length()-1);  // Remove enclosing quotes -- cf. AssrtScribble.g EXTID
		AssertionsLexer lexer = new AssertionsLexer(new ANTLRStringStream(source));
		AssertionsParser parser = new AssertionsParser(
				new CommonTokenStream(lexer));
		CommonTree res = (CommonTree) parser.annot_statevardecls().getTree();
		AssrtStateVarAnnotNode n = new AssrtStateVarAnnotNode(res.getToken());
		//n.addScribChildren(...);
		return n;
	}*/


	//public static List<AssrtAExprNode> parseStateVarArgList(String source) throws RecognitionException
	//public static List<AssrtAExprNode> parseStateVarArgList(Token t) throws RecognitionException
	public static AssrtStateVarArgList parseStateVarArgList(Token t) throws RecognitionException
	{
		String source = t.getText();
		source = source.substring(1, source.length()-1);  // Remove enclosing quotes -- cf. AssrtScribble.g EXTID
		AssertionsLexer lexer = new AssertionsLexer(new ANTLRStringStream(source));
		AssertionsParser parser = new AssertionsParser(new CommonTokenStream(lexer));
		parser.setTreeAdaptor(new AssertionsTreeAdaptor());
		CommonTree tree = (CommonTree) parser.assrt_statevarargs().getTree();
		//return tree.getChildren().stream().map(x -> new AssrtAExpr(t.getType(), t, (AssrtAFormula) x)).collect(Collectors.toList());
		//System.out.println("aaa: " + tree + " ,, " + tree.getChildren() + " ,, " + tree.getClass());
		return (AssrtStateVarArgList) tree;
	}
//*/
}



// Not referred to explicitly, deals with whitespace implicitly (don't delete this)
WHITESPACE:
	('\t' | ' ' | '\r' | '\n'| '\u000C')+
	{
		$channel = HIDDEN;
	}
;


fragment LETTER:
	'a'..'z' | 'A'..'Z'
;

fragment DIGIT:
	'0'..'9'
;

IDENTIFIER:
	LETTER (LETTER | DIGIT)*
;  

NUMBER: 
	(DIGIT)+
; 


variable: 
	IDENTIFIER -> ^(INTVAR IDENTIFIER)
; 	  

num: 
	NUMBER -> ^(INTVAL NUMBER)	   
|
	'-' NUMBER -> ^(NEGINTVAL NUMBER)
; 

	

root:  // Seems mandatory?  For top-level Tree?
	bool_root -> bool_root
;

bool_root:  // EOF useful?
	bool_expr EOF -> bool_expr
;

arith_root:  // EOF useful?
	arith_expr EOF -> arith_expr
;


expr:
	bool_expr
;
	
bool_expr:
	bool_or_expr
;

bool_or_expr:
	bool_and_expr (op=('||') bool_and_expr)*
->
	^(BOOLEXPR bool_and_expr ($op bool_and_expr)*)  // ops a bit redundant, but currently using old, shared (and/or) AssrtAntlrBoolExpr parsing routine
;
// ANTLR seems to engender a pattern where expr "kinds" are nested under a single expr
// Cf. https://github.com/antlr/grammars-v3/blob/master/Java1.6/Java.g#L943
// ^Expr categories are all "nested", bottoming out at primary which recursively contains `parExpression`
// Precedence follows the nesting order, e.g., 1+2*3 -> 1+(2*3); o/w left-assoc (preserved by AssrtAntlr... routines)

bool_and_expr:
	comp_expr (op=('&&') comp_expr)*
->
	^(BOOLEXPR comp_expr ($op comp_expr)*)
;

comp_expr:  // "relational" expr
	arith_expr (op=('=' | '<' | '<=' | '>' | '>=') arith_expr)?
->
	^(COMPEXPR arith_expr $op? arith_expr?)
;
	
arith_expr:
	arith_add_expr
;

arith_add_expr:
	arith_mul_expr (arith_addsub_op arith_mul_expr)*
->
	^(ARITHEXPR arith_mul_expr (arith_addsub_op arith_mul_expr)*)  // Cannot distinguish the ops args?  Always the last one?
;

arith_addsub_op:
	'+' | '-'
;

arith_mul_expr:
	arith_unary_expr (op=('*') arith_unary_expr)*
->
	^(ARITHEXPR arith_unary_expr ($op arith_unary_expr)*)
;
	
arith_unary_expr:
	primary_expr
|
	'!' bool_expr -> ^(NEGEXPR bool_expr)  // Highly binding, so nest deeply
;
// '¬' doesn't seem to work

primary_expr:
	paren_expr
|
	literal
|
	variable
/*|
	unint_fun*/
;
	
paren_expr:
	'(' expr ')' -> expr
;

literal:
	TRUE_KW -> ^(TRUE)
|
	FALSE_KW -> ^(FALSE)
|
	num
;

/*
unint_fun:
	IDENTIFIER unint_fun_arg_list
->
	^(UNFUN IDENTIFIER unint_fun_arg_list)
; 
	
unint_fun_arg_list:
	'(' (arith_expr (',' arith_expr )*)? ')'
->
	^(UNFUNARGLIST arith_expr*)
;
*/
	
//*	
//annot_statevardecls: statevardecllist EOF;

statevardecllist:
	bool_expr
->
	//^(ASSRT_STATEVARANNOTNODE ^(ASSRT_STATEVARDECLLISTASSERTION bool_expr))
	//^(ASSRT_STATEVARANNOTNODE ^(ASSRT_STATEVARDECLLISTASSERTION {new AssrtBExprNode(input.LT(-1).getType(), input.LT(-1),   // https://www.antlr3.org/pipermail/antlr-interest/2010-January/037325.html 
					//(AssrtBFormula) AssrtAntlrToFormulaParser .getInstance().parse((CommonTree) $bool_expr.tree))}))
	^(ASSRT_STATEVARANNOTNODE ^(ASSRT_STATEVARDECL_LIST) {new AssrtBExprNode(input.LT(-1).getType(), input.LT(-1),   
			// HACK: https://www.antlr3.org/pipermail/antlr-interest/2010-January/037325.html 
			// FIXME: gives last token, e.g., "x+1" gives "1" -- return bexpr here and create ScribNodes in aux?
					(AssrtBFormula) AssrtAntlrToFormulaParser .getInstance().parse((CommonTree) $bool_expr.tree))})

	//^(ASSRT_STATEVARANNOT {AssertionsParser.parseAssertion((CommonTree) $bool_expr.tree)})
	//^(ASSRT_STATEVARANNOT {AssertionsParser.parseAssertion($bool_root.tree)})
	//^(ASSRT_STATEVARANNOT bool_expr)
	//^(ASSRT_STATEVARANNOT {new AssrtBExprNode($t.type, $t.token, (AssrtBFormula) AssertionsParser.parseAssertion((CommonTree) $bool_expr.tree))})
	//^(ASSRT_STATEVARANNOT ^(ASSRT_STATEVARDECLLISTASSERTION {AssertionsParser.parseAssertion((CommonTree) $bool_expr.tree)}))
	//bool_expr
	/*
|
	'<' statevardecl (',' statevardecl)* '>'
->
	//^(ASSRT_STATEVARDECL_LIST ^(ASSRT_EMPTYASS) statevardecl+)
	^(ASSRT_STATEVARANNOTNODE ^(ASSRT_EMPTYASS) statevardecl+)
	/*
|
	'<' statevardecl (',' statevardecl)* '>' bool_expr?
->
	^(ASSRT_STATEVARDECL_LIST ^(ASSRT_STATEVARDECLLISTASSERTION bool_expr?) statevardecl+)
;
	
statevardecl:
	variable ':=' arith_expr
->
	^(ASSRT_STATEVARDECL variable arith_expr)
	*/
|
	assrt_statevardecls bool_expr_opt?
->
	^(ASSRT_STATEVARANNOTNODE assrt_statevardecls 
			//{new AssrtBExprNode(input.LT(-1).getType(), input.LT(-1), (AssrtBFormula) AssrtAntlrToFormulaParser .getInstance().parse((CommonTree) $bool_expr.tree))}
			bool_expr_opt?
	)
;

bool_expr_opt:
bool_expr
->{new AssrtBExprNode(input.LT(-1).getType(), input.LT(-1),
					(AssrtBFormula) AssrtAntlrToFormulaParser .getInstance().parse((CommonTree) $bool_expr.tree))}
;

assrt_statevardecls:
	'<' assrt_statevardecl (',' assrt_statevardecl)* '>'
->
	^(ASSRT_STATEVARDECL_LIST assrt_statevardecl+)
;

assrt_intvarname: t=IDENTIFIER -> IDENTIFIER<AssrtIntVarNameNode>[$t] ;  // N.B. Specifically int

assrt_statevardecl:
	assrt_intvarname ':=' arith_expr
->
	^(ASSRT_STATEVARDECL assrt_intvarname
			//EXTID<AssrtAExprNode>[$id, AssertionsParser.parseArithAnnotation($id.text)]
			{new AssrtAExprNode(input.LT(-1).getType(), input.LT(-1),   // https://www.antlr3.org/pipermail/antlr-interest/2010-January/037325.html 
					(AssrtAFormula) AssrtAntlrToFormulaParser
				.getInstance().parse((CommonTree) $arith_expr.tree))}
			)
;
	
/*statevararglist:
	'<' arith_expr (',' arith_expr)* '>'
->
	^(ASSRT_STATEVARARGLIST arith_expr+)
;
//*/

assrt_statevarargs:
	'<' assrt_statevararg (',' assrt_statevararg)* '>'
->
	^(ASSRT_STATEVARARG_LIST assrt_statevararg+)  // HERE statevararglist
;

// TODO: refactor with assrt_statevardecl
assrt_statevararg:  // ScribNode "wrappers" (for EXTID/Assertions.g), cf. simple names (for ID)
	arith_expr
->
	{new AssrtAExprNode(input.LT(-1).getType(), input.LT(-1),   // https://www.antlr3.org/pipermail/antlr-interest/2010-January/037325.html 
					(AssrtAFormula) AssrtAntlrToFormulaParser
				.getInstance().parse((CommonTree) $arith_expr.tree))}//EXTID<AssrtAExprNode>[$id, AssertionsParser.parseArithAnnotation($id.text)]
;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
