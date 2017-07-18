<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.reversePublishing.*, trb.tii.efe.tools.ClassifiedToolUser"%>

<%
  //verify session
  ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
  if (user == null)
  {
     response.sendRedirect("index.jsp");
  }
  //String market = user.getMarket();
  String marketParam = request.getParameter("market");
  String classParam = request.getParameter("class");
   
   try
   {
   	
   	//get and store deleteClauses to session object
   	Hashtable deleteClauses = new Hashtable();
   	deleteClauses.put("market", new DataField("market_String", marketParam));
   	deleteClauses.put("class", new DataField("class_String", classParam));
   	session.setAttribute("deleteClauses", deleteClauses);
   	System.out.println(deleteClauses);
   	
   	//get and store deleteTable to session object
   	session.setAttribute("deleteTable", "feedeng.class_map");
   	
        response.sendRedirect("delete.jsp");
   
    }
     catch(Exception e)
     {
     	ClassifiedToolException cte = new ClassifiedToolException("error deleting class mapping: " + e.toString());
   	cte.setReturnLink( request.getHeader("REFERER") );
   	session.setAttribute("classifiedException", cte);
   	response.sendRedirect("classifiedError.jsp");
     }
%>
