/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchatapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Andrey
 */
class ServerConn implements Runnable {
    private BufferedReader in = null;
 
    public ServerConn(Socket server) throws IOException  {
        in = new BufferedReader(new InputStreamReader(
                    server.getInputStream()));
    }
    @Override
    public void run() {
        String msg;
        try {
            while ((msg = in.readLine()) != null && !msg.equals("TERMINATE")) {
                if (msg.startsWith("<<AddClient>>")){
                    ClientApplication.clientApp.addClientToList(msg.substring(13));
                }
                else if(msg.startsWith("<<RemoveClient>>")){
                    ClientApplication.clientApp.remClientFromList(msg.substring(16));
                }
                else if (msg.startsWith("<<FROMCLIENT>>")){
                    ClientApplication.clientApp.printMsgOnScreen(msg);
                }
                else ClientApplication.clientApp.printMsgOnScreen("Server:" + msg);
            }
            ClientApplication.clientApp.printMsgOnScreen("Terminating.....");
            ClientApplication.exitApp();
            Thread.currentThread().interrupt();
            //System.exit(0);
        } catch (IOException e) {
            //System.err.println(e);
            ClientApplication.clientApp.printMsgOnScreen("SERVERCONN Exception" + e.getMessage());
            ClientApplication.exitApp();
            System.exit(0);
        }
    }
}