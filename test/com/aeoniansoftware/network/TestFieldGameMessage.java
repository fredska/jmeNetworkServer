/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeoniansoftware.network;

import com.jme3.math.Vector2f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import java.nio.ByteBuffer;
import org.junit.Test;
import static org.junit.Assert.*;

import com.aeoniansoftware.network.IFieldGameMessageContext.FieldType;

/**
 *
 * @author kg
 */
public class TestFieldGameMessage {
    
	public static final double AllowedFloatingPointDelta = 0.0000000001 ;
	
    @Test
    public void testJavaUtilBasic() 
    throws Exception {
        this.performBasicTest(JavaUtilFieldGameMessage.class );
    }
    
//    @Test
//    public void testTroveBasic() 
//    throws Exception {
//        this.performBasicTest(TroveFieldGameMessage.class);
//    }
    
    private void performBasicTest(Class<? extends IFieldGameMessage> type)
    throws Exception {
        //create message to serialize...
        ByteBuffer in_buffer = this.getByteBuffer() ;
        IFieldGameMessage in = type.newInstance() ;
        
        this.populateMessage(0xF6, Byte.MAX_VALUE, in) ;
        FieldGameMessageSerializer.serialize(in, in_buffer);
        
        //deserialize message and check it
        ByteBuffer out_buffer = ByteBuffer.wrap(in_buffer.array()) ;
        IFieldGameMessage out = FieldGameMessageSerializer.deserialize(type, out_buffer) ;
        this.checkMessage(0xF6, Byte.MAX_VALUE, out);
    }
    
    private void performSecondBasicTest(Class<? extends IFieldGameMessage> type)
            throws Exception{
        
    }
    
//    @Test
//    public void testTroveSomeChildren()
//    throws Exception {
//        this.performChildrenTest(100, TroveFieldGameMessage.class); 
//    }
    
    @Test
    public void testJavaUtilSomeChildren()
    throws Exception {
        this.performChildrenTest(100, JavaUtilFieldGameMessage.class);
    }
  
//    @Test
//    public void testTroveMaxChildren()
//    throws Exception {
//       this.performChildrenTest(255, TroveFieldGameMessage.class); 
//    }
    
    @Test
    public void testJavaUtilMaxChildren()
    throws Exception {
       this.performChildrenTest(255, JavaUtilFieldGameMessage.class); 
    }
    
    private void performChildrenTest(int kcount, Class<? extends IFieldGameMessage> type)
    throws Exception {
        //create message to serialize...
        ByteBuffer in_buffer = this.getByteBuffer() ;
        IFieldGameMessage in = type.newInstance() ;
        this.populateMessage(0xF6, Byte.MAX_VALUE, in) ;
        
        for ( int i = 0 ; i < kcount ; i++) {
            IFieldGameMessage child = type.newInstance() ;
            this.populateMessage(0x04, (byte) i, child) ;
            in.addChildren(child);
        }
        FieldGameMessageSerializer.serialize(in, in_buffer);
        
        ByteBuffer out_buffer = ByteBuffer.wrap(in_buffer.array()) ;
        IFieldGameMessage out = FieldGameMessageSerializer.deserialize(type, out_buffer) ;
        this.checkMessage(0xF6, Byte.MAX_VALUE, out);
        assertEquals( kcount, out.getChildren().size()) ;
        int i = 0 ;
        for (IFieldGameMessage okid : out.getChildren()) {
            this.checkMessage(0x04,(byte) i,okid) ;
            i++ ;
        }
    }
    
//    @Test
//    public void testTroveAliasContext()
//    throws Exception {
//       this.performAliasContextTest(TroveFieldGameMessage.class); 
//    }
    
    @Test
    public void testJavaUtilAliasContext()
    throws Exception {
       this.performAliasContextTest(JavaUtilFieldGameMessage.class); 
    }
    
