import java.util.Scanner;

//Client

public class Main {
  
 public static String regDeregCode(String comm,String name)//makes register and deregister JSON code
    {
        return "{\"command\":\""+comm+"\", \"username\":\""+name+"\"}";
    }

  public static String messageCode(String name,String mess)//makes message JSON code
    {
        return "{\"command\":\"msg\", \"username\":\""+name+"\", \"message\":\""+mess+"\"}";
    }
 public static String getCode(String s)
    {
        String hold =s.split(":",3)[2];
        return hold.substring(0, hold.length() - 1);
    }

  
  
  public static void main(String[] args) {
    Client client= new Client();
    String IP;
    String username="";
    boolean online=true;
    String message;
    Scanner sc = new Scanner(System.in);
        
   
      System.out.print("Enter IP address of message board server: ");
      IP=sc.next();
      //use Connect to connect to server
      client.startConn(IP,8008);
      
   

     System.out.print("Enter preferred username: ");
     username=sc.next();
     String hold=client.sendMessage(regDeregCode("register",username)); //code response
     String resp=getCode(hold);
    if(resp.equals("502"))
    {
      online=false;
      System.out.println("User account already exists in chat room!");
    }

    else
    {
      online=true;
    }

    if(resp.equals("401"))
    {
      while(online)
      {
        System.out.print("Enter Message: ");
        message=sc.next();
        sc.nextLine();
        hold=client.sendMessage(messageCode(username,message));
        resp=getCode(hold);
        
      if(message.equals("bye"))
      {
        online=false;
        hold=client.sendMessage(regDeregCode("deregister",username));
        resp=getCode(hold);

        if(resp.equals("401"))
        {
           System.out.println("Disconnecting");
        }
      }
      

      }
    }
    
    else
    {
      System.out.print("Unsuccessful registration. Exiting...");
    }

    sc.close();
    client.stopConnection();
  }
}