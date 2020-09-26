/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectStreamClass;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.SwingUtilities;

/**
 *
 * @author Serhan
 */
public class Dota2HeroMatchup {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
       final GUI gui = new GUI();
       gui.createAndShowGUI();
       SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui.setVisible();
            }
       });
    }
    
}
