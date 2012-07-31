/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeoniansoftware.network;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.aeoniansoftware.network.IFieldGameMessageContext.FieldType ;
//import com.jme3.network.serializing.Serializable;

/**
 * basic implementation of field game message;
 *      mainly enforces some basic behavior assumptions
 * 
 * @author kg
 */
//@Serializable(id=23, serializer=FieldGameMessageSerializer.class)
public abstract class BaseFieldGameMessage 
extends AbstractMessage 
implements IFieldGameMessage {
    private Byte type = (byte) 0;
    private Byte ord = Byte.MAX_VALUE;
    private IFieldGameMessageContext context ;
    
    public BaseFieldGameMessage() {
        
    }

    public BaseFieldGameMessage(Byte mtype) {
        this.setType(mtype);
    }

    public BaseFieldGameMessage(Integer mtype) {
        this.setType(mtype);
    }
    
    public BaseFieldGameMessage(IFieldGameMessageContext cxt) {
        this.setContext(cxt);
    }
    
    public BaseFieldGameMessage(String type, IFieldGameMessageContext cxt) {
        this(cxt) ;
        this.setType(type);
    }
    
    public void setContext(IFieldGameMessageContext cxt) {
        this.context = cxt ;
    }
    public IFieldGameMessageContext getContext() {
        return this.context ;
    }
    
    public Byte getType() {
        return this.type;
    }
    public String getTypeAlias() {
        int ti = this.getType().intValue() ;
        return this.getContext().alias(FieldType.Type, ti) ;
    }
    public void setType(Integer mtype) {
        this.setType(
           (mtype == null) ? null : mtype.byteValue()
        );
    }
    public void setType(Byte mtype) {
        this.type = mtype;
    }
    public void setType(String typeAlias) {
        IFieldGameMessageContext cxt = this.getContext() ;
        
        if (cxt == null) {
            this.setType((Byte) null);
            return ;
        }
        
        this.setType(
            cxt.unalias(FieldType.Type, typeAlias)
        ); 
    }

    public Byte getOrd() {
        return (this.ord == null) ? Byte.MAX_VALUE : this.ord;
    }

    public void setOrd(Integer o) {
        this.ord = o.byteValue();
    }

    public void setOrd(Byte o) {
        this.ord = o;
    }

    public int compareTo(IFieldGameMessage that) {
        return Compare.compare(this, that);
    }

    protected int resolveAlias(FieldType ft, String alias) {
        IFieldGameMessageContext cxt = this.getContext() ;
        if (cxt == null) throw new RuntimeException("no alias context associated to message entity") ;
        return cxt.unalias(ft, alias) ;
    }
    
    public void setInt(String alias, int v) {
        int k = this.resolveAlias(FieldType.Int, alias) ;
        this.setInt(k, v);
    }
    public int getInt(String alias) {
        int k = this.resolveAlias(FieldType.Int, alias) ;
        return this.getInt(k) ;
    }

    public void setFlag(String alias, boolean v) {
        int k = this.resolveAlias(FieldType.Flag, alias) ;
        this.setFlag(k, v);
    }
    public boolean getFlag(String alias) {
        int k = this.resolveAlias(FieldType.Flag, alias) ;
        return this.getFlag(k) ;
    }
    
    public void setLong(String alias, long v) {
        int k = this.resolveAlias(FieldType.Long, alias) ;
        this.setLong(k, v);
    }
    public long getLong(String alias) {
        int k = this.resolveAlias(FieldType.Long, alias) ;
        return this.getLong(k) ;
    }

    public void setQuaternion(String alias, Quaternion v) {
        int k = this.resolveAlias(FieldType.Quaternion, alias) ;
        this.setQuaternion(k, v);
    }
    public Quaternion getQuaternion(String alias) {
        int k = this.resolveAlias(FieldType.Quaternion, alias) ;
        return this.getQuaternion(k) ;
    }

    public void setString(String alias, String v) {
        int k = this.resolveAlias(FieldType.String, alias) ;
        this.setString(k, v);
    }
    public String getString(String alias) {
        int k = this.resolveAlias(FieldType.String, alias) ;
        return this.getString(k) ;
    }

    public void setVector2f(String alias, Vector2f v) {
        int k = this.resolveAlias(FieldType.Vector2f, alias) ;
        this.setVector2f(k, v);
    }
    public Vector2f getVector2f(String alias) {
        int k = this.resolveAlias(FieldType.Vector2f, alias) ;
        return this.getVector2f(k) ;
    }

    public void setVector3f(String alias, Vector3f v) {
        int k = this.resolveAlias(FieldType.Vector3f, alias) ;
        this.setVector3f(k, v);
    }
    public Vector3f getVector3f(String alias) {
        int k = this.resolveAlias(FieldType.Vector3f, alias) ;
        return this.getVector3f(k) ;
    }

    public void setDouble(String alias, double v) {
        int k =  this.resolveAlias(FieldType.Double, alias) ;
        this.setDouble(k, v);
    }
    public double getDouble(String alias) {
        int k =  this.resolveAlias(FieldType.Double, alias) ;
        return this.getDouble(k) ;
    }
    
    public void setFloat(String alias, float v) {
        int k =  this.resolveAlias(FieldType.Double, alias) ;
        this.setFloat(k, v);
    }
    public float getFloat(String alias) {
        int k =  this.resolveAlias(FieldType.Double, alias) ;
        return this.getFloat(k) ;
    }
}
