/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yipada;
 
public class DataManager implements ChildDataCallBack{

    InputOutput oInputPut ;
    public DataManager(InputOutput oInputPut){
        this.oInputPut = oInputPut ;
    }
 public void writeCallback(String data){
    oInputPut.appendOutPutWindow(data + "\n");
 }
}
