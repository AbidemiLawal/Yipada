/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yipada;

public class ThreeAddressOperand {

    private String sOpCode;
    private String sIdentification_or_SymbolTableIndex;
    private int AstNode_type;

    public ThreeAddressOperand(String s_OpCode,
            String sIdentificationOrLocationInSymbolTable,
            int iTypeOfOperand) {
        sOpCode = s_OpCode;
        sIdentification_or_SymbolTableIndex = sIdentificationOrLocationInSymbolTable;
        AstNode_type = iTypeOfOperand;
    }

    public String getOpCode() {
        return sOpCode;
    }
public String getIdentification_or_SymbolTanleIndex(){
    return sIdentification_or_SymbolTableIndex ;
}
    public int getType() {
        return AstNode_type;
    }
}


