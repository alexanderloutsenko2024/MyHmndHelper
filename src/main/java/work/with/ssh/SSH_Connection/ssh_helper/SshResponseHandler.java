package work.with.ssh.SSH_Connection.ssh_helper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.json.JSONException;
import org.json.JSONObject;

import work.with.ssh.App;
import work.with.ssh.SSH_Connection.JSchLibUsage;

public class SshResponseHandler {
    private String responseString;
    public static String dateOfNextBioAuth;
    public static String remainingTimeToNextBioAuth;
    public static String isNodeActive;
    public static int numOfPeers;
    public static String nodeName;
    public static LocalDateTime localDateTime;
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SshResponseHandler (String response, String action) {
        this.responseString = response;
        if (responseString.length() > 0) {
            switch (action) {
                case ("give me number of peers"):  
                    numOfPeers = getNumOfPeers();
                    break;
                case ("give me BioAuth status"):  
                    isNodeActive = getStatusFromJson();
                    break;
                case ("give me root folder structure"):
                    printFolderStructure();
                    break;
                case ("give me node name"):
                    nodeName = getNodeNameFromJson();
                    break;
            }
        } else {
            dateOfNextBioAuth = null;
            remainingTimeToNextBioAuth = null;
            isNodeActive = "node is down";
            numOfPeers = 0;
        }
    }

    private String getNodeNameFromJson() {
        JSONObject jsonFileObj = new JSONObject(responseString);
        if (jsonFileObj != null) {
            //System.out.println(jsonFileObj);
            nodeName = jsonFileObj.getString("nodename");
        }
        return nodeName;
    }

    private void printFolderStructure() {
        System.out.println("-= the HMND folder structure is like the following: \n" 
                                        + responseString);
    }

    public int getNumOfPeers() {
        String resultStr;
        int resultInt = 0;
        // System.out.println("-= the log content is like the following: \n" 
        //                                 + responseString);
        
        try {
            //*  this code searches for the last 'peers' word in the response
            //*  and assumes that the number of peers may be not bigger than 999 

            int i = responseString.lastIndexOf("peers");
            String tmp = responseString.substring(i-5, i-1); // takes 3 digits (and if the digits are only two it takes also "(" char) and trims the space at the end 
            tmp = tmp.trim();    
                // System.out.println("--- substring is the following: " + tmp);  
                // System.out.println("--- substring's length is: " + tmp.length());
            if(tmp.contains("(")) { // if the substring contains '(' char at the beginning
                resultStr = tmp.substring(tmp.indexOf("(")+1, tmp.length());
                // System.out.println("---   'result' length is: " + resultStr.length()); 
                // System.out.println("---     'result' value is: " + resultStr); 
                resultInt = Integer.parseInt(resultStr);
            }   else {
                resultStr = tmp;
                // System.out.println("---   'result' length is: " + resultStr.length()); 
                // System.out.println("---     'result' value is: " + resultStr); 
                resultInt = Integer.parseInt(resultStr);
            }
        } catch (NullPointerException npe) {
            System.err.println(npe);
            System.out.println("!!! No 'peers' word is found in the returned logs. Check the node as it seems like it's down !!!");
        }                                
  
        return resultInt;
    }

    public String getStatusFromJson() {
        // example of the response with Active status: {"jsonrpc":"2.0","result":{"Active":{"expires_at":1720553394000}},"id":1}
        // example of the response with Inactive status: {"result":"Inactive","id":1,"jsonrpc":"2.0"}
            JSONObject jsonObj;
            
            try {
                jsonObj = new JSONObject(responseString);
                //System.out.println("-= the whole json obj: " + jsonObj);

                if (jsonObj.optString("result").toString().equalsIgnoreCase( "Inactive")) {
                    //System.out.println("-= !!! BioAuth status is not active !!! =-");
                    return "Inactive";
                } else {

                    JSONObject objOfActiveKey = jsonObj.getJSONObject("result");
                        //System.out.println("-= 'Active' json obj: " + objOfActiveKey);

                    JSONObject objOfExpiresAt = objOfActiveKey.getJSONObject("Active");
                        //System.out.println("-=   'expires_at' json obj: " + objOfExpiresAt);

                //if (objOfExpiresAt.opt("expires_at") != null) {
                        //System.out.println("-=     'expires_at' key has a value =-");
                    
                    dateOfNextBioAuth = convertEpochTimeToHumanReadable(objOfExpiresAt);
                        System.out.println("-=  THE TIME FOR BIOAUTH FOR " + JSchLibUsage.nodeName + " IS " + dateOfNextBioAuth + "  ==--");
                    
                    remainingTimeToNextBioAuth = getRemainingTime(dateOfNextBioAuth);
                    return "Active";
                //}    
                }                  //System.out.println("-= the whole json obj: " + jsonObj);

            } catch (JSONException e) {
                System.out.println(e);
                String exceptionResponse = "--==== the node seems to be down as the resonse is '"+ responseString + "'";
                System.out.println(exceptionResponse);
                return exceptionResponse;
            }
   
    }

    private String getRemainingTime(String dateOfNextBioAuth2) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime future  = localDateTime;
        //long difference_In_Time = ChronoUnit.SECONDS.between(now, future);
        long difference_In_Time = ChronoUnit.MILLIS.between(now, future);
        long difference_In_Seconds = (difference_In_Time / 1000) % 60;
        long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
        long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
        long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

        System.out.println(
            difference_In_Days
            + " days, "
            + difference_In_Hours
            + " hours, "
            + difference_In_Minutes
            + " minutes, "
            + difference_In_Seconds
            + " seconds");

        remainingTimeToNextBioAuth = difference_In_Days + " days, "
                                        + difference_In_Hours + " hours, "
                                        + difference_In_Minutes + " minutes, "
                                        + difference_In_Seconds + " seconds";
        return remainingTimeToNextBioAuth;
    }

    private String convertEpochTimeToHumanReadable(JSONObject objOfExpiresAt) {

        long epochTimeMillis = objOfExpiresAt.getLong("expires_at");//Long.parseLong("1721208690000"); 
        Instant instant = Instant.ofEpochMilli(epochTimeMillis);

        setDateTime(instant);
        String formattedDateTime = localDateTime.format(formatter);
        //System.out.println(formattedDateTime); // Output: 2021-06-29 12:13:51
        return formattedDateTime;
    }

    private void setDateTime (Instant instant) {
        ZoneId zoneId = ZoneId.systemDefault(); // Use the system default time zone
        localDateTime = instant.atZone(zoneId).toLocalDateTime(); // convert the time to local timezone
    }
        
}
