package org.scribble.ext.assrt.core.type.session;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.type.name.DataName;
import org.scribble.core.type.session.SType;
import org.scribble.ext.assrt.core.type.name.AssrtIntVar;

public interface AssrtCoreSType<K extends ProtoKind, 
			B extends AssrtCoreSType<K, B>>
		extends SType<K, NoSeq<K>>
{
	<T> Stream<T> assrtCoreGather(Function<AssrtCoreSType<K, B>, Stream<T>> f);

	// Return *additional* env items -- i.e., ctxt intersect with return is empty
	Map<AssrtIntVar, DataName> getSortEnv(Map<AssrtIntVar, DataName> ctxt);

	default void foo()
	{
		Token t = null;
		t.setText(null);
		new CommonTree(t);
	}
}
