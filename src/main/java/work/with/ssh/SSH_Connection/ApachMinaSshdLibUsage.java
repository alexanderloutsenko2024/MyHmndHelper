package work.with.ssh.SSH_Connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.Channel;

public class ApachMinaSshdLibUsage {
    private static String username;
    private static String password;
    private static String host;
    private static int port;
    private static String command;
    private static long defaultTimeoutSeconds;

    public ApachMinaSshdLibUsage (String username, String password, String host, int port, long defaultTimeoutSeconds) {
        this.username = username;
        this.password = password;
        this.host     = host;
        this.port     = port;
        //this.command  = command;
        this.defaultTimeoutSeconds = defaultTimeoutSeconds;

    }
    
    public void listFolderStructure ()  {
        command = work.with.ssh.Constants.COMMAND_TO_GET_HMND_FOLDER_STRUCTURE;
        try {
            connectSshAndExecuteCommand();
        } catch (Exception e) {
            System.out.println(e);
        }        
    }

    public void checkBioAuthStatus ()  {
        command = work.with.ssh.Constants.COMMAND_TO_CHECK_NODE_STATUS;
        try {
            connectSshAndExecuteCommand();
        } catch (Exception e) {
            System.out.println(e);
        }        
    }

    public void getNodeLogs ()  {
        command = work.with.ssh.Constants.COMMAND_TO_GET_NODE_LOGS;
        try {
            connectSshAndExecuteCommand();
        } catch (Exception e) {
            System.out.println(e);
        }        
    }

    public void getNumOfPeers() {
        
    }

    private void connectSshAndExecuteCommand() throws IOException {
            
        SshClient client = SshClient.setUpDefaultClient();
        client.start();
        
        try (ClientSession session = client.connect(username, host, port)
        .verify(defaultTimeoutSeconds, TimeUnit.SECONDS).getSession()) {
            session.addPasswordIdentity(password);
            session.auth().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);
            
            try (ByteArrayOutputStream responseStream = new ByteArrayOutputStream(); 
            ClientChannel channel = session.createChannel(Channel.CHANNEL_SHELL)) {
                channel.setOut(responseStream);
                try {
                    channel.open().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);
                    try (OutputStream pipedIn = channel.getInvertedIn()) {
                        pipedIn.write(command.getBytes());
                        pipedIn.flush();
                    }
                
                    channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 
                    TimeUnit.SECONDS.toMillis(defaultTimeoutSeconds));
                    String responseString = new String(responseStream.toByteArray());
                    System.out.println(responseString);
                } finally {
                    channel.close(false);
                }
            }
        } finally {
            client.stop();
        }
    }
}
