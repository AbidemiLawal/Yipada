/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yipada;

/**
 *
 * @author user
 */
public class AstTree {

    private InputOutput oInputPut;
    private Stack_of_AST_Nodes oStack;
    private SymbolTable oSymbolTable;
    private TypeChecker oTypeChecker;
    private boolean bErroBuildingTree = false;

    public boolean errorBuildingTree() {

        if (bErroBuildingTree == true) {
            return true;
        }

        if (rootNode().iDataType == compiler.iDataType_error) {
            return true;
        }

        return false;

    }

    private void setErrorBuildingTree() {
        bErroBuildingTree = true;
    }

    public AstTree(InputOutput oInputPut,
            SymbolTable oSymb) {
        this.oInputPut = oInputPut;
        oStack = new Stack_of_AST_Nodes(oSymb);
        int type_of_node = 1;
        oSymbolTable = oSymb;
        oTypeChecker = new TypeChecker(this.oInputPut, oSymbolTable);
    }

    public Node rootNode() {
        return oStack.poke();
        // this would return the top node on the stack...which is d root node on d tree
    }

    public boolean addleaf(String sIdentifier, int iTypeofLeafNode) {
        switch (iTypeofLeafNode) {
            case compiler.iTypeOfAstNode_Identifier:
                /* Debug.println("Adding identifier["
                + oSymbolTable.getSymbolFromIndex(Integer.parseInt(sIdentifier))
                + "] to Tree"); */
                oStack.push(new Node(sIdentifier, compiler.iTypeOfAstNode_Identifier, null, null));
                break; // identifier
            case compiler.iTypeOfAstNode_FormalParameter:
                /*  Debug.println("Adding formal parameter["
                + oSymbolTable.getSymbolFromIndex(Integer.parseInt(sIdentifier))
                + "] to tree"); */
                oStack.push(new Node(sIdentifier, compiler.iTypeOfAstNode_FormalParameter, null, null));
                break; // formal parameter 
            case compiler.iTypeOfAstNode_StringLiteral:
                // Debug.println("Adding string [" + sIdentifier + "] to Tree");
                oStack.push(new Node(sIdentifier, compiler.iTypeOfAstNode_StringLiteral, null, null));
                break; // string literal
            case compiler.iTypeOfAstNode_NumberLiteral:
                //Debug.println("Adding number [" + sIdentifier + "] to Tree");
                oStack.push(new Node(sIdentifier, compiler.iTypeOfAstNode_NumberLiteral, null, null));
                break;  // number literal
            case compiler.iTypeOfAstNode_BooleanLiteral:
                //Debug.println("Adding boolean literal [" + sIdentifier + "] to Tree");
                oStack.push(new Node(sIdentifier, compiler.iTypeOfAstNode_BooleanLiteral, null, null));
                break;  // number literal
            default:
                setErrorBuildingTree();
                return false; //  rare exception
        }
        return true;
    }  // addleaf method

    public boolean addOperatorNode(String sIdentifier, int iTypeOfOperatorNode,
            String sCurrentLineNumber) {
        try {
            switch (iTypeOfOperatorNode) {
                case compiler.iTypeOfAstNode_UnaryOperator:
                    return addUnaryOperatorNode(sIdentifier, sCurrentLineNumber);
                // unary operator node
                case compiler.iTypeOfAstNode_BinaryOperator:
                    return addBinaryOperatorNode(sIdentifier, sCurrentLineNumber);
                // binary operator node
                case compiler.iTypeOfAstNode_TernaryOperator:
                    return addTernaryOperatorNode(sIdentifier, sCurrentLineNumber);
                // ternary operator node
                case compiler.iTypeOfAstNode_NaryOperator:
                    return addNaryOperatorNode(sIdentifier, sCurrentLineNumber);
                case compiler.iTypeOfAstNode_NullOperator:
                    return addNullOperatorNode(sIdentifier);

                default:
                    setErrorBuildingTree();
                    return false;  // rare exception
            }
        } catch (Exception ex) {
            setErrorBuildingTree();
            System.out.println("addOperatorNode::Error adding node[operator] to tree : " + ex.getMessage());
            return false;
        }
    }

