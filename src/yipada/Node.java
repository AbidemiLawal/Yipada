/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yipada;

/**
 *
 * @author user
 */
public class Node {

    public String n_identification;
    public int iSymbolTableIndex;  // used by operators that return a value
    public Node n_firstChild = null;
    public Node n_nextSibling = null;
    public int type; // used in classifying node into Identifier,NumberLiteral,StringLiteral,BooleanLiteral,
    public int iDataType;
    // used by operator node to classify type of data that node reference : int ,float,bool, void,string, type-error

    public Node(String sIdentification, int p_type, Node p_firstchild, Node p_secondChild) {
        n_identification = sIdentification;
        type = p_type;
        n_firstChild = null;
        n_nextSibling = null;
        addChildNode(p_firstchild);
        addChildNode(p_secondChild);
        iSymbolTableIndex = -1;

    }
    public boolean addChildNode(Node pChildNode) {
        if (pChildNode == null) {
            return false;
        }
        if (this.n_firstChild == null) {
            this.n_firstChild = pChildNode;
           
        } else {
            this.n_firstChild.addSibling(pChildNode);
        }

        return true;
    }

    public boolean addSibling(Node pNode) {
        if (pNode == null) {
            return false;
        }
        if (this.n_nextSibling == null) {
            this.n_nextSibling = pNode;
          
        } else {
            this.n_nextSibling.addSibling(pNode);
        }

        return true;
    }

    public Node getNextSibling(Node oNode) {
        if (oNode == null) {
            return null; // unassigned node
        }
        return oNode.n_nextSibling; // else
    }

    public void processChildRen(Node oNode) {
        if (oNode == null) {
          // Debug.println("Undefined node!!!");
            return;
        }  // node is null
        if (oNode.n_firstChild == null) {
            //Debug.println("A childless node!!!");
            return;
        } // node is childless
        // else proceed to print d children of this node
        Node oChild = oNode.n_firstChild;
        System.out.println(oChild.n_identification);
        processNode(oChild.n_nextSibling);


    }

    private void processNode(Node oNode) {
    }
}
