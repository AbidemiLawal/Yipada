/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yipada;

import java.io.*;

public class Assembler {

    Process PExecutableProgram;
    InputOutput o_IO;

    public Assembler(InputOutput o, String sAppDir, String sPath2CompliedCode,
            String sTranslatedCodeDir) {
        try {

            o_IO = o; // points to the IO window
            o_IO.appendOutPutWindow("Assembling complied code...\n");

            Runtime oRun = Runtime.getRuntime();



            String sPath2ObjectFile = sTranslatedCodeDir + "\\compiled.o";

            //  sPath2CompliedCode = "C:\\k\\compiled.asm";
            //  sPath2ObjectFile = "C:\\k\\compiled.o" ;

            sPath2CompliedCode = "ycodes\\compiled.asm";
            sPath2ObjectFile = "ycodes\\compiled.o";

            String sAssembler = "NASM\\nasm.exe";

            // sAssembler = " nasm " ;
            // sAssembler = " nasm " ;

            String s = "";

            s = sAssembler + " -f win32 " + sPath2CompliedCode.trim() + " -o " + sPath2ObjectFile.trim();


            PExecutableProgram = (oRun.exec(s));


            BufferedReader buffIn = new BufferedReader(new InputStreamReader(PExecutableProgram.getInputStream()));
            String line = null;

            while ((line = buffIn.readLine()) != null) {
                o_IO.appendOutPutWindow(line + "\n");
            }

            buffIn.close();
            PExecutableProgram.waitFor();

            String linker = "yMinCW\\MinGW\\bin\\gcc.exe";

            // linker = "C:\\MinGW\\bin\\gcc.exe";

            String sExecutable = "ycodes\\compiled.exe";

            s = linker + "  " + sPath2ObjectFile.trim() + " -o " + sExecutable.trim();

            // System.out.println(" got here");

            PExecutableProgram = (oRun.exec(s));

            PExecutableProgram.waitFor();

            s = sExecutable;

            PExecutableProgram.destroy();

            s = "c:\\codes\\hello.exe";
            PExecutableProgram = (oRun.exec(s));

            buffIn = new BufferedReader(new InputStreamReader(PExecutableProgram.getInputStream()));
            line = null;

            while ((line = buffIn.readLine()) != null) {
                o_IO.appendOutPutWindow(line + "\n");
            }

            buffIn.close();


            PExecutableProgram.waitFor();


            s = linker + " " + sPath2ObjectFile + " -o " + sExecutable;

            System.out.println("\nCompiled File : " + sPath2CompliedCode);
            System.out.println("Assembled File :  " + sPath2ObjectFile);
            System.out.println("Command  :  " + s);



        } catch (Exception ex) {
            //System.out.println("here me..");
            System.out.println(ex.getMessage());
        }
    }

    public int assemble() {
        try {
        } catch (Exception ex) {
        }

        return 0;
    }
}
