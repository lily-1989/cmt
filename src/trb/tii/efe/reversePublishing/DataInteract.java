/*
      Author : Garrett Fermoyle
      Date : Jul 30, 2001
      Description : The DataInteract class is a the class that the reverse publishing
                     System uses to connect to and manipulate the database
 */

package trb.tii.efe.reversePublishing;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class DataInteract
{
    public static final boolean DEBUG = false;
    public Connection conn;
    public String junct = new String("AND");
    protected String user;
    protected String pass;
    protected String host;
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet; 
      
                 
    public static void main(String args[])
    {
      DataInteract di = new DataInteract("feedeng", "feedeng", "jdbc:oracle:thin:@ladybug.tii.trb:1521:oradev");
      
      DataField df1 = new DataField("adid_String", "test333");
      DataField df2 = new DataField("feedid_Integer", "104");
      DataField df3 = new DataField("startdate_Date", "08/23/2001");
      DataField df4 = new DataField("class_String", "some");
      DataField df5 = new DataField("adid_String", "test334");
      DataField df6 = new DataField("feedid_Integer", "104");
      DataField df7 = new DataField("startdate_Date", "08/23/2001");
      DataField df8 = new DataField("class_String", "some");
      DataField df13 = new DataField("adid_String", "test888");

      DataField df9 = new DataField("adid_String");
      DataField df10 = new DataField("feedid_Integer");
      DataField df11 = new DataField("startdate_Date");
      DataField df12 = new DataField("class_String");

      
      Hashtable insertValues = new Hashtable();
      insertValues.put(df1.getName(), df1);
      insertValues.put(df2.getName(), df2);
      insertValues.put(df3.getName(), df3);
      insertValues.put(df4.getName(), df4);
      
      Hashtable updateValues = new Hashtable();
      updateValues.put(df13.getName(), df13);
      Hashtable updateClauses = new Hashtable();
      updateClauses.put(df1.getName(), df1);

      try
      {

      if (di.insert(insertValues, "jobs"))
      {
         System.out.println("insert values: success");
      }
      else
      {
         System.out.println("insert values: failed");
      }
      
      if (di.update(updateValues, updateClauses, "jobs"))
      {
         System.out.println("update values: success");
      }
      else
      {
         System.out.println("update values: failed");
      }

      
      insertValues.put(df5.getName(), df5);
      
      if (di.insert(insertValues, "jobs"))
      {
         System.out.println("insert values: success");
      }
      else
      {
         System.out.println("insertValues: failed");
      }

      Hashtable selectValues = new Hashtable();
      selectValues.put(df9.getName(), df9);
      selectValues.put(df10.getName(), df10);
      selectValues.put(df11.getName(), df11);
      selectValues.put(df12.getName(), df12);
 
      Hashtable selectClauses = new Hashtable();
      selectClauses.put(df2.getName(), df2);
         
      Vector v = di.select(selectValues, selectClauses, "jobs");
      
      Enumeration enumVar = v.elements();
      while(enumVar.hasMoreElements())
      {
         Hashtable dataFields = (Hashtable)enumVar.nextElement();
         Enumeration enum2 = dataFields.elements();
         while(enum2.hasMoreElements())
         {
            DataField dataField = (DataField)enum2.nextElement();
            System.out.println(dataField.getName() + " : " +  dataField.getValue());
         }
      }

      /*
      if (di.delete(selectClauses, "jobs"))
      {
         System.out.println("delete : success");
      }

      else
      {
         System.out.println("delete : failure");
      }
      */
      }
      catch(ClassifiedToolException e)
      {
         Logger.log(e.toString());
      }
    }
    
    
    
    public DataInteract(String u, String p, String h)
    {
      user = u;
      pass = p;
      host = h;
    }
    
    private void connect() throws SQLException
    {
        //establish database connection
        System.out.println("connecting to database");
        
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		  conn = DriverManager.getConnection(host, user, pass);
		  Logger.log("successfully established connection");
	}
	   
	
	
	public boolean delete(Hashtable dataFields, String tableName) throws ClassifiedToolException
	{
	   /*
	      The delete method simply constructs and executes a delete statement
	   */
	   
	   DataField field;
	   String fieldName;
	   String oprtr;
	   int updateCount=0;
	   
	   try
	   {
	      String statementString = new String("delete from " + tableName + " where ");
	      int sqlType;
	      int counter = 1;
	      Enumeration enumVar = dataFields.elements();
	      
	      //connect to the database
	      connect(); 
	      
	      //for each DataField, get the datafield's name and append it
	      //to the delete statement.  
	      while (enumVar.hasMoreElements())
	      {
	         field = (DataField)enumVar.nextElement();
                 if (field.getJavaType().equals(DataField.LONGCHAR_TYPE) || field.getJavaType().equals(DataField.CLOB_TYPE)) {
                     throw new ClassifiedToolException("Cannot use LONGCHAR or CLOB in where clause");
                 }
	         fieldName = field.getName();
	         oprtr = field.getOperator();
	         
	         statementString = statementString + fieldName + " " + oprtr + " ? ";
	         //if there are more elements, append the word "and" to the clause
	         //before adding the next one.
	         if (enumVar.hasMoreElements())
	         {
	            statementString = statementString + " and ";
	         }
	      }
	      
	      //using the statement string constructed above,
	      //create a PreparedStatement object
	      System.out.println("statementString: " + statementString);
	      preparedStatement = conn.prepareStatement(statementString);
	      
	      //insert each value of the dataFields to the preparedString in the order they appear
	      //in the enumeration object
	      enumVar = dataFields.elements();
	      while (enumVar.hasMoreElements())
	      {
	         field = (DataField)enumVar.nextElement();
	         setFieldElement(field, counter);
	         counter++;
	      }      
	      
	      updateCount = preparedStatement.executeUpdate();
	      conn.commit();
	      conn.close();
	   }
	   catch(Exception e)
	   {
	      //Logger.log("error deleting ad " + e.toString());
	      //e.printStackTrace();
               throw new ClassifiedToolException("error deleting record: "+e.toString());
	   }
	   System.out.println(updateCount+" rows deleted");
	   if (updateCount > 0) return true;
	   else return false;
	}
	
	
	public boolean insert(Hashtable values, String tableName) throws ClassifiedToolException
	{
	   DataField field;
	   String fieldName;
	   int updateCount=0;
	   int counter=1;
	   String insertString = new String("insert into " + tableName + "(");
	   String variableString = new String("(");
	   String statementString;
           // used to select row if CLOBs exist; will contain all non-LONGCHAR and non-CLOB fields
           // if table has no unique key, multiple rows may be affected by update
           Vector fieldsForSelect = new Vector();
           // collect clob values
           Vector clobs = new Vector();
           String clauseString = "WHERE ";
	   
	   try
	   {
	      Enumeration enumVar = values.elements();
	      
	      //connect to the database
	      connect(); 
	      
	      //for each DataField, get the datafield's name and append it
	      //to the delete statement.  
	      while (enumVar.hasMoreElements())
	      {
	         field = (DataField)enumVar.nextElement();
                 if (! field.getJavaType().equals(DataField.LONGCHAR_TYPE) && ! field.getJavaType().equals(DataField.CLOB_TYPE)) {
                     fieldsForSelect.add(field);
                 }
	         fieldName = field.getName();
	         insertString = insertString + fieldName;

                 if (field.getJavaType().equals(DataField.CLOB_TYPE)) {
                     if (! field.isNull && ! field.getValue().equals("")) {
                         // make sure only non-CLOBs increment the counter when setting preparedStatement params
                         variableString += "empty_clob()";
                         clobs.add(field);
                     } else
                         variableString += "null";
                 } else
                     variableString += "?";
	         
	         if (enumVar.hasMoreElements())
	         {
	            insertString = insertString + ", ";
	            variableString = variableString + ", ";
	         }
	         else
	         {
	            insertString = insertString + ")";
	            variableString = variableString + ")";
	         }
	      }
	      
	      statementString = insertString + " VALUES " + variableString;
	      System.out.println("statementString : " + statementString);
	      preparedStatement = conn.prepareStatement(statementString);
	      
	      enumVar = values.elements();
	      while(enumVar.hasMoreElements())
	      {
	         field = (DataField)enumVar.nextElement();
                 // only count and set non-CLOBs here
	         if (! field.getJavaType().equals(DataField.CLOB_TYPE)) {
                     try
                     {
                        setFieldElement(field, counter);
                     }
                     catch(ClassifiedToolException e)
                     {
                        throw e;
                     }
                     counter++;
                 }
	      }

              // build where clause for selecting CLOB
              for (int i=0; i<fieldsForSelect.size(); i++) {
                  field = (DataField)fieldsForSelect.get(i);
                  clauseString += field.getName() + " = ?";
                  if (i<fieldsForSelect.size()-1)
                      clauseString += " AND ";
              }

              conn.setAutoCommit(false);
	      updateCount = preparedStatement.executeUpdate();
              
              // load CLOB values
              for (int i=0; i<clobs.size(); i++) {
                  field = (DataField)clobs.get(i);
                  // NOTE: due to Oracle driver bug, each CLOB value needs its own select statement
                  String clobSelect = "select "+ field.getName() +" from "+ tableName +" "+ clauseString;
                  System.out.println("insert CLOB select: "+clobSelect);
                  preparedStatement = conn.prepareStatement(clobSelect);
                  // set values in where clause
                  for (int j=0; j<fieldsForSelect.size(); j++) {
                      setFieldElement((DataField)fieldsForSelect.get(j), j+1);
                  }
                  resultSet = preparedStatement.executeQuery();
                  while (resultSet.next()) {
                      ((oracle.sql.CLOB)resultSet.getObject(1)).putString(1, field.getValue());
                  }
              }
              
	      conn.commit();
              //conn.setAutoCommit(true); // not necessary because conn is closed
	      conn.close();
	   }
	   
	   catch(Exception e)
	   {
	      throw new ClassifiedToolException("error inserting ad : " + e.toString());
	   }
	   if (updateCount > 0) return true;
	   else return false;
	}
	
	
	public boolean update(Hashtable values, Hashtable clauses, String tableName) throws ClassifiedToolException
	{
	   DataField field;
	   String fieldName;
	   int updateCount=0;
	   int counter=1;
	   String updateString = new String("UPDATE " + tableName + " SET ");
           // both update and CLOB select statements will use this
	   String clauseString = new String("WHERE ");
	   String statementString;
            Vector clobs = new Vector();
	   
	   try
	   {
         //connect to the database
	      connect(); 
	      
	      
	      //for each DataField value, get the datafield's name and append it
	      //to the update statement.  
	      Enumeration enumVar = values.elements();
	      while (enumVar.hasMoreElements())
	      {
	         field = (DataField)enumVar.nextElement();
	         fieldName = field.getName();
                 if (field.getJavaType().equals(DataField.CLOB_TYPE)) {
                     // since there's no placeholder for CLOBs, make sure counter is not incremented for them below
                     if (! field.isNull && ! field.getValue().equals("")) {
                         updateString = updateString + fieldName + " = empty_clob()";
                         // save CLOBs for loading after update is executed
                         clobs.add(field);
                     } else
                         updateString += fieldName + " = null";
                 } else {
                     updateString = updateString + fieldName + " = ?";
                 }
	         
	         if (enumVar.hasMoreElements())
	         {
	            updateString = updateString + ", ";
	         }
	         else
	         {
	            updateString = updateString + " ";
	         }
	      }
	      
	      //for each DataField clause, get the datafield's name and append
	      //it to the clause statement
	      enumVar = clauses.elements();
	      while (enumVar.hasMoreElements())
	      {
	         field = (DataField)enumVar.nextElement();
                  if (field.getJavaType().equals(DataField.LONGCHAR_TYPE) || field.getJavaType().equals(DataField.CLOB_TYPE)) {
                      throw new ClassifiedToolException("Cannot use LONGCHAR or CLOB in where clause");
                  }
	         String oprtr = field.getOperator();
	         fieldName = field.getName();
	         clauseString = clauseString + fieldName + " " + oprtr + " ?";
	         
	         if (enumVar.hasMoreElements())
	         {
	            clauseString = clauseString + " " + junct + " ";
	         }
	      }
	      
	      statementString = updateString + clauseString;
	      System.out.println("statementString : " + statementString);
	      preparedStatement = conn.prepareStatement(statementString);
	      
	      enumVar = values.elements();
	      while(enumVar.hasMoreElements())
	      {
	         field = (DataField)enumVar.nextElement();
                 // only count and set non-CLOBs here
                 if (! field.getJavaType().equals(DataField.CLOB_TYPE)) {
                     setFieldElement(field, counter);
                     counter++;
                 }
	      }
	      
	      enumVar = clauses.elements();
	      while(enumVar.hasMoreElements())
	      {
	         field = (DataField)enumVar.nextElement();
	         setFieldElement(field, counter);
	         counter++;
	      }
	     
              conn.setAutoCommit(false);
	      updateCount = preparedStatement.executeUpdate();

              // load CLOB values
              for (int i=0; i<clobs.size(); i++) {
                  field = (DataField)clobs.get(i);
                  // NOTE: due to Oracle driver bug, each CLOB value needs its own select statement 
                  String clobSelect = "select "+ field.getName() +" from "+ tableName +" "+ clauseString;
                  System.out.println("update CLOB select: "+clobSelect);
                  preparedStatement = conn.prepareStatement(clobSelect);
                  // set values in where clause for CLOB select
                  enumVar = clauses.elements();
                  counter = 1;
                  // set params in where clause
                  while (enumVar.hasMoreElements()) {
                      DataField idxField = (DataField)enumVar.nextElement();
                      // if value has been updated, retrieve new value
                      if (values.containsKey(idxField.getName())) {
                          idxField = (DataField)values.get(idxField.getName());
                      }
                      setFieldElement(idxField, counter);
                      counter++;
                  }
                  resultSet = preparedStatement.executeQuery();
                  while (resultSet.next()) {
                      ((oracle.sql.CLOB)resultSet.getObject(1)).putString(1, field.getValue());
                  }
              }

	      conn.commit();
              //conn.setAutoCommit(true); // not necessary if connection is closed
	      conn.close();
	   }
	   
	   catch(Exception e)
	   {
	      throw new ClassifiedToolException("error updating ad : " + e.toString());
	   }
           System.out.println(updateCount+" rows updated");
	   if (updateCount > 0) return true;
	   else return false;
	}
	
        public Vector select(Hashtable values, Hashtable clauses, String tableName) throws ClassifiedToolException {
            return select(values, clauses, tableName, "");
        }
    
        // extraClauses is used for clauses like "order by"
	public Vector select(Hashtable values, Hashtable clauses, String tableName, String extraClauses) throws ClassifiedToolException
	{
	   String statementString = new String("select ");
	   DataField field;
	   Vector records = new Vector();
	   
	   
	   try
	   {
	   
	   connect();
	   
	   //For each value in the values hashtable, list the field 
	   //in the statement string, separated by commas
	   System.out.println("populating values to string");
	   Enumeration enumVar = values.elements();
	   while(enumVar.hasMoreElements())
	   {
	      field = (DataField)enumVar.nextElement();
	      statementString = statementString + field.getName();
	      
	      if(enumVar.hasMoreElements())
	      {
	         statementString = statementString + ", ";
	      }
	   }
	   
	   //append FROM and the name of the table
	   statementString = statementString + " FROM " + tableName;
	   System.out.println("checking to see if clauses is null");
	  
	   //If there are any clauses in the clauses hashtable,
	   //append the word "WHERE" to begin the clause portion
	   if (clauses != null)
	   {
	      enumVar = clauses.elements();
	      if (enumVar.hasMoreElements())
	      {
	         statementString = statementString + " WHERE ";
	      }
	
	      //For each clause in the clauses hashtable, add the string
	      //clauseName = ?
	      System.out.println("populating clauses to string");
	      while (enumVar.hasMoreElements())
	      {
	         field = (DataField)enumVar.nextElement();
                 if (field.getJavaType().equals(DataField.LONGCHAR_TYPE) || field.getJavaType().equals(DataField.CLOB_TYPE)) {
                     throw new ClassifiedToolException("Cannot use LONGCHAR or CLOB in where clause");
                 }
	         String oprtr = field.getOperator();
	         statementString = statementString + field.getName() + " " + oprtr + " ?";
	      
	         if (enumVar.hasMoreElements())
	         {
	            statementString = statementString + " " + junct + " ";
	         }
	      }
	   }//end if
               statementString += " " + extraClauses;

	      //use the statementString to generate a PreparedStatement object
	      preparedStatement = conn.prepareStatement(statementString);
	      System.out.println("statementString : " + statementString);
	   
	      
	   if (clauses != null)
	   {
	      //For each clause, assign the appropriate value to the corresponding
	      //variable in the preparedStatement.
	      int counter = 1;
	      enumVar = clauses.elements();
	      
	      while (enumVar.hasMoreElements())
	      {
	         field = (DataField)enumVar.nextElement();
	         setFieldElement(field, counter);
	         counter++;
	      }
	   }//end if
	   System.out.println("missed if");
	   
	   //obtain the result set and close connection
	   resultSet = preparedStatement.executeQuery();
	   
	   //obtain the records as a Vector of Hashtables
	   records = getResults(values);
	   conn.close();
	   
	   }
	   catch(Exception e)
	   {
	      throw new ClassifiedToolException("error selecting records : " + e.toString());
	   }
	   
	   return records;
	}
	
	private Vector getResults(Hashtable values) throws SQLException, IOException
	{
	   /*  
	      The getResults method assumes contains the functionality to convert
	      the information contained in a ResultSet object into a Vector of Hashtables.
	      The Hashtables contain the Datafields of the particular record.  A Vector
	      of Hashtables is used to handle and return multiple records at one time.
	   */
	   
	   Vector records = new Vector();
	   DataField field;
	   
	   //fieldClone is used to clone each field from the values Hashtable
	   //this is used so that the original set of values is not corrupted
	   //as the ResultSet is traversed and fields are being populated
	   //with their return values.
	   DataField fieldClone;
	   Hashtable returnedValues;
	   Enumeration enumVar;
	   String fieldName;
	   String javaType;
	   
	   //for every result from ResultSet, extract the information 
	   while(resultSet.next())
	   {
	      //create a new hashtable corresponding to the current record
	      //in the resultSet
	      returnedValues = new Hashtable();
	      enumVar = values.elements();
	      
              // save CLOBS to be extracted after other columns 
              Vector clobs = new Vector();

	      //for every datafield in the values hashtable, create a new
	      //datafield clone and then populate it with the correct
	      //information from the result set.
	      while(enumVar.hasMoreElements())
	      {
	         field = (DataField)enumVar.nextElement();
	         fieldClone = (DataField)field.clone();
	         fieldName = field.getName();
	         javaType = field.getJavaType();
	         
	         
	         if (javaType.equals(DataField.STRING_TYPE))
	         {
	            if (resultSet.getString(fieldName) != null)
	            fieldClone.setValue(resultSet.getString(fieldName));
	         }
	         
	         if (javaType.equals(DataField.DATE_TYPE))
	         {
	            String dateFormat = field.getDateFormat();
	            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	            
	            
	            if (resultSet.getTimestamp(fieldName) != null)
	            {
	               java.sql.Timestamp date = resultSet.getTimestamp(fieldName);
	               Date newDate = new Date(date.getTime());
	               fieldClone.setValue(sdf.format(newDate));
	            }
	         }
	         
	         if (javaType.equals(DataField.INTEGER_TYPE))
	         {
	            int integer = resultSet.getInt(fieldName);
	            fieldClone.setValue(Integer.toString(integer));
	         }
	         
	         if (javaType.equals(DataField.LONG_TYPE))
	         {
	            long longtype = resultSet.getLong(fieldName);
	            fieldClone.setValue(Long.toString(longtype));
	         }
                 
                  if (javaType.equals(DataField.DOUBLE_TYPE)) {
                     double doubleVal = resultSet.getDouble(fieldName);
                     fieldClone.setValue(Double.toString(doubleVal));
                  }
                  
                  if (javaType.equals(DataField.LONGCHAR_TYPE)) {
                      fieldClone.setValue(getLongVarchar(resultSet, fieldName));
                  }
                  
                  if (javaType.equals(DataField.CLOB_TYPE)) {
                      // extract CLOBs after other columns 
                      clobs.add(fieldClone);
                  }
	         
	         returnedValues.put(fieldName, fieldClone);
	      }
              // extract CLOBs
              for (int i=0; i<clobs.size(); i++) {
                  DataField clobField = (DataField)clobs.get(i);
                  clobField.setValue(ClobToString((oracle.sql.CLOB)resultSet.getObject( clobField.getName() )));
                  // NOTE: this field has already been put into returnedValues
              }
	      
	      //add the most recently acquired record to the records
	      //vector.
	      records.add(returnedValues);
	      }
	    
	      return records;
	}
	
	private void setFieldElement(DataField field, int counter) throws ClassifiedToolException
	{
	   /*
	         The purpose of setFieldElement is to set a single value
	         of the java.sql.PreparedStatement object.  
	         @field - the datafield containing the value to be set
	         @counter - the number label (1-n) of the variable
	                     in the prepared statement to be set.
	   */
	 
	   
	   
	   //depending on the javaType, different methods of the PreparedStatement
	   //object are called to set the actual value.
	   try
	   {
	   if (field.getValue() != null)
	   {
	      Logger.log("value : " + field.getValue());
	   }
	   
	   String javaType = field.getJavaType();
	      
	      if (javaType.equals(DataField.STRING_TYPE))
	      {
            if (field.isNull)
            {
               preparedStatement.setNull(counter, java.sql.Types.VARCHAR);  
            }
            else
            {
               preparedStatement.setString(counter, (String)field.getJavaObject());
            }
               
         }
         
         if (javaType.equals(DataField.DATE_TYPE))
         {  
            if (field.isNull)
            {
               preparedStatement.setNull(counter, java.sql.Types.DATE);
            }
            else
            {
               java.sql.Timestamp sqlDate;
               Date date = (Date)field.getJavaObject();
               sqlDate = new java.sql.Timestamp(date.getTime());
               preparedStatement.setTimestamp(counter, sqlDate);
            }
         }
         
         if (javaType.equals(DataField.INTEGER_TYPE))
         {
            if (field.isNull)
            {
               preparedStatement.setNull(counter, java.sql.Types.INTEGER);
            }
            else
            {
               Integer integer = (Integer)field.getJavaObject();
               preparedStatement.setInt(counter, integer.intValue());
            }
         }
         
         if (javaType.equals(DataField.LONG_TYPE))
         {
            if (field.isNull)
            {
               preparedStatement.setNull(counter, java.sql.Types.NUMERIC);
            }
            else
            {
               Long longNumber = (Long)field.getJavaObject();
               preparedStatement.setLong(counter, longNumber.longValue());
            }
         }
               
         if (javaType.equals(DataField.DOUBLE_TYPE)) {
             if (field.isNull) {
                 preparedStatement.setNull(counter, java.sql.Types.DOUBLE);
             } else {
                 Double doubleNumber = (Double)field.getJavaObject();
                 preparedStatement.setDouble(counter, doubleNumber.doubleValue());
             }
         }
               
         if (javaType.equals(DataField.LONGCHAR_TYPE)) {
             if (field.isNull)
                 preparedStatement.setNull(counter, java.sql.Types.LONGVARCHAR);
             else
                 setLongVarchar(preparedStatement, counter, field.getValue());
         }
         if (javaType.equals(DataField.CLOB_TYPE)) {
             // do nothing; CLOB value is loaded using select statement
         }
      }
      catch (Exception e)
      {
         throw new ClassifiedToolException("error setting field element " + field.getName() + " : " + e.toString());
      }

	}

    public static void setLongVarchar(PreparedStatement stmt, int position, String text) throws SQLException {
        CharArrayReader stream = new CharArrayReader(text.toCharArray());
        stmt.setCharacterStream(position, stream, text.length());
    }

    public static String getLongVarchar(ResultSet rset, String colName) throws SQLException, IOException {
        int BUFSIZE = 1024;
        int chars = 0;
        StringBuffer result = new StringBuffer();
        char[] cbuf = new char[BUFSIZE];
        BufferedReader reader = null;
    
        reader = new BufferedReader(rset.getCharacterStream(colName));
        while ( (chars = reader.read(cbuf, 0, BUFSIZE)) != -1 ) {
            result.append(cbuf, 0, chars);
        }
        return result.toString();
    }

    public static String ClobToString(oracle.sql.CLOB cl) throws SQLException, IOException {
        BufferedReader reader = null;
        int chars = 0;
        StringBuffer result = new StringBuffer();
        char[] buf = new char[cl.getChunkSize()];
    
        reader = new BufferedReader(cl.getCharacterStream());
        while ((chars = reader.read(buf)) != -1) {
            result.append(buf, 0, chars);
        }
        return result.toString();
    }
}
   
