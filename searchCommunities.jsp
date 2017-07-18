<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.tools.*"%>

<%
  //check to make sure session
  if (session.getAttribute("user") == null)
  {
     response.sendRedirect("index.jsp");
  }

    Vector values = (Vector) session.getAttribute("commStateValues");
%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Real Estate Communities
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
              <td><font class="page-header">Real Estate Communities</font></td>
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
    
    <!--beginning of searchCommunities table-->
      <table width="100%" border="0">
        <tr> 
          <td align = "center" valign = "top">
		  <FORM ACTION="listCommunities" METHOD= "post">
      
	  <table width = "100%" border="0" cellspacing="1" cellpadding="1">		
	  <tr>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	  <th width="35%"> Select State:</th>
	  <td>  
	    <Select Type="select" size = "1" NAME="state">	
<%
    for (int i=0; i<values.size(); i++) {
        
%>

		<option value = "<%= values.elementAt(i) %>"><%= values.elementAt(i) %> </option>	
		
<%
		}

%>		  
		  
		</Select>
		</td>
		</tr>
		<tr>
		<td></td>
		<td><input type="submit" value="Search"/></td>
		</tr>
        
		

  </TABLE>
 </FORM>
          </td>
        </tr>
      </table>

    <!--end of searchIndex table -->

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
