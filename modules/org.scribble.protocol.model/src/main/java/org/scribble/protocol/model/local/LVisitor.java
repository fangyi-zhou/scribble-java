/*
 * Copyright 2009 www.scribble.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble.protocol.model.local;

import org.scribble.protocol.model.Visitor;

/**
 * This interface represents a visitor which can be used
 * to traverse a model.
 */
public interface LVisitor extends Visitor {
    
    /**
     * This method indicates the start of a
     * block.
     * 
     * @param elem The block
     * @return Whether to process the contents
     */
    public boolean start(LBlock elem);
    
    /**
     * This method indicates the end of a
     * block.
     * 
     * @param elem The block
     */
    public void end(LBlock elem);
    
    /**
     * This method indicates the start of a
     * choice.
     * 
     * @param elem The choice
     * @return Whether to process the contents
     */
    public boolean start(LChoice elem);
    
    /**
     * This method indicates the end of a
     * choice.
     * 
     * @param elem The choice
     */
    public void end(LChoice elem);
    
    /**
     * This method indicates the start of a
     * parallel.
     * 
     * @param elem The parallel
     * @return Whether to process the contents
     */
    public boolean start(LParallel elem);
    
    /**
     * This method indicates the end of a
     * parallel.
     * 
     * @param elem The parallel
     */
    public void end(LParallel elem);
    
    /**
     * This method indicates the start of a
     * protocol.
     * 
     * @param elem The protocol
     * @return Whether to process the contents
     */
    public boolean start(LProtocol elem);
    
    /**
     * This method indicates the end of a
     * protocol.
     * 
     * @param elem The protocol
     */
    public void end(LProtocol elem);
    
    /**
     * This method indicates the start of a
     * labelled block.
     * 
     * @param elem The labelled block
     * @return Whether to process the contents
     */
    public boolean start(LRecursion elem);
    
    /**
     * This method indicates the end of a
     * labelled block.
     * 
     * @param elem The labelled block
     */
    public void end(LRecursion elem);
    
    /**
     * This method visits a send component.
     * 
     * @param elem The send
     */
    public void accept(LSend elem);
    
    /**
     * This method visits a receive component.
     * 
     * @param elem The receive
     */
    public void accept(LReceive elem);
    
    /**
     * This method visits a recursion component.
     * 
     * @param elem The recursion
     */
    public void accept(LContinue elem);
    
    /**
     * This method visits a
     * run construct.
     * 
     * @param elem The run
     */
    public void accept(LCreate elem);
    
    /**
     * This method visits a
     * run construct.
     * 
     * @param elem The run
     */
    public void accept(LEnter elem);
   
    /**
     * This method visits a
     * custom activity construct.
     * 
     * @param elem The custom activity
     */
    public void accept(LCustomActivity elem);
   
}