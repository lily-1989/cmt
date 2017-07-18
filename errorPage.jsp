<%@ page language="java" isErrorPage="true" import="java.util.*, java.sql.*" %>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Unhandled Error
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

<!-- being error table -->
<table border="0" width="100%">

<tr class="td-info-bg">
    <th colspan="2" align="left"><span class="table-header">Exception Details</span></th>
</tr>
<tr>
<td colspan="2"><span class="table-text">
<!-- The fully-qualified class that is the exception -->
<%
    if (exception != null) {
%>
<%= exception.toString() %>
<%
    }
%>
<br>
<!-- The exception's message to the world -->
<%
    if (exception != null) {
%>
<%= exception.getMessage() %>
<%
    }
%>
</span>
</td>
</tr>

<tr>
  <td></td>
</tr>

<tr>
    <td colspan="2">
    <span class="table-regtext">
      <a href="javascript:window.history.back()"
         onMouseOver="window.status='Go Back'; return true"
         onMouseOut="window.status=''; return true">Go Back</a> &nbsp;/&nbsp;
      <a href="<%= request.getHeader("REFERER") %>">Start Over</a>
    </span>
    </td>
</tr>

<tr>
  <td></td>
</tr>

<%! Enumeration parameterList; %>
<%! Enumeration attributeList; %>


<tr class="td-title-bg">
  <th colspan="2" align="left"><span class="table-header">Field Values</span></th>
</tr>

<%
   int tog = -1;
   parameterList = request.getParameterNames(); 
   while (parameterList.hasMoreElements()) {
     String pName = (String) parameterList.nextElement();
%>

<tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
    <td width="35%" valign="top"><span class="table-regtext"><%= pName %></span></td>
    <td><span class="table-regtext"><%= request.getParameter(pName) %></span></td>
</tr>

<% } %>

<tr>
  <td></td>
</tr>

<tr class="td-title-bg">
  <th colspan="2" align="left"><span class="table-header">Attribute listing</span></th>
</tr>

<%
   tog = -1;
   Enumeration attributeList = request.getAttributeNames(); 
   while (attributeList.hasMoreElements()) {
     String attName = (String) attributeList.nextElement();
%>

<tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
    <td width="35%" valign="top"><span class="table-regtext"><%= attName %></span></td>
    <td><span class="table-regtext"><%= request.getAttribute(attName) %></span></td>
</tr>

<% } %>
</table>
<!-- end error table -->

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
