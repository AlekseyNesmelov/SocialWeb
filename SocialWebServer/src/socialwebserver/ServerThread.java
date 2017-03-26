package socialwebserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
    private ServerSocket mServerSocket;
    private static final int SERVER_PORT = 8888;
    private boolean mBreaked = false;
    
    @Override
    public void interrupt() {
        mBreaked = true;
    }
    
    @Override
    public void run() {
        try {
            mServerSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server has been started");
            while(!mBreaked) {
                final Socket socket = mServerSocket.accept();
                final ClientThread clientThread = new ClientThread(socket);
                clientThread.start();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    } 
}
