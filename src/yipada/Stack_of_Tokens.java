/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yipada;

/**
 *
 * @author user
 */
public class Stack_of_Tokens {

    private String aNodes[];
    private int iMaxSizeOfStack = 20000;
    private int iIndexOfTopNodeOnStack = -1;
    private int iTestPop;

    public Stack_of_Tokens() {
        aNodes = new String[iMaxSizeOfStack];
    }

    public boolean push(String oNode) {
        if (iIndexOfTopNodeOnStack < iMaxSizeOfStack) {
            iIndexOfTopNodeOnStack = iIndexOfTopNodeOnStack + 1;
            aNodes[iIndexOfTopNodeOnStack] = oNode;
            // System.out.println("push => " +  oNode);
            iTestPop = iIndexOfTopNodeOnStack;
            return true;  // node pushed into stack
        }
        return false;  // node could not be pushed into stack
    }

    public int size() {
        if (iIndexOfTopNodeOnStack < 0) {
            return 0;
        }
        return iIndexOfTopNodeOnStack + 1;
    }

     public String pop() {
        if (iIndexOfTopNodeOnStack >= 0) {
            String oNode = aNodes[iIndexOfTopNodeOnStack];
            iIndexOfTopNodeOnStack = iIndexOfTopNodeOnStack - 1;
           // System.out.println("pop => " + oNode);
            return  oNode;
        }


        return "";
    }
/*
public int size2(){
    return iTestPop + 1 ;
}
    public String pop2() {
        int i;
        String sIndex = Integer.toString(iTestPop);
        i = iTestPop;
        iTestPop = iTestPop - 1;
        return sIndex + "-" + aNodes[i];

    }*/

   

    public boolean empty() {
        if (iIndexOfTopNodeOnStack < 0) {
            return true;
        }
        return false;
    }
}
