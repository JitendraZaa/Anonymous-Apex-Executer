/**
 * @Website http://JitendraZaa.com
 * @GitHub https://github.com/JitendraZaa
 * @Date 02-22-2015
 *
 * Execute using command - java -cp Apex_Executer.jar  com.jitendrazaa.ToolingAPI.CommandLine
 * Get Help using command - java -cp Apex_Executer.jar  com.jitendrazaa.ToolingAPI.CommandLine -help
 * 
 */
package com.jitendrazaa.ToolingAPI;

import static com.jitendrazaa.ToolingAPI.Utility.log;

/**
 *
 * @author Jitendra Zaa
 */
public class CommandLine {

    public static void main(String[] args) {
        boolean isHelp = Utility.provideHelp(args);
        if (!isHelp) {
            CommandLineConfigPOJO settings = Utility.readCommandLineSettings();

            if (settings != null) {
                String code = Utility.readCode(settings.getApexFile());
                if (code != null && !code.trim().equals("")) {
                    Utility.execute(settings.getUsername(), settings.getPassword(), settings.isIsSandbox(),
                            settings.getProxyAddress(), String.valueOf(settings.getProxyPort()), settings.getProxyUserName(),
                            settings.getProxyPassword(),
                            String.valueOf(settings.getRepeat()), String.valueOf(settings.getPause()), code);
                } else {
                    log.messageln("No Valid Apex code to run");
                }
            } 
        }
        
        if(log != null)
            log.dispose();
    }

}
