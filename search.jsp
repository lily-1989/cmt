<!-- Search.jsp -->

<%@ page language="java" errorPage="errorPage.jsp" import = "java.util.*, java.text.*"%>

<%
  //check to make sure session
  if (session.getAttribute("user") == null)
  {
     response.sendRedirect("index.jsp");
  }
%>

<html>
<head>
    <title>Search Ads</title>
    <link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">

    <SCRIPT LANGUAGE="JavaScript" SRC="include/calendarpopup/CalendarPopup.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">var cal = new CalendarPopup("caldiv");</SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
    <DIV ID="caldiv" STYLE="position:absolute;visibility:hidden;background-color:white;layer-background-color:white;"></DIV>

    <script type="text/javascript">
    function setfocus() {
        document.forms[0].adid.focus()
    }
    </script>

    <SCRIPT LANGUAGE="JavaScript">
    function checkDates() {

    var dateRecVar = document.search.dateReceived;
    var startDateVar = document.search.startDate;

    var dateRecResult = true;
    var startDateResult = true;

    var regex = /(19|20)\d\d[/](0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])/;


    if ((!dateRecVar.value.match(regex)) && (dateRecVar.value != '')) {
        dateRecResult = false;
    }

    if ((!startDateVar.value.match(regex)) && (startDateVar.value != '')) {
        startDateResult = false;
    }


    if (!dateRecResult && !startDateResult) {
        alert ('Date Received and Start Date must be in the format yyyy/mm/dd');
        dateRecVar.focus();
        return false;
    }
    else if (!dateRecResult && startDateResult) {
        alert ('Date Received must be in the format yyyy/mm/dd');
        dateRecVar.focus();
        return false;
    }
    else if (dateRecResult && !startDateResult) {
        alert ('Start Date must be in the format yyyy/mm/dd');
        startDateVar.focus();
        return false;
    }
    else {
        return true;
    }

    }
    </script>



</head>

<body bgcolor="#ffffff" text="#000000" link="#003366" vlink="#336699" alink="#990000" leftmargin="0" topmargin="2" marginwidth="0" marginheight="2" onload=setfocus()>

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
              <td><font class="page-header">Search Ads</font></td>
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



<FORM NAME="search" ACTION = "results" METHOD= "post" onsubmit = "return checkDates()";>

<table width="100%" border="0" cellspacing="0" cellpadding="3">
<tr>
    <td class="td-title-bg" colspan="2">
	<div align="left">
	    <span class="table-header">Search by</span>
	</div>
    </td>
</tr>

<tr class="td-on">
  <td> <span class="edit-label">Ad Type:</span> </td>
  <td>
    <select name="adtype">
      <option value = "0">All</option>
      <option value = "1">Apartments</option>
      <option value = "2">General Classifieds</option>
      <option value = "3">Jobs</option>
      <option value = "4">Real Estate</option>
      <option value = "5">Transportation</option>
      <option value = "6">Vital Notices</option>
      <option value = "7">Recycler</option>
    </select>
  </td>
</tr>

<tr class="td-off">
    <td><span class="edit-label">Ad ID:</span></td>
    <td><input type="text" size="40"  name="adid"></td>
</tr>
<tr class="td-on">
    <td><span class="edit-label">Class:</span></td>
    <td><input type="text" size="40"  name="class"></td>
</tr>
<tr class="td-off">
    <td><span class="edit-label">Content:</span></td>
    <td><input type="text" size="40"  name="content"></td>
</tr>
<tr class="td-on">
    <td><span class="edit-label">Contact Phone:</span></td>
    <td><input type="text" size="40"  name="phone"></td>
</tr>
<tr class="td-off">
    <td><span class="edit-label">Contact Email:</span></td>
    <td><input type="text" size="40"  name="email"></td>
</tr>

<TR class="td-on">
     <td><span class="edit-label">Date Received:</span></td>
     <td>
         <input name="dateReceived" size="10" title="yyyy/mm/dd">
         <A HREF="#" onClick="cal.select(document.forms['search'].dateReceived,'anchor1','yyyy/MM/dd'); return false;" NAME="anchor1" ID="anchor1"><img src = "images/cal.gif" border="0" alt="calendar"></A>
         &nbsp<span class="edit-helptext">(yyyy/mm/dd)</span>
     </td>
</TR>
<TR>
    <td><span class="edit-label">Start Date:</span></td>
    <td>
	    <input name="startDate" size="10" title="yyyy/mm/dd">
        <A HREF="#" onClick="cal.select(document.forms['search'].startDate,'anchor2','yyyy/MM/dd'); return false;" NAME="anchor2" ID="anchor2"><img src = "images/cal.gif" border="0" alt="calendar"></A>
        &nbsp<span class="edit-helptext">(yyyy/mm/dd)</span>
   </td>
</TR>
<TR>
    <TD></td>
    <td><input type = "submit" value = "Display Ads" colspan ="2"/> <INPUT TYPE="reset"></td>
</TR>
</TABLE>
</FORM>
          </td>
        </tr>
      </table>
    <!--end of searchIndex table -->

<br><br><br>
<!--end main table -->
<tr><td colspan="3" bgcolor="#000000" width="100%"><img src="images/clear.gif" height="1" width="100%"></td></tr>
</table>
</td>
</tr>
</table>
</body>
</html>
