/**
 * @author  Jitendra Zaa
 * @Website http://JitendraZaa.com
 * @GitHub https://github.com/JitendraZaa
 * @Date 02-22-2015
 * 
 */
package com.jitendrazaa.ToolingAPI;

import static com.jitendrazaa.ToolingAPI.UI.LaunchWindow.log;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.tooling.ExecuteAnonymousResult;
import com.sforce.soap.tooling.SoapConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jitendra Zaa , visit @ http://JitendraZaa.com
 */
public class Util {

    private static final double APIVersion = 33.0;

    private static final String productionAPIPath = "https://login.salesforce.com/services/Soap/u/" + String.valueOf(APIVersion) + "/";
    private static final String sandBoxAPIPath = "https://test.salesforce.com/services/Soap/u/" + String.valueOf(APIVersion) + "/";

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
                    SoapConnection toolCon = new Util().login(userName, password, isSandBox,
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
                    log.messageln(Util.stackTraceToString(e));
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
            ex.printStackTrace();
        }
        return config;
    }

}
