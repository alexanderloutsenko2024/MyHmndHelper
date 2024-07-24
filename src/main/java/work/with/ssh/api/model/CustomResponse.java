package work.with.ssh.api.model;

public class CustomResponse {
    private String host;
    private String token;
    private String nodeName;
    private String bioAuthStatus;
    private String bioAuthStatusDetails;
    private int numOfPeers;

    public CustomResponse(String inputtedHost, String node, String status, String details, int peers) {
        this.host = inputtedHost;
        //this.token = inputtedToken;
        this.nodeName = node;
        this.bioAuthStatus = status;
        this.bioAuthStatusDetails = details;
        this.numOfPeers = peers;
    }

    public String getHost() {
        return host;
    }

    public String getToken() {
        return token;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getBioStatus() {
        return bioAuthStatus;
    }

    public String getBioStatusDetails() {
        return bioAuthStatusDetails;
    }

    public int getPeers() {
        return numOfPeers;
    }
}
