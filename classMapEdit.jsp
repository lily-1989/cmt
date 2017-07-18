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
%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Edit Recycler Class Mapping
</title>
</head>

<body bgcolor="#ffffff" text="#000000" link="#003366" vlink="#336699" alink="#990000" leftmargin="0" topmargin="2" marginwidth="0" marginheight="2">

<table width="760" border="0" cellspacing="0" cellpadding="0">
<font class="text">
<%@include file="include/topbar.html"%>

<tr>
<%@include file="include/leftnav.html"%>


  <td bgcolor="#ffffff" align="left" width="5%"> </td>


  <td valign="top">
    <table width="100%" cellspacing="0" cellpadding="0" border="0">
      <tr>
        <td><img src="images/clear.gif" height="1" width="12"></td>
        <td width="100%">
          <table cellpadding="4" cellspacing="0" border="0" bgcolor="#cccccc">
            <tr><br>
              <td><font class="page-header">Edit Recycler Class Mapping</font></td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td><img src="images/clear.gif" height="1" width="12"></td>
        <!-- Note: there should be no whitespace around the img tag,
             to prevent the cell from containing any text, which causes the bar to be thicker than a hairline -->
        <td bgcolor="#000000"><img src="images/clear.gif" height="1" width="100%"></td>
      </tr>


<!--main table-->
<table width="610" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
<tr>
  <td rowspan="24" width="12" bgcolor="#ffffff"><img src="images/clear.gif" width="1" height="1" alt=""></td>
  <td>

<!-- edit class table -->
<% 

   try
   {   
         DataField _market = new DataField();
         DataField _class = new DataField();
         DataField _target_class = new DataField();

         RecordSet class_map_recs = (RecordSet)session.getAttribute("class_map");
         
         //find the record''s place in the Vector
         System.out.println("getting record from class_map_recs");
         Hashtable clauses = new Hashtable();
         clauses.put("market", marketParam);
         clauses.put("class", classParam);
       System.out.println("marketParam: "+marketParam);
       System.out.println("classParam: "+classParam);
         Hashtable record = class_map_recs.findRecord(clauses);

         if (record != null)
         {
            _market = (DataField)record.get("market");
            System.out.println("market : " + _market.getValue());
            
            _class = (DataField)record.get("class");
            System.out.println("class : " + _class.getValue());
            
            _target_class = (DataField)record.get("target_class");
            System.out.println("target_class : " + _target_class.getValue());

            //get and store updateClauses to session object
            //this is used to identify a unique row for update
	    Hashtable updateClauses = new Hashtable();
	    updateClauses.put("market", _market);
	    updateClauses.put("class", _class);
            session.setAttribute("updateClauses", updateClauses);
            
            //get and store updataTable to session object
            session.setAttribute("updateTable", "feedeng.class_map");
         }
%>

<table border="0" width="100%">
        <tr>
          <td align = "left" valign = "top">

       <form action="updateRP.jsp" method="post">
	 <table width = "100%" border="0" cellspacing="2" cellpadding="1">		
          <tr>
            <th colspan="2" align="left" class="td-title-bg"><span class="table-header">Fields</span></th>
          </tr>
<%
  int tog = -1;
%>
          <input type="hidden" name="market" value="<%= _market.getValue()%>">
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left" width="25%"><span class="table-text">Class:</span></td>
	     <td align="left"><input type="text" name="class" value="<%= _class.getValue()%>" size="10"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Target Class:</span></td>
	     <td align="left"><input type="text" name="target_class" value="<%= _target_class.getValue()%>" size="10"/></td>
          </tr>

          <tr>
             <td>&nbsp;</td>
             <td><input type="submit" name="update" value="Update"/> <input type="reset"/></td>
          </tr>
       </table>

<%
     }//end try
     catch(Exception e)
     {
           ClassifiedToolException cte = new ClassifiedToolException("error getting class mapping: " + e.toString());
   	   cte.setReturnLink(request.getHeader("REFERER"));
   	   session.setAttribute("classifiedException", cte);
   	   response.sendRedirect("classifiedError.jsp");

     }
%>



       </form>
    </td>
  </tr>
  </table>

  </td>
</tr>
</table>
<br><br><br>
<!--end main table -->
<tr><td colspan="3" bgcolor="#000000" width="100%"><img src="images/clear.gif" height="1" width="100%"></td></tr>
</table>
</td>
</tr>
</table>
</body>
</html>
