/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chomp;

import static chomp.NewJFrame.b1;
import static chomp.NewJFrame.b10;
import static chomp.NewJFrame.b11;
import static chomp.NewJFrame.b12;
import static chomp.NewJFrame.b13;
import static chomp.NewJFrame.b14;
import static chomp.NewJFrame.b15;
import static chomp.NewJFrame.b16;
import static chomp.NewJFrame.b17;
import static chomp.NewJFrame.b18;
import static chomp.NewJFrame.b19;
import static chomp.NewJFrame.b2;
import static chomp.NewJFrame.b20;
import static chomp.NewJFrame.b21;
import static chomp.NewJFrame.b22;
import static chomp.NewJFrame.b23;
import static chomp.NewJFrame.b24;
import static chomp.NewJFrame.b25;
import static chomp.NewJFrame.b26;
import static chomp.NewJFrame.b27;
import static chomp.NewJFrame.b28;
import static chomp.NewJFrame.b29;
import static chomp.NewJFrame.b3;
import static chomp.NewJFrame.b30;
import static chomp.NewJFrame.b31;
import static chomp.NewJFrame.b32;
import static chomp.NewJFrame.b33;
import static chomp.NewJFrame.b34;
import static chomp.NewJFrame.b35;
import static chomp.NewJFrame.b36;
import static chomp.NewJFrame.b4;
import static chomp.NewJFrame.b5;
import static chomp.NewJFrame.b6;
import static chomp.NewJFrame.b7;
import static chomp.NewJFrame.b8;
import static chomp.NewJFrame.b9;
import static chomp.NewJFrame.jPanel1;
import static chomp.NewJFrame.newGame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;


