/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yipada;


public class Debug {

    public static boolean debugMode = false;

    public static void println(String sPrompt) {
        if (debugMode) {
            System.out.println(sPrompt);
        }
    }
 
    public static void setDebugMode(boolean bMode) {
        debugMode = bMode;
    }
}
