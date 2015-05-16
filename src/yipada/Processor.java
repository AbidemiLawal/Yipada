/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yipada;

import java.io.*;

public class Processor {


    private ChildDataCallBack cd;
    private InputOutput oInputPut;

    public void registerCallback(ChildDataCallBack cd) {
        this.cd = cd;
    }

    public Processor(InputOutput oInputPut) {
        try {
            this.oInputPut = oInputPut;


        } catch (Exception ex) {
        }
    }

    

    public boolean Execute( String sExecutableFile) {
        try {
           
            oInputPut.output("Executing...\n");
             String s = sExecutableFile;
            ChildDataCallBack cd = new DataManager(oInputPut);
            this.registerCallback(cd);
            CommandProcessor rt = new CommandProcessor( s, cd);
            rt.start();
           

        } catch (Exception ex) {
            Debug.println("Execution Error : " + ex.getMessage());
            return false;
        }
        return true;
    }
}
