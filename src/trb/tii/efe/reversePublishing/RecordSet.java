package trb.tii.efe.reversePublishing;

import java.lang.*;
import java.text.*;
import java.util.*;

public class RecordSet
{
   /********************************************************************

	The record set object handles all the functionality for indexing
        through a recordSet object.

        The bufferSize attribute, default to 10, is an int specifying the
        size of the buffer when accessing a specific subset of the
        recordSet.  By setting the index, the previous and next attributes
        are calculated based on the size of the buffer and the total
        number of records.

   **********************************************************************/


   public Vector records;
   public int index;
   public int recordCount = 0;
   public int bufferSize = 10;
   public int last;  //the index number of the last record to be displayed 
   public boolean hasNext;
   public boolean hasPrevious;
   public int next;
   public int previous;

   

   public RecordSet()
   {}

   public void setIndex(int n)
   {
      

      index = n;

      if ((index + bufferSize) >= recordCount)
      {
         hasNext = false;
         last = recordCount;
      }
      else
      {
         hasNext = true;
         last = index + bufferSize;
         next = index + bufferSize;
      }

      if ((index - bufferSize) < 0)
      {
         hasPrevious = false;
      }
      else
      {
         hasPrevious = true;
         previous = index - bufferSize;
      }
   }
         
   public void setRecords(Vector r)
   {
      records = r;
      recordCount = r.size();
   }
   
   public int getNext()
   {
      return next;
   }

   public int getPrevious()
   {
      return previous;
   }

   public int getIndex()
   {
      return index;
   }
   
   public Hashtable elementAt(int i)
   {
      return (Hashtable)records.elementAt(i);
   }
   
   public Hashtable findRecord(Hashtable clauses)
{
   /**
   
      This method returns one Hashtable of DataFields from the Vector of
      Hashtables corresponding to a record.  findRecord returns the first 
      Hashtable who has a key/value pair equal to those provided as
      parameters.  if no corresponding Hashtable, getRecord returns null
   
   **/
   
   Hashtable record = null;
   Hashtable temp;
   
   System.out.println("inside DataField.getRecord()");
   int i = 0;
   int length = records.size();
   boolean isNull = true;
   while (isNull && i < length)
   {
      System.out.println("inside isNull && i<length while");
      temp = (Hashtable)records.elementAt(i);
      i++;
      Enumeration keys = clauses.keys();
      boolean isValid = true;
      while(keys.hasMoreElements() && isValid)
      {
         System.out.println("inside keys.hasMoreElements and isValid while");
         String key = (String)keys.nextElement();
         String value = (String)clauses.get(key);
         if (temp.get(key) != null)
         {
            System.out.println("key : " + key);
            System.out.println("value : " + value);
            
            DataField field = (DataField)temp.get(key);
            System.out.println("getValue " + field.getValue());
            if (!field.getValue().equals(value))
            {
               isValid=false;
            }
         }
         else
         {
            isValid=false;
         }
         
         if (isValid) 
         {
            record=temp;
            isNull=false;
         }
         else
         {
	    record=null;
            isNull=true;
         }
      }
   }
   
   return record;
}

public Enumeration elements()
{
   return records.elements();
}
}
