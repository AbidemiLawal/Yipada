/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yipada;

import java.io.*;

public class Linker {

    private ChildDataCallBack cd;
    private String sAppDir;
    private InputOutput oInputPut;

    public void registerCallback(ChildDataCallBack cd) {
        this.cd = cd;
    }

    public Linker(InputOutput oInputPut, String sAppDir) {
        try {
            this.oInputPut = oInputPut;
            this.sAppDir = sAppDir;

        } catch (Exception ex) {
        }
    }

    private String createExecutableFile() {

        return this.sAppDir + "\\compiledGrammar\\ypadaTarget_";
    }

    public String getExecutableFile(){
        return createExecutableFile();
    }

    public boolean Link( String sOutPutFile) {
        try {
            oInputPut.appendOutPutWindow("Linking...\n");                    
            String s = "g++ \"" + sOutPutFile + "\" -o \""
                    + createExecutableFile() + "\"";
            ChildDataCallBack cd = new DataManager(oInputPut);
            this.registerCallback(cd);
            CommandProcessor rt = new CommandProcessor(s, cd);
            rt.start();




        } catch (Exception ex) {
            Debug.println("Linker Error : " + ex.getMessage());
            return false;
        }
        return true;
    }
}
