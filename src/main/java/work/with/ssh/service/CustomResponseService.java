package work.with.ssh.service;

import org.springframework.stereotype.Service;

import work.with.ssh.SSH_Connection.JSchLibUsage;
import work.with.ssh.SSH_Connection.ssh_helper.SshResponseHandler;
import work.with.ssh.api.model.CustomResponse;

@Service
public class CustomResponseService {
// this class is supposed to return the custom response with all expected data related to the HMND node
    private String host;
    private String token;
    private CustomResponse customResponse;

    public CustomResponse getCustomService(String host, String token) {
        this.host = host;
        this.token = token;

        useJSchLibToConnect();
        
        return customResponse;
    }

    private void useJSchLibToConnect() {
        try {
            JSchLibUsage jschConnection = new JSchLibUsage(host, token);

            customResponse = new CustomResponse();
                // set host
                customResponse.setHost(host);      
                // set and get a node name
                jschConnection.getNodeName();                              
                customResponse.setNodeName(SshResponseHandler.nodeName);
                // get number of connected peers from the node logs (the node even may be down)
                jschConnection.getNodeLogs();
                // set and get bio auth status
                jschConnection.checkBioAuthStatus();
                customResponse.setBioStatus(SshResponseHandler.isNodeActive);
                // get the date of next bio authentication
                customResponse.setDateOfNextBioAuth(SshResponseHandler.dateOfNextBioAuth);
                // get the time remaining till the next bio authentication
                customResponse.setRemainingTimeToNextBioAuth(SshResponseHandler.remainingTimeToNextBioAuth);
                // set number of peers connected to the node
                customResponse.setNumOfPeers(SshResponseHandler.numOfPeers);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            System.out.println(e);
        }
    }
    
}
