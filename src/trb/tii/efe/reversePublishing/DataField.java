package trb.tii.efe.reversePublishing;

import java.lang.*;
import java.text.*;
import java.util.*;

/***************************************************************************

The DataField class represents one data field from the database.  It holds 
all of the necessary meta-information about the field.  It can also convert
the string value that is given as part of its constructor string, "datastring",
into its appropriate java object.  The present version supports the following
java objects and types:

Integer
Long
String
Date

****************************************************************************/

public class DataField implements Cloneable
{
public static final String INTEGER_TYPE = "Integer";
public static final String LONG_TYPE = "Long";
public static final String DOUBLE_TYPE = "Double";
public static final String STRING_TYPE = "String";
public static final String DATE_TYPE = "Date";
public static final String LONGCHAR_TYPE = "LongChar";
public static final String CLOB_TYPE = "CLOB";

public String name = new String("null"); //The name of the field
public String value = new String("null");  //The value of the field AS A STRING
public String dateFormat = "MM/dd/yyyy";  //date format, defaulted (only   
                                          //used when javaType = "Date")
public String javaType;  //the java type 

//specifies whether this datafield has a value associated with it.  If no value,
//then isNull = true;
public boolean isNull = true;

public boolean isCurrentTime = false;

//The operator "oprtr" specifies the operator to be used when comparing
//this field in the clause of a query, update or delete statement
//The default is "=", but this is configurable for such things as
//"like", ">", "<", etc.
public String oprtr = new String("=");


public static void main(String args[])
{
   
   try
   {
      Hashtable record = new Hashtable();
      DataField make = new DataField("make_String");
      DataField model = new DataField("model_String");
      DataField class1 = new DataField("class_Integer");
      DataField classname = new DataField("classname_String");
      DataField lastupdated = new DataField("lastupdated_Date");
      record.put("make", make);
      record.put("model", model);
      record.put("class", class1);
      record.put("classname", classname);
      record.put("lastupdated", lastupdated);
      
      DataInteract di = new DataInteract("feedeng", "feedeng", "dbc:oracle:thin:@ladybug.tii.trb:1521:oradev");
      
      Vector records = di.select(record, null, "car_rp_vehicletype");
      
      Hashtable findthis = new Hashtable();
      findthis.put("make", "Honda");
      findthis.put("model", "Civicy");
      
     /* record = DataField.getRecord(records, findthis);
      make = (DataField)record.get("make");
      model = (DataField)record.get("model");
      System.out.println("make : " + make.getValue());
      System.out.println("model : " + model.getValue());*/
   }
   catch(Exception e)
   {
      System.out.println("error in main " + e.toString());
   }
}

public DataField(String dataString, String v)
{
    if (v != null)
    {
      setValue(v);
    }
    
    if (dataString != null)
    {
      parseDataString(dataString);
    }
}

public DataField(String dataString)
{
    parseDataString(dataString);
}

public DataField()
{}

public void setOperator(String op)
{
   oprtr = op;
}

public String getOperator()
{
   return oprtr;
}

public void setToCurrentTime() throws ClassifiedToolException
{
   try
   {
   Calendar cal = Calendar.getInstance();
   Date today = cal.getTime();
   dateFormat = new String("MM/dd/yyyy hh:mm a");
   DateFormat format = new SimpleDateFormat(dateFormat);
   setValue(format.format(today));
   System.out.println("setting today : " + value);
   }
   catch(Exception e)
   {
      throw new ClassifiedToolException("error setting current date for " + name + " " + e.toString());
   }
}

private void parseDataString(String dataString)
{

    //get the length of the string
    int stringLength = dataString.length();

    //get the position of the '_' character
    int pos1 = dataString.lastIndexOf('_');
 
    name = dataString.substring(0,pos1);

    javaType = dataString.substring(pos1+1, stringLength);

}

public String getName()
{
    return name;
}

public String getValue()
{
    return value;
}

public String getDateFormat()
{
    return dateFormat;
}

public String getJavaType()
{
   return javaType;
}

public void setDateFormat(String df)
{
    dateFormat = df;
}

public Object getJavaObject() throws ClassifiedToolException {
    Object javaObject = null;
    
    try {
    
    if (javaType != null) {
        if (javaType.equals(STRING_TYPE) || javaType.equals(LONGCHAR_TYPE) || javaType.equals(CLOB_TYPE)) {
            javaObject = value;
        }
        
        if (javaType.equals(DATE_TYPE)) {
            javaObject = getDate();
        }
        
        if (javaType.equals(INTEGER_TYPE)) {
            javaObject = getInt();
        }
        
        if (javaType.equals(LONG_TYPE)) {
            javaObject = getLong();
        }
        
        if (javaType.equals(DOUBLE_TYPE)) {
            javaObject = getDouble();
        }
    }
    
    }
    catch(Exception e) {
      ClassifiedToolException cte = new ClassifiedToolException("error getting java object for " + name + e.toString());
      e.printStackTrace();
    }
    
    return javaObject;
}

public Date getDate() throws ParseException
{
   SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
   
   Date result = sdf.parse(value);
  
   return result;   
}

public Integer getInt() throws NumberFormatException
{
   return new Integer(value);
}

public Long getLong() throws NumberFormatException
{
   return new Long(value);
}

public Double getDouble() throws NumberFormatException {
    return new Double(value);
}

public void setValue(String v)
{
   value = v;
   isNull = false;
}

public Object clone()
{
   try
   {
      return super.clone();
   }
   catch(CloneNotSupportedException e)
   {
      System.out.println("error cloning DataField object : " + e.toString());
      return null;
   }
}
   
}


