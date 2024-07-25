package work.with.ssh.api.model;

public class CustomResponse {
    private String host;
    private String token;
    private String nodeName;
    private String bioAuthStatus;
    //private String bioAuthStatusDetails;
    private String dateOfNextBioAuth;
    private String remainingTimeToNextBioAuth;
    private int numOfPeers;

    public CustomResponse() {}
    
    public CustomResponse(String inputtedHost, String node, String status, int peers) {
        this.host = inputtedHost;
        //this.token = inputtedToken;
        this.nodeName = node;
        this.bioAuthStatus = status;
        //this.bioAuthStatusDetails = details;
        this.numOfPeers = peers;
    }

    // host methods
    public String getHost() {
        return host;
    }    
    
    public void setHost(String input) {
        this.host = input;
    }

    // token methods
    public String getToken() {
        return token;
    }

    // node name methods
    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String input) {
        this.nodeName = input;
    }

    // bio status methods
    public String getBioStatus() {
        return bioAuthStatus;
    }

    public void setBioStatus(String input) {
        this.bioAuthStatus = input;
    }

    // bio status details method
    // public String getBioStatusDetails() {
    //     return bioAuthStatusDetails;
    // }

    // public void setBioStatusDetails(String input) {
    //     this.nodeName = input;
    // }

    // num of peers methods
    public int getPeers() {
        return numOfPeers;
    }

    public void setNumOfPeers(int input) {
        this.numOfPeers = input;
    }
    
    // date and time of next bio authentication 
    public String getDateOfNextBioAuth() {
        return dateOfNextBioAuth;
    }

    public void setDateOfNextBioAuth(String input) {
        this.dateOfNextBioAuth = input;
    }

    // how many hours are remaining till the next bio authentication 
    public String getRemainingTimeToNextBioAuth() {
        return remainingTimeToNextBioAuth;
    }

    public void setRemainingTimeToNextBioAuth(String input) {
        this.remainingTimeToNextBioAuth = input;
    }
}
