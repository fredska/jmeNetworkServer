package com.aeoniansoftware.network;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
//import com.jme3.network.serializing.Serializable;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntLongMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.map.hash.TIntFloatHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntLongHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * generic field-based message class
 * @author m.William buda
 */
//@Serializable(id=12, serializer=FieldGameMessageSerializer.class)
public class TroveFieldGameMessage
extends BaseFieldGameMessage
{

    private TIntObjectMap<String> sfields  = new TIntObjectHashMap<String>();
    private TIntIntMap i32fields = new TIntIntHashMap() ;
    private TIntLongMap i64fields = new TIntLongHashMap() ;
    private TIntObjectMap<Boolean> flags   = new TIntObjectHashMap<Boolean>();
    private List<IFieldGameMessage> kids = new ArrayList<IFieldGameMessage>() ;
    private TIntObjectMap<Quaternion> v4fs   = new TIntObjectHashMap<Quaternion>();
    private TIntObjectMap<Vector3f> v3fs   = new TIntObjectHashMap<Vector3f>();
    private TIntObjectMap<Vector2f> v2fs   = new TIntObjectHashMap<Vector2f>();
    private TIntDoubleHashMap dfs = new TIntDoubleHashMap() ;
    private TIntFloatHashMap ffs = new TIntFloatHashMap() ;
    
    public TroveFieldGameMessage() {
        super() ;
    }

    public TroveFieldGameMessage(Byte mtype) {
        super(mtype);
    }

    public TroveFieldGameMessage(Integer mtype) {
        super(mtype);
    }
    
    public TroveFieldGameMessage(IFieldGameMessageContext cxt) {
        super(cxt);
    }
    
    public TroveFieldGameMessage(String type, IFieldGameMessageContext cxt) {
        super(type, cxt) ;
    }

    public String getString(int k) {
        return this.sfields.get(k) ;
    }
    public void setString(int k, String v) {
        this.sfields.put(k, v) ;
    }
    public int[] getStringKeys() {
        return this.sfields.keys() ;
    }

    public int getInt(int k) {
        return this.i32fields.get(k) ;
    }
    public void setInt(int k, int v) {
        this.i32fields.put(k, v);
    }
    public int[] getIntKeys() {
        return this.i32fields.keys() ;
    }

    public long getLong(int k) {
        return this.i64fields.get(k) ;
    }
    public void setLong(int k, long v) {
        this.i64fields.put(k, v);
    }
    public int[] getLongKeys() {
        return this.i64fields.keys() ;
    }
    
    public boolean getFlag(int k) {
        return this.flags.get(k) ;
    }
    public void setFlag(int k, boolean v) {
        this.flags.put(k, v) ;
    }
    public int[] getFlagKeys() {
        return this.flags.keys() ;
    }

    public Collection<IFieldGameMessage> getChildren() {
        return Collections.unmodifiableCollection(this.kids) ;
    }
    public void addChildren(IFieldGameMessage... newkids) {
        if (newkids.length + this.kids.size() > 255) throw new RuntimeException("too many children") ;
        this.kids.addAll(Arrays.asList(newkids));
    }

    public Quaternion getQuaternion(int k) {
        return this.v4fs.get(k) ;
    }
    public void setQuaternion(int k, Quaternion v) {
        this.v4fs.put(k, v) ;
    }
    public int[] getQuaternionKeys() {
        return this.v4fs.keys() ;
    }

    public Vector3f getVector3f(int k) {
        return this.v3fs.get(k) ;
    }
    public void setVector3f(int k, Vector3f v) {
        this.v3fs.put(k, v) ;
    }
    public int[] getVector3fKeys() {
        return this.v3fs.keys() ;
    }

    public Vector2f getVector2f(int k) {
        return this.v2fs.get(k) ;
    }
    public void setVector2f(int k, Vector2f v) {
        this.v2fs.put(k, v) ;
    }
    public int[] getVector2fKeys() {
        return this.v2fs.keys() ;
    }
    
    public double getDouble(int k) {
        return this.dfs.get(k) ;
    }
    public void setDouble(int k, double v) {
        this.dfs.put(k, v) ;
    }
    public int[] getDoubleKeys() {
        return this.dfs.keys() ;
    }
    
    public float getFloat(int k) {
        return this.ffs.get(k) ;
    }
    public void setFloat(int k, float v) {
        this.ffs.put(k, v) ;
    }
    public int[] getFloatKeys() {
        return this.ffs.keys() ;
    }
}
