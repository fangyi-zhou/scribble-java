package org.scribble.ext.f17.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.scribble.ext.f17.ast.local.F17LType;
import org.scribble.ext.f17.ast.local.action.F17LAccept;
import org.scribble.ext.f17.ast.local.action.F17LConnect;
import org.scribble.ext.f17.ast.local.action.F17LDisconnect;
import org.scribble.ext.f17.ast.local.action.F17LReceive;
import org.scribble.ext.f17.ast.local.action.F17LSend;
import org.scribble.ext.f17.model.action.F17Action;
import org.scribble.sesstype.name.Role;


public class F17ModelBuilder
{
	public F17ModelBuilder()
	{

	}
	
	public F17State build(Map<Role, F17LType> P0, boolean explicit)
	{
		F17State init = new F17State(P0, explicit); 
		
		Set<F17State> todo = new HashSet<>();
		Set<F17State> seen = new HashSet<>();
		todo.add(init);
		
		while (!todo.isEmpty())
		{
			Iterator<F17State> i = todo.iterator();
			F17State curr = i.next();
			i.remove();
			seen.add(curr);

			Map<Role, List<F17Action>> fireable = curr.getFireable();
			Set<Entry<Role, List<F17Action>>> es = new HashSet<>(fireable.entrySet());
			while (!es.isEmpty())
			{
				Iterator<Entry<Role, List<F17Action>>> j = es.iterator();
				Entry<Role, List<F17Action>> e = j.next();
				j.remove();
				//boolean removed = es.remove(e);

				Role r = e.getKey();
				List<F17Action> as = e.getValue();
				for (F17Action a : as)
				{
					// cf. SState.getNextStates
					final F17State tmp;
					if (a.action instanceof F17LSend || a.action instanceof F17LReceive || a.action instanceof F17LDisconnect)
					{
						tmp = curr.fire(r, a);
					}
					else if (a.action instanceof F17LConnect || a.action instanceof F17LAccept)
					{
						F17Action dual = new F17Action(a.action.toDual());
						tmp = curr.sync(r, a, a.action.peer, dual);
						for (Entry<Role, List<F17Action>> foo : es)
						{
							if (foo.getKey().equals(a.action.peer))
							{
								es.remove(foo);
								foo.getValue().remove(dual);  // remove side effect causes underlying hashing to become inconsistent, so need to manually remove/re-add
								es.add(foo);
								break;
							}
						}
						if (a.action instanceof F17LAccept)
						{
							a = dual;  // HACK: draw connect/accept sync edges as connect (to stand for the sync of both) -- set of actions as edge label probably more consistent
						}
					}
					else
					{
						throw new RuntimeException("[f17] Shouldn't get in here: " + a);
					}

					F17State next = tmp;
					if (seen.contains(tmp))
					{
						next = seen.stream().filter((s) -> s.equals(tmp)).iterator().next();
					}
					else if (todo.contains(tmp))
					{
						next = todo.stream().filter((s) -> s.equals(tmp)).iterator().next();
					}
					curr.addEdge(a, next);
					if (!seen.contains(tmp) && !todo.contains(next))
					{
						todo.add(next);
					}
				}
			}
		}
		
		return init;
	}
}
