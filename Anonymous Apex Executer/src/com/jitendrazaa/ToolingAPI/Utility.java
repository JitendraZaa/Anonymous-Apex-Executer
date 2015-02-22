/** 
 * @Website http://JitendraZaa.com
 * @GitHub https://github.com/JitendraZaa
 * @Date 02-22-2015
 * 
 */
package com.jitendrazaa.ToolingAPI;

import com.jitendrazaa.ToolingAPI.UI.LogWindow;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.tooling.ExecuteAnonymousResult;
import com.sforce.soap.tooling.SoapConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jitendra Zaa  
 */
public class Utility {

    public static LogWindow log = null;
    
    private static final double APIVersion = 33.0;

    private static final String productionAPIPath = "https://login.salesforce.com/services/Soap/u/" + String.valueOf(APIVersion) + "/";
    private static final String sandBoxAPIPath = "https://test.salesforce.com/services/Soap/u/" + String.valueOf(APIVersion) + "/";

    static{
        if (log == null) {
            log = new LogWindow();
        }
    }
    /**
     * This Utility method is used center position Window
     *
     * @param frm
     * @param JFrame to Center
     */
    public static void centerWindow(JFrame frm) {
        if (frm != null) {
            // Get the size of the screen
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

            // Determine the new location of the window
            int w = frm.getSize().width;
            int h = frm.getSize().height;
            int x = (dim.width - w) / 2;
            int y = (dim.height - h) / 2;

            // Move the window
            frm.setLocation(x, y);
        }
    }

    /**
     * Get String equivalent to PrintStackTrace of error
     *
     * @param e
     * @return
     */
    public static String stackTraceToString(Exception e) {
        String retVal = "";
        if (e != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            retVal = sw.toString();
        }
        return retVal;
    }

    /**
     * Show message to end user
     *
     * @param parFrame
     * @param msg
     */
    public static void showMessage(JFrame parFrame, String msg) {
        JOptionPane.showMessageDialog(parFrame, msg);
    }

    /**
     * Utility method to start Authentication and execution of code
     * @param userName
     * @param password
     * @param isSandBox
     * @param proxyAddress
     * @param proxyPort
     * @param proxyUserName
     * @param proxyPassword
     * @param totalLoop
     * @param pause
     * @param sourceCode 
     */
    public static void execute(String userName, String password, boolean isSandBox,
            String proxyAddress, String proxyPort, String proxyUserName, String proxyPassword,
            String totalLoop, String pause, String sourceCode) {
        (new Thread() {
            @Override
            public void run() {
                try {
                    log.setVisible(true);
                    log.messageln("Login using - " + userName);
                    SoapConnection toolCon = new Utility().login(userName, password, isSandBox,
                            proxyAddress, proxyPort, proxyUserName, proxyPassword);

                    int loopCounter = Integer.valueOf(totalLoop);
                    int pauseInMili = Integer.valueOf(pause);

                    int counter = 0;

                    while (counter < loopCounter) {
                        ExecuteAnonymousResult result = toolCon.executeAnonymous(sourceCode);
                        if (result.isCompiled()) {
                            log.messageln("Code Executed succesfully");
                        } else {
                            log.messageln(result.getCompileProblem());
                        }
                        counter++;
                        if (pauseInMili > 0) {
                            Thread.sleep(pauseInMili);
                            log.messageln(".... Pause - " + pauseInMili);
                        }
                        log.messageln("Loop completed -> " + counter
                                + ", Remaining Counter -> " + (loopCounter - counter));
                    }
                    log.messageln("All Anonymous Code is executed succesfully");
                } catch (Exception e) {
                    log.messageln(e.getMessage());
                }
            }
        }).start();
    }

     
    /**
     * Utility method to login for Tooling API
     *
     * @param userName
     * @param password
     * @param isSandbox
     * @param proxyAddress
     * @param port
     * @param proxyUserName
     * @param proxyPassword
     * @return
     * @throws ConnectionException
     */
    public SoapConnection login(String userName, String password, boolean isSandbox,
            String proxyAddress, String port, String proxyUserName, String proxyPassword
    ) throws ConnectionException {

        ConnectorConfig config = getConfigForLogin(userName, password, isSandbox,
                proxyAddress, port, proxyUserName, proxyPassword);
        LoginResult loginResult = (new PartnerConnection(config)).login(userName, password);
        config.setServiceEndpoint(getToolingAPIURLFromMetadata(loginResult.getMetadataServerUrl()));
        config.setSessionId(loginResult.getSessionId());
        return new SoapConnection(config);
    }

    /**
     * Convert https://cs21.salesforce.com/services/Soap/m/30.0/00Dq00000009cFC
     * to https://cs21.salesforce.com/services/Soap/T/30.0/00Dq00000009cFC Else
     * we will get error saying operation is not supported
     *
     * @param url
     * @return
     */
    private String getToolingAPIURLFromMetadata(String url) {
        return url.replace("/m/", "/T/");
    }

