/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yipada;


public class Symbol {
    int iIndexInSymbolTable ;
    String sSymbol;
    String slocation;
    String sDatatype;
    String sclass_block = null;
    String sMethod_block = null ;
    String sType_of_symbol;
    String sValue = null;
    boolean AssignedValue = false ;
    boolean bIsAClass =  false ;
    boolean bIsAMethod = false;
    boolean bIsAFreeStoreSymbol = false ;

    public Symbol()
    {
        iIndexInSymbolTable = -1 ;
    }
    public String sContainingMethod(){
        return     sMethod_block ;

    }
}
