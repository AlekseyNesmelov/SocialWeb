package socialwebserver;

import java.sql.Timestamp;
import request.Request;
import java.util.ArrayList;
import java.util.Arrays;
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
            case Constants.GET_MESSAGES: {
                final Map<String, String> body = request.body;
                final String from = body.get(Constants.FROM);
                final String to = body.get(Constants.TO);
                getMessages(from, to, socketConnection);
                break;
            }
            case Constants.GET_COMMUNITIES: {
                getCommunities(socketConnection);
                break;
            }
            case Constants.GET_COMMUNITY: {
                final Map<String, String> body = request.body;
                final String name = body.get(Constants.COMMUNITY);
                getCommunity(name, socketConnection);
                break;
            }
            case Constants.CREATE_COMMUNITY: {
                final Map<String, String> body = request.body;
                final String name = body.get(Constants.COMMUNITY_NAME);
                final String moderator = body.get(Constants.COMMUNITY_MODERATOR);
                final String method = body.get(Constants.COMMUNITY_METHOD);
                final List<String> interests = Arrays.asList(body.get(Constants.COMMUNITY_INTERESTS).split(":"));
                createCommunity(name, moderator, method, interests, socketConnection);
                break;
            }
            case Constants.EDIT_COMMUNITY: {
                final Map<String, String> body = request.body;
                final String name = body.get(Constants.COMMUNITY_NAME);
                final String method = body.get(Constants.COMMUNITY_METHOD);
                final List<String> interests = Arrays.asList(body.get(Constants.COMMUNITY_INTERESTS).split(":"));
                editCommunity(name, method, interests, socketConnection);
                break;
            }
            case Constants.GET_COMMUNITY_MEMBERS: {
                final Map<String, String> body = request.body;
                final String community = body.get(Constants.COMMUNITY_NAME);
                getCommunityMembers(community, socketConnection);
                break;
            }
            case Constants.JOIN_THE_COMMUNITY: {
                final Map<String, String> body = request.body;
                final String community = body.get(Constants.COMMUNITY_NAME);
                final String user = body.get(Constants.WEBNAME);
                joinTheCommunity(community, user, socketConnection);
                break;
            }
            case Constants.QUIT_THE_COMMUNITY: {
                final Map<String, String> body = request.body;
                final String community = body.get(Constants.COMMUNITY_NAME);
                final String user = body.get(Constants.WEBNAME);
                quitTheCommunity(community, user, socketConnection);
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

    public void getMessages(String firstUser, String secondUser, SocketConnection socketConnection) {
        final StringBuilder sb = new StringBuilder();
        final List<String> interests = mDataAccess.getMessages(firstUser, secondUser);
        interests.stream().forEach((String interest) -> {
            sb.append(interest).append("<:::>");
        });
        
        final Request response = new Request();
        response.senderType = Constants.SERVER;
        response.requestType = Constants.GET_MESSAGES;
        response.body.put(Constants.MESSAGES, sb.toString());
        socketConnection.send(response);
    }

    public void getCommunities(SocketConnection socketConnection) {
        final Request response = new Request();
        response.senderType = Constants.SERVER;
        response.requestType = Constants.GET_COMMUNITIES;
        response.body.put(Constants.COMMUNITIES, String.join(";", mDataAccess.getCommunities()));
        socketConnection.send(response);
    }

    public void getCommunity(String name, SocketConnection socketConnection) {
        final Request response = new Request();
        response.senderType = Constants.SERVER;
        response.requestType = Constants.GET_COMMUNITY;
        String[] result = mDataAccess.getCommunity(name);
        response.body.put(Constants.COMMUNITY, String.join(";", result));
        socketConnection.send(response);
    }

    public void createCommunity(String name, String moderator, String method, List<String> interests, SocketConnection socketConnection) {
        final Request response = new Request();
        response.senderType = Constants.SERVER;
        response.requestType = Constants.CREATE_COMMUNITY;
        response.body.put(Constants.STATE, mDataAccess.createCommunity(name, moderator, method, interests) ? Constants.SUCCESS : Constants.FAIL);
        socketConnection.send(response);
    }

    public void editCommunity(String name, String method, List<String> interests, SocketConnection socketConnection) {
        final Request response = new Request();
        response.senderType = Constants.SERVER;
        response.requestType = Constants.EDIT_COMMUNITY;
        response.body.put(Constants.STATE, mDataAccess.editCommunity(name, method, interests) ? Constants.SUCCESS : Constants.FAIL);
        socketConnection.send(response);
    }

    public void getCommunityMembers(String community, SocketConnection socketConnection) {
        final Request response = new Request();
        response.senderType = Constants.SERVER;
        response.requestType = Constants.GET_COMMUNITY_MEMBERS;
        response.body.put(Constants.COMMUNITY_MEMBERS, String.join(";", mDataAccess.getCommunityMembers(community)));
        socketConnection.send(response);
    }

    public void joinTheCommunity(String community, String user, SocketConnection socketConnection) {
        final Request response = new Request();
        response.senderType = Constants.SERVER;
        response.requestType = Constants.JOIN_THE_COMMUNITY;
        response.body.put(Constants.STATE, mDataAccess.joinTheCommunity(community, user) ? Constants.SUCCESS : Constants.FAIL);
        socketConnection.send(response);
    }

    public void quitTheCommunity(String community, String user, SocketConnection socketConnection) {
        final Request response = new Request();
        response.senderType = Constants.SERVER;
        response.requestType = Constants.QUIT_THE_COMMUNITY;
        response.body.put(Constants.STATE, mDataAccess.quitTheCommunity(community, user));
        socketConnection.send(response);
    }
}
