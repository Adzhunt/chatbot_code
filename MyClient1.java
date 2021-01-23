import java.net.*;
import java.io.*;
import String.*;

public class MyClient1
{

  Socket sk;
  DataInputStream din;
  DataOutputStream dout;
  String s1=" ";

  public MyClient1()

  {
     try
        {
            sk=new Socket("localhost",10);
            System.out.println("client is connected now.........");
            System.out.println("Enter username");
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            s1=br.readLine();

                                   
           din=new DataInputStream(sk.getInputStream());
           dout=new DataOutputStream(sk.getOutputStream());
           dout.writeUTF(s1);
           dout.flush();
           chating();
        }
     catch(Exception e)
       {
         System.out.println(e);
       }
  }

 public void chating() throws IOException
    {
       My m=new My(din,dout);
       Thread t=new Thread(m);
       t.start();
       BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
       String s1;
           do
              {
                 s1=br.readLine();
                dout.writeUTF(s1);
                dout.flush();
              }while(!s1.equals("stop"));

    }

 public static void main(String args[])
   {
      new MyClient1();
   }
}
 
 class My implements Runnable
     {
        DataInputStream din;
        DataOutputStream dout;

           My(DataInputStream din,DataOutputStream dout)
              {
                 this.din=din;
                 this.dout=dout;
              }

    public void run()
       {
           String s2=" ";
     
           try
              {
                  do
                   {
                     
                        s2=din.readUTF();
                        System.out.println(s2);
                       
                    } while(!s2.equals("stop"));
                 }catch(Exception e)
                    {
                       System.out.println("reader stop ho gaya");
                    }
                }                    
            }
     
