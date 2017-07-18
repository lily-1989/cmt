<%@ page language="java" errorPage="errorPage.jsp" import = "java.util.*, java.text.*, trb.tii.efe.tools.*"%>

<%

  //check to make sure session
  // file name: listCommunities.jsp
  
  if (session.getAttribute("user") == null)
  {
     response.sendRedirect("index.jsp");
  }

    Vector values = (Vector) session.getAttribute("listCommunitiesValues");

%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Community List
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
              <td><font class="page-header">Community List</font></td>
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
    
<!-- list communities table -->
<table border="0" width="100%">
        <tr>
          <td align = "left" valign = "top">

<table border = "0" width = "100%" cellspacing="2">
    <form method = "post" action = "createCommunity.jsp">

<tr>
   <th colspan="6" align = "left" class="td-info-bg"><span class="table-header">Number of Results: <%= values.size() %></span></th>
</tr>
<tr class="td-title-bg">
   <th width = "130" align = "left"><span class="table-header">Neighborhood</span></th>
   <th width = "130" align = "left"><span class="table-header">Community</span></th>
   <th width = "130" align = "left"><span class="table-header">City</span></th>
   <th width = "130" align = "left"><span class="table-header">County</span></th>
   <th align = "left" colspan = "2"><span class="table-header">State</span></th>
</tr>

<%
        int tog = -1;
        
        for (int i=0; i<values.size(); i++)
        {
           Community cmt = (Community) values.elementAt(i);
		   
%>
<tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
	<td width ="130" align="left"><span class="table-regtext"><%= cmt.getNeighborhood() %></span></td>
	<td width ="130" align="left"><span class="table-regtext"><%= cmt.getCommunity() %></span></td>
	<td width ="130" align="left"><span class="table-regtext"><%= cmt.getCity() %></span></td>
	<td width ="130" align="left"><span class="table-regtext"><%= cmt.getCounty() %></span></td>
	<td align="left"><span class="table-regtext"><%= cmt.getState() %></span></td>

    <th><span class="table-regtext">
      <a href = "listCommonNames?communityid=<%= cmt.getId() %>&community=<%= cmt.getCommunity() %>">view</a>
      </span>
    </th>		
</tr>
<%
}
%>
    <tr>
        <td></td>
        <td valign = "middle" align = "left"><input type = "submit" value = "Create New Community"/></td>
    </tr>
  </form>
</table>
</td>
</tr>
</table>
<!-- list communities table -->


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
