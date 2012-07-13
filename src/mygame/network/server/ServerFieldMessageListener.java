/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.network.server;

import com.aeoniansoftware.network.FieldMessageListener;
import com.aeoniansoftware.network.IFieldGameMessage;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Work
 */
public class ServerFieldMessageListener extends FieldMessageListener<HostedConnection> {

    @Override
    protected void handle(IFieldGameMessage m) {
        //throw new UnsupportedOperationException("Not supported yet.");
        Vector3f localPosition = m.getVector3f(12);
        Quaternion localRotation = m.getQuaternion(12);
        int playerName = m.getInt(12);
        /*
        if(MyGameServer.getClientPlayers() == null)
            MyGameServer.clientPlayers = new ArrayList<Node>();
        
        if(MyGameServer.clientPlayers.isEmpty())
        {
            MyGameServer.clientPlayers.add(createNewPlayer(m));
        }
        else
        {
            boolean playerFound = false;
            for(Node player : MyGameServer.clientPlayers)
            {
                if(player.getName().equals("Player_" + playerName))
                {
                    player.setLocalTranslation(localPosition);
                    player.setLocalRotation(localRotation);
                    playerFound = true;
                    break;
                }
            }
            
            if(!playerFound)
            {
                MyGameServer.clientPlayers.add(createNewPlayer(m));
            }
        }
         * 
         */
    }
    
    private Node createNewPlayer(IFieldGameMessage m)
    {
        Vector3f localPosition = m.getVector3f(12);
        Quaternion localRotation = m.getQuaternion(12);
        int playerName = m.getInt(12);
            Node player = new Node("Player_" + playerName);
            
            //Load player data into here, such as shape, material, etc...
            player.setLocalTranslation(localPosition);
            player.setLocalRotation(localRotation);
            player.setUserData("PlayerNum", playerName);
            return player;
    }
    
}
