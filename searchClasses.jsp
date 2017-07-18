<%@ page language="java" errorPage="errorPage.jsp" import="java.util.*, java.sql.*, oracle.jdbc.driver.*, trb.tii.efe.tools.*" %>

<%
  //check to make sure session
  if (session.getAttribute("user") == null)
  {
     response.sendRedirect("index.jsp");
  }
%>


<% 
    List feedList = (ArrayList) session.getAttribute("feedNames");      
    List vendorList = (ArrayList) session.getAttribute("vendorNames"); 

// set up recycler categories for drop down box on insertCategory.jsp and updateCategory.jsp
Vector rcyCatList = new Vector(50);
    // make sure empty string is first so it will be default for blank or invalid categories
    rcyCatList.add("");
    rcyCatList.add("Antiques & Collectibles");
    rcyCatList.add("Automotive");
    rcyCatList.add("Automotive Parts & Accessories");
    rcyCatList.add("Baby & Kids Merchandise");
    rcyCatList.add("Bikes");
    rcyCatList.add("Boats");
    rcyCatList.add("Cars, Trucks & Vans");
    rcyCatList.add("Cats");
    rcyCatList.add("Child Care & Domestic Jobs");
    rcyCatList.add("Clothes & Accessories");
    rcyCatList.add("Collector Shows");
    rcyCatList.add("Computer");
    rcyCatList.add("Computers");
    rcyCatList.add("Dogs");
    rcyCatList.add("Electronics & Cameras");
    rcyCatList.add("Employment Services");
    rcyCatList.add("Financial");
    rcyCatList.add("For Rent");
    rcyCatList.add("Free, Swap & Barter");
    rcyCatList.add("Furniture & Furnishings");
    rcyCatList.add("Garage Sales");
    rcyCatList.add("Groups");
    rcyCatList.add("Household & Home Improvement");
    rcyCatList.add("Household Appliances & Tools");
    rcyCatList.add("Jobs Wanted");
    rcyCatList.add("Legal");
    rcyCatList.add("Lessons");
    rcyCatList.add("Lost & Found");
    rcyCatList.add("Miscellaneous");
    rcyCatList.add("Miscellaneous Announcements");
    rcyCatList.add("Motorcycles & ATVs");
    rcyCatList.add("Musical Instruments & Supplies");
    rcyCatList.add("Odd Jobs & Part-Time Jobs");
    rcyCatList.add("Other Animals");
    rcyCatList.add("Pet Services");
    rcyCatList.add("Pet Supplies & Accessories");
    rcyCatList.add("RVs, Trailers & Motor Homes");
    rcyCatList.add("Real Estate Agents");
    rcyCatList.add("Real Estate Wanted");
    rcyCatList.add("Real Estate for Sale");
    rcyCatList.add("Ride Share");
    rcyCatList.add("Roommates");
    rcyCatList.add("Seasonal Merchandise");
    rcyCatList.add("Small Business");
    rcyCatList.add("Sporting Goods");
    rcyCatList.add("Tickets");
    rcyCatList.add("Vacation Rentals");
    rcyCatList.add("Volunteers");

    session.setAttribute("rcyCatList", rcyCatList);
%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Export Class Lookup
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
              <td><font class="page-header">Export Class Lookup</font></td>
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


<%
    if (!feedList.isEmpty())
    {
        %>
<form action="listClasses">
<input type="hidden" name="index" value="0"/>

<table width = "100%" border="0" cellspacing="0" cellpadding="3">
<tr>
<th class="td-title-bg" colspan="2" align="left"><span class="table-header">Search by</span></th>
</tr>


<tr class="td-on">
    <td width><span class="edit-label">Choose the feed:</span></td>
    <td>
        <select name="feedID">
<%
            Iterator feedListIterator = feedList.iterator();
            MarketRecord record;

            while ( feedListIterator.hasNext() ) {
                record = ( MarketRecord ) feedListIterator.next();
%>
        <option value="<%= record.getFeedID() %>"><%= record.getPubName() %> - <%= record.getFeedName() %></option>
<%
        }
%>
        </select>
    </td>
</tr>

<tr class="td-off">
    <td><span class="edit-label">Export Vendor:</span></td>
    <td>
        <select name="vendorID">
        <option value="%">All</option>
<%
            Iterator vendorListIterator = vendorList.iterator();
            ExportClassRecord ecl2;

            while ( vendorListIterator.hasNext() ) {
                ecl2 = ( ExportClassRecord ) vendorListIterator.next();
%>
        <option value="<%= ecl2.getVendorID() %>"><%= ecl2.getVendorName() %></option>
<%
        }
%>
        </select>
    </td>
</tr>

<tr class="td-on">
    <td><span class="edit-label">Class ID:</span></td>
    <td><input type="text" size="10" maxlength = "8"  name="classID"></td>
</tr>

<tr class="td-off">
    <td><span class="edit-label">Category:</span></td>
    <td><input type="text" size="50" maxlength = "50"  name="cciCategory"></td>
</tr>

<tr class="td-on">
    <td><span class="edit-label">Description:</span></td>
    <td><input type="text" size="50" maxlength = "50"  name="cciDescription"></td>
</tr>

<!--
<tr class="td-off">
     <td align="left"><span class="table-text">Recycler Category</span> <span class="table-helptext">(for RSS):</span></td>
     <td align = "left">
         <select name = "recyclerCategory">
<%
    //for (int i=0; i<rcyCatList.size(); i++) {
%>
                 <option><%//= rcyCatList.get(i) %></option>
<%
    //}
%>
         </select>
      </td>
</tr>
-->

<tr>
  <td></td>
  <td align="left"><input type=submit value="Get Listing"></td>
</tr>
</table>

</form>
<%
    }
    else
    {
%>

     <tr>
       <td><span class = "edit-label">Your market is not set up for Export Classes.</span></td>
     </tr>
     <tr>
       <td><span class = "edit-label">&nbsp;</span></td>
     </tr>
     <tr>
       <td><span class = "edit-label">Please contact <a href="mailto:feedsupport@tribuneinteractive.com">feedsupport@tribuneinteractive.com</a> if you have any questions.</span></td>
     </tr>

<% } %>


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
