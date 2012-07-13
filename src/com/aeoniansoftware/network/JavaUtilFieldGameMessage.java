/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeoniansoftware.network;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kg
 */
@Serializable(id=1, serializer=FieldGameMessageSerializer.class)
public class JavaUtilFieldGameMessage 
extends BaseFieldGameMessage {

    private Map<Integer,String> sfs = new HashMap<Integer,String>() ;
    private Map<Integer,Integer> ifs = new HashMap<Integer,Integer>()  ;
    private Map<Integer,Long> lfs = new HashMap<Integer,Long>() ;
    private Map<Integer,Boolean> bfs = new HashMap<Integer,Boolean>()  ;
    private Map<Integer,Quaternion> v4ffs = new HashMap<Integer,Quaternion>() ;
    private Map<Integer,Vector3f> v3ffs = new HashMap<Integer,Vector3f>() ;
    private Map<Integer,Vector2f> v2ffs = new HashMap<Integer,Vector2f>() ;
    private Map<Integer,Double> dfs = new HashMap<Integer, Double>() ;
    private Map<Integer,Float> ffs = new HashMap<Integer, Float>() ;
    
    private List<IFieldGameMessage> kids = new ArrayList<IFieldGameMessage>() ;
    
	public JavaUtilFieldGameMessage () {
        super() ;
    }

    public JavaUtilFieldGameMessage (Byte mtype) {
        super(mtype);
    }

    public JavaUtilFieldGameMessage (Integer mtype) {
        super(mtype);
    }
    
    public JavaUtilFieldGameMessage (IFieldGameMessageContext cxt) {
        super(cxt);
    }
    
    public JavaUtilFieldGameMessage (String type, IFieldGameMessageContext cxt) {
        super(type, cxt) ;
    }
	
    private static int[] convertKeysToArray(Collection<Integer> ks) {
        int sz = ks.size() ;
        int[] aks = new int[sz] ;
        int i = 0 ;
        for (int k : ks) {
            aks[i] = k ;
            i++ ;
        } 
        return aks ;
    }
    
    public String getString(int k) {
        return this.sfs.get(k) ;
    }
    public void setString(int k, String v) {
        this.sfs.put(k, v) ;
    }
    public int[] getStringKeys() {
        return convertKeysToArray(this.sfs.keySet()) ;
    }

    public int getInt(int k) {
        return this.ifs.get(k) ;
    }
    public void setInt(int k, int v) {
        this.ifs.put(k, v) ;
    }
    public int[] getIntKeys() {
        return convertKeysToArray(this.ifs.keySet()) ;
    }

    public long getLong(int k) {
        return this.lfs.get(k) ;
    }
    public void setLong(int k, long v) {
        this.lfs.put(k, v) ;
    }
    public int[] getLongKeys() {
        return convertKeysToArray(this.lfs.keySet()) ;
    }

    public boolean getFlag(int k) {
        return this.bfs.get(k) ;
    }
    public void setFlag(int k, boolean v) {
        this.bfs.put(k, v) ;
    }
    public int[] getFlagKeys() {
        return convertKeysToArray(this.bfs.keySet()) ;
    }

    public Collection<IFieldGameMessage> getChildren() {
        return Collections.unmodifiableCollection(this.kids) ;
    }
    public void addChildren(IFieldGameMessage... newkids) {
        if (newkids.length + this.kids.size() > 255) throw new RuntimeException("too many children") ;
        this.kids.addAll(Arrays.asList(newkids));
    }

    public Quaternion getQuaternion(int k) {
        return this.v4ffs.get(k) ;
    }
    public void setQuaternion(int k, Quaternion v) {
        this.v4ffs.put(k, v) ;
    }
    public int[] getQuaternionKeys() {
        return convertKeysToArray(this.v4ffs.keySet()) ;
    }

    public Vector3f getVector3f(int k) {
        return this.v3ffs.get(k) ;
    }
    public void setVector3f(int k, Vector3f v) {
        this.v3ffs.put(k, v) ;
    }
    public int[] getVector3fKeys() {
       return convertKeysToArray(this.v3ffs.keySet()) ;
    }

    public Vector2f getVector2f(int k) {
        return this.v2ffs.get(k) ;
    }
    public void setVector2f(int k, Vector2f v) {
        this.v2ffs.put(k, v) ;
    }
    public int[] getVector2fKeys() {
        return convertKeysToArray(this.v2ffs.keySet()) ;
    }
    
    public double getDouble(int k) {
        return this.dfs.get(k) ;
    }
    public void setDouble(int k, double v) {
        this.dfs.put(k, v) ;
    }
    public int[] getDoubleKeys() {
        return convertKeysToArray(this.dfs.keySet()) ;
    }
    
    public float getFloat(int k) {
        return this.ffs.get(k) ;
    }
    public void setFloat(int k, float v) {
        this.ffs.put(k, v) ;
    }
    public int[] getFloatKeys() {
        return convertKeysToArray(this.ffs.keySet()) ;
    }
}
