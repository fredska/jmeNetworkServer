/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeoniansoftware.network;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kg
 */
public class BaseFieldGameMessageContext 
implements IFieldGameMessageContext {

	private final static Map<String,BaseFieldGameMessageContext> Instances = new HashMap<String, BaseFieldGameMessageContext>() ;
	
    private final Map<FieldType, Map<String,Integer>> LookupTable ;
    {
        this.LookupTable = new EnumMap<FieldType, Map<String, Integer>>(FieldType.class) ;
        for (FieldType ft : FieldType.values()) {
            this.LookupTable.put(ft, new HashMap<String, Integer>()) ;
        }
    }
    
	public static BaseFieldGameMessageContext getInstance(String name) {
		if (!Instances.containsKey(name)) Instances.put(
			name, 
			new BaseFieldGameMessageContext() 
		) ;
		
		return Instances.get(name) ;
	}
	
    public void defineAlias(FieldType ft, String alias, int k) {
        this.getAliases(ft).put(alias, k) ;
    }

    public String alias(FieldType ft, int k) {
        Map<String,Integer> as = this.getAliases(ft) ;
        for (String alias : as.keySet()) {
            int x = as.get(alias) ;
            if (x == k) return alias ;
        }
        return null ;
    }
    
    public int unalias(FieldType ft, String alias ) {
        if (alias == null) throw new RuntimeException(
            "cannot specify null message field alias"
        ) ;
        
        Integer k = this.getAliases(ft).get(alias) ;
        
        if (k == null) throw new RuntimeException(
            "invalid message field alias: (" + ft.name() + "/" + alias + ")"
        ) ;
        
        return k ;
    }
    
    public Map<String, Integer> getAliases(FieldType ft) {
        return this.LookupTable.get(ft) ;
    }
    
    
}
