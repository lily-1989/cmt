/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trb.tii.efe.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;


/**
 *
 * @author cqin
 */
public class FtpAd {
    
    /**
     * 
     * @param configText
     * @return
     * this method will 
     * 1. remove the interval node;
     * 2. add the adid stmt to the sql section
     */
    public String[] constructConfigFile(String adId, String configText, String configFileName, String rootDir, String cfgDir) {
        //TODO: 1. remove the interval and date_column
        //          2. add adid= at the end of the select stmt
        //          3. construct the export config file
    
        String fileName = "";
        String[] configInfo = new String[2];
        try {
            //add adid stmt
            int i = configText.indexOf("]]>");
            
            if (i>0) {
                String temp = configText.substring(0, i);
                if (temp != null) {
                    if (!temp.matches("(?s)(.*?)(union|UNION)(.*?)")) {
                        configText = temp + "\tand adid='" + adId + "'\n " + configText.substring(i);
                    } else {
                        int j=temp.toLowerCase().indexOf("union");
                        String innerTemp = "";
                        while (j >0) {
                            innerTemp = innerTemp + temp.substring(0, j) + "\tand adid='"+ adId + "'\nunion";
                            temp = temp.substring(j+5);
                            j = temp.toLowerCase().indexOf("union");
                            if (j<0) {
                                innerTemp = innerTemp + temp + "\tand adid='"+ adId + "'\n";
                            }
                        }
                        configText = innerTemp + configText.substring(i);
                    }
                    
                }
            }

            //extract the sql stmt from the config text
            String sqlStmt = configText.substring(configText.indexOf("[CDATA[")+7, configText.indexOf("]]>"));
            configInfo[0] = sqlStmt.trim();
            
            //remove interval
            String[] temp = configText.split("interval>");
            if (temp.length >1) {
                configText = temp[0].substring(0, temp[0].length()-1) + temp[2];
                //remove date_column
                temp = configText.split("date_column>");
                configText = temp[0].substring(0, temp[0].length()-1) + temp[2];
            }
        } catch(Exception e) {
            Logger.log("error process config file content" + e.toString());
        }
 
        fileName = rootDir + File.separator + cfgDir
                + File.separator + "cmt_" + configFileName;
        
         //convert the string to file
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            out.print(configText);
            out.close();
        } catch(Exception e) {
            Logger.log("error writing to file" + e.toString());
        }
        //return fileName;
        configInfo[1] = fileName;
        return configInfo;
        
    }
    
    /*
     * This method actually send the ad FTP or MV depends
     * upon the settings in the config file passed in 
     */
    public int Send(String rootDir, String binDir, String command, String configName, String workDir) {

        String execCmd = rootDir + File.separator  + binDir + File.separator  + command + " " + configName;
        File dir = new File(rootDir);
        String tempStr = null;
        int i = -1;
        
        try {
            //run the command
            Logger.log("Execute Command: " + execCmd);
            Logger.log("Working directory: " + rootDir);
            Process p = Runtime.getRuntime().exec(execCmd, null, dir);
            int j = p.waitFor();
            if (j==0) {
                BufferedReader stdInput = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
                
                //read the output from the command
                while ((tempStr = stdInput.readLine()) != null) {
                    //System.out.println(tempStr);
                    Logger.log(tempStr);
                }
            } else {
                BufferedReader stdErr = new BufferedReader(
                        new InputStreamReader(p.getErrorStream()));
                
                //read the output from the command
                while ((tempStr = stdErr.readLine()) != null) {
                    //System.out.println(tempStr);
                    Logger.log(tempStr);
                }
            }
            i = p.exitValue();
            return i;
            
            
        } catch(Exception e) {
            Logger.log("FTP error: " + e.toString());
        }
        return i;
    }

}
