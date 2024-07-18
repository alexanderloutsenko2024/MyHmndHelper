package work.with.ssh;

import java.io.InputStreamReader;
import java.util.Scanner;

import work.with.ssh.SSH_Connection.ApachMinaSshdLibUsage;
import work.with.ssh.SSH_Connection.JSchLibUsage;
import work.with.ssh.models.User;

/**
 * Hello world!
 *
 */
public class App 
{
    public static String hostForWhom;
    private static String pswInputed;
    private static final Scanner SCANNER_IN = new Scanner(System.in);
    private static User user;

    public static void main( String[] args )
    {    
        user = new User(SCANNER_IN.nextLine());
        pswInputed = user.getPassword();
        //setPsw(reader.toString());
        for (String hostOwner : Constants.LIST_OF_HOSTS_OWNERS ) {
            hostForWhom = hostOwner;
            printInitialMessage();
            
            useJSchLibToConnect();
            //useApachMinaSshdToConnect();
        }
    
    }

    private static void useJSchLibToConnect() {
        try {
            JSchLibUsage jschConnection = new JSchLibUsage(
                                                    Constants.USER_NAME, 
                                                    pswInputed,
                                                    Constants.getHost(hostForWhom), 
                                                    Constants.PORT
                                                    );

            //jschConnection.listFolderStructure();
            jschConnection.checkBioAuthStatus();
            //jschConnection.getNodeLogs();
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            System.out.println(e);
        }
    }

    // private static void useApachMinaSshdToConnect() {
    //     try {
    //         ApachMinaSshdLibUsage apachMinaConnection = new ApachMinaSshdLibUsage(
    //                                                 Constants.USER_NAME, 
    //                                                 Constants.PSW, 
    //                                                 Constants.getHost(hostForWhom), 
    //                                                 Constants.PORT,
    //                                                 Constants.DEFAULT_TIMEOUT_SECONDS
    //                                                 );

    //         apachMinaConnection.checkBioAuthStatus();

    //     } catch (Exception e) {
    //         //System.out.println(e.getMessage());
    //         System.out.println(e);
    //     }
    // }

    private static void printInitialMessage() {
        System.out.println( "\n -----------------------------------------\n" 
                            + " ----------------- " + hostForWhom + " ------------------\n"
                            + " -----------------------------------------");
    }

    private static void setPsw() {
        
        pswInputed = SCANNER_IN.nextLine();
        System.out.println("-- DEBUG: #1 -- psw entered by user is : " + pswInputed);
    }
}
