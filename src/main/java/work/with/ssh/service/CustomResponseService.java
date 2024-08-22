package work.with.ssh.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import work.with.ssh.Constants;
import work.with.ssh.SSH_Connection.JSchLibUsage;
import work.with.ssh.SSH_Connection.ssh_helper.SshResponseHandler;
import work.with.ssh.api.model.CustomResponse;

@Service
public class CustomResponseService {
// this class is supposed to return the custom response with all expected data related to the HMND node
    private String host;
    private String token;
    private CustomResponse singleCustomResponse;
    private List<CustomResponse> multipleCustomResponse = new ArrayList<CustomResponse>();

    public CustomResponse getSingleCustomResponse(String host, String token) {
        this.host = host;
        this.token = token;

        useJSchLibToConnect();
        
        return singleCustomResponse;
    }

    public List<CustomResponse> getMultipleCustomResponses(String token) {
        this.token = token;

        for (String oneHost : Constants.getListOfNodes()) {
            host = oneHost;
                System.out.println("-- inside of getMultipleCustomResponses - checked expected host is " + oneHost);
            useJSchLibToConnect(); 
                
                System.out.println("-- inside of getMultipleCustomResponses - singleCustomResponse " + singleCustomResponse.toString());
            if (singleCustomResponse != null) {
                multipleCustomResponse.add(singleCustomResponse);
            }
        }       
        
        return multipleCustomResponse;
    }

    private void useJSchLibToConnect() {
        try {
            JSchLibUsage jschConnection = new JSchLibUsage(host, token);

            singleCustomResponse = new CustomResponse();
                // set host
                singleCustomResponse.setHost(host);      
                // set and get a node name
                jschConnection.getNodeName();                              
                singleCustomResponse.setNodeName(SshResponseHandler.nodeName);
                // get number of connected peers from the node logs (the node even may be down)
                jschConnection.getNodeLogs();
                // set and get bio auth status
                jschConnection.checkBioAuthStatus();
                singleCustomResponse.setBioStatus(SshResponseHandler.isNodeActive);
                // get the date of next bio authentication
                singleCustomResponse.setDateOfNextBioAuth(SshResponseHandler.dateOfNextBioAuth);
                // get the time remaining till the next bio authentication
                singleCustomResponse.setRemainingTimeToNextBioAuth(SshResponseHandler.remainingTimeToNextBioAuth);
                // set number of peers connected to the node
                singleCustomResponse.setNumOfPeers(SshResponseHandler.numOfPeers);
                // set current timestamp
                singleCustomResponse.setCurrentDateTime();
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            System.out.println(e);
        }
    }
    
}
