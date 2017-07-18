<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.util.*, trb.tii.efe.reversePublishing.*"%>

<%

String returnPage = (String)session.getAttribute("returnPage");
String deleteTable = (String)session.getAttribute("deleteTable");

try
{

Hashtable deleteClauses = (Hashtable)session.getAttribute("deleteClauses");
Enumeration enum = deleteClauses.elements();
while(enum.hasMoreElements())
{
   DataField clause = (DataField)enum.nextElement();
   System.out.println("delete clause " + clause.getName() + " = " + clause.getValue());
}

//get the database user information from the session object
String dataUser = (String)session.getAttribute("dataUser");
String dataPass = (String)session.getAttribute("dataPass");
String dataHost = (String)session.getAttribute("dataHost");

//instantiate a DataInteract object
DataInteract di = new DataInteract(dataUser, dataPass, dataHost); 


//if the delete is successful, redirect to returnPage
//otherwise, instantiate classifiedToolException and send to exception page
if (di.delete(deleteClauses, deleteTable)) System.out.println("delete success.");
else System.out.println("delete failed!");
response.sendRedirect(returnPage);
}
catch(Exception e)
{
   ClassifiedToolException cte = new ClassifiedToolException("error deleting records: " + e.toString());
   cte.setReturnLink(returnPage);
   session.setAttribute("exception", cte);
   System.out.println("error deleting records " + e.toString());
}
%>
