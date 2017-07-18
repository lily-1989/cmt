<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.util.*, trb.tii.efe.reversePublishing.*"%>

<%

String returnPage = (String)session.getAttribute("returnPage");
String updateTable = (String)session.getAttribute("updateTable");

try
{
Hashtable fields = new Hashtable();
Hashtable dataRecord = (Hashtable)session.getAttribute("dataRecord");

Hashtable updateClauses = (Hashtable)session.getAttribute("updateClauses");
Enumeration enum = updateClauses.elements();
while(enum.hasMoreElements())
{
   DataField clause = (DataField)enum.nextElement();
   System.out.println("update clause " + clause.getName() + " = " + clause.getValue());
}


enum = dataRecord.elements();
Vector records;

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
if (di.update(dataRecord, updateClauses, updateTable))
    System.out.println("updated success.");
else
    System.out.println("update failed!");
response.sendRedirect(returnPage);
}
catch(Exception e)
{
   ClassifiedToolException cte = new ClassifiedToolException("no records updated: " + e.toString());
   cte.setReturnLink(returnPage);
   session.setAttribute("classifiedException", cte);
   response.sendRedirect("classifiedRPError.jsp");

   //System.out.println("error updating records " + e.toString());
}
%>
