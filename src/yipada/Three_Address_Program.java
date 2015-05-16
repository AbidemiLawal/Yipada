/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yipada;

public class Three_Address_Program {

    private final static int iMaxSizeOfProgram = 50000;
    private int iIndexOfLastCodeGenerated;

    private InputOutput oInputPut ;
    private Quadruple oQuadruple[];
    private SymbolTable oSymbolTable;
    private AstTree oAstTree;
    private static int iIndexOfLabelGenerated = 0;

    public int SizeOf3AddressProgram() {
        return iIndexOfLastCodeGenerated + 1;
    }

    public Quadruple getQuadrupleAtIndex(int iIndexOfQuadruple) {

        Quadruple o3AddressQuadruple = null;
        if (iIndexOfQuadruple <= iIndexOfLastCodeGenerated) {
            o3AddressQuadruple = oQuadruple[iIndexOfQuadruple];
        }
        return o3AddressQuadruple;
    }

    public Three_Address_Program(InputOutput oInputPut ,
                  AstTree oAst, SymbolTable oSymTable) {
        try {
            this.oInputPut  = oInputPut ;
            oQuadruple = new Quadruple[Three_Address_Program.iMaxSizeOfProgram];
            for (int i = 0; i < iMaxSizeOfProgram; i++) {
                oQuadruple[i] = new Quadruple();
            }
            iIndexOfLastCodeGenerated = -1;  // no code generated so far
            oAstTree = oAst;
            oSymbolTable = oSymTable;
        } catch (Exception ex) {
        }
    }//

