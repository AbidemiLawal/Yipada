/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yipada;

public class TypeChecker {

    InputOutput oInputPut ;
    private SymbolTable oSymbolTable;
    

    public TypeChecker(InputOutput oInputPut , SymbolTable pSymTable) {
        this.oInputPut = oInputPut ;
        oSymbolTable = pSymTable;
    }

    public boolean symbolIsDefined() {
        return true;  // search symbol table for definition of operand
    }

    public boolean operandsCompatible4Program(Node oNode1,
            Node oNode2,
            String sCurrentLineNumber) {
        if (dataTypeOfNode(oNode1) != compiler.iDataType_error
                && dataTypeOfNode(oNode2) != compiler.iDataType_error) {
            Debug.println("Program is type correct!!!");

            return true;
        }
         Debug.println("Line[" + sCurrentLineNumber
                    + "].Program is wrongly typed!!!");
        return false;

    }

    public boolean operandsCompatibile4MethodDefinition(Node oNode1,
            Node oNode2, Node oNode3,
            Node oNode4, Node oNode5,
            String sCurrentLineNumber) {
        if (dataTypeOfNode(oNode1) != compiler.iDataType_string) {
            oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                    + "]. Method-definition : Access modifier is wrongly typed!!!\n");
            return false;
        }
        if (dataTypeOfNode(oNode2) != compiler.iDataType_string) {
           oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                    + "]. Method-definition : Return type is wrongly typed!!!\n");
            return false;
        }

        if (dataTypeOfNode(oNode3) != compiler.iDataType_void) {
            oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                    + "]. Method-definition : Method name is wrongly typed!!!\n");
            return false;
        }
        if (dataTypeOfNode(oNode4)  != compiler.iDataType_void &&
                oNode4.type  != compiler.iTypeOfAstNode_FormalParameter) {
            oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                    + "]. Method-definition : Parameter list is wrongly typed!!!\n");
            return false;
        }
        if (dataTypeOfNode(oNode5) != compiler.iDataType_void) {
            Debug.println("Line[" + sCurrentLineNumber
                    + "]. Method-definition : Method body is wrongly typed!!!");
            return false;
        }

        Debug.println("Method definition is type correct!!!");
        return true;
    }

    public boolean operandsCompatibile4ClassDefinition(Node oNode1,
            Node oNode2, Node oNode3,
            String sCurrentLineNumber) {
        if (dataTypeOfNode(oNode1) != compiler.iDataType_string) {
            oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                    + "]. Class-definition : Access modifier is wrongly typed!!!\n");
            return false;
        }
        if (dataTypeOfNode(oNode2) != compiler.iDataType_void) {
           oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                    + "]. Class-definition : class name is wrongly typed!!!\n");
            return false;
        }
        if (dataTypeOfNode(oNode3) != compiler.iDataType_void) {
            Debug.println("Line[" + sCurrentLineNumber
                    + "]. Class-definition : class body is wrongly typed!!!");
            return false;
        }
        Debug.println("Class definition is type correct!!!");
        return true;
    }

    public boolean operandsCompatibile4IfThenElseEndIfOperations(Node oNode1,
            Node oNode2, Node oNode3,
            String sCurrentLineNumber) {
        if ((dataTypeOfNode(oNode1) != compiler.iDataType_boolean)) {
            if (!isNumericType(dataTypeOfNode(oNode1))) {
               oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                        + "]. If-then-else-endif-block : logic expression is type invalid!!!\n");
                return false;
            }
        }
        if ((dataTypeOfNode(oNode2) != compiler.iDataType_void)) {
            Debug.println("Line[" + sCurrentLineNumber
                    + "If-then-else-endif-block :  then block is type invalid.");
            return false;
        }
        if ((dataTypeOfNode(oNode3) != compiler.iDataType_void)) {
            Debug.println("Line[" + sCurrentLineNumber
                    + "If-then-else-endif-block :  else block is type invalid.");
            return false;
        }
        Debug.println("If-then-else-endif-block is type correct!!!");
        return true;
    }

    public boolean operandsCompatibile4MethodDefinitions(Node oNode1,
            Node oNode2, String sCurrentLineNumber) {
        if (dataTypeOfNode(oNode1) == compiler.iDataType_void
                && dataTypeOfNode(oNode2) == compiler.iDataType_void) {
            Debug.println("Line[" + sCurrentLineNumber
                    + "] . Method-definitions : Operands are type compatibile!!!");
            return true;
        }
       Debug.println("Line[" + sCurrentLineNumber
                + "] . Method-definitions: Operands contain type error(s)!!!");
        return false;
    }

    public boolean operandsCompatibile4ClassDefinitions(Node oNode1,
            Node oNode2, String sCurrentLineNumber) {
        if (dataTypeOfNode(oNode1) == compiler.iDataType_void
                && dataTypeOfNode(oNode2) == compiler.iDataType_void) {
            Debug.println("Line[" + sCurrentLineNumber
                    + "] . Class-definitions : Operands are type compatibile!!!");
            return true;
        }
        Debug.println("Line[" + sCurrentLineNumber
                + "] . Class-definitions: Operands contain type error(s)!!!");
        return false;
    }

    public boolean operandsCompatibile4ClassBody(Node oNode1,
            Node oNode2, String sCurrentLineNumber) {
        if (dataTypeOfNode(oNode1) == compiler.iDataType_void
                && dataTypeOfNode(oNode2) == compiler.iDataType_void) {
            Debug.println("Line[" + sCurrentLineNumber
                    + "] . Class-body : Operands are type compatibile!!!");
            return true;
        }
        Debug.println("Line[" + sCurrentLineNumber
                + "] . Class-body : Operands contain type error(s)!!!");
        return false;
    }

    public boolean operandsCompatibile4VarDeclarations(Node oNode1,
            Node oNode2, String sCurrentLineNumber) {
        if (dataTypeOfNode(oNode1) == compiler.iDataType_void
                && dataTypeOfNode(oNode2) == compiler.iDataType_void) {
                       return true;
        }
        Debug.println("Line[" + sCurrentLineNumber
                + "] . Var-Declarations : Operands contain type error(s)!!!");
        return false;
    }

    public boolean operandsCompatibile4Statements(Node oNode1,
            Node oNode2, String sCurrentLineNumber) {
        if (dataTypeOfNode(oNode1) == compiler.iDataType_void
                && dataTypeOfNode(oNode2) == compiler.iDataType_void) {
            Debug.println("Line[" + sCurrentLineNumber
                    + "] . Statements : Operands are type compatibile!!!");
            return true;
        }
       Debug.println("Line[" + sCurrentLineNumber
                + "] . Statements Block : Enclosing Statements contain type error(s)!!!");
        return false;
    }

    public boolean operandsCompatibile4ParameterList(Node oNode1,
            Node oNode2, String sCurrentLineNumber) {
        if (oNode1.type != compiler.iTypeOfAstNode_FormalParameter) {
            oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                    + "]. Parameter List : Parameter expected!!!");
            return false;
        }

        if (oNode2.type != compiler.iTypeOfAstNode_FormalParameter
                && dataTypeOfNode(oNode2) != compiler.iDataType_void) {
           Debug.println("Line[" + sCurrentLineNumber
                    + "]. Parameter List : Second Operand is wrongly typed!!!");
            return false;
        }

        Debug.println("Line[" + sCurrentLineNumber
                + "] . Parameter-List : Operands are type compatibile!!!");
        return true;

    }

    public boolean operandsCompatibile4LogicOperations(Node oNode1,
            Node oNode2, String sCurrentLineNumber) {
        if (dataTypeOfNode(oNode1) == compiler.iDataType_boolean) {
            if (dataTypeOfNode(oNode2) == compiler.iDataType_boolean) {
                Debug.println("Line[" + sCurrentLineNumber
                        + "] . Logic Expression : Operands are type compatibile!!!");
                return true;
            }
           oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                    + "]. Logic Expression : Second Operand should be bool type!!!\n");
            return false;

        }
       oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                + "]. Logic Expression : First Operand should be bool type!!!\n");
        return false;

    }

    public boolean operandsCompatibile4RelationalOperations(Node oNode1,
            Node oNode2, String sCurrentLineNumber) {
        // Expected operators : < , <= , > , >=
        if (!isNumericType(oNode1)) {
            oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                    + "]. Relational Expression : First Operand should be numeric type!!!\n");
            return false;
        }
        if (!isNumericType(oNode2)) {
           oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                    + "]. Relational Expression : Second Operand should be numeric type!!!\n");
            return false;
        }

        if (dataTypeOfNode(oNode1) != dataTypeOfNode(oNode2)) {
            oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                    + "]. Relational Expression : Operands must be same type!!!\n");
            return false;
        }
        Debug.println("Line[" + sCurrentLineNumber
                + "] . Relational Expression : Operands are type compatibile!!!");
        return true;
    }

    public boolean operandsCompatibile4RelationalEqualities(Node oNode1,
            Node oNode2, String sCurrentLineNumber) {
        // Expected operators : == , !=
        if ((dataTypeOfNode(oNode1) == compiler.iDataType_void)
                || (dataTypeOfNode(oNode1) == compiler.iDataType_error)) {
           oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                    + "]. Relational Expression : Type of First Operand is invalid!!!\n");

            return false;
        }
        if (dataTypeOfNode(oNode1) != dataTypeOfNode(oNode2)) {
            if (dataTypeOfNode(oNode1) != dataTypeOfNode(oNode2)) {
               oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                        + "]. Relational Expression : Operands must be same type!!!\n");
                return false;
            }
        }

        Debug.println("Line[" + sCurrentLineNumber
                + "] . Relational Expression : Operands are type compatibile!!!");

        return true;
    }

    public boolean operandsCompatibile4If_Then_Endif_Operation(Node oNode1,
            Node oNode2, String sCurrentLineNumber) {
        if ((dataTypeOfNode(oNode1) != compiler.iDataType_boolean)) {
            if (!isNumericType(dataTypeOfNode(oNode1))) {
               oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                        + "]. If-then-endif-block : logic expression is type invalid!!!\n");
                return false;
            }
        } // else logic expression is type valid... test for type validity of while statements

        if ((dataTypeOfNode(oNode2) != compiler.iDataType_void)) {
           Debug.println("Line[" + sCurrentLineNumber
                    + "If-then-endif-block :  Body is type invalid.");
            return false;
        }
        Debug.println("If-then-endif-block is type correct!!!");

        return true;

    }

    public boolean operandsCompatible4WhileOperation(Node oNode1, Node oNode2, String sCurrentLineNumber) {
        if ((dataTypeOfNode(oNode1) != compiler.iDataType_boolean)) {
            if (!isNumericType(dataTypeOfNode(oNode1))) {
                oInputPut.appendOutPutWindow("Line[" +  sCurrentLineNumber +
                        "]. While-block : logic expression is type invalid.  :"
                         + "\n");
                return false;
            }
        } // else logic expression is type valid... test for type validity of while statements

        if ((dataTypeOfNode(oNode2) != compiler.iDataType_void)) {
          Debug.println("Line[" + sCurrentLineNumber +  "]. While-block :  Body is type invalid.\n" );
            return false;
        }
        Debug.println("While-block is type correct!!!");

        return true;
    }

     public boolean operandsCompatibile4ModuloArithmeticOperation(Node oNode1,
                  Node oNode2 , String sCurrentLineNumber) {
         if ( !(dataTypeOfNode(oNode1) == compiler.iDataType_integer ||
               dataTypeOfNode(oNode1) == compiler.iDataType_long ) ){
             oInputPut.appendOutPutWindow("Line["+  sCurrentLineNumber +
                "]. Type of operand-1 not acceptable in modulo arithmetic!!!\n");
             return false ;
         }
         if ( !(dataTypeOfNode(oNode2) == compiler.iDataType_integer ||
               dataTypeOfNode(oNode2) == compiler.iDataType_long ) ){
             oInputPut.appendOutPutWindow("Line["+  sCurrentLineNumber +
                "]. Type of operand-2 not acceptable in modulo arithmetic!!!\n");
             return false ;
         }
         return true ;
     }
    public boolean operandsCompatibile4BinaryArithmeticOperation(Node oNode1,
                  Node oNode2 , String sCurrentLineNumber) {
        if (isNumericType(oNode1) && isNumericType(oNode2)) {
            return true;
        }
        oInputPut.appendOutPutWindow("Line["+  sCurrentLineNumber +
                "]. Performing Arithmetic on Non-compatible operands!!!\n");
        return false;
    }

    public boolean operandsCompatibile4AssignmentOperation(Node oNode1, Node oNode2, String sCurrentLineNumber) {
        if (dataTypeOfNode(oNode1) == dataTypeOfNode(oNode2)) {
           // System.out.println("Return from here???");
            return true; // similar types can be assigned
        }
        if (isNumericType(oNode1) && isNumericType(oNode2)) {
            
            if (greaterType(oNode1, oNode2) == dataTypeOfNode(oNode1)) {
                // assignment is possible since d rvalue can b upcast and d result stored in lvalue
               
                Debug.println("Line[" + sCurrentLineNumber
                        + "]. Operands are compatible for assignment!!!");
                return true;
            }
            oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                    + "]. Assignment of Rvalue would result in data loss!!!\n");
            return false;
        }
       oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                + "]. Assignment : non-compatibile lvalue and r-value!!! \n");
        return false;
    }

    public int greaterType(Node oNode1, Node oNode2) {
        int iType1 = dataTypeOfNode(oNode1);
        int iType2 = dataTypeOfNode(oNode2);
        return greaterType(iType1, iType2);
    }

    private int greaterType(int iType1, int iType2) {
        if (iType1 == iType2) {
            return iType1;
        }

        if (isNumericType(iType1) && isNumericType(iType2)) {
            if (iType1 < iType2) {
                return iType2;
            } else {
                return iType1;
            }
        }
        return compiler.iDataType_void; // would rarely b executed as only numeric types would b passed
    }

    private boolean isNumericType(int iType) {
        switch (iType) {
            case compiler.iDataType_integer:
            case compiler.iDataType_long:
            case compiler.iDataType_float:
                return true;
            default: //
                return false;
        }

    }

    private int dataTypeOfNode(Node oNode) {
        if (oNode.type == compiler.iTypeOfAstNode_Identifier
                || oNode.type == compiler.iTypeOfAstNode_FormalParameter) {
            int iDataType =
                    oSymbolTable.getDataTypeOfIdentifierFromSymbolTable(Integer.parseInt(oNode.n_identification));
            return iDataType;
        } else if (oNode.type == compiler.iTypeOfAstNode_NumberLiteral) {
            if (oNode.n_identification.contains(".")) {
                return compiler.iDataType_float;
            } else {
                return compiler.iDataType_integer;
            }
        } else if (oNode.type == compiler.iTypeOfAstNode_StringLiteral) {
            return compiler.iDataType_string;
        }
        else if (oNode.type == compiler.iTypeOfAstNode_BooleanLiteral) {
            return compiler.iDataType_boolean;
        }
        else if (isExpressiveOperator(oNode)) {
            return oSymbolTable.getDataTypeOfIdentifierFromSymbolTable(oNode.iSymbolTableIndex);
        }

        return oNode.iDataType; // for other categories of nodes on the Ast Tree...this stores dataType
    }

    private boolean isNumericType(Node oNode) {

        if (oNode.type == compiler.iTypeOfAstNode_Identifier
                || oNode.type == compiler.iTypeOfAstNode_FormalParameter) {
            int iDataType =
                    oSymbolTable.getDataTypeOfIdentifierFromSymbolTable(Integer.parseInt(oNode.n_identification));
            switch (iDataType) {
                case compiler.iDataType_integer:
                case compiler.iDataType_long:
                case compiler.iDataType_float:
                    return true;
                default: //
                    return false;
            }
        } else if (oNode.type == compiler.iTypeOfAstNode_NumberLiteral) {
            return true; // node is numeric
        } else if (isExpressiveOperator(oNode)) {
            return (isNumericType(oSymbolTable.getDataTypeOfIdentifierFromSymbolTable(oNode.iSymbolTableIndex)));

        } else {
            return false;
        }


    }

    private boolean isExpressiveOperator(Node oNode) {
        if (oNode.n_identification.trim().equals("+")) {
            return true;
        } else if (oNode.n_identification.trim().equals("-")) {
            return true;
        } else if (oNode.n_identification.trim().equals("*")) {
            return true;
        } else if (oNode.n_identification.trim().equals("/")) {
            return true;
        } else if (oNode.n_identification.trim().equals("&&")) {
            return true;
        } else if (oNode.n_identification.trim().equals("||")) {
            return true;
        } else if (oNode.n_identification.trim().equals("<")) {
            return true;
        } else if (oNode.n_identification.trim().equals("<=")) {
            return true;
        } else if (oNode.n_identification.trim().equals(">")) {
            return true;
        } else if (oNode.n_identification.trim().equals(">=")) {
            return true;
        } else if (oNode.n_identification.trim().equals("==")) {
            return true;
        } else if (oNode.n_identification.trim().equals("!=")) {
            return true;
        }

        return false;
    }
}
