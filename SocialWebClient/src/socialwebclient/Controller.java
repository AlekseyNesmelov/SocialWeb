package socialwebclient;

import java.util.ArrayList;
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
}