    public boolean GenerateCode() {
        try {
            Node oNode = oAstTree.rootNode();
            if (oNode == null) {
                oInputPut.appendOutPutWindow("3-Address code cannot be generated because the AST tree is non-existent!!!");
                return false; // would rarely b executed as this would b caught ealier
            }
            switch (oNode.iDataType) {
                case compiler.iDataType_error:
                    oInputPut.appendOutPutWindow("3-Address code cannot be generated: source contains type error(s)!!!");
                    return false;
            }

           Debug.println("Generating 3-address code ..." + oAstTree.rootNode().n_identification);
            code_gen(oNode);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    private boolean addCode(String sOpCode, ThreeAddressOperand arg_1, ThreeAddressOperand arg_2, int iSymbolTableIndex) {
        // note
        try {

            iIndexOfLastCodeGenerated = iIndexOfLastCodeGenerated + 1;

            if (iIndexOfLastCodeGenerated >= Three_Address_Program.iMaxSizeOfProgram) {
                return false; // code could not be added to program...max-size exceeded for storage
            }
            oQuadruple[iIndexOfLastCodeGenerated].sOpCode = sOpCode;
            oQuadruple[iIndexOfLastCodeGenerated].sArg_1 = arg_1;
            oQuadruple[iIndexOfLastCodeGenerated].sArg_2 = arg_2;
            oQuadruple[iIndexOfLastCodeGenerated].iSymbolTableIndex =
                    iSymbolTableIndex;
           Debug.println("Adding 3-address-code :" + sOpCode);
            return true;
        } catch (Exception ex) {
            System.out.println("Error ::" + ex.getMessage());
            return false;
        }
    }

    private ThreeAddressOperand code_gen(Node oNode) {
        ThreeAddressOperand o3AddressCodeOperand = null;
        if (oNode.n_firstChild == null) {
            // a terminal node is found
            o3AddressCodeOperand =
                    new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                    oNode.n_identification, oNode.type);
            // 4 identifier nodes...symboltable index is same as node identification
            // literals are another class of terminal nodes on d ast tree...
            if (oNode.type == compiler.iTypeOfAstNode_FormalParameter) {
                // generate code for parameter...
                code_gen_4_Parameter(oNode);
            }
            return o3AddressCodeOperand;
        }
        if (oNode.n_identification.equals("program")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            Node oNode1 = oNode.n_firstChild; // var declaration(s)
            Node oNode2 = oNode1.n_nextSibling; // class definition(s)
            code_gen(oNode1);
            code_gen(oNode2);
            oNode1 = null;
            oNode2 = null;
        } else if (oNode.n_identification.equals("class-definitions")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            code_gen_4_ClassDefinitions(oNode);

        } else if (oNode.n_identification.equals("class-definition")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            code_gen_4_ClassDefinition(oNode);

        } else if (oNode.n_identification.equals("class-body")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            Node oNode1 = oNode.n_firstChild; // var-declaration(s)
            Node oNode2 = oNode1.n_nextSibling; // method-definition(s)
            code_gen(oNode1);
            code_gen(oNode2);
            oNode1 = null;
            oNode2 = null;
        } else if (oNode.n_identification.equals("var-declarations")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            Node oNode1 = oNode.n_firstChild;
            code_gen(oNode1);
            oNode1 = null;
        } else if (oNode.n_identification.equals("var-declaration")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            Node oNode1 = oNode.n_firstChild;
            code_gen_4_var_declaration(oNode1);
            oNode1 = null;
        } else if (oNode.n_identification.equals("method-definitions")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            code_gen_4_MethodDefinitions(oNode);

        } else if (oNode.n_identification.equals("method-definition")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            code_gen_4_MethodDefinition(oNode);

        } else if (oNode.n_identification.equals("param-list")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            code_gen_4_ParameterList(oNode);


        } else if (oNode.n_identification.equals("statements")) {
            // no direct code is generated for this abstract construct
            Node oNode1 = oNode.n_firstChild; // statement
            code_gen(oNode1);
            code_gen(oNode1.n_nextSibling); // statements
            oNode1 = null;

        } else if (oNode.n_identification.equals(":=")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            code_gen_4_ASSIGNMENT_STATEMENT(oNode);

        } else if (compiler.isArithMeticOperator(oNode.n_identification)) {

            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            o3AddressCodeOperand = code_gen_4_arithmetic_expression(oNode);

        } else if (oNode.n_identification.equals("||")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            o3AddressCodeOperand = code_gen_4_OR_expression(oNode);

        } else if (oNode.n_identification.equals("&&")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            o3AddressCodeOperand = code_gen_4_AND_expression(oNode);
        } else if (compiler.isRelationalOperator(oNode.n_identification)) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            o3AddressCodeOperand = code_gen_4_Relational_expression(oNode);
        } else if (oNode.n_identification.equals("while")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            code_gen_4_While_expression(oNode);
        } else if (oNode.n_identification.equals("if-then-endif")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            code_gen_4_IF_THEN_ENDIF_expression(oNode);

        } else if (oNode.n_identification.equals("if-then-else-endif")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            code_gen_4_IF_THEN_ELSE_ENDIF_expression(oNode);

        } else if (oNode.n_identification.equals("console.readline")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            addCode(compiler.s3AddressOperator_READCONSOLE,
                    new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                    oNode.n_firstChild.n_identification,
                    oNode.n_firstChild.type), null, -1);

        } else if (oNode.n_identification.equals("console.writeline")) {
            Debug.println("Generating code for Ast Node :"
                    + oNode.n_identification);
            ThreeAddressOperand arg_1 = null ;
            arg_1 = code_gen(oNode.n_firstChild); // generate code for println argument
            addCode(compiler.s3AddressOperator_PRINTCONSOLE, arg_1, null, -1);

        } else {
            Debug.println("Generating code for Unknown(???) Ast Node :"
                    + oNode.n_identification);  // a rare exception
        }
        return o3AddressCodeOperand;
    }

    private ThreeAddressOperand code_gen_4_Relational_expression(Node oNode) {
        ThreeAddressOperand o3AddressCodeOperand = null;
        Node oNode1 = oNode.n_firstChild;  // left operand
        Node oNode2 = oNode1.n_nextSibling; // right operand

        ThreeAddressOperand arg_1 = code_gen(oNode1);
        ThreeAddressOperand arg_2 = code_gen(oNode2);
        String sLabel_on_false = newLabel();
        String sLabel_on_exit = newLabel();

        o3AddressCodeOperand =
                new ThreeAddressOperand(compiler.s3AddressOperator_LESS,
                Integer.toString(oNode.iSymbolTableIndex), oNode.type);
        Debug.println("**********************BEGIN CODE GEN**************");
        addCode(compiler.s3AddressOperator_CMP, arg_1, arg_2, -1); // CMP E1.Place , E2.Place
        if (oNode.n_identification.equals("<")) {
            addCode(compiler.s3AddressOperator_JAE,
                    new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                    sLabel_on_false, -1), null, -1); // JAE E.False
        } else if (oNode.n_identification.equals("<=")) {
            addCode(compiler.s3AddressOperator_JA,
                    new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                    sLabel_on_false, -1), null, -1); // JAE E.False
        } else if (oNode.n_identification.equals(">")) {
            addCode(compiler.s3AddressOperator_JBE,
                    new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                    sLabel_on_false, -1), null, -1); // JAE E.False
        } else if (oNode.n_identification.equals(">=")) {
            addCode(compiler.s3AddressOperator_JB,
                    new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                    sLabel_on_false, -1), null, -1); // JAE E.False
        } else if (oNode.n_identification.equals("==")) {
            addCode(compiler.s3AddressOperator_JNE,
                    new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                    sLabel_on_false, -1), null, -1); // JAE E.False
        } else if (oNode.n_identification.equals("!=")) {
            addCode(compiler.s3AddressOperator_JE,
                    new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                    sLabel_on_false, -1), null, -1); // JAE E.False
        }
        addCode(compiler.s3AddressOperator_MOV,
                o3AddressCodeOperand,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                "1", -1), -1);  // MOV E.Place , 1
        addCode(compiler.s3AddressOperator_JMP,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_on_exit, -1), null, -1); // JMP E.Exit
        addCode(compiler.s3AddressOperator_LABEL,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_on_false, -1), null, -1); // E.False :
        addCode(compiler.s3AddressOperator_MOV,
                o3AddressCodeOperand,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                "0", -1), -1);  // MOV E.Place , 0
        addCode(compiler.s3AddressOperator_LABEL,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_on_exit, -1), null, -1); // E.Exit :
        Debug.println("**********************END CODE GEN**************");
        oNode1 = null;
        oNode2 = null;

        return o3AddressCodeOperand;
    }

    private ThreeAddressOperand code_gen_4_IF_THEN_ELSE_ENDIF_expression(Node oNode) {

        ThreeAddressOperand o3AddressCodeOperand = null;
        Node oNode1 = oNode.n_firstChild;  // logical expression
        Node oNode2 = oNode1.n_nextSibling; // then statement
        Node oNode3 = oNode2.n_nextSibling; // else statement

        String sLabel_EndIF = newLabel();
        String sLabel_ELSE = newLabel();

        Debug.println("***************BEGIN CODE GEN IF THEN ELSE**************");
        ThreeAddressOperand arg_1 = code_gen(oNode1);
        addCode(compiler.s3AddressOperator_CMP, arg_1,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                "0", -1), -1); // CMP E.Place , 0
        addCode(compiler.s3AddressOperator_JE,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_ELSE, -1), null, -1); // JE ELSEIF

        ThreeAddressOperand arg_2 = code_gen(oNode2);

        addCode(compiler.s3AddressOperator_JMP,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_EndIF, -1), null, -1); // JMP ENDIF

        addCode(compiler.s3AddressOperator_LABEL,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_ELSE, -1), null, -1); // LABEL ELSE :

        ThreeAddressOperand arg_3 = code_gen(oNode3);

        addCode(compiler.s3AddressOperator_LABEL,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_EndIF, -1), null, -1); // LABEL ENDIF :

        Debug.println("*******************END CODE GEN IF THEN ELSE**************");
        oNode1 = null;
        oNode2 = null;
        oNode3 = null;
        return o3AddressCodeOperand;

    }

    private ThreeAddressOperand code_gen_4_IF_THEN_ENDIF_expression(Node oNode) {

        ThreeAddressOperand o3AddressCodeOperand = null;
        Node oNode1 = oNode.n_firstChild;  // left operand
        Node oNode2 = oNode1.n_nextSibling; // right operand
        ThreeAddressOperand arg_1 = code_gen(oNode1);

        String sLabel_Exit_IFThenEndIF = newLabel();
        Debug.println("**********************BEGIN CODE GEN**************");
        addCode(compiler.s3AddressOperator_CMP, arg_1,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                "0", -1), -1); // CMP E.Place , 0
        addCode(compiler.s3AddressOperator_JE,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_Exit_IFThenEndIF, -1), null, -1); // JE E.EXIT_IFTHENENDIF
        ThreeAddressOperand arg_2 = code_gen(oNode2);
        addCode(compiler.s3AddressOperator_LABEL,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_Exit_IFThenEndIF, -1), null, -1); // E.EXIT_IFTHENENDIF  :
        Debug.println("**********************END CODE GEN**************");
        oNode1 = null;
        oNode2 = null;

        return o3AddressCodeOperand;

    }

    private ThreeAddressOperand code_gen_4_While_expression(Node oNode) {

        ThreeAddressOperand o3AddressCodeOperand = null;
        Node oNode1 = oNode.n_firstChild;  // left operand
        Node oNode2 = oNode1.n_nextSibling; // right operand

        String sLabel_Entry = newLabel();
        String sLabel_Exit = newLabel();

        addCode(compiler.s3AddressOperator_LABEL,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_Entry, -1), null, -1); // LABEL ENTRY :

        ThreeAddressOperand arg_1 = code_gen(oNode1);
        addCode(compiler.s3AddressOperator_CMP, arg_1,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                "0", -1), -1); // CMP E.Place , 0
        addCode(compiler.s3AddressOperator_JE,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_Exit, -1), null, -1); // JE EXIT
        ThreeAddressOperand arg_2 = code_gen(oNode2);
        addCode(compiler.s3AddressOperator_JMP,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_Entry, -1), null, -1); // JMP ENDIF
        addCode(compiler.s3AddressOperator_LABEL,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_Exit, -1), null, -1); // LABEL EXIT :
        oNode1 = null;
        oNode2 = null;
        return o3AddressCodeOperand;

    }

    private ThreeAddressOperand code_gen_4_ASSIGNMENT_STATEMENT(Node oNode) {
        ThreeAddressOperand o3AddressCodeOperand = null;
        Node oNode1 = oNode.n_firstChild;  // l-value
        Node oNode2 = oNode1.n_nextSibling; // r-value
        ThreeAddressOperand arg_1 = code_gen(oNode1);
        ThreeAddressOperand arg_2 = code_gen(oNode2);
        Debug.println("**********************BEGIN CODE GEN**************");
        addCode(compiler.s3AddressOperator_ASSIGN, arg_1, arg_2, oNode.iSymbolTableIndex);
        Debug.println("**********************END CODE GEN**************");
        oNode1 = null;
        oNode2 = null;
        return o3AddressCodeOperand;

    }

    private ThreeAddressOperand code_gen_4_Parameter(Node oNode) {
        ThreeAddressOperand o3AddressCodeOperand = null;

        addCode(compiler.s3AddressOperator_METHOD_FORMAL_PARAM,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                oNode.n_identification, oNode.type), null, -1);

        return o3AddressCodeOperand;

    }

    private ThreeAddressOperand code_gen_4_ClassDefinitions(Node oNode) {

        ThreeAddressOperand o3AddressCodeOperand = null;
        Node oNode1 = oNode.n_firstChild;  // class definition
        Node oNode2 = oNode1.n_nextSibling; // class definition(s)
        code_gen_4_ClassDefinition(oNode1);
        if (oNode2.n_identification.trim().equals("class-definition")) {
            code_gen_4_ClassDefinition(oNode2);
        } else {
            code_gen(oNode2);
        }
        oNode1 = null;
        oNode2 = null;

        return o3AddressCodeOperand;

    }

    private ThreeAddressOperand code_gen_4_ClassDefinition(Node oNode) {
        ThreeAddressOperand o3AddressCodeOperand = null;
        Node oNode1 = oNode.n_firstChild;  // access modifier
        Node oNode2 = oNode1.n_nextSibling; // class name
        Node oNode3 = oNode2.n_nextSibling;  // class body

        addCode(compiler.s3AddressOperator_CLASS_DEF_BEGIN,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                oNode1.n_identification, oNode1.type),
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                oNode2.n_identification, oNode2.type), -1); // access modifier n class name

        code_gen(oNode1); // access modifier : no code is generated
        code_gen(oNode2); // class name : no code is generated
        code_gen(oNode3); // class body : code is generated

        addCode(compiler.s3AddressOperator_CLASS_DEF_END,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                oNode1.n_identification, oNode1.type),
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                oNode2.n_identification, oNode2.type), -1); // access modifier n class name

        oNode1 = null;
        oNode2 = null;
        oNode3 = null;

        return o3AddressCodeOperand;
    }

    private ThreeAddressOperand code_gen_4_var_declaration(Node oNode) {
        
        ThreeAddressOperand o3AddressCodeOperand = null;

        addCode(compiler.s3AddressOperator_VAR_DECLARATION,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                oNode.n_identification, oNode.type), null, -1);

        return o3AddressCodeOperand;

    }

    private ThreeAddressOperand code_gen_4_MethodDefinition(Node oNode) {
        ThreeAddressOperand o3AddressCodeOperand = null;
        Node oNode1 = oNode.n_firstChild;  // access modifier
        Node oNode2 = oNode1.n_nextSibling; // return type
        Node oNode3 = oNode2.n_nextSibling;  // method name
        Node oNode4 = oNode3.n_nextSibling; // parameter list
        Node oNode5 = oNode4.n_nextSibling; //statement(s)

        addCode(compiler.s3AddressOperator_METHOD_DEF_BEGIN,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                oNode2.n_identification, oNode2.type),
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                oNode3.n_identification, oNode3.type), -1); // return type n name

        code_gen(oNode1);  // access modifier :  no code is generated by this...
        code_gen(oNode2);  // return type : no code is generated by this...
        code_gen(oNode3);  //  method name : no code is generated
        code_gen(oNode4);  //  parameter list : code is generated by this
        code_gen(oNode5);  //  statements : code is generated

        addCode(compiler.s3AddressOperator_METHOD_DEF_END,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                oNode2.n_identification, oNode2.type),
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                oNode3.n_identification, oNode3.type), -1);  // return type n name

        oNode1 = null;
        oNode2 = null;
        oNode3 = null;
        oNode4 = null;
        oNode5 = null;

        return o3AddressCodeOperand;

    }

    private ThreeAddressOperand code_gen_4_MethodDefinitions(Node oNode) {
        ThreeAddressOperand o3AddressCodeOperand = null;
        Node oNode1 = oNode.n_firstChild;  // method definition
        Node oNode2 = oNode1.n_nextSibling; // method definition(s)
        code_gen_4_MethodDefinition(oNode1);
        if (oNode2.equals("method-definition")) {
            code_gen_4_MethodDefinition(oNode2);
        } else {
            code_gen(oNode2);
        }
        oNode1 = null;
        oNode2 = null;
        return o3AddressCodeOperand;
    }

    private ThreeAddressOperand code_gen_4_ParameterList(Node oNode) {
        ThreeAddressOperand o3AddressCodeOperand = null;
        Node oNode1 = oNode.n_firstChild; // parameter
        Node oNode2 = oNode1.n_nextSibling; // parameter(s) or param-list
        code_gen_4_Parameter(oNode1);
        if (oNode2.n_identification.equals("param-list")) {
            code_gen(oNode2);
        } else {

            code_gen_4_Parameter(oNode2);
        }

        oNode1 = null;
        oNode2 = null;

        return o3AddressCodeOperand;

    }

    private ThreeAddressOperand code_gen_4_AND_expression(Node oNode) {
        ThreeAddressOperand o3AddressCodeOperand = null;

        Node oNode1 = oNode.n_firstChild;  // left operand
        Node oNode2 = oNode1.n_nextSibling; // right operand

        ThreeAddressOperand arg_1 = code_gen(oNode1);

        String sLabel_FALSE = newLabel();
        String sLabel_EXIT = newLabel();

        o3AddressCodeOperand =
                new ThreeAddressOperand(compiler.s3AddressOperator_AND,
                Integer.toString(oNode.iSymbolTableIndex), oNode.type);

        addCode(compiler.s3AddressOperator_CMP, arg_1,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                "0", -1), -1); // CMP E1.Place , 1
        addCode(compiler.s3AddressOperator_JE,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_FALSE, -1), null, -1); // JE, LABEL.TRUE

        ThreeAddressOperand arg_2 = code_gen(oNode2);

        addCode(compiler.s3AddressOperator_CMP, arg_2,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                "0", -1), -1); // CMP E2.Place , 1
        addCode(compiler.s3AddressOperator_JE,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_FALSE, -1), null, -1); // JE, LABEL.TRUE
        addCode(compiler.s3AddressOperator_MOV,
                o3AddressCodeOperand,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                "1", -1), -1);  // MOV E.Place , 0
        addCode(compiler.s3AddressOperator_JMP,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_EXIT, -1), null, -1); // JMP, LABEL.EXIT
        addCode(compiler.s3AddressOperator_LABEL,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_FALSE, -1), null, -1); // LABEL.TRUE :
        addCode(compiler.s3AddressOperator_MOV,
                o3AddressCodeOperand,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                "0", -1), -1);  // MOV E.Place , 1
        addCode(compiler.s3AddressOperator_LABEL,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_EXIT, -1), null, -1); // LABEL.TRUE :

        oNode1 = null;
        oNode2 = null;

        return o3AddressCodeOperand;

    }

    private ThreeAddressOperand code_gen_4_OR_expression(Node oNode) {
        ThreeAddressOperand o3AddressCodeOperand = null;

        Node oNode1 = oNode.n_firstChild;  // left operand
        Node oNode2 = oNode1.n_nextSibling; // right operand

        ThreeAddressOperand arg_1 = code_gen(oNode1);

        String sLabel_TRUE = newLabel();
        String sLabel_EXIT = newLabel();

        o3AddressCodeOperand =
                new ThreeAddressOperand(compiler.s3AddressOperator_OR,
                Integer.toString(oNode.iSymbolTableIndex), oNode.type);

        addCode(compiler.s3AddressOperator_CMP, arg_1,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                "1", -1), -1); // CMP E1.Place , 1
        addCode(compiler.s3AddressOperator_JE,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_TRUE, -1), null, -1); // JE, LABEL.TRUE
        ThreeAddressOperand arg_2 = code_gen(oNode2);
        addCode(compiler.s3AddressOperator_CMP, arg_2,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                "1", -1), -1); // CMP E2.Place , 1
        addCode(compiler.s3AddressOperator_JE,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_TRUE, -1), null, -1); // JE, LABEL.TRUE
        addCode(compiler.s3AddressOperator_MOV,
                o3AddressCodeOperand,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                "0", -1), -1);  // MOV E.Place , 0
        addCode(compiler.s3AddressOperator_JMP,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_EXIT, -1), null, -1); // JMP, LABEL.EXIT
        addCode(compiler.s3AddressOperator_LABEL,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_TRUE, -1), null, -1); // LABEL.TRUE :
        addCode(compiler.s3AddressOperator_MOV,
                o3AddressCodeOperand,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                "1", -1), -1);  // MOV E.Place , 1
        addCode(compiler.s3AddressOperator_LABEL,
                new ThreeAddressOperand(compiler.s3AddressOperator_NULL,
                sLabel_EXIT, -1), null, -1); // LABEL.TRUE :

        oNode1 = null;
        oNode2 = null;

        return o3AddressCodeOperand;
    }

    private ThreeAddressOperand code_gen_4_arithmetic_expression(Node oNode) {
        ThreeAddressOperand o3AddressOperand = null;
        Node oNode1 = oNode.n_firstChild;  // left operand
        Node oNode2 = oNode1.n_nextSibling; // right operand
        ThreeAddressOperand arg_1 = code_gen(oNode1);
        ThreeAddressOperand arg_2 = code_gen(oNode2);
        Debug.println("**********************BEGIN CODE GEN**************");
        
        if (oNode.n_identification.equals("%")) {
            addCode(compiler.s3AddressOperator_MODULO, arg_1, arg_2, oNode.iSymbolTableIndex);
            // System.out.println("adding 3-address-code for % ...");
            o3AddressOperand =
                    new ThreeAddressOperand(compiler.s3AddressOperator_MODULO,
                    Integer.toString(oNode.iSymbolTableIndex), oNode.type);
        }
        
        else if(oNode.n_identification.equals("+")) {
            addCode(compiler.s3AddressOperator_ADD, arg_1, arg_2, oNode.iSymbolTableIndex);
            // System.out.println("adding 3-address-code for + ...");
            o3AddressOperand =
                    new ThreeAddressOperand(compiler.s3AddressOperator_ADD,
                    Integer.toString(oNode.iSymbolTableIndex), oNode.type);
        } else if (oNode.n_identification.equals("-")) {
            addCode(compiler.s3AddressOperator_SUB, arg_1, arg_2, oNode.iSymbolTableIndex);
            o3AddressOperand =
                    new ThreeAddressOperand(compiler.s3AddressOperator_SUB,
                    Integer.toString(oNode.iSymbolTableIndex), oNode.type);
        } else if (oNode.n_identification.equals("*")) {
            addCode(compiler.s3AddressOperator_MULTIPLY, arg_1, arg_2, oNode.iSymbolTableIndex);
            o3AddressOperand =
                    new ThreeAddressOperand(compiler.s3AddressOperator_MULTIPLY,
                    Integer.toString(oNode.iSymbolTableIndex), oNode.type);
        } else if (oNode.n_identification.equals("/")) {
            addCode(compiler.s3AddressOperator_DIVIDE, arg_1, arg_2, oNode.iSymbolTableIndex);
            o3AddressOperand =
                    new ThreeAddressOperand(compiler.s3AddressOperator_DIVIDE,
                    Integer.toString(oNode.iSymbolTableIndex), oNode.type);
        } else {
            System.out.println("Cant generate code for unhandled arithmetic expression!!!");
            // rare exception
        }


        Debug.println("**********************END CODE GEN**************");
        oNode1 = null;
        oNode2 = null;
        return o3AddressOperand;

    }

    public boolean printThreeAddressProgram() {
        //System.out.println("here to print three address codes");
        for (int i = 0; i <= iIndexOfLastCodeGenerated; i++) {
            System.out.println(oQuadruple[i].sOpCode + "\t"
                    + oQuadruple[i].sArg_1 + "\t"
                    + oQuadruple[i].sArg_2 + "\t"
                    + oQuadruple[i].iSymbolTableIndex);
        }
        return true;
    }

    public static String newLabel() {
        iIndexOfLabelGenerated = iIndexOfLabelGenerated + 1;
        return "_ypda_label_" + Integer.toString(iIndexOfLabelGenerated);
    }
}
