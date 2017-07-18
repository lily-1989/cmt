<%@ page language="java" errorPage="errorPage.jsp" import = "java.util.*, java.text.*, trb.tii.efe.tools.*"%>

<%

  //check to make sure session
  //file name: listCommonNames.jsp
  if (session.getAttribute("user") == null)
  {
     response.sendRedirect("index.jsp");
  }

    Vector values = (Vector) session.getAttribute("listCommonNamesValues");
    String community = request.getParameter("community");
    int communityid = Integer.parseInt(request.getParameter("communityid"));

%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
View Commonly Used Names
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
              <td><font class="page-header">Commonly Used Names</font></td>
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
    
<!-- communities table -->
<table border = "0" width = "100%" valign = "top">
    <form method = "post" action = "createCommonName">


<tr>
   <th colspan="3" align = "left" class="td-info-bg"><span class="table-header">Number of Results: <%= values.size() %></span></th>
</tr>
<tr class="td-title-bg">
   <th width = "100" align = "left"><span class="table-header">Feed ID</span></th>
   <th align = "left" colspan="2"><span class="table-header">Commonly Used Names</span></th>
</tr>

<%
        int tog = -1;
        
        for (int i=0; i<values.size(); i++)
        {
           CommonName cmn = (CommonName) values.elementAt(i);
	
		   String comName = cmn.getName();
		   String newComName = "";
		   
		   if (comName.indexOf("<")<0) {
		   		newComName = comName;
		   } else {
			   //take the < off of the string
			   int ii = comName.indexOf("<");
			   String comName1 = comName.substring(0,ii);
			   String comName2 = comName.substring(ii+1);
			   newComName = comName1 + "&lt;" + comName2;
			   System.out.println("The new common name is: " + newComName);
	   	   }
%>
<tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
    <td width="100" align="left"><span class="table-regtext"><%= cmn.getFeedid() %></span></td>
	<td align="left"><span class="table-regtext"><%= newComName %></span></td>
    <th align="right"><span class="table-regtext">
        <a href = "deleteCommonName?id=<%= cmn.getId() %>&communityid=<%= cmn.getCommunityid() %>&community=<%= community%>">delete</a>
        </span>
    </th>
</tr>
<%
}
%>
	<tr>
		<td><input type="hidden" name="community" value="<%= community %>"</td>
		<td><input type="hidden" name="communityid" value="<%= communityid %>"</td>
	</tr>
    <tr>
        <td colspan = "6" valign = "middle" align = "right"><input type = "submit" value = "New Common Name"/></td>
    </tr>
  </form>
</table>
<!-- communities table -->

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
