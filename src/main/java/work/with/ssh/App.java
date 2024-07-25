package work.with.ssh;

import java.io.InputStreamReader;
import java.util.Scanner;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.stdDSA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import work.with.ssh.SSH_Connection.ApacheMinaSshdLibUsage;
import work.with.ssh.SSH_Connection.JSchLibUsage;
import work.with.ssh.api.model.User;
import work.with.ssh.util.UserRequestsExitException;

/**
 * some comments come here
 *
 */
@SpringBootApplication
public class App 
{
    //public static String hostForWhom;
    private static String userInput;
    private static String hostInputted;
    private static String pswInputted;
    private static final Scanner SCANNER_IN = new Scanner(System.in);
    private static User user;
    public static boolean isAppRunning = true;

    public static void main( String[] args )
    {    
        SpringApplication.run(App.class);

        // user = new User(SCANNER_IN.nextLine());
        // pswInputed = user.getPassword();
 
        //for (String hostOwner : Constants.LIST_OF_HOSTS_OWNERS ) {
            //hostForWhom = hostOwner;
            //useJSchLibToConnect();
            //useApacheMinaSshdToConnect(); // this method is to use another type of SSH connection
        //}

        try {
            while (isAppRunning) {
                //startMenu(); 
            }            
        } catch (UserRequestsExitException e) {
            System.out.println("You've requested to shutdown the app.");
            System.out.println("...... shutting down the app");
            System.exit(0);
            // add additional steps to properly shutdown the app if necessary
        } 
    
    }

    // private static void useJSchLibToConnect() {
    //     try {
    //         JSchLibUsage jschConnection = new JSchLibUsage(
    //                                                 Constants.USER_NAME, 
    //                                                 pswInputted,
    //                                                 hostInputted, //Constants.getHost(hostForWhom), 
    //                                                 Constants.PORT
    //                                                 );

    //         jschConnection.getNodeName();
    //         //jschConnection.listFolderStructure();
    //         jschConnection.checkBioAuthStatus();
    //         //jschConnection.getNodeLogs();
    //     } catch (Exception e) {
    //         //System.out.println(e.getMessage());
    //         System.out.println(e);
    //     }
    // }

    // private static void useApacheMinaSshdToConnect() {
    //     try {
    //         ApacheMinaSshdLibUsage apachMinaConnection = new ApachMinaSshdLibUsage(
    //                                                 Constants.USER_NAME, 
    //                                                 Constants.PSW, 
    //                                                 Constants.getHost(hostForWhom), 
    //                                                 Constants.PORT,
    //                                                 Constants.DEFAULT_TIMEOUT_SECONDS
    //                                                 );

    //         apacheMinaConnection.checkBioAuthStatus();

    //     } catch (Exception e) {
    //         //System.out.println(e.getMessage());
    //         System.out.println(e);
    //     }
    // }

    // private static void startMenu() {
    //     // read user's initial command
    //     System.out.print( "----------------------------------------------------------------- \n"
    //                     + "----- MENU OPTIONS ---------------------------------------------- \n"
    //                     + "-- 1) if you want to start/continue monitoring node status enter Y and hit ENTER \n"
    //                     + "-- 2) if you want to exit the App just enter EXIT and press ENTER: \n"
    //                     + "-- (You wanna continue of exit?) >");
    //     userInput = SCANNER_IN.nextLine();  

    //     if (userInput.equalsIgnoreCase("exit")) {
    //         throw new UserRequestsExitException();
    //     } else if (userInput != null && userInput != "") {
    //         // read a host IP from the console
    //         System.out.print("-- ENTER HOST IP HERE: >");
    //         hostInputted = SCANNER_IN.nextLine();

    //         // read a user's password from the console
    //         System.out.print("-- ENTER PASSWORD HERE: >");
    //         user = new User(SCANNER_IN.nextLine());
    //         pswInputted = user.getPassword(); 

    //         // execute a command requested by user
    //         useJSchLibToConnect();
    //         //useApacheMinaSshdToConnect(); // this method is to use another type of SSH connection
    
    //     } else if (userInput == null) {
    //         System.out.println("-- Please enter a Y to continue or EXIT to exit the App --");
    //         startMenu(); 
    //     }
    //     System.out.println("");
    // }
}
