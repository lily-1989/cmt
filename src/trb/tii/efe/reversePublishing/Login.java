/*
      Author : Garrett Fermoyle
      Date : Aug 6, 2001
      Description : The Login class handles all functionality dealing with
                     logging into the reverse publishing system.  Its principle
                     method, login, takes a username and password and returns
                     a ReversePublishingUser object.
 */

package trb.tii.efe.reversePublishing;

import java.util.*;

public class Login
{
   DataInteract dataInteract;
   public static String TABLENAME = new String("car_rp_users");
   
   public static void main(String args[])
   {
      Login login = new Login("feedeng", "feedeng", "jdbc:oracle:thin:@ladybug.tii.trb:1521:oradev");
      ReversePublishingUser user = null;
      try
      {
      user = login.login("gfermoyle", "key5log");
      }
      catch(ClassifiedToolException e)
      {
         System.out.println(e.toString());
      }
      if (user.isUser)
      {
         System.out.println("successfully logged in");
      }
      else
      {
         System.out.println("user not found");
      }
   }
   
   public Login(String user, String pass, String host)
   {
      dataInteract = new DataInteract(user, pass, host);
   }
   
   public ReversePublishingUser login(String username, String password) throws ClassifiedToolException
	{
	   /*this method is used to check the database to see if a username/password
	     login is valid and retrieves the session information for the user, storing
	     this information in a ReversePublishingUser object and returning it.  If
	     the user is found in the database the isUser property of the ReversePublishingUser
	     object is set to true.  Otherwise it remains as default false
	     */

	   
	   
	   
	   System.out.println("user attempting to log in.");
	   System.out.println("user : " + username);
	   
	   //instantiate new ClassifiedToolUser object and set its username and
	   //password as they've been passed in as parameters to the method
	   ReversePublishingUser user = new ReversePublishingUser();
	   user.setUsername(username);
	   user.setPassword(password);
	   
	   
	   try
	   {
	   //setup query datafields and put into hashtable
	   Hashtable retrieveValues = new Hashtable();
	   DataField userField = new DataField("username_String");
	   DataField passField = new DataField("password_String");
	   DataField emailField = new DataField("email_String");
	   DataField usertypeField = new DataField("usertype_String");
	   retrieveValues.put(userField.getName(), userField);
	   retrieveValues.put(passField.getName(), passField);
	   retrieveValues.put(emailField.getName(), emailField);
	   retrieveValues.put(usertypeField.getName(), usertypeField);
	   
	   //setup query clauses and put into hashtable
	   Hashtable clauses = new Hashtable();
	   DataField usernameClause = new DataField("username_String", username);
	   DataField passwordClause = new DataField("password_String", password);
	   clauses.put(usernameClause.getName(), usernameClause);
	   clauses.put(passwordClause.getName(), passwordClause);
	   
	   //use DataInteract to run query
	   Vector results = dataInteract.select(retrieveValues, clauses, TABLENAME);
	   
	   if (results.isEmpty())
	   {
	      System.out.println("login failed.");
	   }
	   else
	   {
	      user.isUser = true;
	      Hashtable record = (Hashtable)results.firstElement();
	      emailField = (DataField)record.get("email");
	      usertypeField = (DataField)record.get("usertype");
	      
	      if (!emailField.isNull)
	      {
	         user.setEmail(emailField.getValue());
	      }
	      else
	      {
	         user.setEmail("NULL");
	      }
	      
	      if (!usertypeField.isNull)
	      {
	         user.setUsertype(usertypeField.getValue());
	      }
         else
         {
            user.setUsertype("general");
         }
	   }
	   }
	   catch(Exception e)
	   {
	      System.out.println("error logging in " + e.toString());
	      throw new ClassifiedToolException("error logging in : " + e.toString());
	   }
	   return user; 
	}
}
	