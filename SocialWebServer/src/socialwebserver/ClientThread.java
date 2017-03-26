package socialwebserver;

import request.Request;
import java.net.Socket;

public class ClientThread extends Thread {
    private final SocketConnection mSocketConnection;
    private final Socket mClientSocket;
    private boolean mBreaked = false;
    
    private final UserController mUserController;
    
    public ClientThread(final Socket socket) {
        mClientSocket = socket;
        mSocketConnection = new SocketConnection();
        mUserController = new UserController();
    }
    
    @Override
    public void interrupt() {
        mBreaked = true;
    }
    
    @Override
    public void run() {
        if (mSocketConnection.connect(mClientSocket)) {
            while (!mBreaked && mSocketConnection.isConnected()) {
                Request request = (Request)mSocketConnection.getResponse();
                if (request != null) {
                    if (request.body.equals(Constants.CONNECTION_REFUSED)) {
                        mSocketConnection.disconnect();
                        mBreaked = true;
                    } else {
                        mUserController.processRequest(request, mSocketConnection);
                    }
                }
            }
        }
    }
}
