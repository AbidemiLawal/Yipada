/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yipada;

import java.lang.*;
import java.io.*;

public class Interpreter extends Thread {

    Process PExecutableProgram;
    InputOutput o_IO;

    public void run() {
        // interpreter executes process in a seperate thread
        switch (NativePlatform()) {
            case 10: {
                // windows
                 executeOnWindow();
              // testRead_n_write();
                break;
            }

            case 20: {
                // linux
                break;
            }

            default:

        }

    }

    public Interpreter(InputOutput o, String sTargetCode) {
        // loads the target code to b executed by d native machine
        o_IO = o;


    }

    private int NativePlatform() {
        int iNative = 10;  // defaults to windows

        return iNative;
    }

    private void testRead_n_write() {
        try {
            String s;
            String line = "i am a man";


            OutputStream stdin = null;
            InputStream stderr = null;
            InputStream stdout = null;



            Runtime oRun = Runtime.getRuntime();
            s = "perl.exe C:\\Perl\\hello.pl";
            s = "c:\\codes\\a";
            PExecutableProgram = (oRun.exec(s));

            stdin = PExecutableProgram.getOutputStream();
            stderr = PExecutableProgram.getErrorStream();
            stdout = PExecutableProgram.getInputStream();

            // "write" the parms into stdin
            //line = "param1" + "\n";
          //  stdin.write(line.getBytes());
           // stdin.flush();

            stdin.close();


            // clean up if any output in stdout
            BufferedReader brCleanUp =
                    new BufferedReader(new InputStreamReader(stdout));
            while ((line = brCleanUp.readLine()) != null) {
                System.out.println("[Stdout] " + line);
            }
            brCleanUp.close();




            this.o_IO.appendOutPutWindow("here biy\n");


        } catch (Exception ex) {
        }
    }

    private int executeOnWindow() {
        try {
            Runtime oRun = Runtime.getRuntime();

            String s = "cmd /C dir";
            s = "ping www.yahoo.com";
            s = "perl.exe C:\\Perl\\hello.pl";

            PExecutableProgram = (oRun.exec(s));

            // BufferedWriter buffOut = new BufferedWriter(new OutputStreamWriter(P.getOutputStream()));


            Streamclass oStrIn = new Streamclass(PExecutableProgram, 10, o_IO);
            Streamclass oStrOut = new Streamclass(PExecutableProgram, 20, o_IO);

           
            oStrOut.start();
            oStrOut.write("10");
           // oStrOut.flush();

             oStrOut.write("20");
          //  oStrOut.flush();


             oStrIn.start();
             
            PExecutableProgram.waitFor();

            oStrIn = null;
            oStrOut = null;


            /*
            BufferedReader buffIn = new BufferedReader(new InputStreamReader(PExecutableProgram.getInputStream()));

            String line = null;
            while ((line = buffIn.readLine()) != null) {
            o_IO.appendOutPutWindow(o_IO.readOutPutWindow() + line + "\n");
            }
             */
        } catch (Exception ex) {
        }

        return 0;
    }
}

class Streamclass extends Thread {

    private int iType;
    Process pExecutableProcess;
    InputOutput o_IO;
    BufferedReader buffIn;
    BufferedWriter buffOut;

    public Streamclass(Process p, int iTypeOfStream,
            InputOutput o) {
        iType = iTypeOfStream;
        pExecutableProcess = p;
        o_IO = o;
    }

    public void run() {


        switch (iType) {
            case 10:
                // inputstream reads from external process
                read();
                break;
            case 20:
                // outputstream ... writes to external process
                setup_write();
                break;

        }
    }

    private void setup_write() {
        try {

            buffOut = new BufferedWriter(new OutputStreamWriter(pExecutableProcess.getOutputStream()));

//System.out.println("dddd");

        } catch (Exception ex) {
        }
    }

    private void read() {
        try {

            buffIn = new BufferedReader(new InputStreamReader(pExecutableProcess.getInputStream()));
            String line = null;
            while (true) {
                if ((line = buffIn.readLine()) != null) {
                    o_IO.appendOutPutWindow(line + "\n");
                }
            } // loop forever...exits only when parent thread terminates this thread
        } catch (Exception ex) {
            // System.out.println(ex.getMessage());
        }
    }

    public void write(String s) {
        // would test this with a simple perl program
        try {
            OutputStream stdin = null;

            s = s + "\n";
           stdin =  pExecutableProcess.getOutputStream() ;
           stdin.write(s.getBytes());
           stdin.flush();
          // stdin.close();
          // stdin = null ;
         //   buffOut.write(s);
            //  buffOut.newLine();
        } catch (Exception ex) {
            // System.out.println(ex.getMessage());
        }
    }

    public void flush() {
    }
}
