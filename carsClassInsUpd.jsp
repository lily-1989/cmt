<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.util.*, java.net.*, trb.tii.efe.reversePublishing.*"%>

<%

String returnPage = (String)session.getAttribute("returnPage");
String updateTable = (String)session.getAttribute("updateTable");
if (updateTable == null)
  updateTable = (String)session.getAttribute("insertTable");
String doAction = request.getParameter("doAction");

try
{
Hashtable dataRecord = (Hashtable)session.getAttribute("dataRecord");

//get the database user information from the session object
String dataUser = (String)session.getAttribute("dataUser");
String dataPass = (String)session.getAttribute("dataPass");
String dataHost = (String)session.getAttribute("dataHost");

//instantiate a DataInteract object
DataInteract di = new DataInteract(dataUser, dataPass, dataHost); 

if (request.getParameter("isdefault").equals("1")) {
  Hashtable clauses = new Hashtable();
  // disable default class mapping
  clauses.put("isdefault", new DataField("isdefault_Integer", "1"));
  Hashtable values = new Hashtable();
  values.put("isdefault", new DataField("isdefault_Integer", "0"));
  if ( di.update(values, clauses, updateTable) )
    System.out.println("Default class disabled");
  else
    System.out.println("No default class found");
}

StringBuffer paramBuf = new StringBuffer();
Enumeration enum = dataRecord.elements();
while(enum.hasMoreElements())
{
   DataField field = (DataField)enum.nextElement();
   paramBuf.append(field.getName()+"="+ URLEncoder.encode(request.getParameter(field.getName())));

   if (enum.hasMoreElements())
     paramBuf.append("&");
}
System.out.println(paramBuf.toString());

// simulate form submit using URL
if (doAction.equals("update")) {
  response.sendRedirect("updateRP.jsp?"+paramBuf.toString());
} else if (doAction.equals("insert")) {
  response.sendRedirect("insert.jsp?"+paramBuf.toString());
} else {
  out.println("Error: invalid action specified. Please contact developer");
}

}    
catch(Exception e)
{
   ClassifiedToolException cte = new ClassifiedToolException("error with "+doAction+": " + e.toString());
   cte.setReturnLink(returnPage);
   session.setAttribute("exception", cte);
   System.out.println("error with "+doAction+": " + e.toString());
}

%>
