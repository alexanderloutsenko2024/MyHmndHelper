package work.with.ssh.api.model;

import org.bouncycastle.crypto.generators.OpenBSDBCrypt;
import java.security.SecureRandom;

public class User {
    private String returnedPassword;

    public User(String input) {
        setPassword(input);
    }
    
    public String getPassword() { 
        /* return a hashed password */
        return returnedPassword;
    }

    private void setPassword(String password) { 
        returnedPassword = password;
    }

    public void setHashedPassword(String password) { 
        /* store the hashed password */ 
        byte[] salt = new byte[16]; // Use a cryptographically secure random number generator to generate the salt
        new SecureRandom().nextBytes(salt);

        // Hash the password with bcrypt
        String hashedPassword = OpenBSDBCrypt.generate(password.toCharArray(), salt, 12);

        // Verifying the hashed password
        String passwordToVerify = "userPassword";
        boolean isPasswordCorrect = OpenBSDBCrypt.checkPassword(hashedPassword, passwordToVerify.toCharArray());
        System.out.println("Password is correct: " + isPasswordCorrect);

        returnedPassword = hashedPassword;
    }
}



// public class PasswordHashing {
//     public static void hashPassword(String pswToHash) {
//         String password = "userPassword";
//         byte[] salt = new byte[16]; // Use a cryptographically secure random number generator to generate the salt
//         new SecureRandom().nextBytes(salt);

//         // Hash the password with bcrypt
//         String hashedPassword = OpenBSDBCrypt.generate(password.toCharArray(), salt, 12);

//         // Verifying the hashed password
//         String passwordToVerify = "userPassword";
//         boolean isPasswordCorrect = OpenBSDBCrypt.checkPassword(hashedPassword, passwordToVerify.toCharArray());
//         System.out.println("Password is correct: " + isPasswordCorrect);
//     }
// }