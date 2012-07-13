/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.network.server;

import com.aeoniansoftware.network.BaseFieldGameMessage;
import com.aeoniansoftware.network.FieldGameMessageSerializer;
import com.aeoniansoftware.network.IFieldGameMessage;
import com.aeoniansoftware.network.JavaUtilFieldGameMessage;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import mygame.HelloMessage;

/**
 *
 * @author Work
 */
public class MyGameServer extends SimpleApplication implements ConnectionListener, MessageListener<HostedConnection> {
    private Node allPlayers = new Node();
    private List<IFieldGameMessage> messagequeue = new LinkedList<IFieldGameMessage>();
    Server myServer;
    
    public static void main(String[] args)
    {
        MyGameServer app = new MyGameServer();
        app.start(JmeContext.Type.Headless);
    }
    
    @Override
    public void simpleInitApp()
    {
        Serializer.registerClass(HelloMessage.class);
        Serializer.registerClass(FieldGameMessageSerializer.class, new FieldGameMessageSerializer(JavaUtilFieldGameMessage.class));
        
        try
        {
            myServer = Network.createServer(6013);
            //myServer.addMessageListener(new ServerListener(), HelloMessage.class);
            //myServer.addMessageListener(new ServerFieldMessageListener(), JavaUtilFieldGameMessage.class);
            myServer.addMessageListener(this, JavaUtilFieldGameMessage.class);
            myServer.addConnectionListener(this);
            myServer.start();
            System.out.println("Server has started");
        }
        catch(IOException ie){ie.printStackTrace();}
    }

    float globalTimer = 0;
    int numberOfPlayers = 0;
    boolean start = false;
    @Override
    public void simpleUpdate(float tpf) {
       
        if(start)
        {
            if(globalTimer > 0.0333f)
            {
                    //Process the Message Queue
            IFieldGameMessage fgm;
            if(!messagequeue.isEmpty()) {
                fgm = messagequeue.get(0);
            } else {
                fgm = null;
            }

            //Update server's level with all recieved messages
            //TODO: Combine everything into one message instead of one message per
            // connected client
            if(!messagequeue.isEmpty())
            {
                for(IFieldGameMessage m : messagequeue)
                {
                    Node player = (Node)allPlayers.getChild("Player_" + m.getInt(12));
                    if(player == null) {
                        allPlayers.attachChild(createNewPlayer(m));
                    } else {
                        player.setLocalTranslation(m.getVector3f(12));
                        player.setLocalRotation(m.getQuaternion(12));
                    }
                }
            }

            //Broadcast all messages for each player on server
            for(Spatial player : allPlayers.getChildren())
            {
                myServer.broadcast(createMessage((Node)player));
            }
            globalTimer = 0;
            }
            globalTimer += tpf;
        }
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
    
    private BaseFieldGameMessage createMessage(Node player)
    {
        BaseFieldGameMessage message = new JavaUtilFieldGameMessage();
        message.setType(0x01);
        message.setOrd(Byte.MAX_VALUE);
        message.setInt(12, (Integer)player.getUserData("PlayerNum"));
        message.setVector3f(12, player.getLocalTranslation());
        message.setQuaternion(12, player.getLocalRotation());
        
        return message;
    }
    
    public void messageReceived(HostedConnection source, Message m) {
        //throw new UnsupportedOperationException("Not supported yet.");
       if(m instanceof IFieldGameMessage){
            final IFieldGameMessage fgm = (IFieldGameMessage)m;
            this.enqueue(new Callable<Void>(){

                public Void call() throws Exception {
                    messagequeue.add(fgm);
                    
                    return null;
                }
                
            });
        }
    }

    public void connectionAdded(Server server, HostedConnection conn) {
        start = true;
        System.out.println("Client " + conn.getId() + " has Connected!");
    }

    public void connectionRemoved(Server server, HostedConnection conn) {
        //If no connections remain, turn the server "off"
        if(server.getConnections().size() == 0)
            start = false;
    }
}
