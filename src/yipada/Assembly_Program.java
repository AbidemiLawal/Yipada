/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yipada;

import java.io.*;

public class Assembly_Program {

    ChildDataCallBack cd;
    private String sOutPutFile;  // generated code are stored in this file
    private String sExecutableFile;
    private String sText2Write2File;
    private SymbolTable oSymbolTable;
    private Three_Address_Program o3AddressProgram;
    private String sActiveClassName = "";
    private String sActiveMethodName = "";
    private String mAppDir = "";

    public Assembly_Program(SymbolTable oSymTable,
            Three_Address_Program o3AddyProgram) {

        oSymbolTable = oSymTable;
        o3AddressProgram = o3AddyProgram;
        sText2Write2File = "";

    }

    public void registerCallback(ChildDataCallBack cd){
        this.cd = cd ;
    }
    public boolean GenerateCode(String sAppDir) throws Exception {
        createAnOutputFile(sAppDir); // creates an output file for storing generated assembly code

        mAppDir = sAppDir;
        add_C_Header2_File(); // add header information to output file
        addglobalVariableDeclarations();
        add_C_Codes2File();
        addFooter2File(""); // add footer information to output file

        // System.out.println(sText2Write2File);

        System.out.println("Output file ::" + sOutPutFile);
        System.out.println("Executable : " + sExecutableFile);

        compiler.saveText2File(sText2Write2File, sOutPutFile);
/*
        Process PExecutableProgram;
        Runtime oRun = Runtime.getRuntime();

        String s = "g++ \"" + sOutPutFile + "\" -o \"" + sExecutableFile + "\"";

        PExecutableProgram = (oRun.exec(s));

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(PExecutableProgram.getInputStream()));
       
        ChildDataCallBack cd = new DataManager();
        this.registerCallback(cd);

        ReaderThread rt = new ReaderThread(reader , cd) ;

        rt.run() ;

        System.out.println("here with threading ..." + s);
*/
        return true;
    }

    private boolean add_C_Header2_File() {
        println("#include <iostream>");
        println("#include <stdlib.h>");
        println("using namespace std ;");

        createComparisionFlags();
        return true;
    }

    private boolean addglobalVariableDeclarations() {
        return true;
    }

    private boolean add_C_Codes2File() {

        Quadruple oQuadruple = null;
        String sCode = "";
        for (int i = 0; i < o3AddressProgram.SizeOf3AddressProgram(); i++) {
            oQuadruple = o3AddressProgram.getQuadrupleAtIndex(i);
            if (oQuadruple != null) {
                add_a_C_Code2File(oQuadruple, i);
            } else {
                System.out.println(i + 1 + "An invalid 3-address quadruple!!!"); // rare exception
                return false;

            }
        }

        return true;
    }

    private void createComparisionFlags() {

        println("bool _b_JMP_ZERO  ;");
        println("bool _b_JMP_POSITIVE ;");
        println("bool _b_JMP_NEGATIVE ; ");
        println("bool _b_JA ; ");
        println("bool _b_JAE ; ");
        println("bool _b_JB ; ");
        println("bool _b_JBE ; ");
        println("bool _b_JE ; ");
        println("bool _b_JNE ; ");

        println("void _reInitialiseComparisonFlags(){");
        println(" _b_JMP_ZERO = 1 ;");
        println(" _b_JMP_POSITIVE = 1 ;");
        println(" _b_JMP_NEGATIVE = 1 ;");
        println(" _b_JA = 1 ;");
        println(" _b_JAE = 1 ;");
        println(" _b_JB = 1 ;");
        println(" _b_JBE = 1 ;");
        println(" _b_JE = 1 ;");
        println(" _b_JNE = 1 ;");
        println("}");

    }

