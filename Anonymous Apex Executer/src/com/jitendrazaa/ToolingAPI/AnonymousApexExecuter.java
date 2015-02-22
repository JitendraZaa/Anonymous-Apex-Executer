/**
 * @author  Jitendra Zaa
 * @Website http://JitendraZaa.com
 * @GitHub https://github.com/JitendraZaa
 * @Date 02-22-2015
 * 
 */
package com.jitendrazaa.ToolingAPI;

import com.jitendrazaa.ToolingAPI.UI.LaunchWindow;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 *
 * @author Jitendra
 */
public class AnonymousApexExecuter {

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
                        //new TableDemo().setVisible(true);
                    //new SecurityMatrix().setVisible(true);
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(AnonymousApexExecuter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
