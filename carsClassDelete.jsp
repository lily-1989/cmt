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
   	deleteClauses.put("make", new DataField("make_String", request.getParameter("make")));
   	deleteClauses.put("model", new DataField("model_String", request.getParameter("model")));
   	session.setAttribute("deleteClauses", deleteClauses);
   	System.out.println(deleteClauses);
   	
   	//get and store deleteTable to session object
   	session.setAttribute("deleteTable", "feedeng.car_rp_vehicletype");
   	
        response.sendRedirect("delete.jsp");
   
    }
     catch(Exception e)
     {
     	ClassifiedToolException cte = new ClassifiedToolException("error deleting vehicle type: " + e.toString());
   	cte.setReturnLink( request.getHeader("REFERER") );
   	session.setAttribute("classifiedException", cte);
   	response.sendRedirect("classifiedError.jsp");
     }
%>
