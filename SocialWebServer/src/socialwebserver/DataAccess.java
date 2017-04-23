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

    public boolean connect() {
        try {
            Class.forName("org.postgresql.Driver");
            mConnection = DriverManager.getConnection(
                    "jdbc:postgresql://" + SERVER_IP + ":" + SERVER_PORT
                    + "/" + DATABASE, USER, PASSWORD);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
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
                    query = "INSERT INTO public.\"User\" (webname, password, email, fullname, work, birthday) \n"
                            + " VALUES ('" + username + "','" + password + "','" + email + "','" + fullname
                            + "','" + work + "','" + birthday + "'" + ");";
                    statement.executeUpdate(query);
                    for (final String interest : interests) {
                        query = "INSERT INTO public.\"UserInterests\" (\"user\", interest) \n"
                                + " VALUES ('" + username + "','" + interest + "');";
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
                final String query = "INSERT INTO public.\"UserMessages\" (\"from\", \"to\", \"message\", \"timestamp\") \n"
                        + " VALUES ('" + from + "','" + to + "','" + message + "','" + time + "'" + ");";
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
                final String query = "select * from public.\"UserMessages\" where (\"from\"='" + firstUser
                        + "' AND \"to\"='" + secondUser + "') OR (\"from\"='" + secondUser
                        + "' AND \"to\"='" + firstUser + "') ORDER BY \"timestamp\";";
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

    public String[] getCommunities() {
        synchronized (mLock) {
            final List<String> communities = new ArrayList<>();
            try {
                final String query = "select name from public.\"Community\";";
                final Statement statement = mConnection.createStatement();
                final ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    communities.add(resultSet.getString(1));
                }
                return (String[]) communities.toArray(new String[0]);
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return new String[0];
        }
    }

    public String[] getCommunity(String name) {
        synchronized (mLock) {
            final List<String> result = new ArrayList<>();
            try {
                String query = "select * from public.\"Community\" where \"name\" = '" + name + "';";
                final Statement statement = mConnection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if(!resultSet.next())
                    return new String[0];
                result.add(resultSet.getString(1));
                result.add(resultSet.getString(2));
                result.add(resultSet.getString(3));
                query = "select interest from public.\"CommunityInterests\" where \"community\" = '" + name + "';";
                resultSet = statement.executeQuery(query);
                while(resultSet.next())
                    result.add(resultSet.getString(1));
                return (String[]) result.toArray(new String[0]);
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return new String[0];
        }
    }

    public boolean createCommunity(String name, String moderator, String method, List<String> interests) {
        synchronized (mLock) {
            try {
                String query = "select * from public.\"Community\" where \"name\" = '" + name + "';";
                Statement statement = mConnection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    return false;
                }
                query = "select * from public.\"User\" where \"webname\" = '" + moderator + "';";
                resultSet = statement.executeQuery(query);
                if (!resultSet.next()) {
                    return false;
                }
                query = "INSERT INTO public.\"Community\" (\"name\", \"moderator\", \"method\") \n"
                        + " VALUES ('" + name + "','" + moderator + "','" + method + "');";
                statement.executeUpdate(query);
                for (String interest : interests) {
                    query = "INSERT INTO public.\"CommunityInterests\" (\"interest\", \"community\") \n"
                            + " VALUES ('" + interest + "','" + name + "');";
                    statement.executeUpdate(query);
                }
                query = "INSERT INTO public.\"CommunityMembers\" (\"community\", \"member\") \n"
                        + " VALUES ('" + name + "','" + moderator + "');";
                statement.executeUpdate(query);
                return true;
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return false;
    }

    public boolean editCommunity(String name, String method, List<String> interests) {
        synchronized (mLock) {
            try {
                String query = "select * from public.\"Community\" where \"name\" = '" + name + "';";
                Statement statement = mConnection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (!resultSet.next()) {
                    return false;
                }
                query = "UPDATE public.\"Community\" SET method = '" + method + "' WHERE name = '" + name + "';";
                statement.executeUpdate(query);
                query = "DELETE FROM public.\"CommunityInterests\" WHERE community = '" + name + "';";
                statement.executeUpdate(query);
                for (String interest : interests) {
                    query = "INSERT INTO public.\"CommunityInterests\" (\"interest\", \"community\") \n"
                            + " VALUES ('" + interest + "','" + name + "');";
                    statement.executeUpdate(query);
                }
                return true;
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return false;
    }
    
    public String[] getCommunityMembers(String community) {
        synchronized (mLock) {
            try {
                String query = "select member from public.\"CommunityMembers\" where \"community\" = '" + community + "';";
                Statement statement = mConnection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                List<String> result = new ArrayList<>();
                while(resultSet.next())
                    result.add(resultSet.getString(1));
                return (String[]) result.toArray(new String[0]);
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return new String[0];
        }
    }

    public boolean joinTheCommunity(String community, String user) {
        synchronized (mLock) {
            try {
                String query = "select * from public.\"Community\" where \"name\" = '" + community + "';";
                Statement statement = mConnection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (!resultSet.next()) {
                    return false;
                }
                query = "select * from public.\"User\" where \"webname\" = '" + user + "';";
                resultSet = statement.executeQuery(query);
                if (!resultSet.next()) {
                    return false;
                }
                query = "select * from public.\"CommunityMembers\" where \"community\" = '" + community + "' and member = '" + user + "';";
                resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    return false;
                }
                query = "INSERT INTO public.\"CommunityMembers\" (\"community\", \"member\") \n"
                        + " VALUES ('" + community + "','" + user + "');";
                statement.executeUpdate(query);
                return true;
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return false;
    }

    public String quitTheCommunity(String community, String user) {
        synchronized (mLock) {
            try {
                Statement statement = mConnection.createStatement();
                String query = "select * from public.\"Community\" where name = '" + community + "' and moderator = '" + user + "';";
                ResultSet resultSet = statement.executeQuery(query);
                if(resultSet.next())
                    return Constants.YOU_ARE_A_MODERATOR;
                query = "DELETE FROM public.\"CommunityMembers\" WHERE community = '" + community + "' and member = '" + user + "';";
                return statement.executeUpdate(query) > 0 ? Constants.SUCCESS : Constants.NOT_IN_COMMUNITY;
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return Constants.FAIL;
    }
}
