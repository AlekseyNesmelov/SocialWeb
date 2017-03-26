package socialwebclient;

import request.Request;
import java.net.Socket;

public interface ISocketConnection {

    public boolean connect(final String ip, final int port);
    
    public boolean connect(final Socket socket);

    public void disconnect();

    public Request sendAndGetResponse(final Request request);
    
    public boolean send(final Request request);

    public Request getResponse();
}
