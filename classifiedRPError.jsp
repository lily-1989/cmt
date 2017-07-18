<%@ page language="java" import = "java.lang.*, trb.tii.efe.reversePublishing.*"%>

<%
    ClassifiedToolException e = (ClassifiedToolException)session.getAttribute("classifiedException");
%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Error
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
              <td><font class="page-header">Error</font></td>
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

    <!--classified Error starts here-->
        <table width="100%" border="0">
          <tr>
            <td align = "left" valign = "top" class="table-text">
              <p>&nbsp;</p>

              <!-- CR 18124 -->
			  <% if (e.getDisplay().indexOf("java.sql.SQLException: ORA-00001: unique constraint (FEEDENG.UDX_MERCH_CAT) violated") != -1) { %>
			  <p>Class ID already exists.  Please try again.</p>
			  <% } else {%>
			  <p><%= e.getDisplay() %></p>
              <% } %>

              <p>&nbsp;</p>
              <!--<p><u><i><a href="javascript:window.history.back()"
                          onMouseOver="window.status='Go Back'; return true"
                          onMouseOut="window.status=''; return true">Go Back</a></i></u> &nbsp;/&nbsp; -->
              <p><u><i><a href = "<%= e.getReturnLink() %>">Try Again</a></i></u></p>
              <p>&nbsp;</p>
              <p><u><i><a href = "<%= e.getContactInfo() %>">Email Administrator</a></i></u></p>
            </td>
          </tr>
        </table>

      <!--classifiedError ends here -->

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
