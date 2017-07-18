<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.reversePublishing.*, trb.tii.efe.tools.ClassifiedToolUser"%>

<%     
  //verify session
  ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
  if (user == null)
  {
     response.sendRedirect("index.jsp");
  }
  //String market = user.getMarket();

    //get and store insertTable to session object
    session.setAttribute("insertTable", "feedeng.car_rp_vehicletype");
%>


<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Add Class Mapping
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
              <td><font class="page-header">Add Class Mapping</font></td>
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

<!-- add class table -->
<table width="100%">
  <tr>
     <td valign="top" align="left">
       <form action="carsClassInsUpd.jsp" method="post">
         <input type="hidden" name="doAction" value="insert">
	 <input type="hidden" name="feedid" value="<%= request.getParameter("feedid") %>"/>

	<table width="100%">
	  <tr>
	     <th align="left" colspan="2" class="td-title-bg"><span class="table-header">Fields</span></th>
	  </tr>
          <tr class="td-on">
             <td align="left" width="20%"><span class="table-text">Make:</span></td>
	     <td align = "left"><input type="text" name = "make" size="40"/></td>
          </tr>
          <tr class="td-off">
             <td align="left"><span class="table-text">Model:</span></td>
	     <td align = "left"><input type="text" name = "model" size="40"/></td>
          </tr>
          <tr class="td-on">
             <td align="left"><span class="table-text">Class:</span></td>
	     <td align = "left"><input type="text" name = "class" size="8"/></td>
          </tr> 
          <tr class="td-off">
             <td align="left"><span class="table-text">Default?</span></td>
	     <td align = "left">
	     <input type="radio" name="isdefault" value="1"> Yes
	     <input type="radio" name="isdefault" value="0" checked> No
	     <br>
	     <font color="red">Setting default to "Yes" will disable the previous default class</font>
	     </td>
          </tr> 
          <tr>
             <td>&nbsp;</td>
             <td><input type="submit" name="insert" value="add"/> <input type="reset" value="clear"/></td>
          </tr>
       </table>
       </form>
    </td>
  </tr>
</table>
<!-- add class table -->

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
