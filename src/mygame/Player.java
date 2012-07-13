/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Work
 */
public class Player extends Node{
    public Player()
    {
        
    }
    
    public Player(Vector3f position)
    {
        this.setLocalTranslation(position);
    }
    
    public Player(Vector3f position, Spatial model)
    {
        this.setLocalTranslation(position);
    }
}
