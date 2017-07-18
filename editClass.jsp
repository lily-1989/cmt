<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.tools.*"%>

<%
  //check to make sure session
  if (session.getAttribute("user") == null)
  {
     response.sendRedirect("index.jsp");
  }
%>


<%
    Vector rcyCatList = (Vector)session.getAttribute("rcyCatList");
    ExportClassRecord record = (ExportClassRecord)session.getAttribute("classRecord");
    List vendorList = (ArrayList) session.getAttribute("vendorNames");
%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Edit Export Class
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
              <td><font class="page-header">Edit Export Class</font></td>
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

       <form action="updateClass" method="post">


	 <table width = "100%" border="0" cellspacing="0" cellpadding="4">
          <tr>
            <th colspan="2" align="left" class="td-title-bg"><span class="table-header">Edit Class</span></th>
          </tr>
          <tr>
            <td class="tdL1" align="left"><span class="edit-label">Feed:</span></td>
	        <td class="tdR1"><input type="hidden" name="feedID" value="<%= record.getFeedID() %>"><span class="edit-text"><!--<%//=record.getMarket()%> - <%//=record.getFeedName()%>--><%=record.getFeedID()%></span></td>
          </tr>
          <tr>
            <th colspan="2">&nbsp</th>
          </tr>
          <tr>
            <td colspan="2"><span class="edit-label">Note: </span><span class="edit-reqtext">Required fields marked in Red</span></td>
          </tr>
          <tr>
            <th colspan="2"><hr></th>
          </tr>
          <tr>
             <td class="tdL"><span class="edit-reqlabel">Export Vendor:</span></td>
             <td class="tdR">
                <select name="vendorID">
            <%
            Iterator vendorListIterator = vendorList.iterator();
            ExportClassRecord ecl2;

            while ( vendorListIterator.hasNext() ) {
                ecl2 = ( ExportClassRecord ) vendorListIterator.next();
            %>
               <option <%= record.getVendorID().equals(ecl2.getVendorID()) ? "selected" : ""%> value="<%= ecl2.getVendorID() %>"><%= ecl2.getVendorName() %></option>
            <% } %>
                </select>
            </td>
          </tr>
          <tr>
             <td align="left"><span class="edit-reqlabel">Class ID:</span></td>
	         <td align = "left"><input type="text" name = "classID" value = "<%=record.getClassID()%>" size="10" maxlength = "8"/></td>
          </tr>
          <tr>
             <td class="tdL" align="left"><span class="edit-reqlabel">Category:</span></td>
	         <td class="tdR"><input type="text" name = "cciCategory" value = "<%=record.getCciCategory()%>" size="50" maxlength = "50"/></td>
          </tr>
          <tr>
             <td class="tdL" align="left"><span class="edit-reqlabel">Description:</span></td>
	         <td class="tdR"><input type="text" name = "cciDescription" value = "<%=record.getCciDescription()%>" size="50" maxlength = "50"/></td>
          </tr>
          <tr>
             <td class="tdL" align="left"><span class="edit-label">Recycler Category:</span><br><span class="table-helptext">(for RSS)</span></td>
             <td class="tdR">
                 <select name = "recyclerCategory">
                 <%  for (int i=0; i<rcyCatList.size(); i++) {  %>
                 <option <%= record.getRecyclerCategory().equals(rcyCatList.get(i)) ? "selected" : "" %>><%= rcyCatList.get(i) %></option>
                 <% }   %>
                </select>
              </td>
          </tr>
          <tr>
             <td>&nbsp;</td>
             <td><input type="submit" name="update" value="Update"/> <input type="reset"/></td>
          </tr>
       </table>
       </form>

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
