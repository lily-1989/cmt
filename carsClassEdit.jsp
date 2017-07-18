<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.reversePublishing.*, trb.tii.efe.tools.ClassifiedToolUser"%>

<%
  //verify session
  ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
  if (user == null)
  {
     response.sendRedirect("index.jsp");
  }
  //String market = user.getMarket();
%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Edit Class Mapping
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
              <td><font class="page-header">Edit Class Mapping</font></td>
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
         DataField _feedid = new DataField();
         DataField _make = new DataField();
         DataField _model = new DataField();
         DataField _class = new DataField();
         DataField _isdefault = new DataField();
         // DataField lastupdated = new DataField();
         String feedidValue = request.getParameter("feedid");
         String makeValue = request.getParameter("make");
         String modelValue = request.getParameter("model");
         
         RecordSet makemodel = (RecordSet)session.getAttribute("makemodel");
         
         //find the record''s place in the Vector
         System.out.println("getting record from class map");
         Hashtable clauses = new Hashtable();
         clauses.put("feedid", feedidValue);
         clauses.put("make", makeValue);
         clauses.put("model", modelValue);
         Hashtable record = makemodel.findRecord(clauses);

         if (record != null)
         {
            _feedid = (DataField)record.get("feedid");
            System.out.println("feedid : " + _feedid.getValue());
            
            _class = (DataField)record.get("class");
            System.out.println("class : " + _class.getValue());
            
            _make = (DataField)record.get("make");
            System.out.println("make : " + _make.getValue());

            _model = (DataField)record.get("model");
            System.out.println("model : " + _model.getValue());

            _isdefault = (DataField)record.get("isdefault");
            System.out.println("isdefault : " + _isdefault.getValue());
            
	       //lastupdated = (DataField)record.get("lastupdated");
            //System.out.println("lastupdated : " + lastupdated.getValue());
            
            //get and store updateClauses to session object
            //this is used to identify a unique row for update
	    Hashtable updateClauses = new Hashtable();
	    updateClauses.put("feedid", _feedid);
	    updateClauses.put("make", _make);
	    updateClauses.put("model", _model);
            session.setAttribute("updateClauses", updateClauses);
            
            //get and store updataTable to session object
            session.setAttribute("updateTable", "feedeng.car_rp_vehicletype");
         }
%>

<table border="0" width="100%">
        <tr>
          <td align = "left" valign = "top">

       <form action="carsClassInsUpd.jsp" method="post">
	<input type="hidden" name="doAction" value="update">

	 <table width = "100%" border="0" cellspacing="2" cellpadding="1">		
          <tr>
            <th colspan="2" align="left" class="td-title-bg"><span class="table-header">Fields</span></th>
          </tr>
<%
  int tog = -1;
%>
          <input type="hidden" name="feedid" value="<%= _feedid.getValue()%>">
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Make:</span></td>
	     <td align = "left"><input type="text" name="make" value="<%= _make.getValue()%>" size="20"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Model:</span></td>
	     <td align = "left"><input type="text" name="model" value="<%= _model.getValue()%>" size="20"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Class:</span></td>
	     <td align = "left"><input type="text" name="class" value="<%= _class.getValue()%>" size="20"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Default?</span></td>
	     <td align = "left">
	     <input type="radio" name="isdefault" value="1" <%= _isdefault.getValue().equals("1") ? "checked" : "" %>> Yes
	     <input type="radio" name="isdefault" value="0" <%= _isdefault.getValue().equals("0") ? "checked" : "" %>> No
	     <br>
	     <font color="red">Setting default to "Yes" will disable the previous default class</font>
	     </td>
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
           ClassifiedToolException cte = new ClassifiedToolException("error getting account details: " + e.toString());
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
