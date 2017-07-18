<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.util.*, trb.tii.efe.tools.*"%>

<% List searchList = (ArrayList) session.getAttribute("ads"); %>


<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
View Ads
</title>
</head>

<script type = "text/javascript">
<!--

function confirmDelete() {
    var myConfirm = confirm("Sure you want to Delete?");
    return myConfirm
}

-->
</script>

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
              <td><font class="page-header">View Ads</font></td>
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
   <th bgcolor="#ffffff"><img src="images/clear.gif" height="4" width="100%"></th>
</tr>

<tr>
  <td rowspan="24" width="12" bgcolor="#ffffff"><img src="images/clear.gif" width="1" height="1" alt=""></td>
  <td>


<!-- main panel -->
<table border = "0" width = "100%" valign = "top" cellspacing="0" cellpadding="4" border="1">

<tr>
   <th colspan="7" align = "left" class="td-info-bg"><span class="results-header">Number of Results: <%= searchList.size() %></span></th>
</tr>
<tr>
   <th align = "left" class="td-title-bg"><span class="results-header">Market</span></th>
   <th align = "left" class="td-title-bg"><span class="results-header">Ad</span></th>
   <th align = "left" class="td-title-bg"><span class="results-header">Class</span></th>
   <th align = "left" class="td-title-bg"><span class="results-header">Title</span></th>
   <th colspan="3" class="td-title-bg">&nbsp;</th>
</tr>
<%
        int toggle = 0;
            Iterator searchListIterator = searchList.iterator();
            CCI_Search ad;

            while ( searchListIterator.hasNext() ) {
                ad = ( CCI_Search ) searchListIterator.next();
%>
<input type = "hidden" name = "adid" value = "<%= ad.getAdID() %>"/>
<tr <%= toggle==0 ? "class=\"td-on\"" : "class=\"td-off\"" %>>
    <th align = "left" valign = "top"><span class="results-bold "><%= ad.getMarket() %>-<%= ad.getFeedID() %></span></th>
    <th align = "left" valign = "top"><span class="results-bold "><%= ad.getAdID() %></span></th>
    <th align = "left" valign = "top"><span class="results-bold "><%= ad.getClassID() %></span></th>
    <th align = "left" valign = "top" rowspan = "2"><span class="results"><%= ad.getTitle() %></span></th>
    <th align = "left" valign = "top"><span class="results-bold"><a href = "editAd?adid=<%=ad.getAdID()%>&feedid=<%=ad.getFeedID()%>&table=<%=ad.getTableName()%>&market=<%= ad.getMarket() %>">Edit</a></span></th>
</tr>
<tr <%= toggle==0 ? "class=\"td-on\"" : "class=\"td-off\"" %>>
    <th colspan = "3"></span>
    <th align = "left" valign = "top"><span class="results-bold"><a href="editImages?adid=<%=ad.getAdID()%>&feedid=<%=ad.getFeedID()%>&table=<%=ad.getTableName()%>&market=<%= ad.getMarket()%>">Images</a></span></th>
</tr>
<tr <%= toggle==0 ? "class=\"td-on\"" : "class=\"td-off\"" %>>
    <th align = "left" valign = "top" colspan = "1"><span class="results-italic">StartDate:</span></th>
    <th align = "left" valign = "top" colspan = "1"><span class="results"><%= ad.getStartDate() %></span></th>
    <th colspan = "2"></span>
    <th align = "left" valign = "top"><span class="results-bold"><a href="events?adid=<%=ad.getAdID()%>&feedid=<%=ad.getFeedID()%>&table=<%=ad.getTableName()%>&market=<%= ad.getMarket()%>">Events</a></span></th>
</tr>
<tr <%= toggle==0 ? "class=\"td-on\"" : "class=\"td-off\"" %>>
    <th align = "left" valign = "top" colspan = "1"><span class="results-italic">StopDate:</span></th>
    <th align = "left" valign = "top" colspan = "1"><span class="results"><%= ad.getStopDate() %></span></th>
    <th colspan = "2"></span>
    <th align = "left" valign = "top"><span class="results-bold"><a href = "deleteAd?adid=<%= ad.getAdID() %>&table=<%= ad.getTableName() %>&publication=<%= ad.getPublication() %>&feedid=<%= ad.getFeedID() %>" onClick="return confirmDelete()">Delete</a></span></th>
</tr>

<%
           toggle = (toggle+1) % 2; 
        }
%>
</table>
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