    private boolean addNullOperatorNode(String sIdentifier) {

        sIdentifier = sIdentifier.trim().toLowerCase();
        Node oNode;
        if (sIdentifier.equals("param-list")) {
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_NullOperator, null, null));
            oNode.iDataType = compiler.iDataType_void;
            oNode = null;
            return true;
        }
        setErrorBuildingTree();
        oNode = null;
        return false;
    }

    private boolean addUnaryOperatorNode(String sIdentifier, String sCurrentLineNumber) {
        sIdentifier = sIdentifier.trim().toLowerCase();
        if (sIdentifier.equals("++")) {
            //Debug.println("Adding Operator ++ to tree. Line : " + sCurrentLineNumber);
            addleaf("1", compiler.iTypeOfAstNode_NumberLiteral);
            return addBinaryOperatorNode("+", sCurrentLineNumber); // no invoke d binary operator
        } else if (sIdentifier.equals("--")) {
            //Debug.println("Adding Operator -- to tree . Line : " + sCurrentLineNumber);
            addleaf("1", compiler.iTypeOfAstNode_NumberLiteral);
            return addBinaryOperatorNode("-", sCurrentLineNumber); // noew invoke d binary operator
        } else if (sIdentifier.equals("-")) {  // unary minus or negation
            return true;  // would be added to source language in later edition
        } else if (sIdentifier.equals("console.writeline")) {
            //Debug.println("Adding Operator println to tree. Line" + sCurrentLineNumber);
            Node oNode;
            Node oNode1 = oStack.pop();
            oStack.push(oNode = new Node("console.writeline", compiler.iTypeOfAstNode_UnaryOperator,
                    oNode1, null));
            oNode.iDataType = compiler.iDataType_void;
            if (oNode1.iDataType == compiler.iDataType_error) {
                this.setErrorBuildingTree();  // if argument contains semantic error
            }
            oNode = null;
            return true;
        } else if (sIdentifier.equals("console.readline")) {
            // Debug.println("Adding Operator readln to tree");
            Node oNode;
            Node oNode1 = oStack.pop();  // any type of data could be printed
            oStack.push(oNode = new Node("console.readline", compiler.iTypeOfAstNode_UnaryOperator,
                    oNode1, null));
            oNode.iDataType = compiler.iDataType_void;
            oNode = null;
            return true;
        } else if (sIdentifier.equals("var-declaration")) {
            // Debug.println("Adding Operator var-declaration to tree");
            Node oNode;
            Node oNode1 = oStack.pop();  // type checking not applicable here
            oStack.push(oNode = new Node("var-declaration", compiler.iTypeOfAstNode_UnaryOperator,
                    oNode1, null));
            oNode.iDataType = compiler.iDataType_void;
            oNode = null;
            return true;
        } else {
            setErrorBuildingTree();
            return false; // rare exception
        }
    }

    public boolean isNumericType(Node oNode) {
        // return true if node is numeric else returns false
        if (oNode.type == compiler.iTypeOfAstNode_Identifier) {
            // get the data type stored in symbol table

            /* Debug.println(" data type : " + this.oSymbolTable.getDataTypeOfIdentifierFromSymbolTable(
            Integer.parseInt(oNode.n_identification)));

             */
        }
        return true;
    }

    private boolean addBinaryOperatorNode(String sIdentifier, String sCurrentLineNumber) {
        // pop the last two operands from stack...then check d operands for type compatibility...
        // ... then generate the 3-address-code ... then push new operator node to stack
        sIdentifier = sIdentifier.trim().toLowerCase();
        if (sIdentifier.equals("%")) {
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            Symbol oSymb = new Symbol();
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            if (!oTypeChecker.operandsCompatibile4ModuloArithmeticOperation(oNode1, oNode2, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            } else {
                oNode.iDataType = oTypeChecker.greaterType(oNode1, oNode2);
            }
            oSymbolTable.getFreeStore(sCurrentLineNumber,
                    Integer.toString(oNode.iDataType),
                    Integer.toString(compiler.iTypeOfAstNode_Identifier), oSymb);
            oNode.iSymbolTableIndex = oSymb.iIndexInSymbolTable;
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals("+")) {
            // Debug.println("Adding Binary Operator + to tree. Line :" + sCurrentLineNumber);
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            Symbol oSymb = new Symbol();
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            if (!oTypeChecker.operandsCompatibile4BinaryArithmeticOperation(oNode1, oNode2, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            } else {
                oNode.iDataType = oTypeChecker.greaterType(oNode1, oNode2);
            }
            oSymbolTable.getFreeStore(sCurrentLineNumber,
                    Integer.toString(oNode.iDataType),
                    Integer.toString(compiler.iTypeOfAstNode_Identifier), oSymb);
            oNode.iSymbolTableIndex = oSymb.iIndexInSymbolTable;
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals("-")) {
            //Debug.println("Adding Binary Operator - to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            Symbol oSymb = new Symbol();
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            if (!oTypeChecker.operandsCompatibile4BinaryArithmeticOperation(oNode1, oNode2, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            } else {
                oNode.iDataType = oTypeChecker.greaterType(oNode1, oNode2);
            }
            oSymbolTable.getFreeStore(sCurrentLineNumber,
                    Integer.toString(oNode.iDataType),
                    Integer.toString(compiler.iTypeOfAstNode_Identifier), oSymb);

            oNode.iSymbolTableIndex = oSymb.iIndexInSymbolTable;
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals("*")) {
            // Debug.println("Adding Binary Operator * to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            Symbol oSymb = new Symbol();
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            if (!oTypeChecker.operandsCompatibile4BinaryArithmeticOperation(oNode1, oNode2, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            } else {
                oNode.iDataType = oTypeChecker.greaterType(oNode1, oNode2);
            }
            oSymbolTable.getFreeStore(sCurrentLineNumber,
                    Integer.toString(oNode.iDataType),
                    Integer.toString(compiler.iTypeOfAstNode_Identifier), oSymb);

            oNode.iSymbolTableIndex = oSymb.iIndexInSymbolTable;
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals("/")) {
            //Debug.println("Adding Binary Operator / to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            Symbol oSymb = new Symbol();
            oStack.push(oNode = new Node(sIdentifier,
                    compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            if (!oTypeChecker.operandsCompatibile4BinaryArithmeticOperation(oNode1,
                    oNode2, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            } else {
                oNode.iDataType = oTypeChecker.greaterType(oNode1, oNode2);
            }

            if (oNode2.type == compiler.iTypeOfAstNode_NumberLiteral
                    && Integer.parseInt(oNode2.n_identification) == 0) {
                oNode.iDataType = compiler.iDataType_error;
                oInputPut.appendOutPutWindow("Line[" + sCurrentLineNumber
                        + "].Division by Zero!!!\n");
                setErrorBuildingTree();
            }
            oSymbolTable.getFreeStore(sCurrentLineNumber,
                    Integer.toString(oNode.iDataType),
                    Integer.toString(compiler.iTypeOfAstNode_Identifier), oSymb);

            oNode.iSymbolTableIndex = oSymb.iIndexInSymbolTable;
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                "<")) {
            //  Debug.println("Adding Binary Operator < to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oTypeChecker.operandsCompatibile4RelationalOperations(oNode1,
                    oNode2, sCurrentLineNumber);
            Symbol oSymb = new Symbol();
            oSymbolTable.getFreeStore(sCurrentLineNumber,
                    Integer.toString(compiler.iDataType_boolean),
                    Integer.toString(compiler.iTypeOfAstNode_Identifier),
                    oSymb);
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iSymbolTableIndex = oSymb.iIndexInSymbolTable;
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                "<=")) {
            //Debug.println("Adding Binary Operator <= to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oTypeChecker.operandsCompatibile4RelationalOperations(oNode1,
                    oNode2, sCurrentLineNumber);
            Symbol oSymb = new Symbol();
            oSymbolTable.getFreeStore(sCurrentLineNumber,
                    Integer.toString(compiler.iDataType_boolean),
                    Integer.toString(compiler.iTypeOfAstNode_Identifier),
                    oSymb);
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iSymbolTableIndex = oSymb.iIndexInSymbolTable;
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                ">")) {
            // Debug.println("Adding Binary Operator > to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oTypeChecker.operandsCompatibile4RelationalOperations(oNode1,
                    oNode2, sCurrentLineNumber);
            Symbol oSymb = new Symbol();
            oSymbolTable.getFreeStore(sCurrentLineNumber,
                    Integer.toString(compiler.iDataType_boolean),
                    Integer.toString(compiler.iTypeOfAstNode_Identifier), oSymb);
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iSymbolTableIndex = oSymb.iIndexInSymbolTable;
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                "==")) {
            //Debug.println("Adding Binary Operator == to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oTypeChecker.operandsCompatibile4RelationalEqualities(oNode1, oNode2, sCurrentLineNumber);
            Symbol oSymb = new Symbol();
            oSymbolTable.getFreeStore(sCurrentLineNumber,
                    Integer.toString(compiler.iDataType_boolean),
                    Integer.toString(compiler.iTypeOfAstNode_Identifier), oSymb);
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iSymbolTableIndex = oSymb.iIndexInSymbolTable;
            return true;

        } else if (sIdentifier.equals(
                ">=")) {
            // Debug.println("Adding Binary Operator >= to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oTypeChecker.operandsCompatibile4RelationalOperations(oNode1,
                    oNode2, sCurrentLineNumber);
            Symbol oSymb = new Symbol();
            oSymbolTable.getFreeStore(sCurrentLineNumber,
                    Integer.toString(compiler.iDataType_boolean),
                    Integer.toString(compiler.iTypeOfAstNode_Identifier), oSymb);
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iSymbolTableIndex = oSymb.iIndexInSymbolTable;
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                "!=")) {
            // Debug.println("Adding Binary Operator != to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oTypeChecker.operandsCompatibile4RelationalEqualities(oNode1, oNode2, sCurrentLineNumber);
            Symbol oSymb = new Symbol();
            oSymbolTable.getFreeStore(sCurrentLineNumber,
                    Integer.toString(compiler.iDataType_boolean),
                    Integer.toString(compiler.iTypeOfAstNode_Identifier), oSymb);
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iSymbolTableIndex = oSymb.iIndexInSymbolTable;
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                "&&")) {
            //Debug.println("Adding Binary Operator && to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oTypeChecker.operandsCompatibile4LogicOperations(oNode1, oNode2, sCurrentLineNumber);
            Symbol oSymb = new Symbol();
            oSymbolTable.getFreeStore(sCurrentLineNumber,
                    Integer.toString(compiler.iDataType_boolean),
                    Integer.toString(compiler.iTypeOfAstNode_Identifier), oSymb);
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iSymbolTableIndex = oSymb.iIndexInSymbolTable;
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                "||")) {
            // Debug.println("Adding Binary Operator || to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oTypeChecker.operandsCompatibile4LogicOperations(oNode1, oNode2, sCurrentLineNumber);
            Symbol oSymb = new Symbol();
            oSymbolTable.getFreeStore(sCurrentLineNumber,
                    Integer.toString(compiler.iDataType_boolean),
                    Integer.toString(compiler.iTypeOfAstNode_Identifier), oSymb);
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iSymbolTableIndex = oSymb.iIndexInSymbolTable;
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                "while")) {
            //Debug.println("Adding Binary Operator while to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iDataType = compiler.iDataType_void;
            if (!oTypeChecker.operandsCompatible4WhileOperation(oNode1, oNode2, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            }
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                "if-then-endif")) {
            //Debug.println("Adding Binary Operator if-then-endif to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iDataType = compiler.iDataType_void;
            if (!oTypeChecker.operandsCompatibile4If_Then_Endif_Operation(oNode1, oNode2, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            }
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                ":=")) { // assignment operator
            //Debug.println("Adding Binary Operator := to tree");
            Node oNode;
            Node oNode1 = oStack.pop(); // l-value
            Node oNode2 = oStack.pop();  // r-value
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iDataType = compiler.iDataType_void;
            if (!oTypeChecker.operandsCompatibile4AssignmentOperation(oNode1, oNode2, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            }
            //Debug.println("Assignment done : data type " + oNode.iDataType);
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                "param-list")) { // 1 or 2  parameters
            //Debug.println("Adding Non-Computable Binary Operator [param-list] to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator,
                    oNode1, oNode2));
            oNode.iDataType = compiler.iDataType_void;
            if (!oTypeChecker.operandsCompatibile4ParameterList(oNode1, oNode2, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            }
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                "statements")) { // one or more statement
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iDataType = compiler.iDataType_void;
            if (!oTypeChecker.operandsCompatibile4Statements(oNode1, oNode2, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            }
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                "var-declarations")) { // one or more statement
            //Debug.println("Adding Non-computable Binary Operator [var-declarations] to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iDataType = compiler.iDataType_void;
            if (!oTypeChecker.operandsCompatibile4VarDeclarations(oNode1, oNode2, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            }
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                "class-body")) {
            Node oNode;
            Node oNode2 = oStack.pop();  // method-definition(s)
            Node oNode1 = oStack.pop(); // var-declaration(s)
            oStack.push(oNode = new Node("class-body", compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iDataType = compiler.iDataType_void;
            if (!oTypeChecker.operandsCompatibile4ClassBody(oNode1, oNode2, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            }
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                "class-definitions")) { // one or more class definition
            //Debug.println("Adding Non-computable Binary Operator [class-definitions] to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iDataType = compiler.iDataType_void;
            if (!oTypeChecker.operandsCompatibile4ClassDefinitions(oNode1, oNode2, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            }
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                "method-definitions")) { // one ore more method-definition
            //Debug.println("Adding Non-computable Binary  Operator [method-definitions] to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iDataType = compiler.iDataType_void;
            if (!oTypeChecker.operandsCompatibile4MethodDefinitions(oNode1, oNode2, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            }
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else if (sIdentifier.equals(
                "program")) {
            // same as class-definitions since var declarations are nt stored on d tree
            Debug.println("Adding Non-computable Binary Operator [program] to tree");
            Node oNode;
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.iDataType = compiler.iDataType_void;
            if (!oTypeChecker.operandsCompatible4Program(oNode1, oNode2,
                    sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            }
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            return true;
        } else {
            setErrorBuildingTree();
            return false; // rare exception
        }
    }

    private boolean addTernaryOperatorNode(String sIdentifier, String sCurrentLineNumber) {
        sIdentifier = sIdentifier.trim().toLowerCase();
        if (sIdentifier.equals("if-then-else-endif")) {
            //Debug.println("Adding Ternary Operator if-then-else-endif to tree");
            Node oNode;
            Node oNode3 = oStack.pop();  // else statement
            Node oNode2 = oStack.pop();  // then statement
            Node oNode1 = oStack.pop(); // logic expression
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.addChildNode(oNode3);
            oNode.iDataType = compiler.iDataType_void;
            if (!oTypeChecker.operandsCompatibile4IfThenElseEndIfOperations(oNode1,
                    oNode2, oNode3, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;

            }
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            oNode3 = null;
            return true;
        } else if (sIdentifier.equals("class-definition")) { // [Access-modifier] [Class-Name] [Method definitions]
            // Debug.println("Adding Ternary Operator class-definition to tree");
            Node oNode;
            Node oNode3 = oStack.pop();  // class body
            Node oNode2 = oStack.pop();  // class name
            Node oNode1 = oStack.pop(); // access modifier
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator, oNode1, oNode2));
            oNode.addChildNode(oNode3);
            oNode.iDataType = compiler.iDataType_void;
            if (!oTypeChecker.operandsCompatibile4ClassDefinition(oNode1,
                    oNode2, oNode3, sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            }

            oNode = null;
            oNode1 = null;
            oNode2 = null;
            oNode3 = null;
            return true;

        } else {
            setErrorBuildingTree();
            return false;
        }
    }

    private boolean addNaryOperatorNode(String sIdentifier, String sCurrentLineNumber) {
        sIdentifier = sIdentifier.trim().toLowerCase();

        if (sIdentifier.equals("method-definition")) { // pop 5 arguments from stack n build/add node
            // [Access-modifier]  [Return-type] [method-name] [parameter-list] [statements]
            Node oNode;
            Node oNode5 = oStack.pop();
            Node oNode4 = oStack.pop();
            Node oNode3 = oStack.pop();
            Node oNode2 = oStack.pop();
            Node oNode1 = oStack.pop();
            oStack.push(oNode = new Node(sIdentifier, compiler.iTypeOfAstNode_BinaryOperator,
                    oNode1, oNode2));

            oNode.addChildNode(oNode3);
            oNode.addChildNode(oNode4);
            oNode.addChildNode(oNode5);

            oNode.iDataType = compiler.iDataType_void;

            if (!oTypeChecker.operandsCompatibile4MethodDefinition(oNode1, oNode2,
                    oNode3, oNode4, oNode5,
                    sCurrentLineNumber)) {
                oNode.iDataType = compiler.iDataType_error;
            }
            oNode = null;
            oNode1 = null;
            oNode2 = null;
            oNode3 = null;
            oNode4 = null;
            oNode5 = null;
            return true;
        }
        setErrorBuildingTree();
        return false;


    }

    public boolean printTree() {
        Node oNode = oStack.poke();

        if (oNode == null) {
            Debug.println("Abstract syntax tree is empty!!!");
            return false;
        }
        Debug.println("Printing Ast Tree...");
        return true; // walks the tree and print nodes for debugging sake


    }

    private boolean processNode(Node oNode) {
        StackOfNodes oStack = new StackOfNodes();
        return true;

    }
} // class AstTree

