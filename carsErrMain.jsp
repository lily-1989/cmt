<%@ page language="java" errorPage="errorPage.jsp" import="java.util.*, java.sql.*, trb.tii.efe.tools.ClassifiedToolUser"%>

<%
    //verify session
    ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
    if (user == null)
    {
       response.sendRedirect("index.jsp");
    }
%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Cars Correction Tool
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
              <td><font class="page-header">Cars Correction Tool</font></td>
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
	<!-- main panel -->
<span class="table-regtext">Click on a dealer ID to view failed ads for that dealer.<br>
Please note that a single ad may have more than one problem. Check all three sections on this
page for the dealer associated with the ad.
</span>
<br><br>

<%
// make sure to use LIKE for comparison
String NO_DEALER_FILE = "%_dealer.txt";
String OVER_MAX_FILE = "%_max.txt";
//String LOG_TBL = "feedeng.classified_transmit_log";
//String CAR_TBL = "feedeng.cars";
//String dateFmt = "yyyy-mm-dd hh24:mi:ss";
String TARGETJSP = "carsErrList.jsp";

int feedid = Integer.parseInt( request.getParameter("feedid") );

// all these selects should be distinct, otherwise multiple entries for same ad may appear

// this query excludes those that have an over_max error
String sql_no_dealer = "select distinct primaryid, advertiser "+
    "from feedeng.classified_transmit_log CTL, feedeng.cars C "+
    "where C.feedid = ? " +
    "and secondaryid = C.feedid "+
    "and primaryid = C.adid "+
    "and filetransmitted like ? "+
    "and trunc(C.datereceived) = trunc(sysdate) "+
    "and not exists ( "+
    "select primaryid from "+
    "classified_transmit_log CT_IN "+
    "where CTL.primaryid = CT_IN.primaryid "+
    "and CTL.secondaryid = CT_IN.secondaryid "+
    "and CT_IN.filetransmitted like '"+OVER_MAX_FILE+"' "+
    ") "+
    "order by advertiser, primaryid";

String sql_over_max = "select distinct primaryid, advertiser, "+
    "ADV.advname "+
    "from feedeng.classified_transmit_log CTL, feedeng.cars C, feedeng.car_rp_adv ADV "+
    "where C.feedid = ? " +
    "and secondaryid = C.feedid "+
    "and ADV.feedid = C.feedid "+
    "and primaryid = C.adid "+
    "and ADV.dealerid = advertiser "+
    "and filetransmitted like ? "+
    "and trunc(C.datereceived) = trunc(sysdate) "+
    "order by advertiser, primaryid";
    
String sql_misc_err = "select distinct primaryid, advertiser, "+
    "ADV.advname "+
    "from feedeng.classified_transmit_log CTL, feedeng.cars C, feedeng.car_rp_adv ADV "+
    "where C.feedid = ? " +
    "and secondaryid = C.feedid "+
    "and ADV.feedid = C.feedid "+
    "and primaryid = C.adid "+
    "and ADV.dealerid = advertiser "+
    "and note like 'AdStar Error%' "+
    "and trunc(C.datereceived) = trunc(sysdate) "+
    "order by advertiser, primaryid";

String dataUser = (String)session.getAttribute("dataUser");
String dataPass = (String)session.getAttribute("dataPass");
String dataHost = (String)session.getAttribute("dataHost");

DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
Connection conn = DriverManager.getConnection(dataHost, dataUser, dataPass);

// select cars with no dealer mapping
PreparedStatement stmt = conn.prepareStatement(sql_no_dealer);
stmt.setInt(1, feedid);
stmt.setString(2, NO_DEALER_FILE);

ResultSet rset = stmt.executeQuery();

Vector no_dealer = new Vector();
Hashtable no_dealer_grp = new Hashtable();
while (rset.next()) {
    Hashtable row = new Hashtable(10);
    String primaryid = rset.getString(1);
    String advertiser = rset.getString(2);

    if (advertiser == null)
        advertiser = "";  // signifies missing advertiser number; but this should never happen
    
    row.put("primaryid", primaryid);
    row.put("advertiser", advertiser);
    no_dealer.add(row);
    
    Vector list = (Vector)no_dealer_grp.get(advertiser);
    if (list == null) {
        list = new Vector();
        no_dealer_grp.put(advertiser, list);
    }
    list.add(row);
}

session.setAttribute("no_dealer", no_dealer);
session.setAttribute("no_dealer_grp", no_dealer_grp);

// do String sort of advertisers
Object keyArr[] = no_dealer_grp.keySet().toArray();
Arrays.sort(keyArr);
%>
<table border="0" cellspacing="1" cellpadding="1">
<tr class="td-info-bg"><th colspan="2"><span class="table-header">Ads with no dealer ID mapping</span></th></tr>
<tr class="td-title-bg">
  <th><span class="table-header">Dealer ID</span></th>
  <th><span class="table-header">Number of ads</span></th>
