/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeoniansoftware.network;


import com.jme3.app.SimpleApplication;
import com.jme3.network.Client;
import com.jme3.network.HostedConnection;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.system.JmeContext;
import java.io.IOException;

/**
 *
 * @author kg
 */
public class NetworkEvalUtility 
extends SimpleApplication {

    public enum Mode {
       Server(JmeContext.Type.Headless),
       Client(JmeContext.Type.Display)
       ;
       
       public final JmeContext.Type startType ;
       
       private Mode(JmeContext.Type st) {
           this.startType = st ;
       }
       
       public static final Mode DefaultMode = Client ;
       
       public static Mode get(String name) {
           for (Mode m : Mode.values()) {
               if (name.equalsIgnoreCase(m.name())) return m ;
           }
           return DefaultMode ;
       }
    } ;
    
    private Mode mode = Mode.Client ;

    public NetworkEvalUtility(Mode m, String[] args) {
        this.mode = m ;
        switch (this.mode) {
            case Client: 
                break ;
            case Server: 
                break ;
        }
    }
    
    public static void main(String[] args) {
        String moden = args[0].trim() ;
        Mode mode = Mode.get(moden) ;
        NetworkEvalUtility app = new NetworkEvalUtility(mode, args) ;
        app.start(mode.startType);
    }
    
    @Override
    public void simpleInitApp() {
        switch (this.mode) {
            case Client:
                this.initClient();
                break ;
            case Server: 
                this.initServer() ;
                break ;
        }
    }
    
    public void initClient() {
        Client client = null ;
        try {
            client = Network.connectToServer("localhost", 6143);
            client.start();
            
        } catch (IOException ie) {
            throw new RuntimeException(ie) ;
        }
        
        //TODO: build GUI for client to take input
        
    }
    
    public void initServer() {
        Server server = null ;
        try {
            server = Network.createServer(6143);
            server.addMessageListener( new FieldMessageListener<HostedConnection>() {
                protected void handle(IFieldGameMessage message) {
//                    Map<Integer,String> sfs = message.getStringFields() ;
//                    for (Integer k : sfs.keySet()) {
//                        String v = sfs.get(k) + "a" ;
//                        sfs.put(k,v) ;
//                    }
//                    Map<Integer,Integer> ifs = message.getIntFields() ;
//                    for (Integer k : ifs.keySet()) {
//                        Integer v = ifs.get(k) + 3 ;
//                        ifs.put(k,v) ;
//                    }
                    NetworkEvalUtility.printMessage(message) ;
                }
            });
            server.start();
        } catch (IOException ie) {
            throw new RuntimeException(ie) ;
        }
    }
    
    public static void printMessage(IFieldGameMessage message)
    {
        StringBuffer sb = new StringBuffer()
                .append("MESSAGE\n")
                .append("type: ").append(message.getType()).append("\n")
                .append("order: ").append(message.getOrd()).append("\n")
        ;
        
        sb.append("\t*strings:\n") ;
        int[] sks = message.getFlagKeys() ;
        for (int k : sks) {
            String v = message.getString(k) ;
            sb.append("\t\t").append(k).append(":").append(v).append("\n") ;
        }
        
        sb.append("\t*ints:\n") ;
        int[] iks = message.getIntKeys() ;
        for (int k : iks) {
            int v = message.getInt(k) ;
            sb.append("\t\t").append(k).append(":").append(v).append("\n") ;
        }
        
        sb.append("\t*flags:\n") ;
        int[] bks = message.getFlagKeys() ;
        for (int k : bks) {
            boolean v = message.getFlag(k) ;
            sb.append("\t\t").append(k).append(":").append(v).append("\n") ;
        }
    }
    
}
