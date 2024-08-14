package work.with.ssh;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String USER_NAME = "root";
    public static final int PORT = 22;
    protected static String nodeName;
    public static final List<String> LIST_OF_HOSTS_OWNERS = Arrays.asList("Sasha","Natalka", "Varvara", "Lada");
    // commands
    public static final String COMMAND_TO_GET_NODE_LOGS = "tail .humanode/workspaces/default/node/logs.txt";
    public static final String COMMAND_TO_GET_HMND_FOLDER_STRUCTURE = "ls -al .humanode";
    public static final String COMMAND_TO_CHECK_NODE_STATUS = 
                "curl -X POST http://localhost:9933 \\\r " + //
                "     -H \"Content-Type: application/json\" \\\r " + //
                "     -d '{\r" + //
                "           \"jsonrpc\": \"2.0\",\r\n" + //
                "           \"id\": 1,\r\n" + //
                "           \"method\": \"bioauth_status\",\r\n" + //
                "           \"params\": []\r\n" + //
                "         }'";
    
    public static final String COMMAND_TO_READ_NODE_NAME = "cat /root/.humanode/workspaces/default/workspace.json";
    
    public static final String COMMAND_TO_RUN_RPC_TUMMEL = "$ ngrok-wrapper 9944";
    public static final String COMMAND_TO_RUN_NODE =
                "humanode-peer --name \"" + nodeName + "\" --validator --chain chainspec.json \\ \r\n" + //
                "      --rpc-url-ngrok-detect --rpc-cors all";


    // var used for Apach Mina Lib only
    protected static final long DEFAULT_TIMEOUT_SECONDS = 20;// defaultTimeoutSeconds var
    //hosts
    protected static final String HOST_NATALKA = "178.159.43.120";
    protected static final String HOST_LADA    = "185.253.7.48";
    protected static final String HOST_SASHA   = "45.88.76.36";
    protected static final String HOST_VARVARA = "185.253.7.232";

    //node names
    protected static final String LADA    = "0___Lada_Lu___";
    protected static final String NATALKA = "0___Natalka_Lu___";
    protected static final String SASHA   = "0___Sasha_Lu___";
    protected static final String VARVARA = "0___Varvara_Lu___";

        

    public static String getHost(String host) {
        String hostToReturn = "";

        switch (host) {
            case "Sasha":
                hostToReturn = HOST_SASHA;
                nodeName = SASHA;
                break;
            case "Natalka":
                hostToReturn = HOST_NATALKA;
                nodeName = NATALKA;
                break;
            case "Varvara":
                hostToReturn = HOST_VARVARA;
                nodeName = VARVARA;
                break;
            case "Lada":
                hostToReturn = HOST_LADA;
                nodeName = LADA;
                break;
        }

        return hostToReturn;
    }
}