    private void performAliasContextTest(Class<? extends IFieldGameMessage> type) 
    throws Exception {
        IFieldGameMessageContext cxt = new BaseFieldGameMessageContext() ;
        for (FieldType ft : FieldType.values()) {
            cxt.defineAlias(ft, "1", 1);
            cxt.defineAlias(ft, "2", 2);
        }
        IFieldGameMessage in = type.newInstance();
        in.setContext(cxt);
        
        //pull and retreive by alias
        in.setType("1"); //
        in.setFlag("1", true); //
        in.setInt("1", 1); //
        in.setLong("1", 1); //
        in.setString("1", "1"); //
        in.setQuaternion("1", new Quaternion(1, 1, 1, 1));
        in.setVector2f("1", new Vector2f(1,1) );
        in.setVector3f("1", new Vector3f(1,1,1));
        
        assertEquals( Byte.valueOf((byte) 1), in.getType()) ;
        assertEquals( "1", in.getTypeAlias()) ;
        assertEquals(true, in.getFlag(1));
        assertEquals(true, in.getFlag("1"));
        assertEquals(1, in.getInt("1")); 
        assertEquals(1, in.getInt(1));
        assertEquals((long) 1, in.getLong("1")); 
        assertEquals((long) 1, in.getLong(1));
        assertEquals("1", in.getString("1")) ;
        assertEquals("1", in.getString(1)) ;
        assertEquals( new Quaternion(1,1,1,1), in.getQuaternion("1")) ;
        assertEquals( new Quaternion(1,1,1,1), in.getQuaternion(1)) ;
        assertEquals( new Vector2f(1,1), in.getVector2f("1")) ;
        assertEquals( new Vector2f(1,1), in.getVector2f(1)) ;
        assertEquals( new Vector3f(1,1,1), in.getVector3f("1")) ;
        assertEquals( new Vector3f(1,1,1), in.getVector3f(1)) ;

        //change by direct byte keys, and check getting back by alias
        in.setFlag(1, false);
        in.setInt(1, 3);
        in.setLong(1, 3);
        in.setString(1, "change");
        in.setQuaternion(1, Quaternion.ZERO);
        in.setVector2f(1, Vector2f.ZERO);
        in.setVector3f(1, Vector3f.ZERO);
        
        assertEquals(false, in.getFlag("1"));
        assertEquals(3, in.getInt("1")); 
        assertEquals((long) 3, in.getLong("1")); 
        assertEquals("change", in.getString("1")) ;
        assertEquals( Quaternion.ZERO, in.getQuaternion("1")) ;
        assertEquals( Vector2f.ZERO, in.getVector2f("1")) ;
        assertEquals( Vector3f.ZERO, in.getVector3f("1")) ;
        
        //test a 2nd alias...
        in.setType("2"); //
        in.setFlag("2", false); //
        in.setInt("2", 2); //
        in.setLong("2", 2); //
        in.setString("2", "2"); //
        in.setQuaternion("2", new Quaternion(2, 2, 2, 2));
        in.setVector2f("2", new Vector2f(2,2) );
        in.setVector3f("2", new Vector3f(2,2,2));
        
        assertEquals( Byte.valueOf((byte) 2), in.getType()) ;
        assertEquals( "2", in.getTypeAlias()) ;
        assertEquals(false, in.getFlag(2));
        assertEquals(false, in.getFlag("2"));
        assertEquals(2, in.getInt("2")); 
        assertEquals(2, in.getInt(2));
        assertEquals((long) 2, in.getLong("2")); 
        assertEquals((long) 2, in.getLong(2));
        assertEquals("2", in.getString("2")) ;
        assertEquals("2", in.getString(2)) ;
        assertEquals( new Quaternion(2,2,2,2), in.getQuaternion("2")) ;
        assertEquals( new Quaternion(2,2,2,2), in.getQuaternion(2)) ;
        assertEquals( new Vector2f(2,2), in.getVector2f("2")) ;
        assertEquals( new Vector2f(2,2), in.getVector2f(2)) ;
        assertEquals( new Vector3f(2,2,2), in.getVector3f("2")) ;
        assertEquals( new Vector3f(2,2,2), in.getVector3f(2)) ;
    }
    
	
    private void populateMessage(int type, byte ord, IFieldGameMessage in) {
        in.setType(type);
        in.setOrd(ord);
        
        in.setFlag(12, false);
		in.setFlag(33, true);
		in.setFlag(254, false) ;
		in.setFlag(255, true);
        
        in.setInt(12, 1200);
        in.setInt(255, 4400);
        
        in.setLong(12, (long) Integer.MAX_VALUE + 3);
        in.setLong(255, (long) Integer.MAX_VALUE + 256);
        
        in.setString(12, "");
        in.setString(15, "hello");
        in.setString(255,"") ;
        in.setString(253,"goodbye") ;
        
        in.setQuaternion(12, Quaternion.ZERO);
        in.setQuaternion(43, new Quaternion(0.5f, 0.5f, 0.6f, 0.6f));
        in.setQuaternion(252, new Quaternion(0.3f, 0.4f, 0.5f, 0.6f));
        in.setQuaternion(255, Quaternion.IDENTITY);
        
        in.setVector3f(12, Vector3f.ZERO);
        in.setVector3f(43, new Vector3f(0.5f, 0.5f, 0.6f));
        in.setVector3f(252, new Vector3f(0.3f, 0.4f, 0.5f));
        in.setVector3f(255, Vector3f.POSITIVE_INFINITY);
        
        in.setVector2f(12, Vector2f.ZERO);
        in.setVector2f(43, new Vector2f(0.5f, 0.5f));
        in.setVector2f(252, new Vector2f(0.3f, 0.4f));
        in.setVector2f(255, new Vector2f(1,4));
		
		in.setDouble(12, 3.33);
		in.setDouble(98, 98.33);
		in.setDouble(253, 12345.67);
		in.setDouble(255, 76.54321);
		
		in.setFloat(12, 3.33f);
		in.setFloat(98, 98.33f);
		in.setFloat(253, 12345.67f);
		in.setFloat(255, 76.54321f);
    }
    
