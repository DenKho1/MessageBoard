import java.net.*;
import java.io.*;

public class Server {
   
    public static void main(String[] args)throws Exception{
		Users u=new Users();
        try{
			
			ServerSocket server = new ServerSocket(8008);
			System.out.println("waiting to receive message");
			
			while(true)
			{
				Socket client=server.accept();
				scThread tserver=new scThread(client,u);
				tserver.start();
				tserver.join();
			}
			
			}
			 catch(Exception e)
			{
					System.out.println(e);
			}
    }
}

class scThread extends Thread{
	Socket client;
	Users user;
	PrintWriter out;
	BufferedReader in;
	scThread(Socket c,Users u)
	{
		client=c;
		user=u;
	}
	
	public void run()
	{
	
		  try{
				Boolean online=true;
				DataInputStream in = new DataInputStream(client.getInputStream());
      			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			
				while(online)
				{
						
				String msg = in.readUTF();
				String trans=getCommand(msg);
				String name=getName(msg);
				if(trans.equals("register"))
				{
					if(name.equals(""))
					{
						out.writeUTF(ret_Code(201));
						out.flush();
					}
					else if(user.checkUser(name))//if the username does not exist
					{
						user.addUser(name);
						out.writeUTF(ret_Code(401));//accepted
						out.flush();
						user.getUsersForm();
					}

					else
					{
						out.writeUTF(ret_Code(502));//user already exists
						out.flush();
					}
				}

				else if(trans.equals("deregister"))
				{
		
					if(name.equals(""))
					{
						out.writeUTF(ret_Code(201));
						out.flush();
					}

					else if(!user.checkUser(name))//username exists
					{
						System.out.println("User "+name+" exiting...");
						user.removeUser(name);
						out.writeUTF(ret_Code(401));//accepted
						out.flush();
						user.getUsersForm();
						online=false;
					}

					else 
					{
						out.writeUTF(ret_Code(501));//user does not exist
						out.flush();
						online=false;
					}
				}
				
				else if(trans.equals("msg"))
				{
				
					if(name.equals("")||getMessage(msg).equals(""))
					{
						out.writeUTF(ret_Code(201));
						out.flush();
					}
					else if(!user.checkUser(name))
					{
						System.out.println("from "+name+" : "+ getMessage(msg));
						out.writeUTF(ret_Code(401));//accepted
						out.flush();
					}
					
					else
					{
						out.writeUTF(ret_Code(501));//user does not exist
						out.flush();
						online=false;
					}
				}

				else{
					
					System.out.println(msg);
					out.writeUTF(ret_Code(201));
					out.flush();
					online=false;
				}

			}
			
		in.close();
		out.close();
		client.close();
	}
         catch(Exception ex)
        {
                System.out.println("Something went wrong with the Server 2");
        }
			
	}

	
	public String getCommand(String message)//for server to translate command 
  {
    return message.split("\"")[3];
  }

  public String getName(String message)//for server to translate client's name
  {
    return message.split("\"")[7];
  }

  public static String getMessage(String message)//for server to translate client's message
  {
   return message.split("\"")[11];
  }

  public String ret_Code(int code)//Makes ret_code JSON code
  {
      return "{\"command\":\"ret_code\", \"code_no\":"+code+"}";
  }
	
}
