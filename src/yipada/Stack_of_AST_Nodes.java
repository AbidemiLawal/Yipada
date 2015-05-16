/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yipada;
//import java.u

/**
 *
 * @author user
 */
public class Stack_of_AST_Nodes {

    private Node aNodes[];
    private SymbolTable oSymbTable ;
   // private String sIdentifer ;

    private int iMaxSizeOfStack = 20000 ;
    private int iIndexOfTopNodeOnStack = -1 ;

    public Stack_of_AST_Nodes(SymbolTable oSymb)
    {
         aNodes = new Node[iMaxSizeOfStack];
         oSymbTable = oSymb ;
    }

    public boolean push(Node oNode)
    {
        if ( iIndexOfTopNodeOnStack < iMaxSizeOfStack)
        {
            iIndexOfTopNodeOnStack = iIndexOfTopNodeOnStack + 1 ;
            aNodes[iIndexOfTopNodeOnStack] = oNode ;

            String sIdentifier = "" ;
            if (oNode.type == compiler.iTypeOfAstNode_Identifier ||
                    oNode.type == compiler.iTypeOfAstNode_FormalParameter) {
                sIdentifier = this.oSymbTable.getSymbolFromIndex(Integer.parseInt(oNode.n_identification));
            } else sIdentifier =oNode.n_identification ;

            Debug.println("Stack Pushed : " + sIdentifier  );
            
            return true ;  // node pushed into stack
        }
        return false ;  // node could not be pushed into stack
    }

    public Node pop()
    {
        if (iIndexOfTopNodeOnStack >=0)
        {
            Node oNode =  aNodes[iIndexOfTopNodeOnStack] ;

            iIndexOfTopNodeOnStack = iIndexOfTopNodeOnStack - 1 ;
            String sIdentifier = "" ;
            if (oNode.type == compiler.iTypeOfAstNode_Identifier ||
                    oNode.type == compiler.iTypeOfAstNode_FormalParameter) {
                sIdentifier = this.oSymbTable.getSymbolFromIndex(Integer.parseInt(oNode.n_identification));
            } else sIdentifier =oNode.n_identification ;

           Debug.println("Stack Popped : " + sIdentifier  );
            return oNode ;
        }
        return null ;
    }

    public Node poke(){
        if (iIndexOfTopNodeOnStack >=0) 
            return aNodes[iIndexOfTopNodeOnStack] ;
        else return null ;
    }
}

 class c

{
  public c(){ }
}

