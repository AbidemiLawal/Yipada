/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mytranslate;

import jep.*;

/**
 *
 * @author user
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       /* try {
        Jep oJep = new Jep();
        System.out.println("Jep OK!");

        } catch (JepException eJepEx) {
        System.out.println(eJepEx.getMessage());
        }
         */
        //  oJep.runscript()
        MainWindow oIDE = new MainWindow();


        oIDE.show(true);


        try {
           // Runtime.getRuntime().;
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }


       // System.out.println("IDE running ...");



    }
}
