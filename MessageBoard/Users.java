
import java.util.*;

public class Users
{
   private HashMap<String,Integer> users;
   private Integer numUsers;

   public Users()
   {
    users= new HashMap<String,Integer>();
    numUsers=0;
   }

   public synchronized void addUser(String n)
   {
        if(checkUser(n))
        {
            users.put(n,numUsers);
            numUsers++;
       }

     else
        {
          System.out.println(n+" has already exists in chat room!");
        }
   }

   public String[] getUsernames()
   {
       String[] usernames=new String[numUsers];
        usernames=users.keySet().toArray(new String[users.size()]);
        return usernames;
   }

  public void getUsersForm()
  {
    String[] names=getUsernames();

    System.out.print("Users in message board: [");
    
    for(int i=0;i<names.length;i++)
    {
      
        if(i==names.length-1)
          System.out.print(names[i]);
        else
          System.out.print(names[i]+",");
    }
    System.out.println("]");

  }

  public synchronized void removeUser(String s)
  {
    if(!checkUser(s))
    {  
      users.remove(s);
      numUsers--;
    }

    else{
      System.out.println(s+" does not exist");
    }
  }

  public boolean checkUser(String name)
  {
    if(users.get(name)==null && name!="")//name not in log
      return true;
    else
      return false;
  }

  public void getFromMessage(String user,String message)
  {
    if(!checkUser(user))
     System.out.println("from "+user+" : " + message);
  }

}
