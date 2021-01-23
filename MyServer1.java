import java.net.*;
import java.io.*;
import java.util.*;

public class MyServer1
{
     public final static String UPDATE_USERS="updateuserslist:";
     ArrayList al=new ArrayList();
    ArrayList users=new ArrayList();
    ServerSocket ss;
    Socket sk;

    public MyServer1()
      {
         try
          {
            ss=new ServerSocket(10);
            System.out.println("server is ready for client request");
            while(true)
             {
                Socket sk=ss.accept();
                System.out.println("client is connected now.........");
                System.out.println("client id is =" + sk);
                
               Runnable r=new MyThread(sk,al,users);
              Thread t=new Thread(r);
                    t.start();
              } 
           }
         catch(Exception e){}
     }

   public static void main(String ars[])
    {
       new MyServer1();
    }
}

class MyThread implements Runnable
  {
            ArrayList al;
            Socket sk;
            String username;
            ArrayList users;
            DataInputStream din;
            DataOutputStream dout;

            MyThread(Socket sk,ArrayList al,ArrayList users)
               {
                  this.sk=sk;
                  this.al=al;
                  this.users=users;
               try
                {
                   din=new DataInputStream(sk.getInputStream());
                   username=din.readUTF();
                   al.add(sk);
                   users.add(username);
                   tellEveryOne(username+"logged");
                   sendNewUserList();
                   
                }
              catch(Exception e){}
             }

            public void run()
              {
                 String s=" ";
                   try
                    {
                          
                       do
                         { 
                             din=new DataInputStream(sk.getInputStream());
                             dout=new DataOutputStream(sk.getOutputStream());
                                               
                             s=din.readUTF();
                             System.out.println(s);
                            if(s.equals("stop"))
                              {
                              users.remove(username);
                             tellEveryOne(username+"logged out");
                             sendNewUserList();
                             al.remove(sk);
                             sk.close();
                              }  
                              else{                          
                         	  tellEveryOne(username+"said:"+s);
                                     }
                          }while(!s.equals("stop"));
                            
                       }  
                   catch(Exception e)
                      {
                         System.out.println(e);
                      }
  }
 
public void tellEveryOne(String s)
  {
     Iterator i=al.iterator();
     while(i.hasNext())
         {
             try
               { 
                   Socket sc=(Socket)i.next();
                   DataOutputStream dout=new DataOutputStream(sc.getOutputStream());
                   dout.writeUTF(s);
                   dout.flush();
                }
              catch(Exception e)
                {
                    System.out.println(e);
                }
          }
 }
public void sendNewUserList()
{
	tellEveryOne(MyServer1.UPDATE_USERS+users.toString());

}

}