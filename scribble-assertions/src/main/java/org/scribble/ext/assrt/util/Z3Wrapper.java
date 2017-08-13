package org.scribble.ext.assrt.util;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ext.assrt.type.formula.AssrtBinaryFormula;
import org.scribble.ext.assrt.type.formula.AssrtBoolFormula;
import org.scribble.ext.assrt.type.formula.AssrtQuantifiedIntVarsFormula;
import org.scribble.ext.assrt.type.formula.AssrtSmtFormula;
import org.scribble.ext.assrt.type.formula.AssrtUnPredicateFormula;
import org.scribble.main.ScribbleException;
import org.scribble.type.name.Role;
import org.scribble.util.ScribUtil;

// "Native" Z3 -- not Z3 Java API
public class Z3Wrapper
{

	// Duplicated from JobContext::runAut
	// protoname only used for naming tmp file
	//public static boolean isSat(String smt2, String protoname) //throws ScribbleException
	public static boolean isSat(GProtocolDecl gpd, AssrtBoolFormula f) //throws ScribbleException
	{
		String tmpName = gpd.header.name + ".smt2.tmp";
		File tmp = new File(tmpName);
		if (tmp.exists())  // Factor out with CommandLine.runDot (file exists check)
		{
			throw new RuntimeException("Cannot overwrite: " + tmpName);
		}
		String smt2 = toSmt2(gpd, f);
		try
		{
			ScribUtil.writeToFile(tmpName, smt2);
			String[] res = ScribUtil.runProcess("Z3", tmpName);
			String trim = res[0].trim();
			if (trim.equals("sat"))  // FIXME: factor out
			{
				return true;
			}
			else if (trim.equals("unsat"))
			{
				return false;
			}
			else
			{
				throw new RuntimeException("[assrt] Z3 error: " + Arrays.toString(res));
			}
		}
		catch (ScribbleException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			tmp.delete();
		}
	}
	
	private static String toSmt2(GProtocolDecl gpd, AssrtBoolFormula f)
	{
		String smt2 = "";
		List<Role> rs = gpd.getHeader().roledecls.getRoles();
		smt2 += IntStream.range(0, rs.size())
				.mapToObj(i -> "(declare-const " + rs.get(i) + " Int)\n(assert (= " + rs.get(i) + " " + i +"))\n").collect(Collectors.joining(""));
		Set<AssrtUnPredicateFormula> preds = getUnPredicates.func.apply(f);
		smt2 += preds.stream().map(p -> "(declare-fun " + p.name + " ("
				+ IntStream.range(0, p.args.size()).mapToObj(i -> ("(Int)")).collect(Collectors.joining(" "))
				+ ") Bool)\n").collect(Collectors.joining(""));
		smt2 +=  
				  "(assert " + f.toSmt2Formula() + ")\n"
				+ "(check-sat)\n"
				+ "(exit)";
		
		//System.out.println("[assrt-core] Z3 string: " + smt2);
		
		return smt2;
	}

	private static final Recursive<Function<AssrtSmtFormula<?>, Set<AssrtUnPredicateFormula>>> getUnPredicates =
			new Recursive<Function<AssrtSmtFormula<?>, Set<AssrtUnPredicateFormula>>>()
	{{
		this.func = ff ->
		{
			if (ff instanceof AssrtBinaryFormula)
			{
				AssrtBinaryFormula<?> bf = (AssrtBinaryFormula<?>) ff;
				return Stream.of(bf.getLeft(), bf.getRight()).flatMap(x -> this.func.apply(x).stream()).collect(Collectors.toSet());
			}
			else if (ff instanceof AssrtQuantifiedIntVarsFormula)
			{
				return this.func.apply(((AssrtQuantifiedIntVarsFormula) ff).expr);
			}
			else if (ff instanceof AssrtUnPredicateFormula)
			{
				return Stream.of((AssrtUnPredicateFormula) ff).collect(Collectors.toSet());  // Nested predicates not possible
			}
			else
			{
				return Collections.emptySet();
			}
		};
	}};
}


// FIXME: factor out?
class Recursive<F>  // F should be a functional interface
{
	public F func;
}

	/*private static Set<AssrtUnPredicateFormula> getUnPredicates(AssrtSmtFormula<?> f) 
	{
		Recursive<Function<AssrtSmtFormula<?>, Set<AssrtUnPredicateFormula>>> rec = new Recursive<>();
		rec.func = ff ->
		{
			if (ff instanceof AssrtBinaryFormula)
			{
				AssrtBinaryFormula<?> bf = (AssrtBinaryFormula<?>) ff;
				return Stream.of(bf.getLeft(), bf.getRight()).flatMap(x -> rec.func.apply(x).stream()).collect(Collectors.toSet());
			}
			else if (ff instanceof AssrtQuantifiedIntVarsFormula)
			{
				return rec.func.apply(((AssrtQuantifiedIntVarsFormula) ff).expr);
			}
			else if (ff instanceof AssrtUnPredicateFormula)
			{
				return Stream.of((AssrtUnPredicateFormula) ff).collect(Collectors.toSet());  // Nested predicates not possible
			}
			else
			{
				return Collections.emptySet();
			}
		};
		return rec.func.apply(f);
	}*/
