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
package org.scribble.ext.assrt.visit.wf.env;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ext.assrt.sesstype.name.AssrtAnnotDataType;
import org.scribble.ext.assrt.sesstype.name.AssrtDataTypeVarName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.env.Env;

public class AssrtAnnotationEnv extends Env<AssrtAnnotationEnv>
{
	//private Map<Role, Set<AssrtPayloadType<? extends PayloadTypeKind>>> payloadTypes;  // "Knowledge" or "ownership" ?

	private Map<Role, Set<AssrtAnnotDataType>> decls;  // Var declaration binding  // Role is the src role of the transfer -- not important?  // FIXME: clarify as a "may" check

	private Map<Role, Set<AssrtDataTypeVarName>> vars;  // "Knowledge" of var (given by message passing)  // FIXME: do by model checking rather than syntactically?
			// FIXME: clarify as a "must" check
	
	public AssrtAnnotationEnv()
	{
		this(Collections.emptyMap(), Collections.emptyMap());
	}
	
	//protected AssrtAnnotationEnv(Map<Role, Set<AssrtPayloadType<?>>> payloads)
	protected AssrtAnnotationEnv(Map<Role, Set<AssrtAnnotDataType>> decls, Map<Role, Set<AssrtDataTypeVarName>> vars)
	{
		//this.payloadTypes = new HashMap<>(payloads);
		this.decls = new HashMap<>(decls);
		this.vars = new HashMap<>(vars);
	}

	@Override
	public AssrtAnnotationEnv copy()
	{
		//return new AssrtAnnotationEnv(new HashMap<Role, Set<AssrtPayloadType<?>>>(this.payloadTypes));
		return new AssrtAnnotationEnv(new HashMap<>(this.decls), new HashMap<>(this.vars));
	}

	@Override
	public AssrtAnnotationEnv enterContext()
	{
		return copy();
	}

	// "Global" syntactic scoping -- binding insensitive to roles (and DataType)
	public boolean isDataTypeVarBound(AssrtDataTypeVarName v)
	{
		return this.decls.values().stream().flatMap(s -> s.stream()).anyMatch(adt -> adt.var.equals(v));
	}
	
	public boolean isDataTypeVarKnown(Role r, AssrtDataTypeVarName avn)
	{
		Set<AssrtDataTypeVarName> tmp = this.vars.get(r);
		return tmp != null && tmp.stream().anyMatch(v -> v.equals(avn));
	}

	/*// FIXME: refactor exception throwing into the del
	public boolean isAnnotVarDeclValid(AssrtAnnotDataType adt, Role src, List<Role> dests) throws ScribbleException 
	{
		if (this.payloadTypes.values().stream().anyMatch(x -> x.contains(adt)))  // FIXME: needs both var and data type to be the same?
		{
			return false;
		}
		addPayloadToRole(src, adt); 
		dests.forEach(d -> addPayloadToRole(d, adt));
		return true;
	}
	
	public boolean isAnnotVarNameValid(AssrtVarName pe, Role src, List<Role> dests) throws ScribbleException 
	{
		if (!this.payloadTypes.containsKey(src) || !this.payloadTypes.get(src).stream().anyMatch(v -> ((AssrtAnnotDataType) v).varName.equals(pe)))
		{
			throw new ScribbleException("Payload " + pe.toString() + " is not in scope");
		}

		// add the type int to the varname before adding the scope of the payload.
		for(Role dest: dests)
		{
			Optional<AssrtPayloadType<?>> newPe = this.payloadTypes.get(src).stream()
					.filter(v -> ((AssrtAnnotDataType) v).varName.equals(pe)).findAny(); 
			addPayloadToRole(dest, newPe.get());
		}
		return true;
	}*/

	// Also records src as "knowing" adt.var
	public AssrtAnnotationEnv addAnnotDataType(Role src, AssrtAnnotDataType adt)
	{
		AssrtAnnotationEnv copy = copy();
		copy.addAnnotDataTypeAux(src, adt);
		copy.addDataTypeVarNameAux(src, adt.var);
		return copy;
	}
	
	private void addAnnotDataTypeAux(Role role, AssrtAnnotDataType adt)
	{
		Set<AssrtAnnotDataType> tmp = this.decls.get(role);
		if (tmp == null)
		{
			tmp = new HashSet<>();
			this.decls.put(role, tmp);
		}
		tmp.add(adt);
	}

	public AssrtAnnotationEnv addDataTypeVarName(Role role, AssrtDataTypeVarName v)
	{
		AssrtAnnotationEnv copy = copy();
		copy.addDataTypeVarNameAux(role, v);
		return copy;
	}
	
	private void addDataTypeVarNameAux(Role role, AssrtDataTypeVarName v)
	{
		Set<AssrtDataTypeVarName> tmp = this.vars.get(role);
		if (tmp == null)
		{
			tmp = new HashSet<>();
			this.vars.put(role, tmp);
		}
		tmp.add(v);
	}

	@Override
	public AssrtAnnotationEnv mergeContext(AssrtAnnotationEnv child)
	{
		return mergeContexts(Arrays.asList(child));
	}
	
