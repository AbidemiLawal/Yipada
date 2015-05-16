/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yipada;

import java.io.* ;
public class ReaderThread extends Thread{
  ChildDataCallBack cd ;
  BufferedReader reader ;
  public ReaderThread(BufferedReader reader , ChildDataCallBack cd){
      this.reader =reader ;
      this.cd =cd ;
  }
  public void run(){
      String line = null ;
      try {
       while (( line = reader.readLine())!= null) {
           cd.writeCallback(line);
       }
      // System.out.println("reader is done ....???");
      }
      catch(Exception ie){

      }
  }
}
