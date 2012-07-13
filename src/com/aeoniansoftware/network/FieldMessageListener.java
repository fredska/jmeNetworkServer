/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeoniansoftware.network;

import com.jme3.network.Message;
import com.jme3.network.MessageListener;


/**
 *
 * @author m.William buda
 */
public abstract class FieldMessageListener<S> 
implements MessageListener<S> {
    
    public void messageReceived(S source, Message m) {
        if (!(m instanceof IFieldGameMessage)) return ;
        IFieldGameMessage thm = (IFieldGameMessage)m ;
        if (!this.matchType(thm.getType())) return ;
        this.handle(thm) ;
    }
    
    protected boolean matchType(Byte type) {
        return true ;
    }
    
    protected abstract void handle(IFieldGameMessage m) ;
    
}