    private static ConnectorConfig getConfigForLogin(String userName, String password, boolean isSandbox,
            String proxyAddress, String port, String proxyUserName, String proxyPassword) {
        ConnectorConfig config = null;
        try {
            config = new ConnectorConfig();
            config.setUsername(userName);
            config.setPassword(password);
            if (isSandbox) {
                config.setAuthEndpoint(sandBoxAPIPath);
            } else {
                config.setAuthEndpoint(productionAPIPath);
            }

            // config.setTraceFile("src/"+sfdcUserName+".txt");
            if (proxyAddress != null && !proxyAddress.trim().equals("")) {
                config.setProxy(proxyAddress, Integer.valueOf(port));
                config.setProxyUsername(proxyUserName);
                config.setProxyPassword(proxyPassword);
            }
        } catch (Exception ex) {
            log.messageln(ex.getMessage());
        }
        return config;
    }
    
    /**
     * If user wants help in command-line mode, then print below information on console
     * @param args Command line argument  
     */
    public static boolean provideHelp(String[] args)
    {
        if(args.length > 0 && args[0].toLowerCase().contains("help"))
        {
            log.messageln("Create XML File by name - commandLine.config"+"\n"+
             "Format of XML File :"+"\n"+
             "<apexconfig>"+"\n"+
             "    <username>YOURUSERNAME</username>"+"\n"+
             "    <password>YOUR PASSWORD + SECURITY TOKEN</password>"+"\n"+
             "    <isSandbox>TRUE/FALSE</isSandbox>"+"\n"+
             "    <proxyAddress>ADDRESS OF PROXY SERVER IF APPLICABLE</proxyAddress>"+"\n"+
             "    <proxyPort>PORT OF PROXY SERVER IF APPLICABLE</proxyPort>"+"\n"+
             "    <proxyUserName>USERNAME OF PROXY SERVER IF APPLICABLE</proxyUserName>"+"\n"+
             "    <proxyPassword>PASSWORD OF PROXY SERVER IF APPLICABLE</proxyPassword>"+"\n"+
             "    <apexFile>PATH OF APEX FILE TO EXECUTE</apexFile>"+"\n"+
             "    <apexFile>PATH OF APEX FILE TO EXECUTE</apexFile>"+"\n"+
             "    <apexFile>PATH OF APEX FILE TO EXECUTE</apexFile>"+"\n"+
             "    <repeat>How many time code should be executed</repeat>"+"\n"+
             "    <pause>interval between each execution in ms</pause>"+"\n"+
             "</apexconfig>"+"\n"+
             "---------------------------------------------"+"\n"+
             "For more information visit - https://github.com/JitendraZaa/Anonymous-Apex-Executer"); 
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @return Settings from XML File "commandLine.config"
     */
    static CommandLineConfigPOJO readCommandLineSettings() {
        CommandLineConfigPOJO retObj = new CommandLineConfigPOJO();

        try {
            File fXmlFile = new File("commandLine.config");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("apexconfig");
            
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
 
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode; 
                    
                    retObj.setUsername(eElement.getElementsByTagName("username").item(0).getTextContent());
                    retObj.setPassword(eElement.getElementsByTagName("password").item(0).getTextContent());
                    retObj.setIsSandbox(Boolean.valueOf(eElement.getElementsByTagName("password").item(0).getTextContent()));
                    retObj.setProxyAddress(eElement.getElementsByTagName("proxyAddress").item(0).getTextContent());
                    
                    String port = eElement.getElementsByTagName("proxyPort").item(0).getTextContent() ;
                    if(port != null && !port.trim().equals(""))                         
                        retObj.setProxyPort(Integer.valueOf(port));
                    
                    String repeat = eElement.getElementsByTagName("repeat").item(0).getTextContent() ;
                    if(repeat != null && !repeat.trim().equals(""))                         
                        retObj.setRepeat(Integer.valueOf(repeat));
                    
                    String pause = eElement.getElementsByTagName("pause").item(0).getTextContent() ;
                    if(pause != null && !pause.trim().equals(""))                         
                        retObj.setPause(Integer.valueOf(pause));
                    
                    retObj.setProxyUserName(eElement.getElementsByTagName("proxyUserName").item(0).getTextContent());
                    retObj.setProxyPassword(eElement.getElementsByTagName("proxyPassword").item(0).getTextContent());
                    retObj.setApexFile(eElement.getElementsByTagName("apexFile").item(0).getTextContent());

                }
            }

        }
        catch(FileNotFoundException fe)
        {
            log.messageln("File \"commandLine.config\" does not exists ! ");
            return null;
        }
        catch (Exception e) {
            log.messageln(e.getMessage()) ;
            return null;
        }
        return retObj;
    }
     
    
    /**
     * This method returns code
     * @param apexFile
     * @return 
     */
    static String readCode(String apexFile) {
        StringBuilder retVal = new StringBuilder();
        try {
                BufferedReader in = new BufferedReader(new FileReader(apexFile));
                String line = in.readLine();
                while (line != null) {
                    retVal.append(line + "\n");
                    line = in.readLine();
                }
                in.close();
            } 
        catch(FileNotFoundException e)
        {
            log.messageln("Source code path in file \"commandLine.config\" is not correct. Trying to search file - "+apexFile);
        }
        catch (Exception e) {  
                log.messageln(e.getMessage()) ;
            }
        return retVal.toString() ;
    }

}
