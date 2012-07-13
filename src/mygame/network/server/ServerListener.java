/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.network.server;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import mygame.HelloMessage;
/**
 *
 * @author Work
 */
public class ServerListener implements MessageListener<HostedConnection> {
  public void messageReceived(HostedConnection source, Message message) {
    if (message instanceof HelloMessage) {
      // do something with the message
      HelloMessage helloMessage = (HelloMessage) message;
      System.out.println("Server received '" +helloMessage.getSomething() +"' from client #"+source.getId() );
    } // else....
  }
}