/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yipada;

public class StackOfNodes {
    final static int iMaxNoOfNodesOnStack = 10000;
    private Node aListOfNodes[];
    private int iIndexOfTopNodeOnStack = -1 ;

    public StackOfNodes(){
     aListOfNodes  = new Node[iMaxNoOfNodesOnStack];
    }
    public boolean push(Node oNode){
        if (!Full()){
            // add node to stack
            iIndexOfTopNodeOnStack = iIndexOfTopNodeOnStack + 1 ;
            aListOfNodes[iIndexOfTopNodeOnStack] = oNode ;
            return true ;
        }
        return false ;
    }

    public Node pop(){
     if (!Empty())   {
         Node oNode =  aListOfNodes[iIndexOfTopNodeOnStack] ;
         return oNode ;
     }
     return null;
    }
    private boolean Full(){
        if ( iIndexOfTopNodeOnStack >= iMaxNoOfNodesOnStack) return true ;
        return false ;
    }

    private boolean Empty(){
        if ( iIndexOfTopNodeOnStack < 0 ) return true ;
        return false;
    }

}
