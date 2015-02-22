/** 
 * @Website http://JitendraZaa.com
 * @GitHub https://github.com/JitendraZaa
 * @Date 02-22-2015
 * 
 */
package com.jitendrazaa.ToolingAPI;

import com.jitendrazaa.ToolingAPI.UI.LaunchWindow;
import com.jitendrazaa.ToolingAPI.UI.LogWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Jitendra
 */
public class AnonymousApexExecuter {

    public static LogWindow log = Utility.log;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            java.awt.EventQueue.invokeLater(new Runnable() {

                public void run() {
                    LaunchWindow win = new LaunchWindow();
                    win.setVisible(true); 
                }
            });
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            log.messageln(Utility.stackTraceToString(ex)); 
        }
    }

}
