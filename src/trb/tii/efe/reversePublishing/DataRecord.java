/*
      Author : Garrett Fermoyle
      Date : Jul 30, 2001
      Description : The DataInteract class is a the class that the reverse publishing
                     System uses to connect to and manipulate the database
 */

package trb.tii.efe.reversePublishing;

import java.util.*;

public class DataRecord extends Hashtable
{
    String tableName = new String("null");
    
    public DataRecord(String table)
    {
        tableName = table;
    }

    public String getTableName()
    {
        return tableName;
    }
}