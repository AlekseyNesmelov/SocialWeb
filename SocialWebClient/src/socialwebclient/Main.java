package socialwebclient;

import javax.swing.JOptionPane;

public class Main {
    private static final String IP = "localhost";
    private static final int PORT = 8888;
    
    public static void main(String[] args) {
        final ISocketConnection socketConnection = new SocketConnection();
        if (socketConnection.connect(IP, PORT)) {
            final Controller controller = new Controller(socketConnection);
            final UserGUI userGUI = new UserGUI(controller);
            userGUI.show();
        } else {
            JOptionPane.showMessageDialog(null, "Can't conect to the server!");
        }
    }
}