    private void checkMessage(int type, byte ord, IFieldGameMessage out)
    throws Exception {
        assertEquals( new Byte((byte) type), out.getType() ) ;
        assertEquals( new Byte(ord), out.getOrd());
        
        assertEquals(false, out.getFlag(12)) ;
        assertEquals(true, out.getFlag(33)) ;
        assertEquals(true, out.getFlag(255)) ;
        assertEquals(false, out.getFlag(254)) ;
        
        assertEquals(1200, out.getInt(12)) ;
        assertEquals(4400, out.getInt(255)) ;
        
        assertEquals( (long) Integer.MAX_VALUE + 3, out.getLong(12)) ;
        assertEquals( (long) Integer.MAX_VALUE + 256, out.getLong(255)) ;
        
        assertEquals("", out.getString(12)) ;
        assertEquals("hello", out.getString(15)) ;
        assertEquals("", out.getString(255)) ;
        assertEquals("goodbye", out.getString(253)) ;

        assertEquals(Quaternion.ZERO, out.getQuaternion(12)) ;
        assertEquals( 
            new Quaternion(0.5f, 0.5f, 0.6f, 0.6f),
            out.getQuaternion(43) 
        ) ;
        assertEquals( 
            new Quaternion(0.3f, 0.4f, 0.5f, 0.6f),
            out.getQuaternion(252) 
        ) ;
        assertEquals(Quaternion.IDENTITY, out.getQuaternion(255)) ;
        
        assertEquals(Vector3f.ZERO, out.getVector3f(12)) ;
        assertEquals( 
            new Vector3f(0.5f, 0.5f, 0.6f),
            out.getVector3f(43) 
        ) ;
        assertEquals( 
            new Vector3f(0.3f, 0.4f, 0.5f),
            out.getVector3f(252) 
        ) ;
        assertEquals(Vector3f.POSITIVE_INFINITY, out.getVector3f(255)) ;
        
        assertEquals(Vector2f.ZERO, out.getVector2f(12)) ;
        assertEquals(new Vector2f(0.5f, 0.5f),out.getVector2f(43)) ;
        assertEquals(new Vector2f(0.3f, 0.4f), out.getVector2f(252)) ;
        assertEquals(new Vector2f(1,4), out.getVector2f(255)) ;
		
		assertEquals( 3.33, out.getDouble(12), AllowedFloatingPointDelta) ;
		assertEquals( 98.33, out.getDouble(98), AllowedFloatingPointDelta) ;
		assertEquals( 12345.67, out.getDouble(253), AllowedFloatingPointDelta) ;
		assertEquals( 76.54321, out.getDouble(255), AllowedFloatingPointDelta) ;
		
		assertEquals( 3.33f, out.getFloat(12), AllowedFloatingPointDelta) ;
		assertEquals( 98.33f, out.getFloat(98), AllowedFloatingPointDelta) ;
		assertEquals( 12345.67f, out.getFloat(253), AllowedFloatingPointDelta) ;
		assertEquals( 76.54321f, out.getFloat(255), AllowedFloatingPointDelta) ;
    }
    
    private ByteBuffer getByteBuffer()
    throws Exception {
       ThreadLocal<ByteBuffer> dataBuffer = new ThreadLocal<ByteBuffer>();
       ByteBuffer buffer = dataBuffer.get();
        if( buffer == null ) {
			//alloc: 20971520 == 20MiB
            buffer = ByteBuffer.allocate( 20971520 );
            dataBuffer.set(buffer);
        }
        buffer.clear(); 
        return buffer ;
    }
    
    
}
