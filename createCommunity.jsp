<%@ page language="java" errorPage="errorPage.jsp"%>

<%
  //check to make sure session
  if (session.getAttribute("user") == null)
  {
     response.sendRedirect("index.jsp");
  }
%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Add New Community
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
              <td><font class="page-header">Add New Community</font></td>
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

    <!--insert ad table-->
        <table width="100%">
        <tr>
          <td align = "left" valign = "top">

           <table border = "0" align="center" width="100%">
               <form method = "post" action = "insertCommunity">
                <tr>

                   <th colspan = "2" align = "left" class="td-title-bg">
		     <span class="table-header">Fields</font></th>
                </tr>

                <tr class="td-on">
                    <th width = "35%" align = "left"><span class="table-text">Neighborhood</span></th>
                    <td><input type = "text" size="50" name = "neighborhood" maxlength = "100"/></td>
                </tr>

                <tr class="td-off">
                    <th align = "left"><span class="table-text">Community</span></th>
                    <td><input type = "text" size="50" name = "community" maxlength = "50"/></td>
                </tr>

                <tr class="td-on">
                    <th align = "left"><span class="table-text">City</span></th>
                    <td><input type = "text" size="50" name = "city" maxlength = "50"/></td>
                </tr>

                <tr class="td-off">
                    <th align = "left"><span class="table-text">County</span></th>
                    <td><input type = "text" size="25" name = "county" maxlength = "25"/></td>
                </tr>

                <tr class="td-on">
                    <th align = "left"><span class="table-text">State</span></th>
                    <td><input type = "text" size="2" name = "state" maxlength = "2"/></td>
                </tr>

                <tr class="td-off">
                    <td></td>
                    <td valign = "middle" align="left">
			<input type = "submit" value = "Insert"/>
			<input type = "reset"/>
		    </td>
                </tr>
                </form>
           </table>
        </td>
        </tr>
    </table>

<!--end insert Ad table -->

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
