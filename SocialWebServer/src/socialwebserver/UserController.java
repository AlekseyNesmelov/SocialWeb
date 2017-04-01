package socialwebserver;

import java.sql.Timestamp;
import request.Request;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserController  {
    
    DataAccess mDataAccess = DataAccess.getInstance();
    
    public UserController() {
    }

    public void processRequest(final Request request, final SocketConnection socketConnection) {
        switch (request.requestType) {
            case Constants.REGISTRATION: {
                final Map<String, String> body = request.body;
                final List<String> interests = new ArrayList<>();
                final String interestsString = body.get(Constants.INTERESTS);
                if (interestsString != null) {
                    final String[] interestsArray = interestsString.split(":");
                    for (final String interest : interestsArray) {
                        interests.add(interest);
                    }
                }
                registration(body.get(Constants.WEBNAME), body.get(Constants.PASSWORD),
                    body.get(Constants.EMAIL), body.get(Constants.FULLNAME), body.get(Constants.WORK),
                    body.get(Constants.BIRTHDAY), interests, socketConnection);
                break;
            }
            case Constants.AUTHORIZATION: {
                final Map<String, String> body = request.body;
                authorization(body.get(Constants.WEBNAME), body.get(Constants.PASSWORD), socketConnection);
                break;
            }
            case Constants.GET_INTERESTS_TREE: {
                sendInterestTree(socketConnection);
                break;
            }
            case Constants.SEND_MAESSAGE: {
                final Map<String, String> body = request.body;
                final String from = body.get(Constants.FROM);
                final String to = body.get(Constants.TO);
                final String message = body.get(Constants.MESSAGE);
                final String timestamp = (new Timestamp(System.currentTimeMillis())).toString();
                sendMessage(from, to, message, timestamp, socketConnection);
                break;
            }
            default:
                sendFail(socketConnection);
                break;
        }
    }   
    
    private void sendFail(final SocketConnection socketConnection) {
        Request response = new Request();
        response.senderType = Constants.SERVER;
        response.requestType = Constants.RESPONSE;
        response.body.put(Constants.STATE, Constants.FAIL);
        socketConnection.send(response);
    }

    public void registration(final String username, final String password,
            final String email, final String fullname, final String work,
            final String birthday, final List<String> interests, 
            final SocketConnection socketConnection) {
        if (mDataAccess.addUser(username, password, email, fullname, work,
                birthday, interests)) {
            Request response = new Request();
            response.senderType = Constants.SERVER;
            response.requestType = Constants.REGISTRATION;
            response.body.put(Constants.STATE, Constants.SUCCESS);
            socketConnection.send(response);
        } else {
            sendFail(socketConnection);
        }
    }
    
    public void sendInterestTree(final SocketConnection socketConnection) {
        final String interests = mDataAccess.getInterestTree();
        Request response = new Request();
        response.senderType = Constants.SERVER;
        response.requestType = Constants.REGISTRATION;
        response.body.put(Constants.INTERESTS, interests);
        socketConnection.send(response);
    }

    public void editInfo(String username, String password, String email, String fullname, String work, String birthday, List<String> interests, SocketConnection socketConection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void authorization(String username, String password, SocketConnection socketConnection) {
        if (mDataAccess.checkUser(username, password)) {
            Request response = new Request();
            response.senderType = Constants.SERVER;
            response.requestType = Constants.AUTHORIZATION;
            response.body.put(Constants.STATE, Constants.SUCCESS);
            socketConnection.send(response);
        } else {
            sendFail(socketConnection);
        }
    }

    public void sendMessage(String from, String to, String message, String time, SocketConnection socketConnection) {
        if (mDataAccess.sendMessage(from, to, message, time)) {
            Request response = new Request();
            response.senderType = Constants.SERVER;
            response.requestType = Constants.SEND_MAESSAGE;
            response.body.put(Constants.STATE, Constants.SUCCESS);
            socketConnection.send(response);
        } else {
            sendFail(socketConnection);
        }
    }

    public void getMessages(String firstUser, String secondUser, SocketConnection socketConection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void createCommunity(String moderator, String communityName, String method, List<String> interests, SocketConnection socketConection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void editCommunity(String communityName, String method, List<String> interests, SocketConnection socketConection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
