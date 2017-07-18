<%@ page language="java" errorPage="errorPage.jsp" import="java.util.*, java.sql.*, trb.tii.efe.tools.ClassifiedToolUser"%>

<%
    //verify session
    ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
    if (user == null)
    {
       response.sendRedirect("index.jsp");
    }
    //String market = user.getMarket();
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

<%
    String dateFmt = "yyyy-mm-dd hh24:mi:ss";
    String feedid = request.getParameter("feedid");
    String type = request.getParameter("type");
    String advertiser = request.getParameter("advertiser");
    Hashtable adgroup = (Hashtable)session.getAttribute(type+"_grp");

    String dataUser = (String)session.getAttribute("dataUser");
    String dataPass = (String)session.getAttribute("dataPass");
    String dataHost = (String)session.getAttribute("dataHost");
    
    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    Connection conn = DriverManager.getConnection(dataHost, dataUser, dataPass);
    
    PreparedStatement st_adinfo = conn.prepareStatement(
            "select make, model, year, trimname, colorexterior, mileage, price, to_char(dateupdated, '"+dateFmt+"') as dateupdated "+
            "from feedeng.cars where feedid = ? and adid = ?" );
    PreparedStatement st_loginfo = conn.prepareStatement( 
            "select note, to_char(logtime, '"+dateFmt+"') as logtime "+
            "from feedeng.classified_transmit_log "+
            "where secondaryid = ? and primaryid = ? "+
            "order by logtime");
    
    Vector list = (Vector)adgroup.get(advertiser);
    String advname = "";
    if (! type.equals("no_dealer") && list.size() > 0) {
        Hashtable headerAd = (Hashtable)list.get(0);
        advname = (String)headerAd.get("advname");
    }
    String typeDesc = "";
    if (type.equals("no_dealer")) {
        typeDesc = "Ads without dealer mapping";
    } else if (type.equals("over_max")) {
        typeDesc = "Ads exceeding maximum allowed";
    } else if (type.equals("misc_err")) {
        typeDesc = "AdStar Errors";
    }
%>
<span class="table-regtext">
IMPORTANT: Please note the following when marking ads for resend:
<ul>
<li>Correct the issue causing the error before marking ads for resend, otherwise they will fail again.
For example, if a dealer ID is not found, create it in the mapping tool.</li>
<li>After ads are marked for resend, they will take 30 minutes to be sent. You do not need to mark them again.</li>
<li>Ads that have already been successfully sent cannot be marked.</li>
<%
    if (type.equals("no_dealer") || type.equals("over_max")) {
%>
<li>If the "AdStar Status" column reports that the ad has been sent but with errors, please go back to the previous page
and look for the dealer under "Ads with AdStar Errors"</li>
<%
    }
%>
</ul>
</span>

<form name="adlist" method="POST" action="carsErrMark.jsp">
<input type="hidden" name="feedid" value="<%= feedid %>">

<table border="0" cellpadding="1" cellspacing="1">
<tr class="td-info-bg">
 <th colspan="6">
 <span class="table-header">Dealer ID <%= advertiser %><%= advname.equals("") ? "" : ": "+advname %>: <%= typeDesc %></span>
 </th>
</tr>
<tr class="td-title-bg">
<th><span class="table-header">Resend?</span></th>
<th><span class="table-header">Ad ID</span></th>
<th><span class="table-header">AdStar Status</span></th>
<%
    if (type.equals("misc_err")) {
%>
<th><span class="table-header">Log Message</span></th>
<%
    }
%>
<th><span class="table-header">Marked for resend at:</span></th>
<th><span class="table-header">Vehicle Details</span></th>
</tr>
<%
    for (int i=0; i<list.size(); i++) {
        Hashtable ad = (Hashtable)list.get(i);
        
        String primaryid = (String)ad.get("primaryid");
        st_adinfo.setString(1, feedid);
        st_adinfo.setString(2, primaryid);
        ResultSet rset;
        rset = st_adinfo.executeQuery();
        rset.next();
        String make = rset.getString(1);
        String model = rset.getString(2);
        String year = rset.getString(3);
        String trimname = rset.getString(4);
        String colorexterior = rset.getString(5);
        String mileage = rset.getString(6);
        String price = rset.getString(7);
        String dateupdated = rset.getString(8);
        rset.close();
        
        st_loginfo.setString(1, feedid);
        st_loginfo.setString(2, primaryid);
        rset = st_loginfo.executeQuery();
        
        // only keep latest error or success
        // index 0 is note, and 1 is logtime
        String[] adstarError = new String[] {"", ""};
        String[] adstarSuccess = new String[] {"", ""};
        
        //Vector loglist = new Vector();
        while (rset.next()) {
            String note = rset.getString(1);
            String logtime = rset.getString(2);
            if (note.indexOf("AdStar Error") == 0) {
                adstarError[0] = note;
                adstarError[1] = logtime;
            } else if (note.indexOf("AdStar: SUCCESS") == 0) {
                adstarSuccess[0] = note;
                adstarSuccess[1] = logtime;
            }
            //loglist.add(new String[] {note, logtime});
        }
        rset.close();
%>
<tr class="td-<%= (i%2)==0 ? "on" : "off" %>">
<td>
 <center><input type="checkbox" name="send_id" value="<%= primaryid %>" <%= adstarSuccess[1].equals("") ? "" : "disabled" %>></center>
</td>
<td><span class="table-regtext"><%= primaryid %></span></td>
<td><span class="table-regtext"><font color="red">
<% if (! adstarSuccess[1].equals("")) { %>
Sent at <%= adstarSuccess[1] %>
<%
} else if (! adstarError[1].equals("")) {
%>
Sent, but with errors
<%
} else {
%>
Not sent
<%
}
%></font></span>
</td>
<%
    if (type.equals("misc_err")) {
%>
<td><span class="table-regtext"><%= adstarError[0] %></span></td>
<%
    }
%>
<td><span class="table-regtext"><%= dateupdated==null ? "&nbsp;" : dateupdated %></span></td>
<td><span class="table-regtext">
 <%= make %> <%= year %> <%= model %> <%= trimname==null ? "" : trimname %>
 <%= colorexterior==null ? "" : colorexterior %>
 <%= mileage==null ? "" : mileage+" mi" %>
 <%= price==null ? "" : "$"+price %>
 </span>
</td>
</tr>
<%
    }
%>
</table>
<br>
<script>
function checkAll(bool) {
  boxlist = document.forms.adlist.send_id;
  if (boxlist==null) return;
  if (boxlist.checked == undefined) {
    for (i=0; i<boxlist.length; i++) {
      if (! boxlist[i].disabled)
	boxlist[i].checked = bool;
    }
  } else {
    if (! boxlist.disabled)
      boxlist.checked = bool;
  }
}
</script>
<input type="button" value="Check all" onClick="checkAll(true)">
<input type="button" value="Uncheck all" onClick="checkAll(false)">
<br><br>
<input type="submit" value="Mark Checked Ads for Resend">
</form>

<%
    conn.close();
%>

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
