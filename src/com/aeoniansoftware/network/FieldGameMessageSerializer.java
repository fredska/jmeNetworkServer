package com.aeoniansoftware.network;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializer ;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;

/**
 * custom jmonkey serializer for our game message class.
 * basicly just calls the de/serialization methods on the message dto class
 * @author m.William buda
 */
public class FieldGameMessageSerializer<X extends IFieldGameMessage>
extends Serializer {

    static {
        Serializer.registerClass(
			IFieldGameMessage.class, 
			new FieldGameMessageSerializer(JavaUtilFieldGameMessage.class)
		) ;
    }
    
    private final Class<X> messageImplClass ;

    public FieldGameMessageSerializer(Class<X> type) {
        this.messageImplClass = type ;
    }
    
    /**
     * this method exists for the sole purpose of shutting the compiler up,
     *  due to jmonkeys weird as fuck type-sigs 
     */
    public <T> T readObject(ByteBuffer data, Class<T> klass)
    throws IOException {
        return (T) readMessage(data) ;
    }
    
    public Object readMessage(ByteBuffer data) 
    throws IOException {
        try {
            return deserialize(this.messageImplClass, data) ;
        } catch (IOException ie) {
            throw ie ;
        } catch (Exception e) {
            throw new IOException(e) ;
        }
    }

    @Override
    public void writeObject(ByteBuffer buffer, Object raw) 
    throws IOException {
        IFieldGameMessage mssg = (IFieldGameMessage)raw ;
        try {
            serialize(mssg, buffer); 
        } catch (IOException ie) {
            throw ie ;
        } catch (Exception e) {
            throw new IOException(e) ;
        }
    }
    
    public static void serialize(IFieldGameMessage message, ByteBuffer sink)
    throws Exception {
        sink.put( message.getType() ) ;
        sink.put( message.getOrd() ) ;
        
        //write out flags
        int[] flagks = message.getFlagKeys() ;
        byte flags_sz = (byte) flagks.length ;
        sink.put(flags_sz) ;
        for (int k : flagks) {
            sink.put((byte) k) ;
            sink.put(
                message.getFlag(k) ? (byte) 1 : (byte) 0
            ) ;
        }
        
        //write out int-32 fields
        int[] intks = message.getIntKeys() ;
        byte i32fs_sz = (byte) intks.length ;
        sink.put(i32fs_sz) ;
        for (int k : intks) {
            sink.put((byte) k) ;
            sink.putInt( message.getInt(k)) ;
        }
        
        //write out int-64 fields
        int[] longks = message.getLongKeys() ;
        byte i64fs_sz = (byte) longks.length ;
        sink.put(i64fs_sz) ;
        for (int k : longks) {
            sink.put((byte) k) ;
            sink.putLong( message.getLong(k) ) ;
        }
		
		//write out floating point fields
        int[] floatks = message.getFloatKeys() ;
        byte ffs_sz = (byte) floatks.length ;
        sink.put(ffs_sz) ;
        for (int k : floatks) {
            sink.put((byte) k) ;
            sink.putFloat( message.getFloat(k) ) ;
        }
		
		//write out double percision fields
        int[] dblks = message.getDoubleKeys() ;
        byte dblfs_sz = (byte) dblks.length ;
        sink.put(dblfs_sz) ;
        for (int k : dblks) {
            sink.put((byte) k) ;
            sink.putDouble( message.getDouble(k) ) ;
        }        
		
        //write out 4f fields
        int[] v4fs = message.getQuaternionKeys() ;
        byte v4fs_sz = (byte) v4fs.length ;
        sink.put(v4fs_sz) ;
        for (int k : v4fs) {
            sink.put((byte) k) ;
            Quaternion q = message.getQuaternion(k) ;
            sink.putFloat(q.getW()) ;
            sink.putFloat(q.getX()) ;
            sink.putFloat(q.getY()) ;
            sink.putFloat(q.getZ()) ;
        }
        
        //write out 3f fields
        int[] v3fs = message.getVector3fKeys() ;
        byte v3fs_sz = (byte) v3fs.length ;
        sink.put(v3fs_sz) ;
        for (int k : v3fs) {
            sink.put((byte) k) ;
            Vector3f v3 = message.getVector3f(k) ;
            sink.putFloat(v3.getX()) ;
            sink.putFloat(v3.getY()) ;
            sink.putFloat(v3.getZ()) ;
        }
        
        //write out 2f fields
        int[] v2fs = message.getVector2fKeys() ;
        byte v2fs_sz = (byte) v2fs.length ;
        sink.put(v2fs_sz) ;
        for (int k : v2fs) {
            sink.put((byte) k) ;
            Vector2f v2 = message.getVector2f(k) ;
            sink.putFloat(v2.getX()) ;
            sink.putFloat(v2.getY()) ;
        }
        
        //write out string fields
        int[] strks = message.getStringKeys() ;
        byte sfields_sz = (byte) strks.length ;
        sink.put(sfields_sz) ;
        for (int k : strks) {
            String v = message.getString(k) ;
            sink.put((byte) k) ;
            sink.putInt( v.length() ) ;
            for (char c : v.toCharArray()) sink.putChar(c) ;
        }
        
        //write out children
        Collection<IFieldGameMessage> children = message.getChildren() ;
        byte kidsz = (byte) children.size() ;
        sink.put(kidsz) ;
        for (IFieldGameMessage child : children) serialize(child, sink) ;
    }
    
    protected static int convertToInt(byte b) {
        return (b < 0) ? (int)(0x00FF & b) : (int) b ;
    }
    
    public static IFieldGameMessage deserialize(
        Class<? extends IFieldGameMessage> messageImplClass, 
        ByteBuffer source
    ) throws Exception {
        IFieldGameMessage result = messageImplClass.newInstance() ;
       result.setType(source.get());
       result.setOrd(source.get()); 
       
       //read in flags
       int flags_sz = convertToInt(source.get()) ;
       for (int i = 0 ; i < flags_sz ; i++) {
           int k = convertToInt(source.get()) ;
           boolean v = (source.get() == 0) ? false : true ;
           result.setFlag(k, v);
       }
       
       //read in int 32 fields
       int i32fs_sz = convertToInt(source.get()) ;
       for (int i = 0 ; i < i32fs_sz ; i++) {
           int k = convertToInt(source.get()) ;
           int v = source.getInt() ;
           result.setInt(k, v);
       }
       
       //read in int 64 fields
       int i64fs_sz = convertToInt(source.get()) ;
       for (int i = 0 ; i < i64fs_sz ; i++) {
           int k = convertToInt(source.get()) ;
           long v = source.getLong() ;
           result.setLong(k, v);
       }
       
	   //read in float fields
	   int floatfs_sz = convertToInt(source.get()) ;
       for (int i = 0 ; i < floatfs_sz ; i++) {
           int k = convertToInt(source.get()) ;
           float v = source.getFloat() ;
           result.setFloat(k, v);
       }
	   
	   //read in double percison fields
	   int dblfs_sz = convertToInt(source.get()) ;
       for (int i = 0 ; i < dblfs_sz ; i++) {
           int k = convertToInt(source.get()) ;
           double v = source.getDouble() ;
           result.setDouble(k, v);
       }
	   
       //read in v4f fields
       int v4fs_sz = convertToInt(source.get()) ;
       for (int i = 0 ; i < v4fs_sz ; i++) {
           int k = convertToInt(source.get()) ;
           float w = source.getFloat() ;
           float x = source.getFloat() ;
           float y = source.getFloat() ;
           float z = source.getFloat() ;
           Quaternion q = new Quaternion(x,y,z,w) ;
           result.setQuaternion(k, q) ;
       }
       
       //read in v3f fields
       int v3fs_sz = convertToInt(source.get()) ;
       for (int i = 0 ; i < v3fs_sz ; i++) {
           int k = convertToInt(source.get()) ;
           float x = source.getFloat() ;
           float y = source.getFloat() ;
           float z = source.getFloat() ;
           Vector3f v3f = new Vector3f(x, y, z) ;
           result.setVector3f(k, v3f);
       }
       
       //read in v2f fields
       int v2fs_sz = convertToInt(source.get()) ;
       for (int i = 0 ; i < v2fs_sz ; i++) {
           int k = convertToInt(source.get()) ;
           float x = source.getFloat() ;
           float y = source.getFloat() ;
           Vector2f v2f = new Vector2f(x, y) ;
           result.setVector2f(k, v2f);
       }
       
       //read in string fields
       int strfs_sz = convertToInt(source.get()) ;
       for (int i = 0 ; i < strfs_sz ; i++) {
           int k = convertToInt(source.get()) ;
           
           int length = source.getInt() ;
           char[] cs = new char[length];           
           for (int c = 0 ; c < length ; c++) cs[c] = source.getChar() ;
           String v = (length > 0) ? new String(cs) : "" ;
           result.setString(k, v);
       }
       
       int childCount = convertToInt( source.get() ) ;
       for ( int i = 0 ; i < childCount ; i++ ) {
           result.addChildren( deserialize(messageImplClass, source) );
       }
       
        return result ;
        
    }
    
}
