import java.net.*;
import java.io.*;

public class Client {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream  in;

    public void startConn(String ip, int port)
    {
        try
        {
        socket = new Socket(ip, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        }catch(UnknownHostException e){
                 System.out.println("Something went wrong1.");
        }
        catch(IOException e)
        {
                 System.out.println("Something went wrong2.");
        }
    }

    public String sendMessage(String msg) {

      
        
        try{
        out.writeUTF(msg);
        out.flush();
        String resp = in.readUTF();//getCode
        return resp;
          
        }catch(IOException e)
        {
        return "";
        }
    }
  
    public void stopConnection() {
          try{
          in.close();
          out.close();
          socket.close();
         }catch(IOException e){
            System.out.println("Something went wrong3.");
         }

        }
}