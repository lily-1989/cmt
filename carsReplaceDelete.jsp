<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.reversePublishing.*, trb.tii.efe.tools.ClassifiedToolUser"%>

<%
  //verify session
  ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
  if (user == null)
  {
     response.sendRedirect("index.jsp");
  }
  //String market = user.getMarket();
   
   try
   {
   	
   	//get and store deleteClauses to session object
   	Hashtable deleteClauses = new Hashtable();
   	deleteClauses.put("feedid", new DataField("feedid_Integer", request.getParameter("feedid")));
   	deleteClauses.put("origstring", new DataField("origstring_String", request.getParameter("origstring")));
   	session.setAttribute("deleteClauses", deleteClauses);
   	System.out.println(deleteClauses);
   	
   	//get and store deleteTable to session object
   	session.setAttribute("deleteTable", "feedeng.abbrev");
   	
        response.sendRedirect("delete.jsp");
   
    }
     catch(Exception e)
     {
     	ClassifiedToolException cte = new ClassifiedToolException("error deleting text replacement: " + e.toString());
   	cte.setReturnLink( request.getHeader("REFERER") );
   	session.setAttribute("classifiedException", cte);
   	response.sendRedirect("classifiedError.jsp");
     }
%>
