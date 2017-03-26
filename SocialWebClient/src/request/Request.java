package request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Request implements Serializable {
    public String senderType;
    public String requestType;
    public Map<String, String> body = new HashMap<>();
}
