package socialwebclient;

import java.util.ArrayList;
import java.util.Arrays;
import request.Request;
import java.util.List;

public class Controller {
    private final ISocketConnection mSocketConnection;

    public Controller(final ISocketConnection socketConnection) {
        mSocketConnection = socketConnection;
    }

    public boolean registration(final String username, final String password,
            final String email, final String fullname, final String work,
            final String birthday, final List<String> interests) {
        final Request request = new Request();
        request.senderType = Constants.USER;
        request.requestType = Constants.REGISTRATION;
        request.body.put(Constants.WEBNAME, username);
        request.body.put(Constants.PASSWORD, password);
        request.body.put(Constants.EMAIL, email);
        request.body.put(Constants.FULLNAME, fullname);
        request.body.put(Constants.WORK, work);
        request.body.put(Constants.BIRTHDAY, birthday);
        String interestsString = "";
        for (final String interest : interests) {
            interestsString+= interest + ":";
        }
        request.body.put(Constants.INTERESTS, interestsString);
        Request response = mSocketConnection.sendAndGetResponse(request);

        return response.senderType.equals(Constants.SERVER) &&
                response.requestType.equals(Constants.REGISTRATION) &&
                response.body.get(Constants.STATE).equals(Constants.SUCCESS);
    }

    public boolean authorization(final String username, final String password) {
        Request request = new Request();
        request.senderType = Constants.USER;
        request.requestType = Constants.AUTHORIZATION;
        request.body.put(Constants.WEBNAME, username);
        request.body.put(Constants.PASSWORD, password);
        final Request response = mSocketConnection.sendAndGetResponse(request);

        return response.senderType.equals(Constants.SERVER) &&
                response.requestType.equals(Constants.AUTHORIZATION) &&
                response.body.get(Constants.STATE).equals(Constants.SUCCESS);
    } 
    
    public List<Pair> getInterestsTree() {
        final Request request = new Request();
        request.senderType = Constants.USER;
        request.requestType = Constants.GET_INTERESTS_TREE;
        final Request response = mSocketConnection.sendAndGetResponse(request);
        
        final List<Pair> result = new ArrayList<>();
        if (response.body.get(Constants.INTERESTS) != null) {
            final String[] interestPairs = response.body.get(Constants.INTERESTS).split(";");
            for (final String pairs : interestPairs) {
                if (!pairs.isEmpty()) {
                    final String[] firstSecond = pairs.split(":");
                    result.add(new Pair(firstSecond[0], firstSecond[1].equals("null") ? "" : firstSecond[1]));
                }
            }
        }
        return result;
    }
    
    public boolean sendMessage(final String from, final String to, final String message) {
        Request request = new Request();
        request.senderType = Constants.USER;
        request.requestType = Constants.SEND_MAESSAGE;
        request.body.put(Constants.FROM, from);
        request.body.put(Constants.TO, to);
        request.body.put(Constants.MESSAGE, message);
        final Request response = mSocketConnection.sendAndGetResponse(request);

        return response.senderType.equals(Constants.SERVER) &&
                response.requestType.equals(Constants.SEND_MAESSAGE) &&
                response.body.get(Constants.STATE).equals(Constants.SUCCESS);
    }
    
    public String getMessages(final String from, final String to) {
        final Request request = new Request();
        request.senderType = Constants.USER;
        request.body.put(Constants.FROM, from);
        request.body.put(Constants.TO, to);
        request.requestType = Constants.GET_MESSAGES;
        final Request response = mSocketConnection.sendAndGetResponse(request);
        
        final StringBuilder result = new StringBuilder();
        if (response.body.get(Constants.MESSAGES) != null) {
            final String[] messages = response.body.get(Constants.MESSAGES).split("<:::>");
            for (final String message : messages) {
                if (!message.isEmpty()) {
                    final String[] splittedMessage = message.split("<:>");
                    result.append(splittedMessage[2]).append(", Oт: ").append(splittedMessage[0]).
                            append(" Кому: ").append(splittedMessage[1]).append("\n").append(splittedMessage[3]).append("\n");
                }
            }
        }
        return result.toString();
    }
    
