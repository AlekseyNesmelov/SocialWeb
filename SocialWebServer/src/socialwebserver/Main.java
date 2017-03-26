package socialwebserver;

public class Main {
    public static void main(String[] args) {
        final DataAccess dataAccess = DataAccess.getInstance();
        if (dataAccess.connect()) {
            final ServerThread serverThread = new ServerThread();
            serverThread.start();
        } else {
            System.out.println("Can't connect to the DB");
        }
    } 
}
