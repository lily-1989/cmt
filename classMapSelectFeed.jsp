<%@ page language="java" errorPage="errorPage.jsp" import="java.util.*, java.sql.*, oracle.jdbc.driver.*, trb.tii.efe.tools.*" %>
<%
  //check to make sure session
  ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
  if (user == null)
  {
     response.sendRedirect("index.jsp");
  }
  String market = user.getMarket();

  // if market has no SQL wildcards
  if (market.indexOf("%") == -1 && market.indexOf("_") == -1)
    response.sendRedirect("classMapList.jsp?market="+market+"&index=0");
%>

<%
String host = (String)session.getAttribute("dataHost");
String dataUser = (String)session.getAttribute("dataUser");
String dataPass = (String)session.getAttribute("dataPass");

DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

Connection conn = DriverManager.getConnection(host, dataUser, dataPass);

 PreparedStatement pstmt = conn.prepareStatement(
			" select distinct market "+
			"from feedconfig FC "+
			"where FC.market LIKE ? "+
                        "order by market");
pstmt.setString(1, market);
ResultSet rset = pstmt.executeQuery();


%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Recycler Class Mapping Translations
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
              <td><font class="page-header">Recycler Class Mapping Translations</font></td>
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


    <!--select Feed table-->

<table border="0" width="100%">
        <tr>
          <td align = "left" valign = "top">

<form action="classMapList.jsp">
<input type="hidden" name="index" value="0"/>

<table width = "100%" border="0" cellspacing="1" cellpadding="1">		
<tr>
<th class="td-title-bg" colspan="2" align="left"><span class="table-header">Filtering Options</span></th>
</tr>
		  
<tr>
  <td width="35%"><span class="table-text">Choose the feed:</span></td>
  <td>
<!-- START SELECT BOX -->
<select name="market">

<%
// START ITERATOR
while (rset.next()) {
String disp_market = rset.getString(1);
%>

<option value="<%= disp_market %>">
  <%= disp_market %>
</option>

<%  // END ITERATOR
} // while (rset.next())

%>

</select>
<!-- END SELECT BOX -->

</td>

<%  // CLEANUP
rset.close();
pstmt.close();
conn.close();
//System.out.println(feedid);
%>

<tr>
  <td></td>
  <td align="left"><input type=submit value="Get Listing"></td>
</tr>
</table>

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
