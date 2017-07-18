<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.reversePublishing.*"%>

<%
   
   try {
   	
        //get and store deleteClauses to session object
   	    Hashtable deleteClauses = new Hashtable();
   	    deleteClauses.put("market", new DataField("market_String", request.getParameter("market")));
   	    deleteClauses.put("class", new DataField("class_String", request.getParameter("classnum")));
        deleteClauses.put("type", new DataField("type_String", request.getParameter("type")));
        deleteClauses.put("zip", new DataField("zip_String", request.getParameter("zip")));

   	    session.setAttribute("deleteClauses", deleteClauses);
   	    System.out.println(deleteClauses);
   	
   	    //get and store deleteTable to session object
   	    session.setAttribute("deleteTable", new String("apts_classes"));
   	
        response.sendRedirect("delete.jsp");       
   
    } catch(Exception e) {
     	ClassifiedToolException cte = new ClassifiedToolException("error deleting vehicle type: " + e.toString());
   	    cte.setReturnLink("manageUserAccounts.jsp");
   	    session.setAttribute("classifiedException", cte);
   	    response.sendRedirect("classifiedRPError.jsp");
    }
%>