package trb.tii.efe.tools;

import org.xml.sax.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.Date;
import oracle.jdbc.driver.*;
import oracle.xml.parser.v2.*;
import org.w3c.dom.*;

public class CommonName
{
    protected int communityid = 0;
    protected int feedid = 0;
    protected int id = 0;
    protected String name = "";
    
    public CommonName()
    {}
    
    public void setFeedid(int x)
    {
      feedid = x;
    }
    public int getFeedid()
    {
      return feedid;
    }

    public void setCommunityid(int x)
    {
      communityid = x;
    }
    public int getCommunityid()
    {
      return communityid;
    }
    
    public void setId(int x)
    {
      id = x;
    }
    public int getId()
    {
      return id;
    }
    
    public void setName(String x)
    {
        name = x;
    }
    public String getName()
    {
        return name;
    }
}    
    