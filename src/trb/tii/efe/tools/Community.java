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

public class Community
{
    protected String market = "";
    protected int feedid = 0;
    protected int id = 0;
    protected String neighborhood = "";
    protected String community = "";
    protected String city = "";
    protected String county = "";
    protected String state = "";
    
    public Community()
    {}

    public void setMarket(String x)
    {
        market = x;
    }
    public String getMarket()
    {
        return market;
    }


    public void setFeedid(int x)
    {
      feedid = x;
    }
    public int getFeedid()
    {
      return feedid;
    }

    public void setId(int x)
    {
      id = x;
    }
    public int getId()
    {
      return id;
    }
    
    public void setNeighborhood(String x)
    {
        neighborhood = x;
    }
    public String getNeighborhood()
    {
        return neighborhood;
    }
    
    public void setCommunity(String x)
    {
        community = x;
    }
    public String getCommunity()
    {
        return community;
    }

    public void setCity(String x)
    {
        city = x;
    }
    public String getCity()
    {
        return city;
    }
    
    public void setCounty(String x)
    {
        county = x;
    }
    public String getCounty()
    {
        return county;
    }
    
    public void setState(String x)
    {
        state = x;
    }
    public String getState()
    {
        return state;
    }
    
    
}    
    
    
    
