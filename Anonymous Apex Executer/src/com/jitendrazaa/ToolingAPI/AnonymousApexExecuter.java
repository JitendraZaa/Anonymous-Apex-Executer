/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jitendrazaa.ToolingAPI;

import com.jitendrazaa.ToolingAPI.UI.ExecuteAnonymous;
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
                    ExecuteAnonymous win = new ExecuteAnonymous();
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
