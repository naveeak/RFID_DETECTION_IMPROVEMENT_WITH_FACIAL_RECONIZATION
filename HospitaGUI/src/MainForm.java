
import com.mot.rfid.api3.GPO_PORT_STATE;
import com.mot.rfid.api3.RFIDReader;
import com.mot.rfid.api3.TagData;
import hospitagui.gettagid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.*;
import java.text.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ashok
 */
public class MainForm extends javax.swing.JFrame {

    /**
     * Creates new form MainForm
     */
     public String readtagarray=new String();
    public int i=0,safe=0,notsafe=0;
    public TagData[] tagdata=null;
    public TagData tagid=null;
    public RFIDReader reader =null;
    public String tag1 = null;
    public String tag3 = null;
    public String tag2 = null;
    public boolean b;
   
    
    public String[] uniqueids=new String[50];
    public MainForm() {
        initComponents();
        jButton5.disable();
       
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jpatientid = new javax.swing.JTextField();
        jmothername = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jattendername = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLoginbutton = new javax.swing.JButton();
        jinfantdob = new com.toedter.calendar.JDateChooser();
        jadmissiondate = new com.toedter.calendar.JDateChooser();
        jLoginbutton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(44, 62, 80));
        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PATIENT ID  ");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(400, 130, 120, 30);

        jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("DATE OF ADMISSION  :");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(570, 400, 220, 30);

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("INFANT DATE OF BIRTH :");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(20, 400, 230, 30);

        jpatientid.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        jpatientid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpatientidActionPerformed(evt);
            }
        });
        jPanel2.add(jpatientid);
        jpatientid.setBounds(590, 130, 160, 30);

        jmothername.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        jmothername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmothernameActionPerformed(evt);
            }
        });
        jPanel2.add(jmothername);
        jmothername.setBounds(270, 220, 220, 30);

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("MOTHER'S NAME          :");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(20, 220, 230, 30);

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("ATTENDER'S NAME        :");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(580, 220, 240, 30);

        jattendername.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        jattendername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jattendernameActionPerformed(evt);
            }
        });
        jPanel2.add(jattendername);
        jattendername.setBounds(850, 220, 230, 30);

        jButton2.setBackground(new java.awt.Color(34, 167, 240));
        jButton2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jButton2.setText("Get TagID");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);
        jButton2.setBounds(970, 280, 130, 29);

        jButton3.setBackground(new java.awt.Color(34, 167, 240));
        jButton3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jButton3.setText("Get TagID");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3);
        jButton3.setBounds(360, 280, 130, 29);

        jButton4.setBackground(new java.awt.Color(34, 167, 240));
        jButton4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jButton4.setText("Get TagID");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4);
        jButton4.setBounds(330, 470, 130, 29);

        jLoginbutton.setBackground(new java.awt.Color(192, 57, 42));
        jLoginbutton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jLoginbutton.setText("RESET");
        jLoginbutton.setOpaque(false);
        jLoginbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLoginbuttonActionPerformed(evt);
            }
        });
        jPanel2.add(jLoginbutton);
        jLoginbutton.setBounds(790, 620, 120, 29);
        jPanel2.add(jinfantdob);
        jinfantdob.setBounds(270, 400, 190, 30);
        jPanel2.add(jadmissiondate);
        jadmissiondate.setBounds(860, 400, 190, 30);

        jLoginbutton1.setBackground(new java.awt.Color(192, 57, 42));
        jLoginbutton1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jLoginbutton1.setText("LOGOUT");
        jLoginbutton1.setOpaque(false);
        jLoginbutton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLoginbutton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jLoginbutton1);
        jLoginbutton1.setBounds(40, 610, 120, 29);

        jButton5.setBackground(new java.awt.Color(34, 167, 240));
        jButton5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jButton5.setText("SUBMIT");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5);
        jButton5.setBounds(1010, 620, 130, 29);

        jPanel3.setBackground(new java.awt.Color(248, 148, 6));

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        jLabel4.setText("RFID ENROLLMENT");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(482, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(458, 458, 458))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        jPanel2.add(jPanel3);
        jPanel3.setBounds(0, 0, 1190, 100);

        jButton1.setBackground(new java.awt.Color(255, 255, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jButton1.setText("Face Enroll");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(193, 280, 127, 30);

        jProgressBar1.setDebugGraphicsOptions(javax.swing.DebugGraphics.FLASH_OPTION);
        jProgressBar1.setStringPainted(true);
        jPanel2.add(jProgressBar1);
        jProgressBar1.setBounds(420, 630, 146, 19);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1185, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
private void cleartextfield()
{
      jpatientid.setText(null);
       jmothername.setText(null);
        jattendername.setText(null);
        
           
}
    private void jpatientidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpatientidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jpatientidActionPerformed

    private void jmothernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmothernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmothernameActionPerformed

    private void jattendernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jattendernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jattendernameActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       
//JOptionPane.showMessageDialog(null,connectme());
tag2 = connectme();
        if(tag2==null)
        { 
             JOptionPane.showMessageDialog(null,"Retry");
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Success!");
        }
try{ reader.Dispose();}
catch(Exception e){}
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        tag1 = connectme();
        if(tag1==null)
        { 
             JOptionPane.showMessageDialog(null,"Retry");
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Success!");
        }
        try{ reader.Dispose();}
        catch(Exception e){}
        
        
// TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //JOptionPane.showMessageDialog(null,connectme());
        tag3 = connectme();
        if(tag3==null)
        { 
             JOptionPane.showMessageDialog(null,"Retry");
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Success!");
        }
        try{ reader.Dispose();}
        catch(Exception e){}

// TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jLoginbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLoginbuttonActionPerformed
        
       cleartextfield();
        // TODO add your handling code here:
    }//GEN-LAST:event_jLoginbuttonActionPerformed

    private void jLoginbutton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLoginbutton1ActionPerformed
         logoutverify p=new logoutverify();
         p.setVisible(true);
         p.pack();
         p.setLocationRelativeTo(null);
         p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         super.dispose();
         

        // TODO add your handling code here:
    }//GEN-LAST:event_jLoginbutton1ActionPerformed
    public int codegen()
    {
        Random rnd =new Random();
        int code = rnd.nextInt(10000);
        if(!(checkuniqueness(code)))
                {
                    return codegen();
                    
                }
        else
        {
            return code;
        }
    }
    private void faceid()
    {
             try{
            String a="";
    String command = "cmd /c start /b D:\\face_recoggg\\try1.py --cascade D:\\Python\\Lib\\site-packages\\cv2\\data\\haarcascade_frontalface_default.xml --output D:\\face_recoggg\\dataset\\" ;
    String b= command + a;
    Process p = Runtime.getRuntime().exec(b);
    p.waitFor();
    BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
    BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
          String line;
        while ((line = bri.readLine()) != null) {
            System.out.println(line);
          }
          bri.close();
          while ((line = bre.readLine()) != null) {
            System.out.println(line);
          }
          bre.close();
          p.waitFor();
       

    p.destroy();
        }
        catch(Exception e)
        {
           JOptionPane.showMessageDialog(null,"Try again"); 
    }
    }
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        
            
        Connection cnn; 
        Statement stt;
        ResultSet rst;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int cd = codegen();
        try
        {
            cnn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "qwerty");
            //JOptionPane.showMessageDialog(null,"Success!");
            stt=cnn.createStatement();
            //JOptionPane.showMessageDialog(null,jadmissiondate.getDate());
            
            String sql = "insert into details values (?,?,?,?,?,?)";
            PreparedStatement pst = cnn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(jpatientid.getText()));
            pst.setString(2,sdf.format(jadmissiondate.getDate()));
            pst.setString(3,jmothername.getText());
            pst.setString(4,jattendername.getText());
            pst.setString(5, sdf.format(jinfantdob.getDate()));
            pst.setInt(6, cd );
            pst.executeUpdate();
            sql="insert into attendertable1 values (?,?)";
            pst= cnn.prepareStatement(sql);
            pst.setString(1, tag1);
            pst.setInt(2, cd);
            pst.executeUpdate();
            pst= cnn.prepareStatement(sql);
            pst.setString(1, tag2);
            pst.setInt(2, cd);
             pst.executeUpdate();
            pst= cnn.prepareStatement(sql);
            pst.setString(1, tag3);
            pst.setInt(2, cd);
            pst.executeUpdate();
            sql="insert into infanttable values (?,?)";
            pst= cnn.prepareStatement(sql);
            pst.setString(1, tag3);
            pst.setInt(2, cd);
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Success!");
            cleartextfield(); 
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Try again");
        }
        
// TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        faceid();
          b=true;
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }


      public String connectme()
 {
     reader = new RFIDReader("192.168.0.100", 5084, 0);
    
     String tag=null;
     try
        {
          reader.connect();
          //System.out.println("connected"); 
          tag=tag_read();
         // TODO code application logic here
        }
     catch(Exception e)
        {
             tag=tag_read();
            // System.out.println("not connected");
             
        }
     return tag;
 }
      public boolean checkuniqueness(int i)
      {
          Connection cnn; 
        Statement stt;
        ResultSet rst;
        int j=0;
        try
        {
            cnn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "qwerty");
            
            stt=cnn.createStatement();
            rst=stt.executeQuery("Select * from infanttable where UniqueID = '"+i+"'");
           
            while(rst.next())
            {
                j = rst.getInt(2);
                
            }
            if(j!=0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        catch(Exception e)
                {
                  return false;   
                }
      }
      
public String tag_read()
{
            try
             {

                reader.Actions.Inventory.perform();
                java.lang.Thread.sleep(200);
                reader.Actions.Inventory.stop();
               
             }
            catch(Exception e)
            {
                        
            }
            tagdata = reader.Actions.getReadTags(50);
            if(tagdata!=null)
               {
                   System.out.println(" Tag read");
                  for(int nIndex = 0; nIndex < tagdata.length; nIndex++)
                  {

                      readtagarray=tagdata[nIndex].getTagID();
                  }
             }
            
            else
               {
                   readtagarray=null;
               }
    
        return readtagarray;
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JButton jLoginbutton;
    private javax.swing.JButton jLoginbutton1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    private com.toedter.calendar.JDateChooser jadmissiondate;
    private javax.swing.JTextField jattendername;
    private com.toedter.calendar.JDateChooser jinfantdob;
    private javax.swing.JTextField jmothername;
    private javax.swing.JTextField jpatientid;
    // End of variables declaration//GEN-END:variables
}