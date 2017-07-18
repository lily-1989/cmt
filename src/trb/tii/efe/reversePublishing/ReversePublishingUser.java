package trb.tii.efe.reversePublishing;

public class ReversePublishingUser
{
    String username;
    String password;
    String market;
    String email;
    String usertype;
    public boolean isUser = false;
    
    public ReversePublishingUser()
    {}
    
    public void setUsertype(String u)
    {
      usertype = u;
    }
    
    public String getUsertype()
    {
      return usertype;
    }
    
    public void setUsername(String u)
    {
        username = u;
    }
    
    public void setPassword(String p)
    {
        password = p;
    }
    
    public void setMarket(String m)
    {
        market = m;
    }
    
    public void setEmail(String e)
    {
        email = e;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public String getMarket()
    {
        return market;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setIsUser(boolean u)
    {
        isUser = u;
    }
    
    public boolean isUser()
    {
      return isUser;
    }
}