  // Cf. WFChoiceEnv merge pattern -- unlike WFChoiceEnv, no env "clearing" on choice enter -- child envs are originally direct copies of parent, so can merge for updated parent directly from children (cf. "running" merge parameterised on original parent)
	@Override
	public AssrtAnnotationEnv mergeContexts(List<AssrtAnnotationEnv> children)
	{
		AssrtAnnotationEnv copy = copy();

		// Take "intersection" for both decls and vars? -- implicitly handles vars "inherited" from before, e.g., a choice
		Set<Role> declRoles = children.stream()
				.flatMap(e -> e.decls.keySet().stream())
				.filter(r -> children.stream().map(e -> e.decls.keySet()).allMatch(ks -> ks.contains(r)))
				.collect(Collectors.toSet());
		Map<Role, Set<AssrtAnnotDataType>> decls = new HashMap<>();
		for (Role r : declRoles)
		{
			decls.put(r, children.stream().map(c -> c.decls.get(r))
					 .reduce((s1, s2) -> s1.stream().filter(s2::contains).collect(Collectors.toSet())).get()
			);
		}
		copy.decls = decls;
		
		Set<Role> varsRoles = children.stream()  // FIXME: factor out with above?
				.flatMap(e -> e.vars.keySet().stream())
				.filter(r -> children.stream().map(e -> e.vars.keySet()).allMatch(ks -> ks.contains(r)))
				.collect(Collectors.toSet());
		Map<Role, Set<AssrtDataTypeVarName>> vars = new HashMap<>();
		for (Role r : varsRoles)
		{
			vars.put(r, children.stream().map(c -> c.vars.get(r))
					 .reduce((s1, s2) -> s1.stream().filter(s2::contains).collect(Collectors.toSet())).get()
			);
		}
		copy.vars = vars;
		
		return copy;
	}
	
	@Override
	public String toString()
	{
		return "[decls=" + this.decls + ", vars=" + this.vars + "]";
	}

	/*public boolean checkIfPayloadValid(AssrtPayloadType<?> pe, Role src, List<Role> dests) throws ScribbleException 
	{
		boolean payloadExist = this.payloadTypes.values().stream().anyMatch(x -> x.contains(pe)); 
		
		if (pe.isAnnotVarDecl() && payloadExist)
		{
			throw new ScribbleException("Payload " + pe.toString() + " is already declared"); 
		}
		else if (pe.isAnnotVarDecl() && !payloadExist)
		{
			addPayloadToRole(src, pe); 
			for(Role dest: dests)
			{
				addPayloadToRole(dest, pe);
			}
		}
		else if (pe.isAnnotVarName() && !this.payloadTypes.containsKey(src)
				|| !this.payloadTypes.get(src).stream().anyMatch(v -> ((AssrtAnnotDataType) v).varName.equals(pe)))
		{
			throw new ScribbleException("Payload " + pe.toString() + " is not in scope");
		}
		else if (pe.isAnnotVarName())
		{
			// add the type int to the varname before adding the scope of the payload.
			for(Role dest: dests)
			{
				Optional<AssrtPayloadType<?>> newPe = this.payloadTypes.get(src).stream()
						.filter(v -> ((AssrtAnnotDataType) v).varName.equals(pe)).findAny(); 
				addPayloadToRole(dest, newPe.get());
			}
		}
		return true; 
	}
	
	// FIXME: not using immutable pattern
	public void addPayloadToRole(Role role, AssrtPayloadType<?> pe)
	{
		if (!this.payloadTypes.containsKey(role))
		{
			this.payloadTypes.put(role, new HashSet<AssrtPayloadType<? extends PayloadTypeKind>>());
			
		}
		this.payloadTypes.get(role).add(pe);
	}*/
	
	/*@Override
	public AssrtAnnotationEnv mergeContext(AssrtAnnotationEnv child)
	{
		//return mergeContexts(Arrays.asList(child));
		return child;  // FIXME: cf. original GChoiceDel.leaveAnnotCheck
	}
	
	// FIXME? not being used to merge into this, as supposed for Env -- factor out separately?
	@Override
	public AssrtAnnotationEnv mergeContexts(List<AssrtAnnotationEnv> envs)
	{
		Map<Role, Set<AssrtPayloadType<? extends PayloadTypeKind>>> payloads = 
		//		envs.stream().findAny().get().payloads;  
		//AnnotationEnv env = copy();
		//Map<Role, HashSet<PayloadType<? extends PayloadTypeKind>>> payloads = 
				envs.stream().flatMap(e -> e.payloadTypes.entrySet().stream())
						.collect(Collectors.toMap(
								Map.Entry<Role, Set<AssrtPayloadType<? extends PayloadTypeKind>>>::getKey,   // e.payloads is: Role -> Set<AsertPayloadType>
								Map.Entry<Role, Set<AssrtPayloadType<? extends PayloadTypeKind>>>::getValue,  
								(v1, v2) -> {
									Set<AssrtPayloadType<? extends PayloadTypeKind>> s = 
											v1.stream().filter(e -> v2.contains(e)).collect(Collectors.toSet());   // Intersection of v1 and v2 sets
									return s; 
								})
						); 
		
		return new AssrtAnnotationEnv(payloads);  
	}*/
}