/**
 *
 * @author bolat
 */

    public class Client {
    private Socket socket;
   static ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private ServerListenThread ListenThread;
    private String server;
    private int port;
    private String username;
    static boolean myTurn = true;
    
    public Client(String server, int port, String username){
        this.server=server;
        this.port=port;
        this.username=username;
        
    }
    public boolean start(){
        try {
            socket=new Socket(this.server,this.port);
            sInput=new ObjectInputStream(socket.getInputStream());
            sOutput=new ObjectOutputStream(socket.getOutputStream());
            this.ListenThread=new ServerListenThread();
            this.ListenThread.start();
            String msg="Connection accepted "+socket.getInetAddress()+":"+socket.getPort();
            display(msg);
        } catch (Exception e) {
            display("Error connecting to server: "+e);
        }
        try {
            sOutput.writeObject(username);
        } catch (Exception e) {
            display("Error doing login: "+e);
            disconnect();
            return false;
        }
        return true;
    }
    //gelen mesajlari ekrana cikartmak icin
    public void display(String msg){
        NewJFrame.jTextField1.setText(NewJFrame.jTextField1.getText()+msg+"\n");
    }
    //servere mesaj gondermek icin
    public void sendMessage(String msg){
        try {
            sOutput.writeObject(msg);
        } catch (Exception e) {
            display("Exception writing to server: "+e);
        }
    }
    //obje gondermek icin
    public void sendMessage(Object msg){
        try {
            sOutput.writeObject(msg);
        } catch (Exception e) {
        }
    }
    public void disconnect(){
        try {
            if (sInput != null) {
                sInput.close();
            }
            if (sOutput != null) {
                sOutput.close();
            }
            if (this.ListenThread != null) {
                this.ListenThread.interrupt();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
        }
    }
    
    class ServerListenThread extends Thread{
        Client cl;
        //buttonlar arrayi
         JButton  butonlar[][]= {{b1,b2,b3,b4,b5,b6},
                                {b7,b8,b9,b10,b11,b12},
                                {b13,b14,b15,b16,b17,b18},
                                {b19,b20,b21,b22,b23,b24},
                                {b25,b26,b27,b28,b29,b30},
                                {b31,b32,b33,b34,b35,b36}};
        public void run(){
            while(true){
                try {
                   //gelen mesajlari aliyoruz
                    Object msg=sInput.readObject();
                    
                    if (msg instanceof String) {
                        String message=msg.toString();
                       
                        
                        if(message.equals("oponent turn")){
                          
                            myTurn = false;
                        }
                        else{
                            
                       
                        if(message.equals("you win")){
                            JOptionPane.showMessageDialog(jPanel1, "you win");
                            newGame.setVisible(true);
                        }
                       
                        else{
                            if(message.length()>3){
                                
                             NewJFrame.jTextField1.setText(NewJFrame.jTextField1.getText()+message+"\n");
                                
                                
                        }
                        }
                        //clienti bekliyoruz
                        if(message.equals("wait oponent")){
                            JOptionPane.showMessageDialog(jPanel1, message);
                        }
                        if(message.equals("you lose")){
                          JOptionPane.showMessageDialog(jPanel1, "you lose");
                          //new game butonin gosterecez
                          newGame.setVisible(true);
                        }
                        //new game basildiginda iconlari  set up edecez
                        if(message.equals("new game")){
                            for (int i = 0; i < 6; i++) {
                                for (int j = 0; j < 6; j++) {
                                    butonlar[i][j].setIcon(NewJFrame.icon1);
                                }
                            }
                            b31.setIcon(NewJFrame.icon2);
                          
                        }
                        //gelen mesaja gore icon degistirecez
                        if(message.equals("b1")){
                                
                       for (int i = 0; i < 1; i++) {
                       for (int j = 0; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
                     } 
                     }
                          
                        }
                        if(message.equals("b2")){
                    for (int i = 0; i < 1; i++) {
                    for (int j = 1; j < 6; j++) {
                butonlar[i][j].setIcon(NewJFrame.icon3);
                           }
                        }
                        }
                      if(message.equals("b8")){
                          for (int i = 0; i < 2; i++) {
           for (int j = 1; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                      }  
                       if(message.equals("b32")){
                           for (int i = 0; i < 6; i++) {
          for (int j = 1; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                       }
                       if(message.equals("b7")){
                           for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                       }
                       if(message.equals("b9")){
                            for (int i = 0; i < 2; i++) {
           for (int j = 2; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                       }
                       if(message.equals("b10")){
                           for (int i = 0; i < 2; i++) {
           for (int j = 3; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                       }
                       if(message.equals("b11")){
                          for (int i = 0; i < 2; i++) {
           for (int j = 4; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        } 
                       }
                       if(message.equals("b12")){
                           for (int i = 0; i < 2; i++) {
           for (int j = 5; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                       }
                       if(message.equals("b13")){
                           for (int i = 0; i < 3; i++) {
           for (int j = 0; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                       }
                       if(message.equals("b3")){
                         for (int i = 0; i < 1; i++) {
           for (int j = 2; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }  
                       }
                       if(message.equals("b4")){
                           for (int i = 0; i < 1; i++) {
           for (int j = 3; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                       }
                       if(message.equals("b5")){
                           for (int i = 0; i < 1; i++) {
           for (int j = 4; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                       }
                       if(message.equals("b6")){
                           for (int i = 0; i < 1; i++) {
           for (int j = 5; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                       }
                       if(message.equals("b14")){
                           for (int i = 0; i < 3; i++) {
           for (int j = 1; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                       }
                       if(message.equals("b15")){
                            for (int i = 0; i < 3; i++) {
           for (int j = 2; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                       }
                         if(message.equals("b16")){
                             for (int i = 0; i < 3; i++) {
           for (int j = 3; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                         }
                         if(message.equals("b17")){
                           for (int i = 0; i < 3; i++) {
           for (int j = 4; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }  
                         }
                         if(message.equals("b18")){
                              for (int i = 0; i < 3; i++) {
           for (int j = 5; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                         }
                         if(message.equals("b19")){
                             for (int i = 0; i < 4; i++) {
           for (int j = 0; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                         }
                         if(message.equals("b20")){
                             for (int i = 0; i < 4; i++) {
           for (int j = 1; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                         }
                         if(message.equals("b21")){
                             for (int i = 0; i < 4; i++) {
           for (int j = 2; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                         }
                         if(message.equals("b22")){
                           for (int i = 0; i < 4; i++) {
           for (int j = 3; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }  
                         }
                         if(message.equals("b23")){
                            for (int i = 0; i < 4; i++) {
           for (int j = 4; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        } 
                         }
                         if(message.equals("b24")){
                            for (int i = 0; i < 4; i++) {
           for (int j = 5; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        } 
                         }
                         if(message.equals("b25")){
                           for (int i = 0; i < 5; i++) {
           for (int j = 0; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }  
                         }   
                         if(message.equals("b26")){
                             for (int i = 0; i < 5; i++) {
           for (int j = 1; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                         }
                         if(message.equals("b27")){
                             for (int i = 0; i < 5; i++) {
           for (int j = 2; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        } 
                         }
                         if(message.equals("b28")){
                             for (int i = 0; i < 5; i++) {
           for (int j = 3; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                         }
                         if(message.equals("b29")){
                              for (int i = 0; i < 5; i++) {
           for (int j = 4; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
                         }
                         }
                         if(message.equals("b30")){
                             for (int i = 0; i < 5; i++) {
           for (int j = 5; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                               }
                         if(message.equals("b33")){
                          for (int i = 0; i < 6; i++) {
           for (int j = 2; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                         }
                         if(message.equals("b34")){
                            for (int i = 0; i < 6; i++) {
           for (int j = 3; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        } 
                         }
                         if(message.equals("b35")){
                             for (int i = 0; i < 6; i++) {
           for (int j = 4; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                         }
                         if(message.equals("b36")){
                             for (int i = 0; i < 6; i++) {
                       for (int j = 5; j < 6; j++) {
               
                butonlar[i][j].setIcon(NewJFrame.icon3);
            }
        }
                         }
                       }   
                    }
                } catch (IOException e) {
                    display("Server Kapatıldı "+e);
                    break;
                } catch (ClassNotFoundException e){
                    
                }
            }
        }
    }
}


