/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yipada;

import java.io.*;

public class UserInputProcessor {

    public UserInputProcessor() {
    }
    public static BufferedWriter out;

    public void sendUserInput2StandardInput(Process P,
            String sData) {

         if (sData.indexOf("?") == 0) {
            sData = sData.substring(1);
        }

        if (UserInputProcessor.out != null) {
           // System.out.println("User input can be sent to processor...!!!");
            try{
            UserInputProcessor.out.write(sData + "\n");
             UserInputProcessor.out.flush();
            }
            catch (IOException ie){
                
            }
        } else {
            Debug.println("processor handle not accessible!!!");
        }
       
        //System.out.println("<STDIN> :: " + sData.trim());


    }
}