    private boolean add_a_C_Code2File(Quadruple oQuadruple, int iIndexOfCode) {
        // println("adding c code to file ..." + iIndexOfCode);
        ThreeAddressOperand o3AddressCodeOperand_1 = null;
        ThreeAddressOperand o3AddressCodeOperand_2 = null;
        boolean bReturn = true;

        if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_CLASS_DEF_BEGIN)) {
            //
            o3AddressCodeOperand_1 = oQuadruple.sArg_1; // class access modifier
            o3AddressCodeOperand_2 = oQuadruple.sArg_2; // class name
            String sClassName =
                    oSymbolTable.getSymbolFromIndex(
                    Integer.parseInt(o3AddressCodeOperand_2.getIdentification_or_SymbolTanleIndex()));
            sActiveClassName = sClassName;
            println("// class " + sActiveClassName + " { ");


        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_CLASS_DEF_END)) {
            //
            o3AddressCodeOperand_1 = oQuadruple.sArg_1; // class access modifier
            o3AddressCodeOperand_2 = oQuadruple.sArg_2; // class name
            String sClassName =
                    oSymbolTable.getSymbolFromIndex(
                    Integer.parseInt(o3AddressCodeOperand_2.getIdentification_or_SymbolTanleIndex()));
            println("//  " + " }  // end class " + sClassName);
            sActiveClassName = "";

        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_METHOD_DEF_BEGIN)) {
            //
            o3AddressCodeOperand_1 = oQuadruple.sArg_1; // method access modifier
            o3AddressCodeOperand_2 = oQuadruple.sArg_2; // method name
            String sMethodName =
                    oSymbolTable.getSymbolFromIndex(
                    Integer.parseInt(o3AddressCodeOperand_2.getIdentification_or_SymbolTanleIndex()));
            sActiveMethodName = sMethodName;
            println(o3AddressCodeOperand_1.getIdentification_or_SymbolTanleIndex()
                    + " " + sMethodName + "() {");
            addCode_4_temporaries_in_this_scope(sActiveClassName, sMethodName);

        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_METHOD_DEF_END)) {
            //
            o3AddressCodeOperand_1 = oQuadruple.sArg_1; // return type
            o3AddressCodeOperand_2 = oQuadruple.sArg_2; // method name
            String sMethodName =
                    oSymbolTable.getSymbolFromIndex(
                    Integer.parseInt(o3AddressCodeOperand_2.getIdentification_or_SymbolTanleIndex()));
            println("return "
                    + generate_random_return_value(
                    o3AddressCodeOperand_1.getIdentification_or_SymbolTanleIndex())
                    + " ; ");
            println("}  // end method " + sMethodName);
            sActiveMethodName = "";

        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_METHOD_FORMAL_PARAM)) {
            //
            o3AddressCodeOperand_1 = oQuadruple.sArg_1; // parameter : symboltableIndex
            int iSymbolIndexInTable =
                    Integer.parseInt(o3AddressCodeOperand_1.getIdentification_or_SymbolTanleIndex());
            String sParameterName = oSymbolTable.getSymbolFromIndex(iSymbolIndexInTable);
            int iParameterType = oSymbolTable.getDataTypeOfIdentifierFromSymbolTable(iSymbolIndexInTable);
            println(compiler.s_DataType(iParameterType) + " " + sParameterName + " ; ");

        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_VAR_DECLARATION)) {
            o3AddressCodeOperand_1 = oQuadruple.sArg_1; // parameter : symboltableIndex
            int iSymbolIndexInTable =
                    Integer.parseInt(o3AddressCodeOperand_1.getIdentification_or_SymbolTanleIndex());
            Symbol oSymbol =
                    oSymbolTable.getSymbolFromIndex_1(iSymbolIndexInTable);
            if (oSymbol.sContainingMethod() != null
                    & oSymbol.sContainingMethod().equals("") == false) {
                int iDataType = oSymbolTable.getDataTypeOfIdentifierFromSymbolTable(iSymbolIndexInTable);
                println(compiler.s_DataType(iDataType) + " "
                        + oSymbol.sSymbol + " ; ");
            }

        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_ADD)) {
            //
            o3AddressCodeOperand_1 = oQuadruple.sArg_1; // l-value
            o3AddressCodeOperand_2 = oQuadruple.sArg_2; // r-value
            translate_arithmeticExpression_2_C(compiler.s3AddressOperator_ADD,
                    o3AddressCodeOperand_1, o3AddressCodeOperand_2,
                    oQuadruple.iSymbolTableIndex);

        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_SUB)) {
            //
            o3AddressCodeOperand_1 = oQuadruple.sArg_1; // l-value
            o3AddressCodeOperand_2 = oQuadruple.sArg_2; // r-value
            translate_arithmeticExpression_2_C(compiler.s3AddressOperator_SUB,
                    o3AddressCodeOperand_1, o3AddressCodeOperand_2,
                    oQuadruple.iSymbolTableIndex);
        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_DIVIDE)) {
            //
            o3AddressCodeOperand_1 = oQuadruple.sArg_1; // l-value
            o3AddressCodeOperand_2 = oQuadruple.sArg_2; // r-value
            translate_arithmeticExpression_2_C(compiler.s3AddressOperator_DIVIDE,
                    o3AddressCodeOperand_1, o3AddressCodeOperand_2,
                    oQuadruple.iSymbolTableIndex);
        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_MULTIPLY)) {
            //
            o3AddressCodeOperand_1 = oQuadruple.sArg_1; // l-value
            o3AddressCodeOperand_2 = oQuadruple.sArg_2; // r-value
            translate_arithmeticExpression_2_C(compiler.s3AddressOperator_MULTIPLY,
                    o3AddressCodeOperand_1, o3AddressCodeOperand_2,
                    oQuadruple.iSymbolTableIndex);

        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_ASSIGN)) {
            //
            o3AddressCodeOperand_1 = oQuadruple.sArg_1; // l-value
            o3AddressCodeOperand_2 = oQuadruple.sArg_2; // r-value
            translate_assignment_2_C(o3AddressCodeOperand_1, o3AddressCodeOperand_2);

        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_MOV)) {
            //
            o3AddressCodeOperand_1 = oQuadruple.sArg_1; // l-value
            o3AddressCodeOperand_2 = oQuadruple.sArg_2; // r-value
            translate_assignment_2_C(o3AddressCodeOperand_1, o3AddressCodeOperand_2);

        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_JMP)) {
            //
            println("goto " + symbolMatching3AddressOperand(oQuadruple.sArg_1) + " ;");
        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_LABEL)) {
            //
            println(symbolMatching3AddressOperand(oQuadruple.sArg_1) + ":");
        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_CMP)) {
            //
            o3AddressCodeOperand_1 = oQuadruple.sArg_1; // operand-1
            o3AddressCodeOperand_2 = oQuadruple.sArg_2; // operand-2
            translate_CMP_2_C(o3AddressCodeOperand_1, o3AddressCodeOperand_2);
            compiler.reInitialiseComparisonFlags(); // clear comparison flags

        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_JE)) {
            //
            println(" if (_b_JE == 1) goto "
                    + symbolMatching3AddressOperand(oQuadruple.sArg_1) + " ;");
        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_JNE)) {
            //
            println(" if (_b_JNE == 1) goto "
                    + symbolMatching3AddressOperand(oQuadruple.sArg_1) + " ;");
        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_JMP_ZERO)) {
            //
            println(" if (_b_JMP_ZERO == 1) goto "
                    + symbolMatching3AddressOperand(oQuadruple.sArg_1) + " ;");
        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_JA)) {
            //
            println(" if (_b_JA == 1) goto "
                    + symbolMatching3AddressOperand(oQuadruple.sArg_1) + " ;");
        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_JB)) {
            //
            println(" if (_b_JB == 1) goto "
                    + symbolMatching3AddressOperand(oQuadruple.sArg_1) + " ;");
        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_JBE)) {
            //
            println(" if (_b_JBE == 1) goto "
                    + symbolMatching3AddressOperand(oQuadruple.sArg_1) + " ;");
        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_JAE)) {
            //
            println(" if (_b_JAE == 1) goto "
                    + symbolMatching3AddressOperand(oQuadruple.sArg_1) + " ;");
        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_PRINTCONSOLE)) {
            //
            o3AddressCodeOperand_1 = oQuadruple.sArg_1; // print argument
            translate_PrintConsole_2_C(o3AddressCodeOperand_1);
        } else if (oQuadruple.sOpCode.trim().equals(compiler.s3AddressOperator_READCONSOLE)) {
            //
            o3AddressCodeOperand_1 = oQuadruple.sArg_1; // read argument : identifier
            translate_ReadConsole_2_C(o3AddressCodeOperand_1);
        } else {
            System.out.println("************Unhandled Op-Code in 3-address code!!!");
            bReturn = false;
        }
        o3AddressCodeOperand_1 = null;
        o3AddressCodeOperand_2 = null;

        return true;

    }

    private String generate_random_return_value(String sReturnType) {
        if (sReturnType.trim().equals("int")) {
            return "0";
        } else if (sReturnType.trim().equals("float")) {
            return "0.0";
        } else if (sReturnType.trim().equals("bool")) {
            return "1";
        } else {
            return "";
        }

    }

    private void addCode_4_temporaries_in_this_scope(String sActiveClassName,
            String sActiveMethodName) {
        Symbol oSymbol;
        for (int i = 0; i <= oSymbolTable.getIndexOfLastSymbolAdded2Table(); i++) {
            oSymbol = oSymbolTable.getSymbolFromIndex_1(i);
            if (oSymbol.sclass_block.equals(sActiveClassName)
                    && oSymbol.sMethod_block.equals(sActiveMethodName)) {
                // symbol is in this scope...
                if (oSymbol.bIsAFreeStoreSymbol) {
                    // a free store symbol in this scope
                    int iDataType = oSymbolTable.getDataTypeOfIdentifierFromSymbolTable(i);
                    println(compiler.s_DataType(iDataType) + " "
                            + oSymbol.sSymbol + " ; ");
                }
            }
        }

    }

    private void translate_ReadConsole_2_C(ThreeAddressOperand o3AddressCodeOperand) {
        // argument is always an identifier

        int iIndexOfSymbolInTable =
                Integer.parseInt(o3AddressCodeOperand.getIdentification_or_SymbolTanleIndex());
        Symbol oSymbol = oSymbolTable.getSymbolFromIndex_1(iIndexOfSymbolInTable);
        int iDataTypeOfSymbol = oSymbolTable.getDataTypeOfIdentifierFromSymbolTable(iIndexOfSymbolInTable);
        println("cin>>" + oSymbol.sSymbol + ";");

    }

    private void translate_arithmeticExpression_2_C(String sArithMeticOperator,
            ThreeAddressOperand o3AddressCodeOperand_1,
            ThreeAddressOperand o3AddressCodeOperand_2,
            int iSymbolTableIndex4Result) {

        String sSymbol = oSymbolTable.getSymbolFromIndex(
                iSymbolTableIndex4Result);
        String sOperand_1 = symbolMatching3AddressOperand(
                o3AddressCodeOperand_1);
        String sOperand_2 = symbolMatching3AddressOperand(
                o3AddressCodeOperand_2);
        if (sArithMeticOperator.trim().equals(compiler.s3AddressOperator_ADD)) {
            println(sSymbol + "=" + sOperand_1 + "+" + sOperand_2 + ";");
        } else if (sArithMeticOperator.trim().equals(compiler.s3AddressOperator_SUB)) {
            println(sSymbol + "=" + sOperand_1 + "-" + sOperand_2 + ";");
        } else if (sArithMeticOperator.trim().equals(compiler.s3AddressOperator_MULTIPLY)) {
            println(sSymbol + "=" + sOperand_1 + "*" + sOperand_2 + ";");
        } else if (sArithMeticOperator.trim().equals(compiler.s3AddressOperator_DIVIDE)) {
            println(sSymbol + "=" + sOperand_1 + "/" + sOperand_2 + ";");
        }

    }

    private void translate_CMP_2_C(ThreeAddressOperand o3AddressCodeOperand_1,
            ThreeAddressOperand o3AddressCodeOperand_2) {
        //  System.out.println("translating comparision operation ...");

        String operand_1 = symbolMatching3AddressOperand(o3AddressCodeOperand_1);
        String operand_2 = symbolMatching3AddressOperand(o3AddressCodeOperand_2);

        println("_reInitialiseComparisonFlags();");

        println("if (" + operand_1 + "<" + operand_2 + ")");
        println("_b_JAE = 0 ; // not >=");

        println("if (" + operand_1 + " <= " + operand_2 + ")");
        println("_b_JA = 0 ;  // not >");

        println("if (" + operand_1 + " > " + operand_2 + ")");
        println("_b_JBE = 0 ;  // not <=");

        println("if (" + operand_1 + " >= " + operand_2 + ")");
        println("_b_JB = 0 ;  // not <");

        println("if (" + operand_1 + " == " + operand_2 + ")");
        println("_b_JNE = 0 ;  // not !=");

        println("if (" + operand_1 + " != " + operand_2 + ")");
        println("_b_JE = 0 ;  // not ==");


    }

    private void translate_assignment_2_C(ThreeAddressOperand o3AddressCodeOperand_1,
            ThreeAddressOperand o3AddressCodeOperand_2) {
        String oLValueSymbol = oSymbolTable.getSymbolFromIndex(
                Integer.parseInt(o3AddressCodeOperand_1.getIdentification_or_SymbolTanleIndex()));
        String sRValueSymbol = symbolMatching3AddressOperand(o3AddressCodeOperand_2);

        println(oLValueSymbol + "=" + sRValueSymbol + ";");
    }

    private String symbolMatching3AddressOperand(ThreeAddressOperand o3AddressCodeOperand) {

        String sSymbol = "";
        if (o3AddressCodeOperand.getType() == compiler.iTypeOfAstNode_Identifier
                || o3AddressCodeOperand.getType() == compiler.iTypeOfAstNode_FormalParameter) {
            //
            Symbol oSymbol = this.oSymbolTable.getSymbolFromIndex_1(
                    Integer.parseInt(o3AddressCodeOperand.getIdentification_or_SymbolTanleIndex()));
            sSymbol = oSymbol.sSymbol;

        } else if (o3AddressCodeOperand.getType() == compiler.iTypeOfAstNode_StringLiteral) {
            //
            sSymbol = o3AddressCodeOperand.getIdentification_or_SymbolTanleIndex();

        } else if (o3AddressCodeOperand.getType() == compiler.iTypeOfAstNode_NumberLiteral) {
            //
            sSymbol = o3AddressCodeOperand.getIdentification_or_SymbolTanleIndex();
        } else if (o3AddressCodeOperand.getType() == compiler.iTypeOfAstNode_BooleanLiteral) {
            //
            sSymbol = o3AddressCodeOperand.getIdentification_or_SymbolTanleIndex();
        } else if (o3AddressCodeOperand.getType() == compiler.iTypeOfAstNode_BinaryOperator) {
            Symbol oSymbol = oSymbolTable.getSymbolFromIndex_1(
                    Integer.parseInt(o3AddressCodeOperand.getIdentification_or_SymbolTanleIndex()));
            sSymbol = oSymbol.sSymbol;
        } else if (o3AddressCodeOperand.getOpCode().equals(compiler.s3AddressOperator_NULL)) {
            if (o3AddressCodeOperand.getType() == -1) {
                sSymbol = o3AddressCodeOperand.getIdentification_or_SymbolTanleIndex();
            }
            if (o3AddressCodeOperand.getType() == compiler.iTypeOfAstNode_Identifier) {
                sSymbol = oSymbolTable.getSymbolFromIndex_1(
                        Integer.parseInt(o3AddressCodeOperand.getIdentification_or_SymbolTanleIndex())).sSymbol;
            }
            if (o3AddressCodeOperand.getType() == compiler.iTypeOfAstNode_FormalParameter) {
                sSymbol = oSymbolTable.getSymbolFromIndex_1(
                        Integer.parseInt(o3AddressCodeOperand.getIdentification_or_SymbolTanleIndex())).sSymbol;
            } else {  // a literal 
                sSymbol = o3AddressCodeOperand.getIdentification_or_SymbolTanleIndex();
            }
        }
        return sSymbol;
    }

    private void translate_PrintConsole_2_C(
            ThreeAddressOperand o3AddressCodeOperand) {
        String sSymbol = symbolMatching3AddressOperand(o3AddressCodeOperand);
        println("cout<<" + sSymbol + "<<\"\\n\";");

    }

    private void addFooter2File(String sFooterInfo) {
    }

    private void print(String sText) {
        System.out.print(sText); // text would b added to .asm file
    }

    private void println(String sText) {
        // System.out.println(sText); // text would b added to .asm file

        sText2Write2File = sText2Write2File + sText + "\n";
    }

    private boolean createAnOutputFile(String sAppDir) {
        sOutPutFile = sAppDir + "\\compiledGrammar\\ypadaTarget_.cpp";  // name would b created dynamically
        sExecutableFile = sAppDir + "\\compiledGrammar\\ypadaTarget_";
        return true;
    }
}
