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
package org.scribble.ast.local;

import java.util.HashSet;
import java.util.Set;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.global.GDisconnect;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public class LDisconnect extends LConnectionAction implements LSimpleInteractionNode
{
	public final RoleNode self;  // super.src
	public final RoleNode peer;  // super.dest
	
	public LDisconnect(CommonTree source, RoleNode self, RoleNode peer)
	{
		super(source, self, GDisconnect.UNIT_MESSAGE_SIG_NODE, peer);
		this.self = self;
		this.peer = peer;
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LDisconnect(this.source, this.self, this.peer);
	}
	
	@Override
	public LDisconnect clone(AstFactory af)
	{
		RoleNode self = this.self.clone(null);
		RoleNode peer = this.peer.clone(null);
		return af.LDisconnect(this.source, self, peer);
	}

	@Override
	public LDisconnect reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	//public LDisconnect reconstruct(RoleNode self, RoleNode peer)
	{
		ScribDel del = del();
		LDisconnect lr = new LDisconnect(this.source, this.self, this.peer);
		lr = (LDisconnect) lr.del(del);
		return lr;
	}

	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(this.self.toName());
		return this.self.toName();
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LSimpleInteractionNode.super.getKind();
	}

	@Override
	public String toString()
	{
		return Constants.DISCONNECT_KW + " " + this.peer + ";";
	}

	@Override
	public LInteractionNode merge(AstFactory af, LInteractionNode ln) throws ScribbleException
	{
		throw new RuntimeScribbleException("Invalid merge on LDisconnect: " + this);
	}

	@Override
	public boolean canMerge(LInteractionNode ln)
	{
		return false;
	}

	@Override
	public Set<Message> getEnabling()
	{
		Set<Message> enab = new HashSet<>();
		//enab.add(this.msg.toMessage());  // Return empty to skip over this in LInteractionSeq
		return enab;
	}
}
