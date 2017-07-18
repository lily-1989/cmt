/*
      Author : Garrett Fermoyle
      Date : Oct 25, 2000
      Description : The ClassifiedToolException class is used within the classified
                    tool context to manually generate and execute a feed export process.
                    The feed export process that is generated is run as an external
                    process.  
 */

package trb.tii.efe.reversePublishing;

import org.w3c.dom.*;
import java.io.*;
import oracle.xml.parser.v2.*;
import java.net.URL;
import java.net.*;
import java.util.*;
import org.xml.sax.SAXException;
import java.text.*;


public class ClassifiedToolException extends Exception
{
   public String display = new String ("Error running last task");
   public String returnLink = new String("index.jsp");
   public String contactInfo = new String("mailto:feedsupport@tribuneinteractive.com");

   public ClassifiedToolException()
   {
      
   }
   
   public ClassifiedToolException(String d)
   {
      display = d;
   }

   public String toString()
   {
      return new String("ClassifiedToolException : " + display);
   }
   
   public void setDisplay(String d)
   {
      display = d;
   }

   public String getDisplay()
   {
      return display;
   }

   public void setReturnLink(String r)
   {
      returnLink = r;
   }

   public String getReturnLink()
   {
      return returnLink;
   }

   public void setContactInfo(String c)
   {
      contactInfo = c;
   }

   public String getContactInfo()
   {
      return contactInfo;
   }
}
      