</tr>
<%
for (int i=0; i<keyArr.length; i++) {
    String advertiser = (String)keyArr[i];
    int count = ((Vector)no_dealer_grp.get(advertiser)).size();
%>
<tr class="td-<%= (i%2)==0 ? "on" : "off" %>">
<td>
  <span class="table-regtext">
  <a href="<%= TARGETJSP %>?feedid=<%= feedid %>&type=no_dealer&advertiser=<%= advertiser %>"><%= advertiser %></a>
  </span>
</td>
<td><span class="table-regtext"><%= count %></span></td>
</tr>
<%
}
%>

</table>
<br><br>

<%
// now select cars over max limit
stmt = conn.prepareStatement(sql_over_max);
stmt.setInt(1, feedid);
stmt.setString(2, OVER_MAX_FILE);
rset = stmt.executeQuery();
Vector over_max = new Vector();
Hashtable over_max_grp = new Hashtable();
while (rset.next()) {
    Hashtable row = new Hashtable(10);
    String primaryid = rset.getString(1);
    String advertiser = rset.getString(2);
    String advname = rset.getString(3);

    // NOTE: advertiser should never be null for this query
    
    row.put("primaryid", primaryid);
    row.put("advertiser", advertiser);
    row.put("advname", advname);
    
    over_max.add(row);
    
    Vector list = (Vector)over_max_grp.get(advertiser);
    if (list == null) {
        list = new Vector();
        over_max_grp.put(advertiser, list);
    }
    list.add(row);
}

session.setAttribute("over_max", over_max);
session.setAttribute("over_max_grp", over_max_grp);

keyArr = over_max_grp.keySet().toArray();
Arrays.sort(keyArr);
%>
<table border="0" cellpadding="1" cellspacing="1">
<tr class="td-info-bg"><th colspan="3"><span class="table-header">Ads above the maximum count for dealer</span></th></tr>
<tr class="td-title-bg">
  <th><span class="table-header">Dealer ID</span></th>
  <th><span class="table-header">Dealer Name</span></th>
  <th><span class="table-header">Number of ads</span></th>
</tr>
<%
for (int i=0; i<keyArr.length; i++) {
    String advertiser = (String)keyArr[i];
    Hashtable row = (Hashtable)((Vector)over_max_grp.get(advertiser)).get(0);
    //System.err.println(row);
    int count = ((Vector)over_max_grp.get(advertiser)).size();
%>
<tr class="td-<%= (i%2)==0 ? "on" : "off" %>">
<td>
  <span class="table-regtext">
  <a href="<%= TARGETJSP %>?feedid=<%= feedid %>&type=over_max&advertiser=<%= advertiser %>"><%= advertiser %></a>
  </span>
</td>
<td><span class="table-regtext"><%= row.get("advname") %></span></td>
<td><span class="table-regtext"><%= count %></span></td>
</tr>
<%
}
%>
</table>
<br><br>

<%
// now select ads with AdStar errors
stmt = conn.prepareStatement(sql_misc_err);
stmt.setInt(1, feedid);
rset = stmt.executeQuery();
Vector misc_err = new Vector();
Hashtable misc_err_grp = new Hashtable();
while (rset.next()) {
    Hashtable row = new Hashtable(10);
    String primaryid = rset.getString(1);
    String advertiser = rset.getString(2);
    String advname = rset.getString(3);

    // NOTE: advertiser should never be null for this query
    
    row.put("primaryid", primaryid);
    row.put("advertiser", advertiser);
    row.put("advname", advname);
    
    misc_err.add(row);
    
    Vector list = (Vector)misc_err_grp.get(advertiser);
    if (list == null) {
        list = new Vector();
        misc_err_grp.put(advertiser, list);
    }
    list.add(row);
}

conn.close();
    
session.setAttribute("misc_err", misc_err);
session.setAttribute("misc_err_grp", misc_err_grp);

keyArr = misc_err_grp.keySet().toArray();
Arrays.sort(keyArr);

%>
<table border="0" cellpadding="1" cellspacing="1">
<tr class="td-info-bg"><th colspan="3"><span class="table-header">Ads with AdStar errors</span></th></tr>
<tr class="td-title-bg">
  <th><span class="table-header">Dealer ID</span></th>
  <th><span class="table-header">Dealer Name</span></th>
  <th><span class="table-header">Number of ads</span></th>
</tr>
<%
for (int i=0; i<keyArr.length; i++) {
    String advertiser = (String)keyArr[i];
    Hashtable row = (Hashtable)((Vector)misc_err_grp.get(advertiser)).get(0);
    //System.err.println(row);
    int count = ((Vector)misc_err_grp.get(advertiser)).size();
%>
<tr class="td-<%= (i%2)==0 ? "on" : "off" %>">
<td>
  <span class="table-regtext">
  <a href="<%= TARGETJSP %>?feedid=<%= feedid %>&type=misc_err&advertiser=<%= advertiser %>"><%= advertiser %></a>
  </span>
</td>
<td><span class="table-regtext"><%= row.get("advname") %></span></td>
<td><span class="table-regtext"><%= count %></span></td>
</tr>
<%
}
%>
</table>
<br><br>

	<!-- end main panel -->
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
