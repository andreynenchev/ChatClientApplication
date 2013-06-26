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
                System.out.println("Server: " + msg);
            }
            //EXIT!!!!!!!!!!!!
            System.out.println("Terminating.....");
            ClientApplication.exitApp();
            Thread.currentThread().interrupt();
            System.exit(0);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}