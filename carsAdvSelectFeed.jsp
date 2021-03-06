<%@ page language="java" errorPage="errorPage.jsp" import="java.util.*, java.sql.*, oracle.jdbc.driver.*, trb.tii.efe.tools.*" %>
<%
  //check to make sure session
  ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
  if (user == null)
  {
     response.sendRedirect("index.jsp");
  }
  String market = user.getMarket();
%>

<%
String host = (String)session.getAttribute("dataHost");
String dataUser = (String)session.getAttribute("dataUser");
String dataPass = (String)session.getAttribute("dataPass");

DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

Hashtable feedids = new Hashtable();
Boolean placeHold = new Boolean(true);

// PUT valid feedids manually
feedids.put( new Integer( 251 ), placeHold ); // CHI
feedids.put( new Integer( 252 ), placeHold ); // LA
feedids.put( new Integer( 253 ), placeHold ); // HRT
feedids.put( new Integer( 254 ), placeHold ); // NY
feedids.put( new Integer( 255 ), placeHold ); // ORL
feedids.put( new Integer( 256 ), placeHold ); // FTL

Connection conn = DriverManager.getConnection(host, dataUser, dataPass);

 PreparedStatement pstmt = conn.prepareStatement(
			" select distinct FC.feedid, market, feedname "+
			"from feedconfig FC "+
			"where FC.market LIKE ?");
pstmt.setString(1, market);
ResultSet rset = pstmt.executeQuery();


%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Cars Reverse Publishing Advertisers
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
              <td><font class="page-header">Cars Reverse Publishing Advertisers</font></td>
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

<form action="carsAdvSearch.jsp">
<input type="hidden" name="index" value="0"/>

<table width = "100%" border="0" cellspacing="1" cellpadding="1">		
<tr>
<th class="td-title-bg" colspan="2" align="left"><span class="table-header">Filtering Options</span></th>
</tr>
		  
<tr>
  <td width="35%"><span class="table-text">Choose the feed:</span></td>
  <td>
<!-- START SELECT BOX -->
<select name="feedid">

<%
int feedcount = 0; // if only 1 feed, select it automatically using a redirect
int lastValidFeedid=0;

// START ITERATOR
while (rset.next()) {
int disp_feedid = rset.getInt(1);
String disp_market = rset.getString(2);
String disp_feedname = rset.getString(3);

if ( feedids.containsKey(new Integer(disp_feedid)) ) {
feedcount++;
lastValidFeedid = disp_feedid;
%>

<option value="<%= disp_feedid %>">
  <%= disp_market %> - <%= disp_feedname %>
</option>

<%  // END ITERATOR
} // if
} // while (rset.next())

if (feedcount == 1)
  response.sendRedirect("carsAdvSearch.jsp?feedid="+lastValidFeedid+"&searchkey=&index=0");
System.out.println("feedcount: "+feedcount);
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
  <td><span class="table-text">Optional advertiser name search:</span></td>
  <td><input type="text" name="searchkey" size="40"></td>
</tr>

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
