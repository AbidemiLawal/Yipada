/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yipada;

/**
 *
 * @author user
 */
public class InputOutput {

    javax.swing.JTextArea jInputOutput;

    public InputOutput(javax.swing.JTextArea jTxtArea) {
        try {
            jInputOutput = jTxtArea;
           // jInputOutput.setText(jInputOutput.getText() + "here now" + "\n");
        } catch (Exception ex) {
        }
    }

    public int output(String s) {

         jInputOutput.setText( s + "\n");
        return 0;

    }

    public String readOutPutWindow()
    {
        return jInputOutput.getText() ;
    }

    public void appendOutPutWindow(String s)
    {
          jInputOutput.setText(readOutPutWindow() + s );
    }
}
