package work.with.ssh.SSH_Connection;

import java.io.ByteArrayOutputStream;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import work.with.ssh.App;
import work.with.ssh.SSH_Connection.ssh_helper.SshResponseHandler;

public class JSchLibUsage {
    private String username;
    private String password;
    private String host;
    private int port;
    public static String nodeName;
    private String command;
    private String responseString;
    private SshResponseHandler sshResponseHandler;


    public JSchLibUsage(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host     = host;
        this.port     = port;
        //this.command  = command;
    }
    
    public void listFolderStructure() throws Exception {
        command = work.with.ssh.Constants.COMMAND_TO_GET_HMND_FOLDER_STRUCTURE;
        try {
            connectSshAndExecuteCommand();
            sshResponseHandler = new SshResponseHandler(responseString, "give me root folder structure");
        } catch (Exception e) {
            System.out.println(e);
        } 
    }

    public void checkBioAuthStatus ()  {
        command = work.with.ssh.Constants.COMMAND_TO_CHECK_NODE_STATUS;

        try {
            connectSshAndExecuteCommand();
            sshResponseHandler = new SshResponseHandler(responseString, "give me BioAuth status");
            System.out.println("--== BioAuth status is ** " + SshResponseHandler.isNodeActive.toUpperCase() + " ** ==--");
        } catch (Exception e) {
            System.out.println(e);
        }        
    }

    public void getNodeLogs() {
        command = work.with.ssh.Constants.COMMAND_TO_GET_NODE_LOGS;

        try {
            connectSshAndExecuteCommand();
            sshResponseHandler = new SshResponseHandler(responseString, "give me number of peers");
            System.out.println("--== Number of peers for " + nodeName + " node is " + SshResponseHandler.numOfPeers + " ==--");
        } catch (Exception e) {
            System.out.println(e);
        }            
    }

    public void getNodeName() {
        command = work.with.ssh.Constants.COMMAND_TO_READ_NODE_NAME;

        try {
            connectSshAndExecuteCommand();
            sshResponseHandler = new SshResponseHandler(responseString, "give me node name");
            nodeName = SshResponseHandler.nodeName;
            System.out.println("--== Node name is " + nodeName + " ==--");
        } catch (Exception e) {
            System.out.println(e);
        }     
    }

    private void connectSshAndExecuteCommand() throws Exception {
        Session session = null;
        ChannelExec channel = null;
        
        try {
            session = new JSch().getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();
            
            while (channel.isConnected()) {
                Thread.sleep(100);
            }
            
            responseString = new String(responseStream.toByteArray());
            //System.out.println("..--== the whole response is:~# " + responseString);
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

}
