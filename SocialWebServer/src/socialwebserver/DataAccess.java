package socialwebserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataAccess {
    private static final String SERVER_IP = "localhost";
    private static final String SERVER_PORT = "5432";
    private static final String DATABASE = "social_web";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";
    
    private static DataAccess instance_ = new DataAccess();
    private Connection mConnection;
    private final Object mLock = new Object();
    private DataAccess() {
    }
    
    public static DataAccess getInstance() {
        return instance_;
    }
    
    public boolean connect () {
        try{       
            Class.forName("org.postgresql.Driver");
            mConnection = DriverManager.getConnection(
                "jdbc:postgresql://" + SERVER_IP + ":" + SERVER_PORT +
                        "/" + DATABASE, USER, PASSWORD);
            return true;
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e.toString());
        }
        return false;
    }

    public void disconnect() {
        try {
            mConnection.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public boolean addUser(final String username, final String password,
            final String email, final String fullname,
            final String work, final String birthday, final List<String> interests) {
        synchronized (mLock) {
            try {
                String query = "select count(*) from public.\"User\" WHERE webname='" + username + "';";
                Statement statement = mConnection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.next();
                if (resultSet.getInt(1) == 0) {
                    query = "INSERT INTO public.\"User\" (webname, password, email, fullname, work, birthday) \n" +
                   " VALUES ('" + username + "','" + password + "','" + email + "','" + fullname
                             + "','" + work + "','" + birthday + "'" + ");";
                    statement.executeUpdate(query);
                    for (final String interest : interests) {
                        query = "INSERT INTO public.\"UserInterests\" (\"user\", interest) \n" +
                            " VALUES ('" + username + "','" + interest + "');";
                        statement.executeUpdate(query);
                    }
                    return true;
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return false;
        }
    }
    
    public boolean checkUser(final String username, final String password) {
        synchronized (mLock) {
            try {
                String query = "select count(*) from public.\"User\" WHERE webname='" + username + "' AND "
                        + "password='" + password + "';";
                Statement statement = mConnection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.next();
                if (resultSet.getInt(1) > 0) {
                    return true;
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return false;
        }
    }
    
    public String getInterestTree() {
        synchronized (mLock) {
            final StringBuilder sb = new StringBuilder();
            try {
                String query = "select * from public.\"Interest\";";
                Statement statement = mConnection.createStatement();
                final ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    final String interest = resultSet.getString(1);
                    final String baseInterest = resultSet.getString(2);
                    sb.append(interest).append(":").append(baseInterest).append(";");
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return sb.toString();
        }
    }

    public boolean editUser(String username, String password, String email, String fullname, String work, String birthday, List<String> interests) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean sendMessage(String from, String to, String message, String time) {
         synchronized (mLock) {
            try {
                final String query = "INSERT INTO public.\"UserMessages\" (\"from\", \"to\", \"message\", \"timestamp\") \n" +
                   " VALUES ('" + from + "','" + to + "','" + message + "','" + time + "'" + ");";
                final Statement statement = mConnection.createStatement();
                statement.executeUpdate(query);
                return true;
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return false;
        }
    }

    public List<String> getMessages(String firstUser, String secondUser) {
        synchronized (mLock) {
            final List<String> messages = new ArrayList<>();
            try {
                final String query = "select * from public.\"UserMessages\" where (\"from\"='" + firstUser +
                        "' AND \"to\"='" + secondUser + "') OR (\"from\"='" + secondUser +
                        "' AND \"to\"='" + firstUser + "') ORDER BY \"timestamp\";";
                final Statement statement = mConnection.createStatement();
                final ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    final StringBuilder sb = new StringBuilder();
                    final String from = resultSet.getString(1);
                    final String to = resultSet.getString(2);
                    final String message = resultSet.getString(3);
                    final String timestamp = resultSet.getString(4);
                    sb.append(from).append("<:>").append(to).append("<:>").
                            append(timestamp).append("<:>").append(message);
                    messages.add(sb.toString());
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return messages;
        }
    }

    public String createCommunity(String moderator, String communityName, String method, List<String> interests) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String editCommunity(String communityName, String method, List<String> interests) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
