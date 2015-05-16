/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yipada;

import java.io.*;
import goldengine.java.*;

public class compiler implements GPMessageConstants {

    /*********************************************************/
    static boolean iCompiler_ScanningOK = false;
    static boolean iCompiler_ParsingOK = false;
    /*********************************************************/
    final static int iTypeOfToken_Identifier = 10;
    final static int iTypeOfToken_StringLiteral = 11;
    final static int iTypeOfToken_NumberLiteral = 12;
    final static int iTypeOfToken_BooleanLiteral = 13; // true and false
    final static int iTypeOfToken_ClassName = 14;
    final static int iTypeOfToken_MethodName = 15;
    /**********************************************************/
    final static int iTypeOfAstNode_Identifier = 30;
    final static int iTypeOfAstNode_StringLiteral = 31;
    final static int iTypeOfAstNode_NumberLiteral = 32;
    final static int iTypeOfAstNode_BooleanLiteral = 33;
    final static int iTypeOfAstNode_FormalParameter = 34;
    /*********************************************************/
    /********************************************************/
    final static int iTypeOfAstNode_UnaryOperator = 50;
    final static int iTypeOfAstNode_BinaryOperator = 51;
    final static int iTypeOfAstNode_TernaryOperator = 52;
    final static int iTypeOfAstNode_NaryOperator = 53;
    final static int iTypeOfAstNode_NullOperator = 54;
    /*********************************************************/
    /*********************************************************/
    final static int iDataType_void = 60;
    final static int iDataType_integer = 61;
    final static int iDataType_long = 62;
    final static int iDataType_float = 63;
    final static int iDataType_boolean = 64;
    final static int iDataType_string = 65;
    final static int iDataType_error = 66;
    /*********************************************************/
    final static int i3AddressOperand_Identifier = 80;
    final static int i3AddressOperand_StringLiteral = 81;
    final static int i3AddressOperand_NumberLiteral = 82;
    final static int i3AddressOperand_BooleanLiteral = 83;
    final static int i3AddressOperand_Null = 84;
    /*********************************************************/
    final static String s3AddressOperator_VAR_DECLARATION = "VAR-DECLARATION";  // class definition
    final static String s3AddressOperator_CLASS_DEF_BEGIN = "CLASS-DEF-BEGIN";  // class definition
    final static String s3AddressOperator_CLASS_DEF_END = "CLASS-DEF-END";  // class definition
    final static String s3AddressOperator_METHOD_DEF_BEGIN = "METHOD-DEF-BEGIN";  // method definition
    final static String s3AddressOperator_METHOD_DEF_END = "METHOD-DEF-END";  // method definition
    /************************************************************/
    final static String s3AddressOperator_METHOD_FORMAL_PARAM = "METHOD-FORMAL-PARAM";  // Parameter definition
    /*********************************************************/
    final static String s3AddressOperator_NULL = "";  // a null operator does nothing
    final static String s3AddressOperator_LABEL = "LABEL";  // a null operator does nothing
    final static String s3AddressOperator_ASSIGN = "ASSIGN";
    final static String s3AddressOperator_MODULO = "MODULO";
    final static String s3AddressOperator_ADD = "ADD";
    final static String s3AddressOperator_SUB = "SUB";
    final static String s3AddressOperator_MULTIPLY = "MULTIPLY";
    final static String s3AddressOperator_DIVIDE = "DIVIDE";
    /**********************************************************/
    final static String s3AddressOperator_MOV = "MOV";
    /**********************************************************/
    final static String s3AddressOperator_PRINTCONSOLE = "PRINTCONSOLE";
    final static String s3AddressOperator_READCONSOLE = "READCONSOLE";
    /**********************************************************/
    final static String s3AddressOperator_OR = "OR";
    final static String s3AddressOperator_AND = "AND";
    /**********************************************************/
    final static String s3AddressOperator_LESS = "<";
    final static String s3AddressOperator_LESSEQUAL = "<=";
    final static String s3AddressOperator_GREATER = ">";
    final static String s3AddressOperator_GREATEREQUAL = ">=";
    final static String s3AddressOperator_EQUAL = "==";
    final static String s3AddressOperator_NOTEQUAL = "!=";
    /***********************************************************/
    final static String s3AddressOperator_CMP = "CMP";
    /***********************************************************/
    final static String s3AddressOperator_JMP_ZERO = "JZ";  // jump on zero
    final static String s3AddressOperator_JMP_POSITIVE = "JP";  // jump on positive
    final static String s3AddressOperator_JMP_NEGATIVE = "JN"; // jump on negative
    final static String s3AddressOperator_JMP = "JMP";  // unconditional jump
    final static String s3AddressOperator_JA = "JA";  // jump on above
    final static String s3AddressOperator_JAE = "JAE";  // jump on above or equal
    final static String s3AddressOperator_JB = "JB";  // jump on below
    final static String s3AddressOperator_JBE = "JBE"; // jump on below or equal
    final static String s3AddressOperator_JE = "JE"; // jump on equal
    final static String s3AddressOperator_JNE = "JNE";  // jump on not equal
    /********************************************************/
    // /***************************************************************
    static boolean b_JMP_ZERO = false;  // jump on zero
    static boolean b_JMP_POSITIVE = false;  // jump on positive
    static boolean b_JMP_NEGATIVE = false; // jump on negative
    static boolean b_JMP = false;  // unconditional jump
    static boolean b_JA = false;  // jump on above
    static boolean b_JAE = false;  // jump on above or equal
    static boolean b_JB = false;  // jump on below
    static boolean b_JBE = false; // jump on below or equal
    static boolean b_JE = false; // jump on equal
    static boolean b_JNE = false;  // jump on not equal
    GOLDParser oParser;
    SymbolTable oSymbolTable;
    Stack_of_Tokens oStackOfTokens; // tokens read during scanning are temporarily stored here
    AstTree oAST;
    Three_Address_Program oThreeAddressCoder;
    Assembly_Program oAssemblyProgram;
    /*********************************************************/
    String sTypeName_of_defined_object = "";
    String sTokenReadAhead = "";
    InputOutput o_IO;
    String sPath2CompiledCode = null;
    String sAppRunningDirectory = null;
    String sLastTokenRead = "";
    Node Var_declaration_node = null;
    Node Basic_Types_node = null;
    Node Statement_Assign_node = null;
    Node Statement_node = null;  //  while,if-then, if-then-else,print,read,assign
    Node Expression_node = null; //
    Node Add_Exp_node = null;
    Node Mult_Exp_node = null;
    Node Negate_Exp_node = null;
    Node Value_node = null;
    Node Class_definition_node = null;
    Node Method_definition_node = null;
    Node Parameter_List_node = null;
    Node Parameter_node = null;
    Node Return_type_node = null;
    Node Access_modifier_node = null;

