
package yipada;

import java.io.*;

public class CommandProcessor extends Thread {

    ChildDataCallBack cd;
    BufferedReader reader;
    Process P;
    Runtime oRun;
    String s;

    public CommandProcessor( String s, ChildDataCallBack cd) {
       
        this.cd = cd;
        this.s = s;

    }
    public void run() {

        try {
            oRun = Runtime.getRuntime();
            P = oRun.exec(this.s);
            UserInputProcessor.out = new BufferedWriter(new OutputStreamWriter(P.getOutputStream()));            
            BufferedReader buffIn = new BufferedReader(
                    new InputStreamReader(P.getInputStream()));
             BufferedReader buffErr = new BufferedReader(
                    new InputStreamReader(P.getErrorStream()));

            String line = null;
            while ((line = buffIn.readLine()) != null) {
                cd.writeCallback(line);

            }
            buffIn.close();

            line = null;
            while ((line = buffErr.readLine()) != null) {
                cd.writeCallback(line);

            }

            P.waitFor();

            UserInputProcessor.out = null ;
            cd.writeCallback("Completed!!!");


        } catch (Exception ie) {
            cd.writeCallback("CommandProcessor Error::" + ie.toString());
            UserInputProcessor.out = null ;

        }
    }
}
