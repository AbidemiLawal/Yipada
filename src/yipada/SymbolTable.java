/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yipada;

import java.io.*;
import java.util.*;
import goldengine.java.*;

public class SymbolTable {

    private Symbol aSymbols[];
    private int iMaxSizeOfTable = 10000;
    private int iIndexOfLastSymbolAddedToTable;
    GOLDParser oParser;
    private String sActiveClassName;
    private String sActiveMethodName;
    private int iTemporaryStoreIndex;

    public SymbolTable(GOLDParser p_Parser) {
        aSymbols = new Symbol[iMaxSizeOfTable];
        for (int i = 0; i < iMaxSizeOfTable; i++) {
            aSymbols[i] = new Symbol();
        }
        //{symbol,location,datatype,class-block,method-block , type-of-symbol }
        iIndexOfLastSymbolAddedToTable = -1;
        oParser = p_Parser;
        sActiveClassName = "";
        sActiveMethodName = "";

        iTemporaryStoreIndex = -1;
    }

    public void setAsClass(int indexOfSymbol) {
        if (indexOfSymbol > iIndexOfLastSymbolAddedToTable) {
            return ;
        }
        // mark symbol as a class
        aSymbols[indexOfSymbol].bIsAClass = true ;

    }
     public void setAsMethod(int indexOfSymbol) {
        if (indexOfSymbol > iIndexOfLastSymbolAddedToTable) {
            return ;
        }
        // mark symbol as a class
        aSymbols[indexOfSymbol].bIsAMethod = true ;

    }

    public boolean addSymbol(String s_Symbol, String sLineNumber, String sDataTypeOfToken,
            String sTypeOfToken, Symbol oSymb) {
        if (s_Symbol.trim().isEmpty()) {
            return false;
        }
        for (int i = 0; i < oParser.symbolTableCount(); i++) {
            if (s_Symbol.trim().equals(oParser.symbolTableEntry(i).getText().trim())) {
                // symbol is not an identifier
                return false;
            }
        }
        if (existInTable(s_Symbol, oSymb) == true) {
            return false;
        }

        iIndexOfLastSymbolAddedToTable = iIndexOfLastSymbolAddedToTable + 1;
        aSymbols[iIndexOfLastSymbolAddedToTable].sSymbol = s_Symbol;
        aSymbols[iIndexOfLastSymbolAddedToTable].slocation = sLineNumber;  // line number of defining occurence
        aSymbols[iIndexOfLastSymbolAddedToTable].sDatatype = sDataTypeOfToken;
        aSymbols[iIndexOfLastSymbolAddedToTable].sclass_block = this.sActiveClassName;
        aSymbols[iIndexOfLastSymbolAddedToTable].sMethod_block = this.sActiveMethodName;
        aSymbols[iIndexOfLastSymbolAddedToTable].sType_of_symbol = sTypeOfToken;

        oSymb.iIndexInSymbolTable = iIndexOfLastSymbolAddedToTable;

        return true;
    }

    public int getDataTypeOfIdentifierFromSymbolTable(int isymbolIndex) {
        try {
            return Integer.parseInt(aSymbols[isymbolIndex].sDatatype);
        } catch (Exception ex) {
            return compiler.iDataType_error;  // unknown identifer carries type-error
        }
    }

    public boolean existInParserTable(String s_Symbol) {
        for (int i = 0; i < oParser.symbolTableCount(); i++) {
            if (s_Symbol.trim().equals(oParser.symbolTableEntry(i).getText().trim())) {
                // symbol is not an identifer bcos it exists in Gold's table
                return true;
            }
        }
        return false;
    }

    public boolean existInTable(String pSymbol, Symbol pSym) {
        for (int i = 0; i <= iIndexOfLastSymbolAddedToTable; i++) {
            if (pSymbol.trim().equals(aSymbols[i].sSymbol.trim())) {
                // System.out.println("got here :" + pSymbol);
                if (this.sActiveClassName.equals(aSymbols[i].sclass_block.trim())
                        && this.sActiveMethodName.equals(aSymbols[i].sMethod_block.trim())) {
                    pSym.iIndexInSymbolTable = i;
                    return true;  // symbol already in table
                }
            }
        }
        return false;
    }

    public int getIndexOfLastSymbolAdded2Table() {
        return iIndexOfLastSymbolAddedToTable;
    }

    public String getLastSymbolAdded2Table() {
        return aSymbols[iIndexOfLastSymbolAddedToTable].sSymbol;
    }

    public String getSymbolFromIndex(int iIndexOfSymbol) {
        if (iIndexOfSymbol > iIndexOfLastSymbolAddedToTable) {
            return "";
        }

        return aSymbols[iIndexOfSymbol].sSymbol;
    }

    public Symbol getSymbolFromIndex_1(int iIndexOfSymbol) {
      if (iIndexOfSymbol > iIndexOfLastSymbolAddedToTable) {
            return null;
        }
        return aSymbols[iIndexOfSymbol];
    }

    public boolean setActiveClass(String pClassName) {
        this.sActiveClassName = pClassName;
        return true;
    }

    public boolean setActiveMethod(String pMethodName) {
        this.sActiveMethodName = pMethodName;
        return true;
    }

    public int getFreeStore(String sLineNumber, String sDataTypeOfToken,
            String sTypeOfToken, Symbol oSymb) {
        iTemporaryStoreIndex = iTemporaryStoreIndex + 1;

        String sIdentifier = "yipadaFree_" + iTemporaryStoreIndex;
        // this would ensure that temporaries would not clash with programmer invented names
        addSymbol(sIdentifier, sLineNumber, sDataTypeOfToken,
                sTypeOfToken, oSymb);
        oSymb.bIsAFreeStoreSymbol = true ;
        aSymbols[iIndexOfLastSymbolAddedToTable].bIsAFreeStoreSymbol = true ;

        return iIndexOfLastSymbolAddedToTable;
    }

    public boolean printTable() {
        Debug.println("[Symbol,data type . Containing Class , Containing Method,type-of-symbol]");

        for (int i = 0; i <= iIndexOfLastSymbolAddedToTable; i++) {
            System.out.println("[" + aSymbols[i].sSymbol + "," + aSymbols[i].sDatatype + ", "
                    + aSymbols[i].sclass_block + " , " + aSymbols[i].sMethod_block + ", "
                    + aSymbols[i].sType_of_symbol + "]");
        }
        return true;
    }
}