    private String createUniqueName4CompiledCode() {
        sPath2CompiledCode = sAppRunningDirectory + "\\ycodes\\compiled.asm";
        return sPath2CompiledCode;
    }

    public String getPath2CompiledCode() {
        return sPath2CompiledCode;
    }

    private void Rule_Program(Reduction myRed, String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (sRuleDefinition.trim().contains("Var-declarations")) {
                //<Program> ::=<Var-declarations><Class-Definitions>
                oAST.addOperatorNode("program", compiler.iTypeOfAstNode_BinaryOperator, sCurrentLineNumber); // var-declarations followed by class-definitions
            }

        } catch (Exception ex) {
        }
    }

    private void Rule_Var_declarations(Reduction myRed, String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            //System.out.println("is rule " + myRed.getParentRule().getText());

            if (sRuleDefinition.trim().contains("Var-declarations")) {
                //<Var-declarations> ::=<Var-declaration><Var-declarations>
                oAST.addOperatorNode("var-declarations", compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber); // 2 or more var-declaration
            }

        } catch (Exception ex) {
        }
    }

    private boolean Rule_Var_declaration(Reduction myRed, Stack_of_Tokens oStackOfTokens,
            SymbolTable oSymbolTable,
            String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            String sVariable = oStackOfTokens.pop();
            // System.out.println(sRuleDefinition + " " + sVariable);
            Symbol oSym = new Symbol();
            if (!oSymbolTable.addSymbol(sVariable, sCurrentLineNumber,
                    sTypeName_of_defined_object,
                    Integer.toString(compiler.iTypeOfToken_Identifier), oSym)) {
                // a new symbol is added to the symbol table... keep information
                this.o_IO.appendOutPutWindow("Line " + sCurrentLineNumber
                        + ": Multiple declarations of " + sVariable + " in current scope");
                return false;
            } else {
                oAST.addleaf(Integer.toString(oSym.iIndexInSymbolTable),
                        compiler.iTypeOfAstNode_Identifier);
                oAST.addOperatorNode("var-declaration", compiler.iTypeOfAstNode_UnaryOperator,
                        sCurrentLineNumber);
            }

            return true;

        } catch (Exception ex) {
            return false;
        }
    }

    private void Rule_Basic_Types(Reduction myRed) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (sRuleDefinition.trim().contains("int")) {
                //<Basic-Types> ::=int
                sTypeName_of_defined_object = Integer.toString(compiler.iDataType_integer);
            } else if (sRuleDefinition.trim().contains("float")) { // <Basic-Types> ::=float
                sTypeName_of_defined_object = Integer.toString(compiler.iDataType_float);
            } else if (sRuleDefinition.trim().contains("string")) { // <Basic-Types> ::=string
                sTypeName_of_defined_object = Integer.toString(compiler.iDataType_string);
            } else if (sRuleDefinition.trim().contains("bool")) { // <Basic-Types> ::=boolean
                sTypeName_of_defined_object = Integer.toString(compiler.iDataType_boolean);
            }
        } catch (Exception ex) {
        }
    }

    private void Rule_Class_Definitions(Reduction myRed, String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            //  System.out.println("class definitions !!!");
            if (sRuleDefinition.trim().contains("Class-Definitions")) { // <Class-Definitions> ::=<Class-definition><Class-Definitions>
                // System.out.println("1-");
            } else { // <Class-Definitions> ::=<Class-definition>
            }
            oAST.addOperatorNode("class-definitions", compiler.iTypeOfAstNode_BinaryOperator,
                    sCurrentLineNumber); // one or more class-definition
        } catch (Exception ex) {
        }
    }

    private void Rule_Statements(Reduction myRed, String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (sRuleDefinition.trim().contains("Statements")) {
                //<Statements> ::=<Statement><Statements>
                // System.out.println("<stmt> <stmts>");
            } else { // <Statements> ::=<Statement>
            }
            oAST.addOperatorNode("statements", compiler.iTypeOfAstNode_BinaryOperator,
                    sCurrentLineNumber); // one or more statement
        } catch (Exception ex) {
        }
    }

    private boolean Rule_Statement(Reduction myRed, Stack_of_Tokens oStackOfTokens,
            SymbolTable oSymbolTable,
            String sCurrentLineNumber,
            AstTree oAST) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (sRuleDefinition.trim().contains("=")) {
                //<Statement> ::= Id = <Expression> ;
                String sLvalue = oStackOfTokens.pop();
                Symbol oSym = new Symbol();
                if (!oSymbolTable.existInTable(sLvalue, oSym)) {
                    o_IO.appendOutPutWindow("Line " + sCurrentLineNumber
                            + ": Undeclared lvalue :" + sLvalue);
                    return false;
                } else {
                    oAST.addleaf(Integer.toString(oSym.iIndexInSymbolTable),
                            compiler.iTypeOfAstNode_Identifier);
                    oAST.addOperatorNode(":=", compiler.iTypeOfAstNode_BinaryOperator,
                            sCurrentLineNumber);
                }
            } else if (sRuleDefinition.trim().contains("while")) {
                //<Statement> ::= while ( <Expression>) {<Statements>}
                oAST.addOperatorNode("while", compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber);
            } else if (sRuleDefinition.trim().contains("else")) {
                //<Statement> ::= if ( <Expression>) {<Statements>} else {<Statements> }
                oAST.addOperatorNode("if-then-else-endif", compiler.iTypeOfAstNode_TernaryOperator,
                        sCurrentLineNumber);
            } else if (sRuleDefinition.trim().contains("if")) {
                //<Statement> ::= if ( <Expression>) {<Statements>}
                oAST.addOperatorNode("if-then-endif", compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber);
            } else { // <Statements> ::=<Statement>
                Debug.println("Unhandled Statement Type ????");
                return false;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private void Rule_Print_statement(Reduction myRed,
            AstTree oAST, String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (sRuleDefinition.trim().contains("Console.WriteLine")) {
                //<Print-statement> ::= Console.WriteLine <Expression>
                oAST.addOperatorNode("console.writeline", compiler.iTypeOfAstNode_UnaryOperator,
                        sCurrentLineNumber);
            } else { // <Statements> ::=<Statement>
                //
            }
        } catch (Exception ex) {
        }
    }

    private boolean Rule_Read_statement(Reduction myRed, Stack_of_Tokens oStackOfTokens,
            String sCurrentLineNumber,
            AstTree oAST) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (sRuleDefinition.trim().contains("Console.ReadLine")) {
                //<Read-statement> ::= Console.ReadLine Id;
                // System.out.println("Read Statement::" + oStackOfTokens.pop());
                String sIdentifier = oStackOfTokens.pop();
                Symbol oSym = new Symbol();
                if (!oSymbolTable.existInTable(sIdentifier, oSym)) {
                    this.o_IO.appendOutPutWindow("Line " + sCurrentLineNumber
                            + ": Undefined occurrence of [" + sIdentifier
                            + "] in current scope!");
                    return false;
                } else {
                    oAST.addleaf(Integer.toString(oSym.iIndexInSymbolTable),
                            compiler.iTypeOfAstNode_Identifier);
                }
                oAST.addOperatorNode("console.readline", compiler.iTypeOfAstNode_UnaryOperator,
                        sCurrentLineNumber);
            } else { // exception
                //
                return false;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private void Rule_Logic_Exp(Reduction myRed, AstTree oAST, String sCurrentLineNumber) {
        try {

            String sRuleDefinition = myRed.getParentRule().definition();
            if (sRuleDefinition.trim().indexOf("&&") != -1) {
                //<Logic-Exp> ::= <Logic-Exp> && <Expression>
                oAST.addOperatorNode("&&", compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber);
            } else if (sRuleDefinition.trim().indexOf("'|''|'") != -1) {
                //<Logic-Exp> ::= <Logic-Exp> || <Expression>
                oAST.addOperatorNode("||", compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber);
            } else {
                Debug.println("Parser : Reduction with <Logic-Exp> not well handled!");
                // System.out.println(sRuleDefinition);
            }
        } catch (Exception ex) {
        }
    }

    private void Rule_ArithMetic_Exp(Reduction myRed, AstTree oAST, String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (myRed.getParentRule().getSymbols(1).getName().trim().indexOf("%") != -1) {
                //<ArithMetic Exp> ::= <ArithMetic Exp> %  <Add Exp>
                oAST.addOperatorNode("%", compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber);
            } else { // exception
                Debug.println("Parser : Reduction with <ArithMetic Exp> not well handled!");
            }

        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <ArithMetic Exp> failed!");
        }
    }

    private void Rule_Expression(Reduction myRed, AstTree oAST, String sCurrentLineNumber) {
        try {

            String sRuleDefinition = myRed.getParentRule().definition();
            if (sRuleDefinition.trim().indexOf(">=") != -1) {
                //<Expression> ::= <Expression> >= <Add Exp>
                oAST.addOperatorNode(">=", compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber);
            } else if (sRuleDefinition.trim().indexOf("<=") != -1) {
                //<Expression> ::= <Expression> <= <Add Exp>
                oAST.addOperatorNode("<=", compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber);
            } else if (sRuleDefinition.trim().indexOf("==") != -1) {
                //<Expression> ::= <Expression> == <Add Exp>
                oAST.addOperatorNode("==", compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber);
            } else if (sRuleDefinition.trim().indexOf("!=") != -1) {
                //<Expression> ::= <Expression> != <Add Exp>
                oAST.addOperatorNode("!=", compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber);
            } else if (myRed.getParentRule().getSymbols(1).getName().trim().indexOf(">") != -1) {
                //<Expression> ::= <Expression> > <Add Exp>
                oAST.addOperatorNode(">", compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber);
            } else if (myRed.getParentRule().getSymbols(1).getName().trim().indexOf("<") != -1) {
                //<Expression> ::= <Expression> < <Add Exp>
                oAST.addOperatorNode("<", compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber);
            } else { // exception
                Debug.println("Parser : Reduction with <Expression> not well handled!");
            }

        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <Expression> failed!");
        }
    }

    private void Rule_Add_Exp(Reduction myRed, AstTree oAST, String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (myRed.getParentRule().getSymbols(1).getName().trim().indexOf("+") != -1) {
                //<Add Exp> ::= <Add Exp> +  <Mult Exp>
                //System.out.println("a+b");
                oAST.addOperatorNode("+", compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber);
            } else if (myRed.getParentRule().getSymbols(1).getName().trim().indexOf("-") != -1) {
                //<Add Exp> ::= <Add Exp> - <Mult Exp>
                //System.out.println("a<b");
                oAST.addOperatorNode("-", compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber);
            } else { // exception
                Debug.println("Parser : Reduction with <Add Exp> not well handled!");
            }

        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <Add Exp> failed!");
        }
    }

    private void Rule_Mult_Exp(Reduction myRed, AstTree oAST, String sCurrentLineNumber) {
        try {
            // String sRuleDefinition = myRed.getParentRule().definition();
            if (myRed.getParentRule().getSymbols(1).getName().trim().indexOf("*") != -1) {
                //<Mult Exp> ::= <Mult Exp> *  <Negate Exp>
                // System.out.println("a * b");
                oAST.addOperatorNode("*", compiler.iTypeOfAstNode_BinaryOperator, sCurrentLineNumber);
            } else if (myRed.getParentRule().getSymbols(1).getName().trim().indexOf("/") != -1) {
                //<Mult Exp> ::= <Mult Exp> - <Negate Exp>
                // System.out.println("a / b");
                oAST.addOperatorNode("/", compiler.iTypeOfAstNode_BinaryOperator, sCurrentLineNumber);
            } else { // exception
                Debug.println("Parser : Reduction with <Mult Exp> not well handled!");
            }

        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <Mult Exp> failed!");
        }
    }

    private void Rule_Unary_Exp(Reduction myRed, AstTree oAST, String sCurrentLineNumber) {
        try {
            if (myRed.getParentRule().getSymbols(0).getName().trim().indexOf("--") != -1) {
                //<Unary Exp> ::= --<Unary Exp>
                oAST.addOperatorNode("--", compiler.iTypeOfAstNode_UnaryOperator,
                        sCurrentLineNumber);
            } else if (myRed.getParentRule().getSymbols(0).getName().trim().indexOf("++") != -1) {
                //<Unary Exp> ::= ++ <Unary Exp>

                oAST.addOperatorNode("++", compiler.iTypeOfAstNode_UnaryOperator,
                        sCurrentLineNumber);
            } else { // exception
                Debug.println("Parser : Reduction with <Unary Exp> not well handled!");
            }

        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <Unary Exp> failed!");
        }
    }

    private boolean Rule_Value(Reduction myRed, Stack_of_Tokens oStackOfTokens,
            SymbolTable oSymbolTable,
            String sCurrentLineNumber,
            AstTree oAST) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (myRed.getParentRule().getSymbols(0).getName().trim().indexOf("Id") != -1) {
                //<Value> ::= Id
                String sIdentifier = oStackOfTokens.pop();
                Symbol oSym = new Symbol();
                if (sIdentifier.trim().equals("true")) {
                    // System.out.println("boolean literal found!" + sIdentifier);
                    oAST.addleaf("1",
                            compiler.iTypeOfAstNode_BooleanLiteral);

                } else if (sIdentifier.trim().equals("false")) {
                    // System.out.println("boolean literal found!" + sIdentifier);
                    oAST.addleaf("0",
                            compiler.iTypeOfAstNode_BooleanLiteral);

                } else if (!oSymbolTable.existInTable(sIdentifier, oSym)) {
                    this.o_IO.appendOutPutWindow("Line " + sCurrentLineNumber
                            + ": Undefined occurrence of [" + sIdentifier
                            + "] in current scope!");
                    return false;
                } else {
                    oAST.addleaf(Integer.toString(oSym.iIndexInSymbolTable),
                            compiler.iTypeOfAstNode_Identifier);
                }

            } else if (myRed.getParentRule().getSymbols(0).getName().trim().indexOf("StringLiteral") != -1) {
                //<Value> ::= StringLiteral
                String StringLiteral = oStackOfTokens.pop();
                oAST.addleaf(StringLiteral,
                        compiler.iTypeOfAstNode_StringLiteral);
            } else if (myRed.getParentRule().getSymbols(0).getName().trim().indexOf("NumberLiteral") != -1) {
                //<Value> ::= NumberLiteral
                String sNumLiteral = oStackOfTokens.pop();
                oAST.addleaf(sNumLiteral,
                        compiler.iTypeOfAstNode_NumberLiteral);

            } else if (myRed.getParentRule().getSymbols(0).getName().trim().indexOf("(") != -1) {
                //<Value> ::= (<Expression>)               
            } else { // exception
                Debug.println("Parser : Reduction with <Value> not well handled!");
                return false;
            }
            return true;
        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <Value> failed!");
            return false;
        }
    }

    private void Rule_Class_definition(Reduction myRed, Stack_of_Tokens oStackOfTokens,
            String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            //<Class-definition> ::= <Access-Modifier> class Id { <Class-body> }
            oAST.addOperatorNode("class-definition", compiler.iTypeOfAstNode_TernaryOperator,
                    sCurrentLineNumber); //
        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <Class-definition> failed!");
        }
    }

    private boolean Rule_Class_def_name(Reduction myRed, Stack_of_Tokens oStackOfTokens,
            SymbolTable oSymbolTable,
            String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (myRed.getParentRule().getSymbols(0).getName().trim().indexOf("Id") != -1) {
                //<Class-def-name> ::= Id
                String sClassName = oStackOfTokens.pop();
                Symbol oSym = new Symbol();
                oSymbolTable.setActiveClass("");
                oSymbolTable.setActiveMethod("");
                if (!oSymbolTable.addSymbol(sClassName, sCurrentLineNumber,
                        Integer.toString(compiler.iDataType_void),
                        Integer.toString(compiler.iTypeOfToken_ClassName),
                        oSym)) {
                    this.o_IO.appendOutPutWindow("??Class definition : Name already defined in this scope!"
                            + sClassName);
                    return false;
                } else {
                    oSymbolTable.setAsClass(oSym.iIndexInSymbolTable);
                    oAST.addleaf(Integer.toString(oSym.iIndexInSymbolTable),
                            compiler.iTypeOfAstNode_Identifier);
                }
                oSymbolTable.setActiveClass(sClassName);
            } else { // exception
                Debug.println("Parser : Reduction with <Class-def-name> not well handled!");
                return false;
            }
            return true;
        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <Class-def-name> failed!");
            return false;
        }
    }

    private void Rule_Class_body(Reduction myRed, String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (sRuleDefinition.trim().contains("Var-declarations")
                    && sRuleDefinition.trim().contains("Method-Definitions")) {
                //<Class-body> ::= <Var-declarations> <Method-Definitions>
                oAST.addOperatorNode("class-body",
                        compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber); // one or more statement

            } else { // exception
                Debug.println("Parser : Reduction with <Class-body> not well handled!");
            }
        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <Class-body> failed!");
        }

    }

    private void Rule_Method_definitions(Reduction myRed, String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (sRuleDefinition.trim().contains("Method-Definition")
                    && sRuleDefinition.trim().contains("Method-Definitions")) {
                //<Class-body> ::= <Var-declarations> <Method-Definitions>
                // System.out.println("method definitions : method-define || method-defines");
            } else { // exception
                Debug.println("Parser : Reduction with <Method-Definitions> not well handled!");
            }
            oAST.addOperatorNode("method-definitions", compiler.iTypeOfAstNode_BinaryOperator,
                    sCurrentLineNumber); // 1 or 2 method definition
        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <Method-Definitions> failed!");
        }

    }

    private void Rule_Method_definition(Reduction myRed, Stack_of_Tokens oStackOfTokens,
            String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            //<Method-Definition> ::= <Access-Modifier> <Return-Type> Id <Parameter-List> ')' '{ <Statements> '}'
            // System.out.println("method definition OK!");
            oAST.addOperatorNode("method-definition", compiler.iTypeOfAstNode_NaryOperator,
                    sCurrentLineNumber); // 5 operands
        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <Method-Definition> failed!");
        }
    }

    private boolean Rule_Method_def_name(Reduction myRed, Stack_of_Tokens oStackOfTokens,
            SymbolTable oSymbolTable,
            String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (sRuleDefinition.trim().contains("Id")) {
                //<Method-Def-name> ::= ID
                String sMethodName = oStackOfTokens.pop();
                Symbol oSym = new Symbol();
                oSymbolTable.setActiveMethod(""); // a new method found...so re-initialize active methodname
                if (!oSymbolTable.addSymbol(sMethodName, sCurrentLineNumber,
                        Integer.toString(compiler.iDataType_void), Integer.toString(compiler.iTypeOfToken_MethodName),
                        oSym)) {
                    System.out.println("Method Definition : Name already used in this scope!");
                    return false;
                } else {
                    oSymbolTable.setAsMethod(oSym.iIndexInSymbolTable);
                    oAST.addleaf(Integer.toString(oSym.iIndexInSymbolTable),
                            compiler.iTypeOfAstNode_Identifier);
                }
                oSymbolTable.setActiveMethod(sMethodName);
                return true;
            } else { // exception
                Debug.println("Parser : Reduction with <Method-Def-name> not well handled!");
                return false;
            }
        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <Method-Def-name> failed!");
            return false;
        }
    }

    private void Rule_Parameter_List(Reduction myRed, Stack_of_Tokens oStackOfTokens,
            String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (sRuleDefinition.trim().contains("Parameter-List")) {
                //<Parameter-List> ::= <Parameter> ,  <Parameter-List>
                // System.out.println("param-list : param || ,|| params");
                oAST.addOperatorNode("param-list", compiler.iTypeOfAstNode_BinaryOperator,
                        sCurrentLineNumber); // 2 parameters
            } else {
                //<Parameter-List> ::=
                oAST.addOperatorNode("param-list", compiler.iTypeOfAstNode_NullOperator,
                        sCurrentLineNumber); // zero or more parameters
            }

        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <Parameter-List> failed!");
        }
    }

    private boolean Rule_Parameter(Reduction myRed, Stack_of_Tokens oStackOfTokens,
            SymbolTable oSymbolTable,
            String sCurrentLineNumber) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (sRuleDefinition.trim().contains("Id")) {
                //<Parameter> ::= <Basic-Types>  Id
                String sParam = oStackOfTokens.pop();
                String sType = sTypeName_of_defined_object;
                Symbol oSym = new Symbol();
                if (!oSymbolTable.addSymbol(sParam, sCurrentLineNumber,
                        sTypeName_of_defined_object,
                        Integer.toString(compiler.iTypeOfToken_Identifier),
                        oSym)) {

                    this.o_IO.appendOutPutWindow("Line " + sCurrentLineNumber
                            + ": Multiple definition of param ["
                            + sParam + "] in current scope!");
                    return false;
                } else {
                    oAST.addleaf(Integer.toString(oSym.iIndexInSymbolTable),
                            compiler.iTypeOfAstNode_FormalParameter);
                }
            } else { // exception
                //<Parameter> ::=
                Debug.println("Parser : Reduction with <Parameter> not well handled!");
                return false;
            }
            return true;
        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <Parameter> failed!");
            return false;
        }
    }

    private void Rule_Return_Type(Reduction myRed) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();

            if (sRuleDefinition.trim().contains("void")) {
                //<Return-Type> ::= void
                sTypeName_of_defined_object = Integer.toString(compiler.iDataType_void);
                oAST.addleaf("void", compiler.iTypeOfAstNode_StringLiteral);
            } else if (sRuleDefinition.trim().contains("int")) {
                //<Return-Type> ::= void
                sTypeName_of_defined_object = Integer.toString(compiler.iDataType_void);
                oAST.addleaf("int", compiler.iTypeOfAstNode_StringLiteral);
            } else if (sRuleDefinition.trim().contains("float")) {
                //<Return-Type> ::= void
                sTypeName_of_defined_object = Integer.toString(compiler.iDataType_void);
                oAST.addleaf("float", compiler.iTypeOfAstNode_StringLiteral);
            } else if (sRuleDefinition.trim().contains("string")) {
                //<Return-Type> ::= void
                sTypeName_of_defined_object = Integer.toString(compiler.iDataType_void);
                oAST.addleaf("string", compiler.iTypeOfAstNode_StringLiteral);
            } else if (sRuleDefinition.trim().contains("bool")) {
                //<Return-Type> ::= void
                sTypeName_of_defined_object = Integer.toString(compiler.iDataType_void);
                oAST.addleaf("bool", compiler.iTypeOfAstNode_StringLiteral);
            } else { // exception
                Debug.println("Parser : Reduction with <Return-Type> not well handled!");
            }
        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <Return-Type> failed!");
        }
    }

    private void Rule_Access_Modifier(Reduction myRed) {
        try {
            String sRuleDefinition = myRed.getParentRule().definition();
            if (sRuleDefinition.trim().contains("public")) {
                //Access-Modifier> ::= public
                // System.out.println("access-modifier : public");
                oAST.addleaf("public", compiler.iTypeOfAstNode_StringLiteral);
            } else if (sRuleDefinition.trim().contains("private")) {
                //<Access-Modifier> ::= private
                //System.out.println("access-modifier : private");
                oAST.addleaf("private", compiler.iTypeOfAstNode_StringLiteral);
            } else { // exception
                System.out.println("Parser : Reduction with <Access-Modifier> not well handled!");
            }
        } catch (Exception ex) {
            Debug.println("Parser Error : Reduction with <Access-Modifier> failed!");
        }
    }

    public static void saveText2File(String sText, String sFile) {
        write_2_file(sText, sFile, false);
    }

    private static void write_2_file(String sText, String s_file, boolean bOption) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(s_file, bOption));
            File oFile = new File("a");
            out.write(sText);
            out.close();
            // System.out.println("yes ooooooooooo");
        } catch (IOException e) {
            // System.out.println(e.getMessage());
        }

    }

    private void closeParserEngine(){
        try {
            oParser.clear();
             oParser.closeFile();
        }
        catch (Exception ex){

        }

    }
    public boolean Compile(javax.swing.JTextArea txtEditor) {

        int iIndexOfLastRuleUsedInReduction = -1;
        o_IO.output("Compiling..");
        try {

            String textToParse = "";
            String compiledGrammar = "appgram";
            compiledGrammar = sAppRunningDirectory + "\\compiledGrammar\\csharp.cgt";
            textToParse = sAppRunningDirectory + "\\compiledGrammar\\test.cs";
            saveText2File(txtEditor.getText()+ "\n", textToParse);
            oParser.loadCompiledGrammar(compiledGrammar);
            oParser.openFile(textToParse);

            Debug.println(compiledGrammar);
            Debug.println(textToParse);

        } catch (ParserException parse) {
            System.out.println("**PARSER ERROR**\n" + parse.toString());
            return false;
        }

        boolean done = false;
        int response = -1;
        int iCount = 0;

        while (!done) {
            try {

                response = oParser.parse();
            } catch (ParserException parse) {

                o_IO.appendOutPutWindow("**PARSER ERROR**\n" + parse.toString() + "\n");
                return false;
            }

            switch (response) {
                case gpMsgTokenRead:
                    Token myTok = oParser.currentToken();
                    sLastTokenRead = (String) myTok.getData();
                    //System.out.println("<=" + sLastTokenRead + "=>");
                     sLastTokenRead = sLastTokenRead.trim();
                     
                    if (!oSymbolTable.existInParserTable(sLastTokenRead)
                            && !sLastTokenRead.trim().isEmpty()) {
                        // an identifier or a literal is found
                        iCount = iCount + 1;
                        if (!sTokenReadAhead.isEmpty()) {
                            oStackOfTokens.push(sTokenReadAhead);
                        }
                        sTokenReadAhead = sLastTokenRead;

                    } else {
                        if (!sTokenReadAhead.isEmpty()) {
                            oStackOfTokens.push(sTokenReadAhead);
                            sTokenReadAhead = "";
                        }
                    }
                    break;

                case gpMsgReduction:
                    Reduction myRed = oParser.currentReduction();
                    String sDebug = myRed.getParentRule().name() + "***"
                            + oParser.currentReduction().getParentRule().getTableIndex()
                            + "-" + myRed.getParentRule().getText();
                    String sRuleName = myRed.getParentRule().name();
                    if (sRuleName.contains("Program")) {
                        Rule_Program(myRed, Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Var-declarations")) {
                        Rule_Var_declarations(myRed, Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Var-declaration")) {
                        Rule_Var_declaration(myRed, oStackOfTokens, oSymbolTable,
                                Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Basic-Types")) {
                        // System.out.println(sDebug);
                        Rule_Basic_Types(myRed);
                    } else if (sRuleName.contains("Class-Definitions")) {
                        // System.out.println(sDebug);
                        Rule_Class_Definitions(myRed, Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Statements")) {
                        //System.out.println(sDebug);
                        Rule_Statements(myRed, Integer.toString(oParser.currentLineNumber()));

                    } else if (sRuleName.contains("Statement")) {
                        // Debug.println(sDebug);
                        done = !Rule_Statement(myRed, oStackOfTokens, oSymbolTable,
                                Integer.toString(oParser.currentLineNumber()),
                                oAST);
                    } else if (sRuleName.contains("Print-statement")) {
                        // Debug.println(sDebug);
                        Rule_Print_statement(myRed, oAST,
                                Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Read-statement")) {
                        //Debug.println(sDebug);
                        Rule_Read_statement(myRed, oStackOfTokens,
                                Integer.toString(oParser.currentLineNumber()),
                                oAST);
                    } else if (sRuleName.contains("Logic-Exp")) {
                        // Debug.println(sDebug);
                        Rule_Logic_Exp(myRed, oAST, Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("ArithMetic Exp")) {
                        // Debug.println(sDebug);
                        Rule_ArithMetic_Exp(myRed, oAST, Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Expression")) {
                        //Debug.println(sDebug);
                        Rule_Expression(myRed, oAST, Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Add Exp")) {
                        //Debug.println(sDebug);
                        Rule_Add_Exp(myRed, oAST, Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Mult Exp")) {
                        //Debug.println(sDebug);
                        Rule_Mult_Exp(myRed, oAST, Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Unary Exp")) {
                        // Debug.println(sDebug);
                        Rule_Unary_Exp(myRed, oAST, Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Value")) {
                        //Debug.println(sDebug);
                        done = !Rule_Value(myRed, oStackOfTokens, oSymbolTable,
                                Integer.toString(oParser.currentLineNumber()),
                                oAST);
                    } else if (sRuleName.contains("Class-definition-name")) {
                        // Debug.println(sDebug);
                        // System.out.println("here for this rule on class definition name???");
                        done = !Rule_Class_def_name(myRed, oStackOfTokens, oSymbolTable,
                                Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Class-definition")) {
                        // Debug.println(sDebug);
                        Rule_Class_definition(myRed, oStackOfTokens, Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Class-body")) {
                        //Debug.println(sDebug);
                        Rule_Class_body(myRed, Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Method-Definitions")) {
                        //System.out.println(sDebug);
                        Rule_Method_definitions(myRed, Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Method-def-name")) {
                        // Debug.println(sDebug);
                        done = !Rule_Method_def_name(myRed, oStackOfTokens, oSymbolTable,
                                Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Method-Definition")) {
                        //Debug.println(sDebug);
                        Rule_Method_definition(myRed, oStackOfTokens, Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Parameter-List")) {
                        //Debug.println(sDebug);
                        Rule_Parameter_List(myRed, oStackOfTokens, Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Parameter")) {
                        // Debug.println(sDebug);
                        Rule_Parameter(myRed, oStackOfTokens, oSymbolTable,
                                Integer.toString(oParser.currentLineNumber()));
                    } else if (sRuleName.contains("Return-Type")) {
                        //Debug.println(sDebug);
                        Rule_Return_Type(myRed);
                    } else if (sRuleName.contains("Access-Modifier")) {
                        // Debug.println(sDebug);
                        Rule_Access_Modifier(myRed);
                    } else {
                        Debug.println("No matching rule to handle reduction ...???");
                    }
                    break;

                case gpMsgAccept:
                    /* The program was accepted by the parsing engine */
                    // ************************************** log file
                    Debug.println("gpMsgAccept::" + oSymbolTable.getIndexOfLastSymbolAdded2Table());
                    oSymbolTable.printTable();
                    // ************************************** end log
                    compiler.iCompiler_ParsingOK = true;
                    done = true;
                    break;
                case gpMsgLexicalError:
                    /* Place code here to handle a illegal or unrecognized token
                    To recover, pop the token from the stack: Parser.PopInputToken */
                    // ************************************** log file
                    o_IO.appendOutPutWindow("\n*******************LEXICAL ERROR**************");
                    // ************************************** end log
                    o_IO.appendOutPutWindow("\nLine " + oParser.currentLineNumber()
                            + ": Invalid token -" + oParser.popInputToken().getData());
                    o_IO.appendOutPutWindow("\n*******************ERROR**************");
                    o_IO.appendOutPutWindow("\n");
                    done = true;
                    closeParserEngine();
                    return false;

                case gpMsgNotLoadedError:
                    /* Load the Compiled Grammar Table file first. */
                    // ************************************** log file
                    this.o_IO.appendOutPutWindow("gpMsgNotLoadedError");
                    // ************************************** end log
                    closeParserEngine();
                    return false;
                case gpMsgSyntaxError:
                    done = true;
                    Token theTok = oParser.currentToken();
                    // System.out.println("Token not expected: " + (String) theTok.getData());
                    // ************************************** log file
                    //System.out.println("gpMsgSyntaxError");
                    o_IO.appendOutPutWindow("\n*******************SYNTAX ERROR**************");
                    // ************************************** end log
                    o_IO.appendOutPutWindow("\nLine " + oParser.currentLineNumber()
                            + ": co-location of tokens is invalid!");
                    o_IO.appendOutPutWindow("\n*******************ERROR**************");
                    // ************************************** end log
                    closeParserEngine();
                    return false;

                case gpMsgCommentError:
                    /* The end of the input was reached while reading a comment.
                    This is caused by a comment that was not terminated */
                    // ************************************** log file
                    this.o_IO.appendOutPutWindow("gpMsgCommentError");
                    // ************************************** end log
                    closeParserEngine();
                    return false;

                case gpMsgInternalError:
                    /* Something horrid happened inside the parser. You cannot recover */
                    // ************************************** log file
                    this.o_IO.appendOutPutWindow("gpMsgInternalError");
                    // ************************************** end log
                    closeParserEngine();
                    return false;
                default:
                    done = true;
                    System.out.println("default");
                    break;
            }

        } // while

        if (compiler.iCompiler_ParsingOK && !this.oAST.errorBuildingTree()) {
            //  ... parsing ok and no error building the tree ...
            // ... proceed to create intermediate n target codes
           
            Three_Address_Program o3AddressGenerator =
                    new Three_Address_Program(this.o_IO, this.oAST, this.oSymbolTable);
            if (!o3AddressGenerator.GenerateCode()) {
                closeParserEngine();
                return false;
            }
            // else proceed to generate target code...
            Target_Program oTargetCodeGenerator =
                    new Target_Program(oSymbolTable, o3AddressGenerator);
            try {
                boolean bRet =
                        oTargetCodeGenerator.GenerateCode(sAppRunningDirectory);
                sPath2CompiledCode = oTargetCodeGenerator.getOutPutFileName();
                closeParserEngine();
                return bRet;
            } catch (Exception ie) {
                o_IO.appendOutPutWindow("Code generation error : "
                        + ie.getMessage());
                closeParserEngine();
                return false;
            }
        }
        closeParserEngine();
        return false;
    }

    public compiler(InputOutput oInputPut,
            String sAppDirectory,
            javax.swing.JTextArea txtEditor) {
        sAppRunningDirectory = sAppDirectory;
        createUniqueName4CompiledCode();
        o_IO = oInputPut;
        String textToParse = "", compiledGrammar = "appgram";

      //  System.out.println("App Dir" + sAppDirectory);

        /********************************************************/
        oParser = new GOLDParser();
        oSymbolTable = new SymbolTable(oParser);
        oStackOfTokens = new Stack_of_Tokens();
        oAST = new AstTree(this.o_IO, oSymbolTable);
        oThreeAddressCoder = new Three_Address_Program(this.o_IO, oAST, oSymbolTable);
       // oAssemblyProgram = new Assembly_Program(oSymbolTable, oThreeAddressCoder);

    }

    public static void reInitialiseComparisonFlags() {

        b_JMP_ZERO = false;  // jump on zero
        b_JMP_POSITIVE = false;  // jump on positive
        b_JMP_NEGATIVE = false; // jump on negative
        b_JA = false;  // jump on above
        b_JAE = false;  // jump on above or equal
        b_JB = false;  // jump on below
        b_JBE = false; // jump on below or equal
        b_JE = false; // jump on equal
        b_JNE = false;  // jump on not equal

    }

    public static boolean isIntegerOperator(int iDataType) {
        switch (iDataType) {
            case compiler.iDataType_integer:
                return true;
            case compiler.iDataType_long:
                return true;
            default:
                return false;

        }
    }

    public static String s_DataType(int iDataType) {
        switch (iDataType) {
            case compiler.iDataType_integer:
                return "int";
            case compiler.iDataType_float:
                return "float";
            case compiler.iDataType_string:
                return "string";
            case compiler.iDataType_boolean:
                return "bool";
            default:
                return "void"; // rare exception
        }

    }

    public static boolean isArithMeticOperator(String sOperator) {
        if (sOperator.trim().equals("%")) {
            return true;
        }
        if (sOperator.trim().equals("+")) {
            return true;
        }
        if (sOperator.trim().equals("-")) {
            return true;
        }
        if (sOperator.trim().equals("/")) {
            return true;
        }
        if (sOperator.trim().equals("*")) {
            return true;
        }
        return false;
    }

    public static boolean isRelationalOperator(String sOperator) {
        if (sOperator.trim().equals("<")) {
            return true;
        }
        if (sOperator.trim().equals("<=")) {
            return true;
        }
        if (sOperator.trim().equals(">")) {
            return true;
        }
        if (sOperator.trim().equals(">=")) {
            return true;
        }
        if (sOperator.trim().equals("==")) {
            return true;
        }
        if (sOperator.trim().equals("!=")) {
            return true;
        }
        return false;
    }

    public static boolean isLogicalOperator(String sOperator) {
        if (sOperator.trim().equals("||")) {
            return true;
        }
        if (sOperator.trim().equals("&&")) {
            return true;
        }
        if (sOperator.trim().equals("!")) {
            return true;
        }
        return false;
    }
}
