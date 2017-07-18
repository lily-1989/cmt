<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.util.*, java.sql.*, oracle.jdbc.driver.*, trb.tii.efe.tools.*"%>

<%
    CCI_Ad ad = (CCI_Ad)session.getAttribute("ad");

%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Events
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
              <td><font class="page-header">Events</font></td>
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

<!-- info table -->
<table border="0" align="center" width="100%" cellspacing="0" cellpadding="4">
<tr>
    <td colspan="2">
        <span class="edit-label"><a href = "editAd?adid=<%=ad.getAdID()%>&feedid=<%=ad.getFeedID()%>&table=<%=ad.getTableName()%>&market=<%= ad.getMarket() %>">Edit Ad</a></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <span class="edit-label"><a href="editImages?adid=<%=ad.getAdID()%>&feedid=<%=ad.getFeedID()%>&table=<%=ad.getTableName()%>&market=<%= ad.getMarket()%>">Edit Images</a></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <span class="edit-label">Events</span>
    </td>
</tr>
                    <tr>
                      <th class="td-title-bg" colspan="2" align="left"><span class="table-header">Ad Summary</span></th>
                    </tr>
                    <tr>
                      <th class="td-on" align = "left" valign="top"><span class="edit-label">Ad ID:</font></span></th>
                      <td class="td-on"><span class="edit-text"><%= ad.getAdID() %></span></td>
                    </tr>
                    <tr>
                      <th class="td-on" align = "left" valign="top"><span class="edit-label">Publication:</span></th>
                      <td class="td-on"><span class="edit-text"><%= ad.getPublication() %> - <%= ad.getFeedID() %></span></td>
                    </tr>
                    <% if (!Constants.RECYCLER_TABLE_NAME.equalsIgnoreCase(ad.getTableName())) { %>
                    <tr>
                      <th class="td-on" align = "left" valign="top"><span class="edit-label">Vertical:</font></span></th>
                      <td class="td-on"><span class="edit-text"><%= ad.getVertical() %> (<%= ad.getTableName() %>)</span></td>
                    </tr>
                    <tr>
                      <th class="tdL1" colspan="2"><hr color="white"></th>
                    </tr>
                    <tr>
                      <th class="td-on" align = "left" valign="top"><span class="edit-label">First Received:</span></th>
                      <td class="td-on"><span class="edit-text"><%= ad.getFirstReceived() == null ? "N/A" : ad.getDateAsLongString(ad.getFirstReceived()) %></span></td>
                    </tr>
                    <% } %>
                    <tr>
                      <th class="td-on" align = "left" valign="top"><span class="edit-label">Date Received:</span></th>
                      <td class="td-on"><span class="edit-text"><%= ad.getDateReceived() == null ? "N/A" : ad.getDateAsLongString(ad.getDateReceived()) %></span></td>
                    </tr>
                    <tr>
                      <th class="td-on" align = "left" valign="top"><span class="edit-label">Date Updated:</span></th>
                      <td class="td-on"><span class="edit-text"><%= ad.getDateUpdated() == null ? "N/A" : ad.getDateAsLongString(ad.getDateUpdated()) %></span></td>
                    </tr>


<%
    DriverManager.registerDriver(new OracleDriver());
    String dataHost = (String) session.getAttribute("dataHost");
    String dataUser = (String) session.getAttribute("dataUser");
    String dataPass = (String) session.getAttribute("dataPass");

    Connection conn = DriverManager.getConnection(dataHost, dataUser, dataPass);
    String adid = ad.getAdID();
    String feedid = ad.getFeedID().toString();
    String selectString;
    PreparedStatement stmt;
    ResultSet rset = (ResultSet)session.getAttribute("eventsResultSet");

    selectString = "select to_char(logtime, 'yyyy-mm-dd hh24:mi:ss'), note, config, filetransmitted from classified_transmit_log "+
                    "where primaryid = ? and secondaryid = ? order by logtime";
    stmt = conn.prepareStatement(selectString);
    stmt.setString(1, adid);
    stmt.setInt(2, Integer.parseInt(feedid));
    rset = stmt.executeQuery();
%>
<tr>
  <th colspan="2">&nbsp;</th> 
</tr>
</table>


<!-- event table -->
<table border="0" width="100%" align="center" cellspacing="0" cellpadding="4">
<tr class="edit-header-bg">
  <th align="left"><span class="table-header">Time</span></th>
  <th align="left"><span class="table-header">Activity</span></th>
  <th align="left"><span class="table-header">Config file</span></th>
  <th align="left"><span class="table-header">File transferred</span></th>
</tr>

<%
    int toggle = -1;
    while (rset.next()) {
%>
<tr class="<%= (toggle=(toggle+1)%2)==0 ? "edit-bg1" : "edit-bg2" %>">
  <td valign = "top" class="results"><%= rset.getString(1) %></td>
  <td valign = "top" class="results"><%= rset.getString(2) %></td>
  <td valign = "top" class="results"><%= rset.getString(3) %></td>
  <td valign = "top" class="results"><%= rset.getString(4) %></td>
</tr>
<%
    }
%>

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