    public List<String> getDialogs(final String from) {
        final Request request = new Request();
        request.senderType = Constants.USER;
        request.body.put(Constants.FROM, from);
        request.requestType = Constants.GET_DIALOGS;
        final Request response = mSocketConnection.sendAndGetResponse(request);
        
        final List<String> result = new ArrayList<>();
        if (response.body.get(Constants.DIALOGS) != null) {
            final String[] dialogs = response.body.get(Constants.DIALOGS).split("<:::>");
            for (final String dialog : dialogs) {
                if (!dialog.isEmpty() && !result.contains(dialog)) {
                    result.add(dialog);
                }
            }
        }
        return result;
    }
    
    public String[] getCommunities() {
        final Request request = new Request();
        request.senderType = Constants.USER;
        request.requestType = Constants.GET_COMMUNITIES;
        final Request response = mSocketConnection.sendAndGetResponse(request);
        
        if (response.body.get(Constants.COMMUNITIES) != null) {
            return response.body.get(Constants.COMMUNITIES).split(";");
        }
        return new String[0];
    }

    public String[] getCommunity(String name) {
        final Request request = new Request();
        request.senderType = Constants.USER;
        request.requestType = Constants.GET_COMMUNITY;
        request.body.put(Constants.COMMUNITY, name);
        final Request response = mSocketConnection.sendAndGetResponse(request);
        
        if (response.body.get(Constants.COMMUNITY) != null) {
            return response.body.get(Constants.COMMUNITY).split(";");
        }
        return new String[0];
    }
    
    public boolean createCommunity(String name, String moderator, String method, List<String> interests) {
        final Request request = new Request();
        request.senderType = Constants.USER;
        request.requestType = Constants.CREATE_COMMUNITY;
        request.body.put(Constants.COMMUNITY_NAME, name);
        request.body.put(Constants.COMMUNITY_MODERATOR, moderator);
        request.body.put(Constants.COMMUNITY_METHOD, method);
        String interestsString = "";
        interestsString = interests.stream().map((interest) -> interest + ":").reduce(interestsString, String::concat);
        request.body.put(Constants.COMMUNITY_INTERESTS, interestsString);
        Request response = mSocketConnection.sendAndGetResponse(request);
        return response.body.get(Constants.STATE).equals(Constants.SUCCESS);
    }

    public boolean editCommunity(String name, String method, List<String> interests) {
        final Request request = new Request();
        request.senderType = Constants.USER;
        request.requestType = Constants.EDIT_COMMUNITY;
        request.body.put(Constants.COMMUNITY_NAME, name);
        request.body.put(Constants.COMMUNITY_METHOD, method);
        String interestsString = "";
        interestsString = interests.stream().map((interest) -> interest + ":").reduce(interestsString, String::concat);
        request.body.put(Constants.COMMUNITY_INTERESTS, interestsString);
        Request response = mSocketConnection.sendAndGetResponse(request);
        return response.body.get(Constants.STATE).equals(Constants.SUCCESS);
    }

    public String[] getCommunityMembers(String community) {
        final Request request = new Request();
        request.senderType = Constants.USER;
        request.requestType = Constants.GET_COMMUNITY_MEMBERS;
        request.body.put(Constants.COMMUNITY_NAME, community);
        final Request response = mSocketConnection.sendAndGetResponse(request);
        
        if (response.body.get(Constants.COMMUNITY_MEMBERS) != null) {
            return response.body.get(Constants.COMMUNITY_MEMBERS).split(";");
        }
        return new String[0];
    }

    public boolean joinTheCommunity(String community, String user) {
        final Request request = new Request();
        request.senderType = Constants.USER;
        request.requestType = Constants.JOIN_THE_COMMUNITY;
        request.body.put(Constants.COMMUNITY_NAME, community);
        request.body.put(Constants.WEBNAME, user);
        Request response = mSocketConnection.sendAndGetResponse(request);
        return response.body.get(Constants.STATE).equals(Constants.SUCCESS);
    }
    
    public String quitTheCommunity(String community, String user) {
        final Request request = new Request();
        request.senderType = Constants.USER;
        request.requestType = Constants.QUIT_THE_COMMUNITY;
        request.body.put(Constants.COMMUNITY_NAME, community);
        request.body.put(Constants.WEBNAME, user);
        Request response = mSocketConnection.sendAndGetResponse(request);
        return response.body.get(Constants.STATE);
    }
}
