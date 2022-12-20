/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitagui;

import com.mot.rfid.api3.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Ashok
 */
public class gettagid {
    public String readtagarray[]=new String[50];
    public int i=0,safe=0,notsafe=0;
    public TagData[] tagdata=null;
    public TagData tagid=null;
    public RFIDReader reader =null;
    
    public String[] uniqueids=new String[50];
    
    /**
     *
     * @return
     */
    public String connectme()
 {
     
     reader = new RFIDReader("192.168.0.100", 5084, 0);
    
    
     try
        {
          reader.connect();
          //System.out.println("connected"); 
         return  tag_read();
         // TODO code application logic here
        }
     catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"inside inventorqqqdeey");
             return  tag_read();
            // System.out.println("not connected");
             
        }
 }
public String tag_read()
{

       
JOptionPane.showMessageDialog(null,"inside inventoryqqq");
            try
             {

                reader.Actions.Inventory.perform();
                java.lang.Thread.sleep(200);
                reader.Actions.Inventory.stop();
             }
            catch(Exception e)
            {
                 JOptionPane.showMessageDialog(null,"inside inventory");
                return null;//System.out.println("no actions performed");
            }

   
 
    
            tagdata = reader.Actions.getReadTags(50);
            if(tagdata!=null)
               {
                   System.out.println(" Tag read");
                  for(int nIndex = 0; nIndex < tagdata.length; nIndex++)
                  {

                      readtagarray[i]=tagdata[nIndex].getTagID();
                      //  System.out.println(readtagarray[i]);
                       
                  }
            }
            JOptionPane.showMessageDialog(null,readtagarray[i] );
            return readtagarray[i];
       }
}
    

