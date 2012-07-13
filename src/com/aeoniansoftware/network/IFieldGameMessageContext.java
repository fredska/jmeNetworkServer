/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeoniansoftware.network;

import java.util.Map;

/**
 *
 * @author kg
 */
public interface IFieldGameMessageContext {

    public static enum FieldType {
        Type, 
        Int, 
        Long, 
        String, 
        Float, 
        Double, 
        Flag, 
        Vector3f, 
        Vector2f, 
        Quaternion
    }

    public void defineAlias(FieldType ft, String alias, int k);
    public String alias(FieldType ft, int k);
    public int unalias(FieldType ft, String alias) ;
    public Map<String, Integer> getAliases(FieldType ft);
    
}
