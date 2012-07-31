/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeoniansoftware.network;

import java.util.Collection;
import java.util.Comparator;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector2f;
import com.jme3.network.Message;

/**
 *
 * @author kg
 */
public interface IFieldGameMessage 
extends Comparable<IFieldGameMessage>, Message {
    
    public static final Comparator<IFieldGameMessage> Compare = 
    new Comparator<IFieldGameMessage>() {
        public int compare(
            IFieldGameMessage a, 
            IFieldGameMessage b
        ) {
            Integer aord = Integer.valueOf( a.getOrd() ) ;
            Integer bord = Integer.valueOf( b.getOrd() ) ;
            return aord.compareTo(bord) ;
        }
    } ;
    
    public void setContext(IFieldGameMessageContext cxt) ;
    public IFieldGameMessageContext getContext() ;
    
    /**
     * default type == 0
     * @return ubyte : type indication of message
     */
    public Byte getType() ;
    public String getTypeAlias() ;
    public void setType(Integer mtype) ;
    public void setType(Byte mtype) ;
    public void setType(String alias) ;
    
    /**
     * default ordering is maximum signed byte
     * @return sbyte : ordering of message compared to others
     */
    public Byte getOrd() ;
    public void setOrd(Integer o) ;
    public void setOrd(Byte o) ;
 
    /**
     * 
     * @param k : unsigned btye
     * @return string field tied to given ubyte
     */
    public String getString(int k);
    public String getString(String alias) ;
    public void setString(int k, String v);
    public void setString(String alias, String v);
    public int[] getStringKeys();
    
    /**
     * 
     * @param k : unsigned btye
     * @return signed int field tied to given ubyte
     */
    public int getInt(int k);
    public int getInt(String alias);
    public void setInt(int k, int v);
    public void setInt(String alias, int v);
    public int[] getIntKeys();
    
    /**
     * 
     * @param k : unsigned btye
     * @return signed long field tied to given ubyte
     */
    public long getLong(int k);
    public long getLong(String alias);
    public void setLong(int k, long v);
    public void setLong(String alias, long v);
    public int[] getLongKeys();
    
    /**
     * 
     * @param k : unsigned btye
     * @return boolean field tied to given ubyte
     */
    public boolean getFlag(int k);
    public boolean getFlag(String alias);
    public void setFlag(int k, boolean v);
    public void setFlag(String alias, boolean v);
    public int[] getFlagKeys();
    
    /**
     * 
     * @return all children message objects
     */
    public Collection<IFieldGameMessage> getChildren() ;
    public void addChildren(IFieldGameMessage... kids) ;

    public Quaternion getQuaternion(int k) ;
    public Quaternion getQuaternion(String alias) ;
    public void setQuaternion(int k, Quaternion v) ;
    public void setQuaternion(String alias, Quaternion v) ;
    public int[] getQuaternionKeys();

    public Vector3f getVector3f(int k) ;
    public Vector3f getVector3f(String alias) ;
    public void setVector3f(int k, Vector3f v) ;
    public void setVector3f(String alias, Vector3f v) ;
    public int[] getVector3fKeys();

    public Vector2f getVector2f(int k) ;
    public Vector2f getVector2f(String alias) ;
    public void setVector2f(int k, Vector2f v) ;
    public void setVector2f(String alias, Vector2f v) ;
    public int[] getVector2fKeys();
    
    public double getDouble(int k) ;
    public double getDouble(String alias) ;
    public void setDouble(int k, double v) ;
    public void setDouble(String alias, double v) ;
    public int[] getDoubleKeys();
    
    public float getFloat(int k) ;
    public float getFloat(String alias) ;
    public void setFloat(int k, float v) ;
    public void setFloat(String alias, float v) ;
    public int[] getFloatKeys();
}
