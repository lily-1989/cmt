<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.util.*, trb.tii.efe.reversePublishing.*"%>

<%

String returnPage = (String)session.getAttribute("returnPage");
String insertTable = (String)session.getAttribute("insertTable");

try
{

Hashtable dataRecord = (Hashtable)session.getAttribute("dataRecord");

Enumeration enum = dataRecord.elements();
 
while(enum.hasMoreElements())
{
   DataField field = (DataField)enum.nextElement();
   String fieldValue = request.getParameter(field.getName());
   if (field.isCurrentTime)
   {
      field.setToCurrentTime();
   }
   else if (!fieldValue.equals("null"))
   {
      field.setValue(fieldValue);
   }
}

//get the database user information from the session object
String dataUser = (String)session.getAttribute("dataUser");
String dataPass = (String)session.getAttribute("dataPass");
String dataHost = (String)session.getAttribute("dataHost");

//instantiate a DataInteract object
DataInteract di = new DataInteract(dataUser, dataPass, dataHost); 


//if the insert is successful, redirect to managerUserAccounts.jsp
//otherwise, instantiate classifiedToolException and send to exception page
if (di.insert(dataRecord, insertTable)) System.out.println("insert successful");
else System.out.println("insert failed!");
response.sendRedirect(returnPage);
}
catch(Exception e)
{
   ClassifiedToolException cte = new ClassifiedToolException("no records inserted: " + e.toString());
   cte.setReturnLink(returnPage);
   session.setAttribute("classifiedException", cte); 
   response.sendRedirect("classifiedRPError.jsp");
}
%>
